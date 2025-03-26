package com.obast.charer.plugin.config.web;

import cn.hutool.core.util.IdUtil;
import com.obast.charer.common.api.Response;
import com.obast.charer.plugin.config.handler.GlobalExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Map;

@Slf4j
@ControllerAdvice(basePackages = {"com.obast.charer.plugin"})
public class PluginResponseHandler implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        if (returnType.getParameterType() == ResponseEntity.class) {
            return false;
        }
        return !converterType.equals(StringHttpMessageConverter.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {

        if (body instanceof GlobalExceptionHandler.RequestResult) {
            GlobalExceptionHandler.RequestResult requestResult = (GlobalExceptionHandler.RequestResult) body;
            return new Response<>(requestResult.getCode(), requestResult.getMessage(),
                    "", IdUtil.simpleUUID());
        }else if (body instanceof Map) {
            Map map = (Map) body;
            //spring mvc内部异常
            if (map.containsKey("timestamp") && map.containsKey("status") && map.containsKey("error")) {
                return new Response<>((Integer) map.get("status"), (String) map.get("error"),
                        "", IdUtil.simpleUUID());
            }
        } else if (body instanceof Response) {
            return body;
        }

        return new Response<>(200, "", body, IdUtil.simpleUUID());
    }

}
