package com.obast.charer.push.wechat;

import com.obast.charer.push.core.IPushProperties;
import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@Configuration
public class WechatPushProperties implements IPushProperties {

    String appId;
    String appSecret;

}
