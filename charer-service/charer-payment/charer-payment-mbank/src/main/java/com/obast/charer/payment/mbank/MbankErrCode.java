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

package com.obast.charer.payment.mbank;

import com.obast.charer.common.enums.IEnum;

/**
 * @author ：tfd
 * 异常枚举类
 */
public enum MbankErrCode implements IEnum {
    /**
     * 系统通用异常段
     */

    // 'OP' parameter Invalid

    OP_NULL(1000, "Параметр OP пуст"),
    OP_INVALID(1001, "Параметр «OP» недействителен"),
    PARAM1_NULL(1002, "Параметр «Param1» имеет значение null"),
    PARAM1_BLANK(1003, "Параметр «Param1» пуст."),


    SUM_NULL(1005, "Сумма равна нулю"),
    SUM_BLANK(1006, "Сумма пуста"),
    SUM_EXCEPTION(1008, "Сумма ненормальная"),

    RECORD_NOT_EXIST(1004, "Лицевой счет не найден"),
    RECORD_STATE_EXCEPTION(1007, "Статус транзакции завершен"),

    REQUISITE2_BLANK(1008, "Параметр Requisite2 пуст"),

    PAYMENT_SUCCESSFUL(2000, "Платеж успешно завершен"),
    CANCEL_SUCCESSFUL(2001, "Отмена прошла успешно"),

    ;

    private final int code;
    private final String message;

    MbankErrCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getKey() {
        return this.code;
    }

    @Override
    public String getValue() {
        return this.message;
    }
}
