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

package com.obast.charer.s3mock.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.obast.charer.s3mock.util.EtagUtil;
import lombok.Data;

/**
 * <a href="https://docs.aws.amazon.com/AmazonS3/latest/API/API_CompletedPart.html">API Reference</a>.
 */
@Data
@JsonRootName("CompletedPart")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CompletedPart {
    @JsonProperty("PartNumber")
    private Integer partNumber;
    @JsonProperty("ETag")
    private String etag;
    @JsonProperty("ChecksumCRC32")
    private String checksumCRC32;
    @JsonProperty("ChecksumCRC32C")
    private String checksumCRC32C;
    @JsonProperty("ChecksumSHA1")
    private String checksumSHA1;
    @JsonProperty("ChecksumSHA256")
    private String checksumSHA256;

    public CompletedPart() {
        etag = EtagUtil.normalizeEtag(etag);
    }
}
