package com.obast.charer.system.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.obast.charer.system.config.interceptor.InvokeTimeInterceptor;
import com.obast.charer.system.config.interceptor.LocaleInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置类
 */
@Configuration
@EnableScheduling
@Slf4j
public class SystemConfig implements WebMvcConfigurer {

    private static final String requestPath = "/admin/**";
    /**
     * 添加拦截器到Spring MVC的拦截器链中
     *
     * @param registry 拦截器注册器，用于添加和配置拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {


        // 创建一个自定义的国际化拦截器实例
        registry.addInterceptor(new LocaleInterceptor())
                .order(0)
                .addPathPatterns(requestPath);

        // 全局访问性能拦截
        registry.addInterceptor(new InvokeTimeInterceptor())
                .order(1)
                .addPathPatterns(requestPath);


        List<String> swaggerUrls = List.of(
                "/doc.html",
                "/favicon.ico",
                "/webjars/**",
                "/resources/**",
                "/swagger-resources/**",
                "/swagger-ui.html/**"
        );

        List<String> pluginStatics = List.of("/static-plugin/**");

        List<String> loginUrls = List.of(
                "/admin/code",
                "/admin/permission/tenant/list",
                "/admin/permission/login",
                "/admin/permission/logout",
                "/admin/iot-oss/**",
                "/admin/app/**"
        );

        List<String> otherUrls = List.of(
                "/iot-oss/**"
        );

        List<String> excludeUrls = new ArrayList<>();
        excludeUrls.addAll(loginUrls);
        excludeUrls.addAll(pluginStatics);
        excludeUrls.addAll(swaggerUrls);
        excludeUrls.addAll(otherUrls);

        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
                .addPathPatterns(requestPath)
                .excludePathPatterns(excludeUrls);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }

    /**
     * 跨域配置
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // 设置访问源地址
        config.addAllowedOriginPattern("*");
        // 设置访问源请求头
        config.addAllowedHeader("*");
        // 设置访问源请求方法
        config.addAllowedMethod("*");
        // 有效期 1800秒
        config.setMaxAge(1800L);
        // 添加映射路径，拦截一切请求
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration(requestPath, config);
        // 返回新的CorsFilter
        return new CorsFilter(source);
    }


    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10); // 设置线程池大小
        scheduler.setThreadNamePrefix("spring-scheduled-"); // 设置线程名前缀
        scheduler.initialize();
        return scheduler;
    }

    @Bean
    MappingJackson2XmlHttpMessageConverter getMappingJackson2XmlHttpMessageConverter() {
        MappingJackson2XmlHttpMessageConverter mappingJackson2XmlHttpMessageConverter = new MappingJackson2XmlHttpMessageConverter();
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        mappingJackson2XmlHttpMessageConverter.setObjectMapper(xmlMapper);
        return mappingJackson2XmlHttpMessageConverter;
    }

}