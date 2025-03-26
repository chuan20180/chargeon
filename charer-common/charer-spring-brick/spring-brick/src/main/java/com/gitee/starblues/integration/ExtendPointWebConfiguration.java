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

package com.gitee.starblues.integration;

import com.gitee.starblues.spring.ResolvePluginThreadClassLoader;
import com.gitee.starblues.spring.web.PluginStaticResourceConfig;
import com.gitee.starblues.spring.web.PluginStaticResourceWebMvcConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.ResourceResolver;
import springfox.documentation.spring.web.plugins.DocumentationPluginsBootstrapper;

/**
 * 系统web环境配置点
 * @author starBlues
 * @version 3.0.0
 */
@ConditionalOnWebApplication
@Import({
        ExtendPointWebConfiguration.PluginStaticResourceConfiguration.class
})
public class ExtendPointWebConfiguration {


    @ConditionalOnClass(ResourceResolver.class)
    public static class PluginStaticResourceConfiguration{

        @Bean
        @ConditionalOnMissingBean
        public PluginStaticResourceWebMvcConfigurer pluginWebResourceResolver(PluginStaticResourceConfig resourceConfig){
            return new PluginStaticResourceWebMvcConfigurer(resourceConfig);
        }

        @Bean
        @ConditionalOnMissingBean
        public PluginStaticResourceConfig pluginStaticResourceConfig() {
            return new PluginStaticResourceConfig();
        }
    }
//
//    @ConditionalOnClass({ DocumentationPluginsBootstrapper.class })
//    @ConditionalOnProperty(name = "plugin.pluginSwaggerScan", havingValue = "true", matchIfMissing = true)
//    public static class SwaggerListenerConfiguration {
//
//        private final GenericApplicationContext applicationContext;
//
//        public SwaggerListenerConfiguration(GenericApplicationContext applicationContext) {
//            this.applicationContext = applicationContext;
//        }
//
//        @Bean
//        @ConditionalOnMissingBean
//        public SwaggerListener swaggerListener(){
//            return new SwaggerListener(applicationContext);
//        }
//
//    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new ResolvePluginThreadClassLoader();
    }

}
