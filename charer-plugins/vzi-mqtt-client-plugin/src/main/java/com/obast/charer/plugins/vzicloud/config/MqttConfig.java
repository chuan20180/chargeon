package com.obast.charer.plugins.vzicloud.config;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class MqttConfig {

    private String host;

    private Integer port = 1883;

    private String type;

    private String clientid;

    private String username;

    private String password;

    private String remote_addr;



}
