package com.obast.charer.push.getui;

import com.obast.charer.push.core.IPushProperties;
import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@Component
@Configuration
public class GetuiPushProperties implements IPushProperties {
    String appId;
    String appKey;
    String masterSecret;

}
