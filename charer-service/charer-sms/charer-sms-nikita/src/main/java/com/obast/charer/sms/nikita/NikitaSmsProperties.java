package com.obast.charer.sms.nikita;


import com.obast.charer.sms.core.ISmsProperties;

import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@Configuration
public class NikitaSmsProperties implements ISmsProperties {
    String login;
    String password;
    String sender;
    Boolean test;
}
