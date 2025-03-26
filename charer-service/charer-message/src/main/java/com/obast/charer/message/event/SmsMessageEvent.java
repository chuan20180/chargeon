package com.obast.charer.message.event;

import com.obast.charer.message.model.Message;

import com.obast.charer.message.model.SmsMessage;
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
public class SmsMessageEvent extends ApplicationEvent {

    private SmsMessage message;

    public SmsMessageEvent(SmsMessage message) {
        super(message);
        this.message = message;
    }

}
