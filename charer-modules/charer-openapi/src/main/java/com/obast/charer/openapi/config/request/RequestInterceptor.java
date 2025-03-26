
package com.obast.charer.openapi.config.request;

import com.obast.charer.common.constant.AppConstants;
import com.obast.charer.common.constant.LocaleConstants;
import com.obast.charer.common.constant.TenantConstants;

import com.obast.charer.common.enums.PlatformTypeEnum;
import com.obast.charer.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.Locale;

/**
 * @ Author：chuan
 * @ Date：2024-09-27-17:19
 * @ Version：1.0
 * @ Description：请求拦截器：用于从HTTP请求头中获取参数。
 */

@Slf4j
public class RequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {

        //获取租户信息
        String tenantIdKey = "x-tenant-id";
        String tenantId = request.getHeader(tenantIdKey);
        if (tenantId == null) {
            tenantId = request.getParameter(tenantIdKey);
        }
        if(tenantId == null) {
            tenantId = TenantConstants.DEFAULT_TENANT_ID;
        }

        // 获取语言信息
        String language = LocaleConstants.DEFAULT_LANGUAGE;
        ServletContext context = request.getServletContext();
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
        if(ctx != null) {
            Environment environment = ctx.getBean(Environment.class);
            String defaultLanguage = environment.getProperty("charer.openapi.i18n.language");
            if(StringUtils.isNoneBlank(defaultLanguage)) {
                language= defaultLanguage;
            }
        }

        /*
        String languageKey = "x-language";
        String language = request.getHeader(languageKey);
        if(language == null) {
            language = request.getParameter(languageKey);
        }
        if(language == null) {
            language = defaultLanguage;
        }
        */

        //分隔语言信息
        assert language != null;
        String[] languageParts = language.split("_");
        Locale locale = new Locale(languageParts[0], languageParts[1]);
        LocaleContextHolder.setLocale(locale);

        String appIdKey = "x-api-id";
        String appId = request.getHeader(appIdKey);
        if (appId == null) {
            appId = request.getParameter(appIdKey);
        }

        if (appId == null) {
            appId = AppConstants.DEFAULT_APP_ID;
        }

        String deviceKey = "x-device";
        String device = request.getHeader(deviceKey);
        if (device == null) {
            device = request.getParameter(deviceKey);
        }

        String osKey = "x-os";
        String os = request.getHeader(osKey);
        if (os == null) {
            os = request.getParameter(os);
        }

        PlatformTypeEnum platformTypeEnum = null;


        String platformKey = "x-platform";
        String platform = request.getHeader(platformKey);
        if (platform == null) {
            platform = request.getParameter(platformKey);
        }

        if(platform != null) {
            try {
                platformTypeEnum = PlatformTypeEnum.valueOf(platform);
            } catch (Exception e) {
                log.debug("解析平台类型错误, platform={}", platform);
            }
        }

        String versionKey = "x-version";
        String version = request.getHeader(versionKey);
        if (version == null) {
            version = request.getParameter(versionKey);
        }

        RequestLocaleHolder.setLocale(new RequestLocale(appId, tenantId, device, os, platformTypeEnum, version, language));
        //log.debug("拦截到的信息：{}", RequestLocaleHolder.getLocale());

        return true;
    }
}