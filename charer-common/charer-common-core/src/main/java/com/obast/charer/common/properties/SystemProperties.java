package com.obast.charer.common.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 自动集成的配置
 *
 * @author starBlues
 * @since 3.0.0
 * @version 3.1.2
 */

@Component
@Slf4j
@ConfigurationProperties(prefix = "charer.system")
@Data
public class SystemProperties {

    private String baseUrl;

    private SystemI18nProperties i18n;

    @Component
    @Data
    public static class SystemI18nProperties {

        private String language;

        private Boolean multiple;

        private I18nLocaleProperties[] locales;

    }


}
