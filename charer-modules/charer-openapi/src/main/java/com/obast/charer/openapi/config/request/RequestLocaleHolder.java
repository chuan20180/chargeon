package com.obast.charer.openapi.config.request;

import org.springframework.core.NamedThreadLocal;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

/**
 * @ Author：chuan
 * @ Date：2024-10-13-23:29
 * @ Version：1.0
 * @ Description：RequestLocaleHolder
 */
public class RequestLocaleHolder {
    private static final ThreadLocal<RequestLocale> requestLocaleHolder = new NamedThreadLocal<>("RequestParamContext");

    private RequestLocaleHolder() {
    }

    public static void resetRequestLocaleContext() {
        requestLocaleHolder.remove();
    }


    public static void setLocale(@Nullable RequestLocale requestLocale) {
        if (requestLocale == null) {
            resetRequestLocaleContext();
        } else  {
            requestLocaleHolder.set(requestLocale);
        }
    }

    @NotNull
    public static RequestLocale getLocale() {
        return requestLocaleHolder.get();
    }

}
