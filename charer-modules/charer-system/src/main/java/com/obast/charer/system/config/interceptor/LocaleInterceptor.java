package com.obast.charer.system.config.interceptor;

/**
 * @ Author：chuan
 * @ Date：2024-09-27-17:19
 * @ Version：1.0
 * @ Description：LocaleInterceptor
 */

import com.obast.charer.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Slf4j
@AutoConfiguration
public class LocaleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 假设请求头中存储语言信息的键名为"Language"
        final String key = "Content-language";
        // 从请求头中获取语言信息
        String language = request.getHeader(key);
        // 使用自定义的字符串工具类判断语言信息是否非空
        if (StringUtils.isNotEmpty(language)) {
            // 假设语言信息格式为"zh_CN"或"en_US"，这里通过下划线分割获取语言和地区信息
            String[] languageParts = language.split("_");
            // 确保分割后有两个部分
            if (languageParts.length == 2) {
                // 创建Locale对象
                Locale locale = new Locale(languageParts[0], languageParts[1]);
                // 设置当前线程的Locale
                LocaleContextHolder.setLocale(locale);
            } else {
                // 如果格式不正确，可以记录日志或进行其他处理
                log.warn("Invalid language format: {}", language);
            }
        }
        // 继续执行后续流程
        return true;
    }

    /**
     * 在请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     * 通常用于处理一些需要在请求处理之后、视图渲染之前执行的逻辑
     *
     * @param request   HttpServletRequest对象
     * @param response  HttpServletResponse对象
     * @param handler   被调用的处理器
     * @param modelAndView 如果处理器方法的返回值是ModelAndView类型，则为该方法的返回值，否则为null
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        // 可以在这里添加处理请求处理之后、视图渲染之前的逻辑
    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     *
     * @param request   HttpServletRequest对象
     * @param response  HttpServletResponse对象
     * @param handler   被调用的处理器
     * @param ex        如果在请求处理过程中发生异常，则为该异常，否则为null
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 可以在这里添加请求处理完毕后的清理逻辑，例如关闭资源等
    }
}