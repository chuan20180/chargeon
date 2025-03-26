package com.obast.charer.system.config.interceptor;

import com.obast.charer.common.api.Request;
import com.obast.charer.common.utils.JsonUtils;
import com.obast.charer.common.utils.SpringUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.common.web.filter.RepeatedlyRequestWrapper;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.map.MapUtil;
import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * web的调用时间统计拦截器
 * dev环境有效
 */
@Slf4j
public class InvokeTimeInterceptor implements HandlerInterceptor {

    private final static String prodProfile = "prod";

    private final TransmittableThreadLocal<StopWatch> invokeTimeTL = new TransmittableThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!prodProfile.equals(SpringUtils.getActiveProfile())) {
            String url = request.getMethod() + " " + request.getRequestURI();
            log.info("request handler:{}, threadId:{}", url, Thread.currentThread().getId());

            Map<String, String> headers = new HashMap<>();
            request.getHeaderNames().asIterator().forEachRemaining(headerName -> {
                headers.put(headerName, request.getHeader(headerName));
            });


            // 打印请求参数
            if (isJsonRequest(request)) {
                String jsonParam = "";
                if (request instanceof RepeatedlyRequestWrapper) {
                    BufferedReader reader = request.getReader();
                    jsonParam = IoUtil.read(reader);
                    Request req = JsonUtils.parseObject(jsonParam, Request.class);
                    MDC.put("requestId", req.getRequestId());
                }

                log.debug("开始请求 => URL[{}],参数类型[json],参数:[{}], Header: {}", url, jsonParam, headers);
            } else {
                Map<String, String[]> parameterMap = request.getParameterMap();
                if (MapUtil.isNotEmpty(parameterMap)) {
                    String parameters = JsonUtils.toJsonString(parameterMap);
                    String[] requestIds = parameterMap.get("requestId");
                    if (Objects.nonNull(requestIds) && requestIds.length > 0) {
                        MDC.put("requestId", requestIds[0]);
                    }
                    log.debug("开始请求 => URL[{}],参数类型[param],参数:[{}], Header: {}", url, parameters, headers);
                } else {
                    log.debug("开始请求 => URL[{}],无参数, Header: {}", url, headers);
                }
            }

            StopWatch stopWatch = new StopWatch();
            invokeTimeTL.set(stopWatch);
            stopWatch.start();
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (!prodProfile.equals(SpringUtils.getActiveProfile())) {
            StopWatch stopWatch = invokeTimeTL.get();
            stopWatch.stop();
            log.debug("结束请求 => URL[{}],耗时:[{}]毫秒", request.getMethod() + " " + request.getRequestURI(), stopWatch.getTime());
            invokeTimeTL.remove();
            MDC.clear();
        }
    }

    /**
     * 判断本次请求的数据类型是否为json
     *
     * @param request request
     * @return boolean
     */
    private boolean isJsonRequest(HttpServletRequest request) {
        String contentType = request.getContentType();
        if (contentType != null) {
            return StringUtils.startsWithIgnoreCase(contentType, MediaType.APPLICATION_JSON_VALUE);
        }
        return false;
    }

}
