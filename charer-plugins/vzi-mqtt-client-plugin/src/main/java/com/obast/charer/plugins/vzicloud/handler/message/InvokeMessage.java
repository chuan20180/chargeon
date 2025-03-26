package com.obast.charer.plugins.vzicloud.handler.message;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvokeMessage {

    private String id;

    private String sn;

    private String name;

    private String version;

    private Long timestamp;

    private Object payload;
}