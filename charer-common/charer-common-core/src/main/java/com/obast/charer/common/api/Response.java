package com.obast.charer.common.api;

import cn.hutool.core.util.IdUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {
    private Integer code;
    private String message;
    private Object data;
    private String requestId;

   public static <T> Response<?> success(T data) {
        return new Response<>(200,"成功", data, IdUtil.simpleUUID());
    }

    public static Response<?> success() {
        return new Response<>(200,"成功", null, IdUtil.simpleUUID());
    }

    public static Response<?> fail(String message) {
        return new Response<>(500,message, null, IdUtil.simpleUUID());
    }
}
