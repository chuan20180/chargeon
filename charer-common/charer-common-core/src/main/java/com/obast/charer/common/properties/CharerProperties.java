package com.obast.charer.common.properties;

import lombok.Data;
import lombok.Getter;
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
@ConfigurationProperties(prefix = "charer")
@Data
public class CharerProperties {

    private TenantProperties tenant;

    private AgentProperties agent;

    private DealerProperties dealer;

    private ProfitProperties profit;

    private SystemProperties system;

    private OpenapiProperties openapi;

    @Getter
    @Data
    @ConfigurationProperties(prefix = "charer.tenant")
    @Component
    public static class TenantProperties {
        /**
         * 是否启用多租户
         */
        private Boolean enable;
    }

    @Data
    @ConfigurationProperties(prefix = "charer.agent")
    @Component
    public static class AgentProperties {
        /**
         * 是否启用代理商
         */
        private Boolean enable;
    }

    @Data
    @ConfigurationProperties(prefix = "charer.dealer")
    @Component
    public static class DealerProperties {
        /**
         * 是否启用分销商
         */
        private Boolean enable;

    }

    @Component
    @Slf4j
    @ConfigurationProperties(prefix = "charer.profit")
    @Data
    public static class ProfitProperties {
        /**
         * 是否启用分润
         */
        private Boolean enabled = false;
    }


}
