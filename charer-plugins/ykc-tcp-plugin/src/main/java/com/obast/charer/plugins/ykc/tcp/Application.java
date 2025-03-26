package com.obast.charer.plugins.ykc.tcp;

import com.gitee.starblues.bootstrap.SpringPluginBootstrap;
import com.gitee.starblues.bootstrap.annotation.OneselfConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@OneselfConfig(mainConfigFileName = {"application.yml"})
@EnableConfigurationProperties
public class Application extends SpringPluginBootstrap {

    public static void main(String[] args) {
        new Application().run(Application.class, args);
    }
}
