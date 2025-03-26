package com.obast.charer.plugins.vzicloud.handler.message;

import lombok.Data;

@Data
public class EventMessage {

    private String id;

    private Integer bv;

    private String name;

    private String sn;

    private Long timestamp;

    private String version;

    private Object payload;
}