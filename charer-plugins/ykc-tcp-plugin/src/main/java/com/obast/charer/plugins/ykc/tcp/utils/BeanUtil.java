package com.obast.charer.plugins.ykc.tcp.utils;

import org.apache.commons.beanutils.BeanUtils;

import java.util.Map;

public class BeanUtil {
    public static Map<String, ?> beanToMap(Object bean) {
        if (null == bean)
            return null;
        try {
            Map<String, ?> map = BeanUtils.describe(bean);
            map.remove("class");
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T mapToBean(Class<?> clazz, Map map) {
        try {
            T newBeanInstance = (T) clazz.newInstance();
            BeanUtils.populate(newBeanInstance, map);
            return newBeanInstance;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}