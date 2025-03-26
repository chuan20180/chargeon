package com.obast.charer.push.wechat;

import com.obast.charer.push.core.IPushProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
public class WechatPushConfig implements IPushProperties {
    /**
     * 微信小程序通知模板id
     */
    private String templateId;
}
