package com.obast.charer.sms.aliyun;


import com.obast.charer.sms.core.ISmsProperties;
import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@Configuration
public class AliyunSmsProperties implements ISmsProperties {
    String login;
    String password;
    String sender;
    Boolean test;
}
