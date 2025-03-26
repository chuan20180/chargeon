package com.obast.charer.vertx.config;

import com.obast.charer.common.thing.ThingModelMessage;

import com.obast.charer.mq.MqConsumer;
import com.obast.charer.mq.MqProducer;
import com.obast.charer.vertx.VertxMqConsumer;
import com.obast.charer.vertx.VertxMqProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VertxConfig {

    @ConditionalOnMissingBean
    @Bean
    public MqProducer<ThingModelMessage> getThingModelMessageProducer() {
        return new VertxMqProducer<>(ThingModelMessage.class);
    }

    @ConditionalOnMissingBean
    @Bean
    public MqConsumer<ThingModelMessage> getThingModelMessageConsumer() {
        return new VertxMqConsumer<>(ThingModelMessage.class);
    }

}
