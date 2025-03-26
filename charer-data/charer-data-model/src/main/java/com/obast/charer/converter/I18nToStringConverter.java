package com.obast.charer.converter;

import com.obast.charer.common.i18n.I18nField;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Locale;

/**
 * @ Author：chuan
 * @ Date：2024-10-18-16:46
 * @ Version：1.0
 * @ Description：I18nToStringConverter
 */
@Component
public class I18nToStringConverter  implements Converter<I18nField> {
    public String i18nToString(I18nField i18n) {
        if (i18n == null) {
            return null;
        }
        Locale locale = LocaleContextHolder.getLocale();
        String requestLang = String.format("%s_%s", locale.getLanguage(), locale.getCountry());
        try {
            Field field = i18n.getClass().getDeclaredField(requestLang);
            field.setAccessible(true);
            return (String) field.get(i18n);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Class<I18nField> supportJavaTypeKey() {
        return I18nField.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public WriteCellData<?> convertToExcelData(I18nField i18n, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {

        Locale locale = LocaleContextHolder.getLocale();
        String requestLang = String.format("%s_%s", locale.getLanguage(), locale.getCountry());
        try {
            Field field = i18n.getClass().getDeclaredField(requestLang);
            field.setAccessible(true);
            return new WriteCellData<String>((String)field.get(i18n));

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}
