package com.obast.charer.message.event;

import com.obast.charer.message.model.Message;
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
public class MessageEvent extends ApplicationEvent {
    private Message message;

    public MessageEvent(Message message) {
        super(message);
        this.message = message;
    }

}
