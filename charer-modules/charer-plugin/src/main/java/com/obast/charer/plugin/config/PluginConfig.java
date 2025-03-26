package com.obast.charer.plugin.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClientsProperties;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置类
 */
@Configuration
@Slf4j
public class PluginConfig implements WebMvcConfigurer {

    /**
     * 解决Spring Brick FeignClient 访问报错
     */
    @Bean
    @ConditionalOnMissingBean
    public LoadBalancerClientFactory loadBalancerClientFactory(LoadBalancerClientsProperties properties) {
        return new LoadBalancerClientFactory(properties) {
            @Override
            protected AnnotationConfigApplicationContext createContext(String name) {
                // FIXME: temporary switch classloader to use the correct one when creating the context
                ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();
                Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
                AnnotationConfigApplicationContext context = super.createContext(name);
                Thread.currentThread().setContextClassLoader(originalClassLoader);
                return context;
            }
        };
    }



}