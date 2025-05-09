/*
 *
 *  * | Licensed 未经许可不能去掉「OPENIITA」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com
 *  * +----------------------------------------------------------------------
 *
 *  Copyright [2024] [OPENIITA]
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * /
 */

package com.obast.charer.s3mock;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

import com.obast.charer.s3mock.dto.ErrorResponse;
import com.obast.charer.s3mock.util.AwsHttpHeaders;
import com.obast.charer.s3mock.store.KmsKeyStore;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A Filter that validates KMS keys of incoming Requests. If Keys can not be found in Keystore the
 * Request will be denied immediately.
 */
class KmsValidationFilter extends OncePerRequestFilter {

  private static final Logger LOG = LoggerFactory.getLogger(KmsValidationFilter.class);

  private static final String AWS_KMS = "aws:kms";

  private final KmsKeyStore keystore;

  private final MappingJackson2XmlHttpMessageConverter messageConverter;

  /**
   * Constructs a new {@link KmsValidationFilter}.
   *
   * @param keystore Keystore for validation of KMS Keys
   */
  KmsValidationFilter(KmsKeyStore keystore,
      MappingJackson2XmlHttpMessageConverter messageConverter) {
    this.keystore = keystore;
    this.messageConverter = messageConverter;
  }

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {
    try {
      LOG.debug("Checking KMS key, if present.");
      var encryptionTypeHeader = request.getHeader(AwsHttpHeaders.X_AMZ_SERVER_SIDE_ENCRYPTION);
      var encryptionKeyId = request.getHeader(AwsHttpHeaders.X_AMZ_SERVER_SIDE_ENCRYPTION_AWS_KMS_KEY_ID);

      if (AWS_KMS.equals(encryptionTypeHeader)
          && !isBlank(encryptionKeyId)
          && !keystore.validateKeyId(encryptionKeyId)) {
        LOG.info("Received invalid KMS key ID {}. Sending error response.", encryptionKeyId);

        request.getInputStream().close();

        response.setStatus(BAD_REQUEST.value());
        response.setHeader(CONTENT_TYPE, APPLICATION_XML_VALUE);

        var errorResponse = new ErrorResponse(
            "KMS.NotFoundException",
            "Key ID " + encryptionKeyId + " does not exist!",
            null,
            null
        );

        messageConverter.getObjectMapper().writeValue(response.getOutputStream(), errorResponse);

        response.flushBuffer();
      } else if (AWS_KMS.equals(encryptionTypeHeader)
          && !isBlank(encryptionKeyId)
          && keystore.validateKeyId(encryptionKeyId)) {
        LOG.info("Received valid KMS key ID {}.", encryptionKeyId);
        filterChain.doFilter(request, response);
      } else {
        filterChain.doFilter(request, response);
      }
    } finally {
      LOG.debug("Finished checking KMS key.");
    }
  }
}
