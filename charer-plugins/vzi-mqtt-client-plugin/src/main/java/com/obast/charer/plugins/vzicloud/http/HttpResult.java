package com.obast.charer.plugins.vzicloud.http;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HttpResult<T> {

    private int code;

    private String message;

    private T data;
}