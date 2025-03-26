package com.obast.charer.common.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 自动集成的配置
 */

@Component
@Slf4j
@ConfigurationProperties(prefix = "charer.openapi")
@Data
public class OpenapiProperties {
    private String baseUrl;
    private OpenapiI18nProperties i18n;

    @Data
    public static class OpenapiI18nProperties {

        private String language;

        private Boolean multiple;

        private I18nLocaleProperties[] locales;

    }
}
