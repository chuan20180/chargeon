package com.obast.charer.openapi.config.web;

import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.util.IdUtil;
import com.obast.charer.common.api.Response;
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

@Slf4j
@ControllerAdvice(basePackages = {"com.obast.charer.openapi"})
public class WebResponseBodyAdvice implements ResponseBodyAdvice<Object> {
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


        //log.info("response body: {}", body);

        if (body instanceof GlobalExceptionHandler.RequestResult) {
            GlobalExceptionHandler.RequestResult requestResult = (GlobalExceptionHandler.RequestResult) body;
            return new Response<>(requestResult.getCode(), requestResult.getMessage(),
                    "", IdUtil.simpleUUID());
        } else if (body instanceof SaResult) {
            SaResult result = (SaResult) body;
            return new Response<>(result.getCode(), result.getMsg(), result.getData(), IdUtil.simpleUUID());
        } else if (body instanceof Response) {
           return body;
        }

        return new Response<>(200, "", body, IdUtil.simpleUUID());
    }

}
