package com.obast.charer;

import com.gitee.starblues.loader.DevelopmentMode;
import com.gitee.starblues.loader.launcher.SpringBootstrap;
import com.gitee.starblues.loader.launcher.SpringMainBootstrap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Slf4j
@SpringBootApplication
@ComponentScan(excludeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*s3mock.*Controller")
})
@EnableTransactionManagement
@EnableAspectJAutoProxy
@EnableScheduling
@EnableJpaAuditing
@EnableFeignClients
public class CharerPluginApplication implements SpringBootstrap {

    public static void main(String[] args) {
        SpringMainBootstrap.launch(CharerPluginApplication.class, args);
        log.info("server start success!");
    }

    @Override
    public void run(String[] args) {
        SpringApplication.run(CharerPluginApplication.class, args);
    }

    @Override
    public String developmentMode() {
        return DevelopmentMode.ISOLATION;
    }
}
