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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * Container for elements related to a particular multipart upload.
 * <a href="https://docs.aws.amazon.com/AmazonS3/latest/API/API_MultipartUpload.html">API Reference</a>
 */
@Data
@AllArgsConstructor
public class MultipartUpload {
    @JsonProperty("Key")
    private String key;
    @JsonProperty("UploadId")
    private String uploadId;
    @JsonProperty("Owner")
    private Owner owner;
    @JsonProperty("Initiator")
    private Owner initiator;
    @JsonProperty("StorageClass")
    private StorageClass storageClass;
    @JsonProperty("Initiated")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Date initiated;

}
