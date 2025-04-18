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

import com.obast.charer.s3mock.store.BucketMetadata;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.file.Path;

/**
 * <a href="https://docs.aws.amazon.com/AmazonS3/latest/API/API_Bucket.html">API Reference</a>.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("Bucket")
public class Bucket {
    @JsonIgnore
    private Path path;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("CreationDate")
    private String creationDate;

    public static Bucket from(BucketMetadata bucketMetadata) {
        if (bucketMetadata == null) {
            return null;
        }
        return new Bucket(bucketMetadata.getPath(),
                bucketMetadata.getName(),
                bucketMetadata.getCreationDate());
    }
}
