package com.obast.charer.message.service;

import com.obast.charer.message.event.MessageEvent;
import com.obast.charer.message.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * author: 石恒
 * date: 2023-05-08 16:02
 * description:
 **/
@Slf4j
@Service
public class MessageService {
    @Resource
    private ApplicationEventPublisher applicationEventPublisher;

    public void sendMessage(Message message) {
        log.debug("开始发送消息 {}", message);
        applicationEventPublisher.publishEvent(new MessageEvent(message));
    }
}
