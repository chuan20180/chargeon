package com.obast.charer.model;

import cn.hutool.core.util.IdUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvokeResult<T> {

    private String requestId;

    private long time;

    private T data;

    public InvokeResult(T data) {
        this.data = data;
        this.requestId = IdUtil.simpleUUID();
        this.time = System.currentTimeMillis();
    }
}
