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

package com.obast.charer.common.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 视图异常
 *
 * @author sjg
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ViewException extends RuntimeException {

    public static final int CODE_FAILED = 500;
    public static final int CODE_WARN = 601;

    private int code;
    private String message;
    private Object data;

    public ViewException() {
    }

    public ViewException(String message) {
        super(message);
        this.message = message;
    }

    public ViewException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ViewException(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
