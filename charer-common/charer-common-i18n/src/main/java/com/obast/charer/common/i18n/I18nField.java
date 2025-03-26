package com.obast.charer.common.i18n;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * author: 石恒
 * date: 2023-05-11 16:30
 * description:
 **/
@Data
@Slf4j
public class I18nField implements Serializable {
    private String zh_CN;
    private String en_US;
    private String ru_RU;

    public String get(String lang) {
        try {
            Class<?> clazz = this.getClass();
            Field field = clazz.getDeclaredField(lang);
            field.setAccessible(true);
            return (String) field.get(this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }
}

