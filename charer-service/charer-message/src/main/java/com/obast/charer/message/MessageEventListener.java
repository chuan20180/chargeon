package com.obast.charer.message;

import com.obast.charer.message.event.MessageEvent;

/**
 * author: 石恒
 * date: 2023-05-08 15:08
 * description:
 **/
public interface MessageEventListener {

    void doEvent(MessageEvent event);

}
