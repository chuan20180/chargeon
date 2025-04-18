package com.obast.charer.plugins.vzicloud;

import com.gitee.starblues.bootstrap.SpringPluginBootstrap;
import com.gitee.starblues.bootstrap.annotation.OneselfConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@OneselfConfig(mainConfigFileName = {"application.yml"})
@EnableConfigurationProperties
@EnableScheduling
public class Application extends SpringPluginBootstrap {
    public static void main(String[] args) {
        new Application().run(Application.class, args);
    }
}
