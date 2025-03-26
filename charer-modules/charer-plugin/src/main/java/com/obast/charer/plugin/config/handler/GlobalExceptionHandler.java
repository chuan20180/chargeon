package com.obast.charer.plugin.config.handler;

import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.exception.ViewException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RequestResult handleException(Exception e, HttpServletResponse response) {
        log.error("handler exception", e);

        if (e instanceof BizException) {
            BizException bizException = (BizException) e;
            response.setStatus(200);
            return new RequestResult(bizException.getCode(), bizException.getMessage());
        }
        if (e instanceof ViewException) {
            response.setStatus(200);
            return new RequestResult(((ViewException) e).getCode(), e.getMessage());
        }

        if (e.getMessage().contains("Unauthorized")) {
            response.setStatus(403);
            return new RequestResult(403, "没有权限");
        }
        response.setStatus(500);
        return new RequestResult(500, e.getMessage());
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class RequestResult {
        private int code;
        private String message;
    }

}


