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

package com.obast.charer.s3mock.store;

import com.obast.charer.s3mock.dto.ChecksumAlgorithm;
import com.obast.charer.s3mock.dto.MultipartUpload;
import com.obast.charer.s3mock.dto.StorageClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Encapsulates {@link MultipartUpload} and corresponding {@code contentType}.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
class MultipartUploadInfo {
    private MultipartUpload upload;
    private String contentType;
    private Map<String, String> userMetadata;
    private Map<String, String> storeHeaders;
    private Map<String, String> encryptionHeaders;
    private String bucket;
    private StorageClass storageClass;
    private String checksum;
    private ChecksumAlgorithm checksumAlgorithm;

}
