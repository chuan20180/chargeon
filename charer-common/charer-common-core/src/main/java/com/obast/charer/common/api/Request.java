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

package com.obast.charer.common.api;

import cn.hutool.core.util.IdUtil;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Data;


import java.io.Serializable;

/**
 * @author: Longjun.Tu
 * @description:
 * @date:created in 2023/5/10 23:14
 * @modificed by:
 */
@Data
public class Request<T> extends RequestEmpty implements Serializable {

    @Valid
    @NotNull
    private T data;

    public static <T> Request<T> of(T data) {
        Request<T> request = new Request<>();
        request.setData(data);
        request.setRequestId(IdUtil.simpleUUID());
        return request;
    }
}
