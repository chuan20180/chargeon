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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.Data;

import java.util.List;

/**
 * <a href="https://docs.aws.amazon.com/AmazonS3/latest/API/API_LifecycleRule.html">API Reference</a>.
 */
@Data
@JsonRootName("LifecycleRule")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class LifecycleRule {
    @JsonProperty("AbortIncompleteMultipartUpload")
    private AbortIncompleteMultipartUpload abortIncompleteMultipartUpload;
    @JsonProperty("Expiration")
    private LifecycleExpiration expiration;
    @JsonProperty("Filter")
    private LifecycleRuleFilter filter;
    @JsonProperty("ID")
    private String id;
    @JsonProperty("NoncurrentVersionExpiration")
    @JacksonXmlElementWrapper(useWrapping = false)
    private NoncurrentVersionExpiration noncurrentVersionExpiration;
    @JsonProperty("NoncurrentVersionTransition")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<NoncurrentVersionTransition> noncurrentVersionTransitions;
    @JsonProperty("Status")
    private Status status;
    @JsonProperty("Transition")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Transition> transitions;

    /**
     * <a href="https://docs.aws.amazon.com/AmazonS3/latest/API/API_LifecycleRule.html">API Reference</a>.
     */
    public enum Status {
        ENABLED("Enabled"),
        DISABLED("Disabled");

        private final String value;

        @JsonCreator
        Status(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return value;
        }
    }
}
