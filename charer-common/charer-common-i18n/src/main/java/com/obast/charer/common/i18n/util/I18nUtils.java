package com.obast.charer.common.i18n.util;

/**
 * @ Author：chuan
 * @ Date：2024-09-27-17:22
 * @ Version：1.0
 * @ Description：MessageUtils
 */

import com.obast.charer.common.i18n.I18nField;
import com.obast.charer.common.properties.CharerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * 获取i18n资源文件
 *
 * @author cccl
 */
@Component
public class I18nUtils {
    private static CharerProperties charerProperties;

    @Autowired
    public I18nUtils(CharerProperties charerProperties) {
        I18nUtils.charerProperties = charerProperties;
    }

    public static String convert(I18nField i18nString) {

        try {
            Field field = i18nString.getClass().getDeclaredField(charerProperties.getSystem().getI18n().getLanguage());
            field.setAccessible(true);
            return (String) field.get(i18nString);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }
}