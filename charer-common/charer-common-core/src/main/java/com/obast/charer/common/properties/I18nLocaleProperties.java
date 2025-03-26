package com.obast.charer.common.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 自动集成的配置
 *
 * @author starBlues
 * @since 3.0.0
 * @version 3.1.2
 */


@Slf4j
@Data
@Component

public class I18nLocaleProperties {
    private String locale;
    private String timezone;
    private String dateFormat;
}