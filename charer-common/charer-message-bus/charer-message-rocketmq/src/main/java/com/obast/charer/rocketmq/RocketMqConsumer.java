package com.obast.charer.rocketmq;

import com.obast.charer.common.utils.JsonUtils;
import com.obast.charer.mq.ConsumerHandler;
import com.obast.charer.mq.MqConsumer;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.nio.charset.StandardCharsets;

@Slf4j
public class RocketMqConsumer<T> implements MqConsumer<T> {

    private String nameServer;

    private final Class<T> msgType;

    public RocketMqConsumer(String nameServer, Class<T> cls) {
        this.nameServer = nameServer;
        this.msgType = cls;
    }

    @Override
    public void consume(String topic, ConsumerHandler<T> handler) {
        try {
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(handler.getClass()
                    .getName().replace(".", ""));
            consumer.setNamesrvAddr(nameServer);
            consumer.subscribe(topic, "*");
            consumer.registerMessageListener((MessageListenerConcurrently) (messages, context) -> {
                for (MessageExt message : messages) {
                    T msg = JsonUtils.parseObject(new String(message.getBody(), StandardCharsets.UTF_8), msgType);
                    handler.handler(msg);
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
            consumer.start();
        } catch (Throwable e) {
            log.error("consume error", e);
        }
    }

}
