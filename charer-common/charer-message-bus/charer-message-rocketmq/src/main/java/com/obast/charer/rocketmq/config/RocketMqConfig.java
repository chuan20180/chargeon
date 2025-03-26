package com.obast.charer.rocketmq.config;

import com.obast.charer.common.thing.ThingModelMessage;
import com.obast.charer.mq.MqConsumer;
import com.obast.charer.mq.MqProducer;
import com.obast.charer.rocketmq.RocketMqConsumer;
import com.obast.charer.rocketmq.RocketMqProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RocketMqConfig {

    @Value("${rocketmq.name-server}")
    private String nameServer;

    @Value("${rocketmq.producer.group}")
    private String group;

    @ConditionalOnMissingBean
    @Bean
    public MqProducer<ThingModelMessage> getThingModelMessageProducer() {
        return new RocketMqProducer<>(nameServer, group);
    }

    @ConditionalOnMissingBean
    @Bean
    public MqConsumer<ThingModelMessage> getThingModelMessageConsumer() {
        return new RocketMqConsumer<>(nameServer, ThingModelMessage.class);
    }


}
