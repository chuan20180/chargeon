package com.obast.charer.sms.core.push;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Setter
@Getter
public class SmsPushEvent extends ApplicationEvent {

    private SmsPushConfig config;

    private SmsPushMessage message;

    public SmsPushEvent(SmsPushConfig config, SmsPushMessage message) {
        super(message);
        this.config = config;
        this.message = message;
    }
}
