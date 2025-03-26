package com.obast.charer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@SpringBootApplication
@ComponentScan(excludeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*s3mock.*Controller")
})
@EnableTransactionManagement
@EnableJpaAuditing
@EnableAspectJAutoProxy
@EnableScheduling
@EnableFeignClients
@EnableAsync
public class CharerSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CharerSystemApplication.class, args);
        System.out.println("Charer-System 模块启动成功 ");
    }
}
