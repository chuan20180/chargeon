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

import static com.obast.charer.s3mock.util.EtagUtil.normalizeEtag;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * <a href="https://docs.aws.amazon.com/AmazonS3/latest/API/API_CopyPartResult.html">API Reference</a>.
 */
@Data
@AllArgsConstructor
@JsonRootName("CopyPartResult")
public class CopyPartResult {
    @JsonProperty("LastModified")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Date lastModified;
    @JsonProperty("ETag")
    private String etag;

    public CopyPartResult() {
        etag = normalizeEtag(etag);
    }

    public static CopyPartResult from(final Date date, final String etag) {
        return new CopyPartResult(date, etag);
    }
}
