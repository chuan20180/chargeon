package com.obast.charer.plugins.vzicloud.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "vzi")
public class VziConfig {

    private String baseUrl;

    private String accesskeyId;

    private String accesskeySecret;
}
