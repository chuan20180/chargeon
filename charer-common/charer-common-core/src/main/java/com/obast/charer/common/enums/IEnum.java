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

public interface IEnum {
    /**
     * 获取枚举的key
     */
    Integer getKey();

    /**
     * 获取枚举的下标
     */
    String getValue();

    /**
     * 将参数反序列化为枚举
     *
     * @param param 当前值
     * @param clazz 枚举类型
     */
    static <T extends Enum<T> & IEnum> T parse(Integer param, Class<T> clazz) {
        if (param == null || clazz == null) {
            return null;
        }
        T[] enums = clazz.getEnumConstants();
        for (T t : enums) {
            Integer key = t.getKey();
            if (key.equals(param)) {
                return t;
            }
        }
        return null;
    }
}
