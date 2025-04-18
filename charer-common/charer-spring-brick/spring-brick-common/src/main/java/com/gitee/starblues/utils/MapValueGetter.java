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

package com.gitee.starblues.utils;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

/**
 * map 值获取者工具类
 *
 * @author starBlues
 * @since 3.0.0
 * @version 3.0.3
 */
public class MapValueGetter {

    private final Map<String, Object> source;

    public MapValueGetter(Map<String, Object> source) {
        if(source == null){
            this.source = Collections.emptyMap();
        } else {
            this.source = source;
        }
    }

    public Map<String, Object> getSource(){
        return source;
    }

    /**
     * 获取object
     * @param key map key
     * @return value
     */
    public Object getObject(String key) {
        return source.get(key);
    }


    /**
     * 获取 String 类型值
     * @param key map key
     * @return String value
     */
    public String getString(String key) {
        return getValue(key, ObjectValueUtils::getString);
    }

    /**
     * 获取 Integer 类型值
     * @param key map key
     * @return Integer value
     */
    public Integer getInteger(String key) {
        return getValue(key, ObjectValueUtils::getInteger);
    }

    /**
     * 获取 Long 类型值
     * @param key map key
     * @return Long value
     */
    public Long getLong(String key) {
        return getValue(key, ObjectValueUtils::getLong);
    }

    /**
     * 获取 Double 类型值
     * @param key map key
     * @return Double value
     */
    public Double getDouble(String key) {
        return getValue(key, ObjectValueUtils::getDouble);
    }

    /**
     * 获取 Float 类型值
     * @param key map key
     * @return Float value
     */
    public Float getFloat(String key) {
        return getValue(key, ObjectValueUtils::getFloat);
    }

    /**
     * 获取 Boolean 类型值
     * @param key map key
     * @return Boolean value
     */
    public Boolean getBoolean(String key) {
        return getValue(key, ObjectValueUtils::getBoolean);
    }

    /**
     * 获取值并根据自定义实现转换类型
     * @param key map key
     * @param function 自定义类型转换
     * @param <T> 转换后的类型泛型
     * @return 转换后的类型值
     */
    private <T> T getValue(String key, Function<Object, T> function){
        Object value = getObject(key);
        if(value == null){
            return null;
        }
        return function.apply(value);
    }


}
