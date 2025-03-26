package com.obast.charer.converter;

import com.obast.charer.common.utils.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-10-18-16:46
 * @ Version：1.0
 * @ Description：I18nToStringConverter
 */
@Component
public class StringToListConverter {
    public List<String> stringToList(String source) {
        if (source == null) {
            return null;
        }

        return StringUtils.splitList(source);

    }
}
