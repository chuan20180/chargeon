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

package com.obast.charer.common.oss.enumd;

import com.obast.charer.common.exception.BizException;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 桶访问策略配置
 *
 * @author 陈賝
 */
@Getter
@AllArgsConstructor
public enum AccessPolicyType {

    /**
     * private
     */
    PRIVATE("0", CannedAccessControlList.Private, PolicyType.WRITE),

    /**
     * public
     */
    PUBLIC("1", CannedAccessControlList.PublicRead, PolicyType.READ),

    /**
     * custom
     */
    CUSTOM("2",CannedAccessControlList.PublicRead, PolicyType.READ);

    /**
     * 桶 权限类型
     */
    private final String type;

    /**
     * 文件对象 权限类型
     */
    private final CannedAccessControlList acl;

    /**
     * 桶策略类型
     */
    private final PolicyType policyType;

    public static AccessPolicyType getByType(String type) {
        for (AccessPolicyType value : values()) {
            if (value.getType().equals(type)) {
                return value;
            }
        }
        throw new BizException("'type' not found By " + type);
    }

}
