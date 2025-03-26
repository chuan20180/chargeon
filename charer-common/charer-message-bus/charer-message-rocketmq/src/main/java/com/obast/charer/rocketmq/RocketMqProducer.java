package com.obast.charer.rocketmq;

import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.JsonUtils;
import com.obast.charer.mq.MqProducer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

public class RocketMqProducer<T> implements MqProducer<T> {

    private final DefaultMQProducer producer;

    public RocketMqProducer(String nameServer, String group) {
        try {
            producer = new DefaultMQProducer(group);
            producer.setNamesrvAddr(nameServer);
            producer.start();
        } catch (Throwable e) {
            throw new BizException(ErrCode.INIT_PRODUCER_ERROR, e);
        }
    }

    @Override
    public void publish(String topic, T msg) {
        try {
            producer.send(new Message(topic,
                    JsonUtils.toJsonString(msg).getBytes(StandardCharsets.UTF_8)));
        } catch (Throwable e) {
            throw new BizException(ErrCode.SEND_MSG_ERROR, e);
        }
    }

}
