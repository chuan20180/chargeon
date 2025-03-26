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
 * 返回状态码
 *
 * @author Lion Li
 */
public interface HttpStatus {
    /**
     * 操作成功
     */
    int SUCCESS = 200;

    /**
     * 对象创建成功
     */
    int CREATED = 201;

    /**
     * 请求已经被接受
     */
    int ACCEPTED = 202;

    /**
     * 操作已经执行成功，但是没有返回数据
     */
    int NO_CONTENT = 204;

    /**
     * 资源已被移除
     */
    int MOVED_PERM = 301;

    /**
     * 重定向
     */
    int SEE_OTHER = 303;

    /**
     * 资源没有被修改
     */
    int NOT_MODIFIED = 304;

    /**
     * 参数列表错误（缺少，格式不匹配）
     */
    int BAD_REQUEST = 400;

    /**
     * 未授权
     */
    int UNAUTHORIZED = 401;

    /**
     * 访问受限，授权过期
     */
    int FORBIDDEN = 403;

    /**
     * 资源，服务未找到
     */
    int NOT_FOUND = 404;

    /**
     * 不允许的http方法
     */
    int BAD_METHOD = 405;

    /**
     * 资源冲突，或者资源被锁
     */
    int CONFLICT = 409;

    /**
     * 不支持的数据，媒体类型
     */
    int UNSUPPORTED_TYPE = 415;

    /**
     * 系统内部错误
     */
    int ERROR = 500;

    /**
     * 接口未实现
     */
    int NOT_IMPLEMENTED = 501;

    /**
     * 系统警告消息
     */
    int WARN = 601;
}
