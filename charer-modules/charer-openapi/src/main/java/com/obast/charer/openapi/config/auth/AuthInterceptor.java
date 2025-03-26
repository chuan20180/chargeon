
package com.obast.charer.openapi.config.auth;

import cn.dev33.satoken.exception.BackResultException;
import cn.dev33.satoken.exception.StopMatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * @ Author：chuan
 * @ Date：2024-09-27-17:19
 * @ Version：1.0
 * @ Description：接口认证请求拦截
 */

@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    public AuthParamFunction<Object> auth;

    public AuthInterceptor(AuthParamFunction<Object> auth) {
        this.auth = auth;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws IOException {
        try {
            // Auth 校验
            auth.run(handler);

        } catch (StopMatchException e) {
            // 停止匹配，进入Controller
        } catch (BackResultException e) {
            // 停止匹配，向前端输出结果
            if(response.getContentType() == null) {
                response.setContentType("text/plain; charset=utf-8");
            }
            response.getWriter().print(e.getMessage());
            return false;
        }

        // 通过验证
        return true;

    }

}