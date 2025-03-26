/*
 *
 *  * | Licensed 未经许可不能去掉「OPENIITA」相关版权
 *  * +----------------------------------------------------------------------
 *  * | Author: xw2sy@163.com
 *  * +----------------------------------------------------------------------
 *
 *  Copyright [2024] [OPENIITA]
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * /
 */

package com.gitee.starblues.bootstrap;

import com.gitee.starblues.bootstrap.processor.ProcessorContext;
import com.gitee.starblues.bootstrap.processor.SpringPluginProcessor;
import com.gitee.starblues.bootstrap.processor.oneself.ConfigureMainPluginEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * 插件自主启动的 SpringApplication
 *
 * @author starBlues
 * @version 3.1.0
 * @since 3.0.4
 */
public class PluginOneselfSpringApplication extends SpringApplication {

    private final Logger logger = LoggerFactory.getLogger(PluginSpringApplication.class);

    protected final SpringPluginProcessor pluginProcessor;
    protected final ProcessorContext processorContext;

    private final ConfigurePluginEnvironment configurePluginEnvironment;
    private final GenericApplicationContext applicationContext;


    public PluginOneselfSpringApplication(SpringPluginProcessor pluginProcessor,
                                          ProcessorContext processorContext,
                                          Class<?>... primarySources) {
        super(primarySources);
        this.pluginProcessor = pluginProcessor;
        this.processorContext = processorContext;
        this.configurePluginEnvironment = new ConfigurePluginEnvironment(processorContext);
        this.applicationContext = getApplicationContext();
    }

    protected GenericApplicationContext getApplicationContext() {
        return (GenericApplicationContext) super.createApplicationContext();
    }

    @Override
    protected void configureEnvironment(ConfigurableEnvironment environment, String[] args) {
        super.configureEnvironment(environment, args);
        configurePluginEnvironment.configureEnvironment(environment, args);
        new ConfigureMainPluginEnvironment(processorContext).configureEnvironment(environment, args);
    }

    @Override
    protected void bindToSpringApplication(ConfigurableEnvironment environment) {
        super.bindToSpringApplication(environment);
        configurePluginEnvironment.logProfiles(environment);
    }

    @Override
    protected ConfigurableApplicationContext createApplicationContext() {
        return this.applicationContext;
    }

    @Override
    public ConfigurableApplicationContext run(String... args) {
        try {
            processorContext.setApplicationContext(this.applicationContext);
            PluginContextHolder.initialize(processorContext);
            pluginProcessor.initialize(processorContext);
            return super.run(args);
        } catch (Exception e) {
            pluginProcessor.failure(processorContext);
            logger.debug("启动插件[{}]失败. {}",
                    processorContext.getPluginDescriptor().getPluginId(),
                    e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void refresh(ConfigurableApplicationContext applicationContext) {
        pluginProcessor.refreshBefore(processorContext);
        super.refresh(applicationContext);
        pluginProcessor.refreshAfter(processorContext);
    }

}
