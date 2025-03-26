package com.obast.charer.push.wechat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * author: 石恒
 * date: 2023-05-11 14:14
 * description:
 **/

@Setter
@Getter
public class WechatPushEvent extends ApplicationEvent {

    private WechatPushConfig config;

    private WechatPushMessage message;

    public WechatPushEvent(WechatPushConfig config, WechatPushMessage message) {
        super(message);
        this.config = config;
        this.message = message;
    }


}
