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

package com.obast.charer.common.model;

import cn.hutool.core.util.IdUtil;
import com.obast.charer.common.api.Response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 动作执行结果
 *
 * @author sjg
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActionResult<T> {

    /**
     * 状态码，0:成功，x:其它错误码
     */
    private int code;

    /**
     * 失败原因
     */
    private String reason;


    private T data;

    public ActionResult(int code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    public ActionResult(int code) {
        this.code = code;
    }

    public ActionResult(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public static <T> ActionResult<?> success() {
        return new ActionResult<>(0,"成功");
    }

    public static <T> ActionResult<?> success(T data) {
        return new ActionResult<>(0,"成功", data);
    }

    public static ActionResult<?> fail(String message) {
        return new ActionResult<>(-1,message);
    }
}
