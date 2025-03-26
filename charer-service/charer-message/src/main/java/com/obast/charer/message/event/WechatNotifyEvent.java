package com.obast.charer.message.event;

import com.obast.charer.message.model.NotifyMessage;
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
public class WechatNotifyEvent extends ApplicationEvent {

    private Properties properties;
    private NotifyMessage message;

    public WechatNotifyEvent(Properties properties, NotifyMessage message) {
        super(message);
        this.message = message;
    }

    @AllArgsConstructor
    @Data
    public static class Properties {
        /**
         * 微信小程序通知模板id
         */
        private String pushWechatTemplateId;


    }

}
