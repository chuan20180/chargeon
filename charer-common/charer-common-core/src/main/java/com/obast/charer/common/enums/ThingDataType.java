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

package com.obast.charer.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 物模型数据类型
 *
 * @author sjg
 */
@Getter
@AllArgsConstructor
public enum ThingDataType {
    /**
     * int 整数
     */
    INT("int32", "整数"),
    /**
     * 小数
     */
    FLOAT("float", "小数"),
    /**
     * 布尔
     */
    BOOL("bool", "布尔"),
    /**
     * 枚举
     */
    ENUM("enum", "枚举"),
    /**
     * 文本字符
     */
    TEXT("text", "文本字符"),
    /**
     * 日期
     */
    DATE("date", "时间戳"),
    ;

    private final String code;
    private final String info;

}
