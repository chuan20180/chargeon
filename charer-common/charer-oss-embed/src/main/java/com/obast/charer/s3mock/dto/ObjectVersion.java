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

import com.obast.charer.s3mock.store.S3ObjectMetadata;
import com.obast.charer.s3mock.util.EtagUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <a href="https://docs.aws.amazon.com/AmazonS3/latest/API/API_ObjectVersion.html">API Reference</a>.
 */
@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ObjectVersion {
    @JsonProperty("Key")
    private String key;
    @JsonProperty("LastModified")
    private String lastModified;
    @JsonProperty("ETag")
    private String etag;
    @JsonProperty("Size")
    private String size;
    @JsonProperty("StorageClass")
    private StorageClass storageClass;
    @JsonProperty("Owner")
    private Owner owner;
    @JsonProperty("ChecksumAlgorithm")
    private ChecksumAlgorithm checksumAlgorithm;
    @JsonProperty("IsLatest")
    private Boolean isLatest;
    @JsonProperty("VersionId")
    private String versionId;


    public ObjectVersion() {
        etag = EtagUtil.normalizeEtag(etag);
    }

    public static ObjectVersion from(S3ObjectMetadata s3ObjectMetadata) {
        return new ObjectVersion(s3ObjectMetadata.getKey(),
                s3ObjectMetadata.getModificationDate(),
                s3ObjectMetadata.getEtag(),
                s3ObjectMetadata.getSize(),
                s3ObjectMetadata.getStorageClass(),
                s3ObjectMetadata.getOwner(),
                s3ObjectMetadata.getChecksumAlgorithm(),
                true,
                "staticVersion");
    }

    public static ObjectVersion from(S3Object s3Object) {
        return new ObjectVersion(s3Object.getKey(),
                s3Object.getLastModified(),
                s3Object.getEtag(),
                s3Object.getSize(),
                s3Object.getStorageClass(),
                s3Object.getOwner(),
                s3Object.getChecksumAlgorithm(),
                true,
                "staticVersion");
    }
}
