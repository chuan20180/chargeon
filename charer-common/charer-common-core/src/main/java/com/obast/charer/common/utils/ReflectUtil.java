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
package com.obast.charer.common.utils;


import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReflectUtil {

    @SneakyThrows
    public static <T> T copyNoNulls(T from, T to, String... fields) {
        List<String> fieldList = Arrays.asList(fields);

        Map<String, Object> map = new HashMap<>();
        new BeanMap(from).forEach((key, value) -> {
            if (value == null) {
                return;
            }
            String field = key.toString();
            if (fields.length == 0 || fieldList.contains(field)) {
                map.put(field, value);
            }
        });
        BeanUtils.populate(to, map);
        return to;
    }

    public static Map<String, ?> toMap(Object bean) {
        Map<String, Object> map = new HashMap<>();
        new BeanMap(bean).forEach((key, value) -> {
            if (key.equals("class")) {
                return;
            }
            String field = key.toString();
            map.put(field, value);
        });
        return map;
    }

}
