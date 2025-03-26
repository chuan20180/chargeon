package com.obast.charer.openapi.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import com.obast.charer.openapi.config.auth.AuthInterceptor;
import com.obast.charer.openapi.config.auth.AuthUtil;
import com.obast.charer.openapi.config.invoke.InvokeTimeInterceptor;
import com.obast.charer.openapi.config.request.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * OpenApi配置
 */
@Configuration
public class OpenApiConfig implements WebMvcConfigurer {

    String requestPath = "/openapi/v1/**";

    List<String> authExcludePath = List.of(
            "/openapi/v1/app/**",
            "/openapi/v1/wechat/notify"
    );

    List<String> excludePath = List.of(
            "/openapi/v1/wechat/notify"
    );

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 全局请求信息拦截
        registry.addInterceptor(new RequestInterceptor())
                .order(0)
                .addPathPatterns(requestPath)
                .excludePathPatterns(excludePath);

        // API接口认证拦截
        registry.addInterceptor(new AuthInterceptor(handle -> AuthUtil.checkAuth()))
                .order(1)
                .addPathPatterns(requestPath)
                .excludePathPatterns(excludePath);

        // 全局访问性能拦截
        registry.addInterceptor(new InvokeTimeInterceptor())
                .order(2)
                .addPathPatterns(requestPath);

        // 用户认证拦截
        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
                .order(3)
                .addPathPatterns(requestPath)
                .excludePathPatterns(authExcludePath);
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
}
