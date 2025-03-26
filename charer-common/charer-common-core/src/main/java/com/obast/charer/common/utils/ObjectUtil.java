package com.obast.charer.common.utils;

import org.springframework.util.ReflectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @ Author：chuan
 * @ Date：2024-12-20-14:46
 * @ Version：1.0
 * @ Description：
 */
public class ObjectUtil {

    public static Map<String, Object> bean2Map(Object object) {
        Map<String, Object> map = new HashMap<>();
        ReflectionUtils.doWithFields(object.getClass(), field -> {
            field.setAccessible(true);
            Object value = ReflectionUtils.getField(field, object);
            if (value != null) {
                map.put(field.getName(), value);
            }
        });
        return map;
    }
}
