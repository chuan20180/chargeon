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

package com.obast.charer.common.constant;

/**
 * 租户常量信息
 *
 * @author Lion Li
 */
public interface TenantConstants {


    /**
     * 超级管理员ID
     */
    String SUPER_ADMIN_ID = "1";

    /**
     * 超级管理员角色 roleKey
     */
    String SUPER_ADMIN_ROLE_KEY = "superadmin";

    /**
     * 租户管理员角色 roleKey
     */
    String TENANT_ADMIN_ROLE_KEY = "admin";

    /**
     * 租户管理员角色名称
     */
    String TENANT_ADMIN_ROLE_NAME = "管理员";

    /**
     * 默认租户ID
     */
    String DEFAULT_TENANT_ID = "10000";



    /**
     * 租户管理员角色 roleKey
     */
    String AGENT_ADMIN_ROLE_KEY = "agent";

    /**
     * 租户管理员角色名称
     */
    String AGENT_ADMIN_ROLE_NAME = "代理管理员";


    /**
     * 租户管理员角色 roleKey
     */
    String DEALER_ADMIN_ROLE_KEY = "dealer";

    /**
     * 租户管理员角色名称
     */
    String DEALER_ADMIN_ROLE_NAME = "合作商管理员";

}
