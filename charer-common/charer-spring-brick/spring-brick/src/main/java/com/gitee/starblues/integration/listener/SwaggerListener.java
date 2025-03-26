///**
// * Copyright [2019-Present] [starBlues]
// *
// *    Licensed under the Apache License, Version 2.0 (the "License");
// *    you may not use this file except in compliance with the License.
// *    You may obtain a copy of the License at
// *
// *        http://www.apache.org/licenses/LICENSE-2.0
// *
// *    Unless required by applicable law or agreed to in writing, software
// *    distributed under the License is distributed on an "AS IS" BASIS,
// *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// *    See the License for the specific language governing permissions and
// *    limitations under the License.
// */
//
//package com.gitee.starblues.integration.listener;
//
//import com.gitee.starblues.core.PluginInfo;
//import com.gitee.starblues.core.descriptor.PluginDescriptor;
//import com.gitee.starblues.loader.utils.ObjectUtils;
//import com.gitee.starblues.utils.MsgUtils;
//import com.gitee.starblues.utils.SpringBeanUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.ApplicationContext;
//import org.springframework.plugin.core.PluginRegistry;
//import org.springframework.plugin.core.PluginRegistrySupport;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.service.Parameter;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spi.service.DocumentationPlugin;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.spring.web.plugins.DocumentationPluginsBootstrapper;
//
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Swagger 监听事件
// * @author starBlues
// * @since 3.0.0
// * @version 3.0.3
// */
//public class SwaggerListener implements PluginListener{
//
//    private final Logger log = LoggerFactory.getLogger(this.getClass());
//    private final ApplicationContext mainApplicationContext;
//    private static List<Parameter> parameterList = new ArrayList<>();
//
//    /**
//     * 设置全局头部/参数
//     * ParameterBuilder tokenPar = new ParameterBuilder();
//    *  tokenPar.name("参数名称")
//     *  .description("参数描述")
//     *  .modelRef(new ModelRef("参数数据类型"))
//     *  .parameterType("header或者query等")
//     *  .required(false);
//    *  Parameter param = tokenPar.build();
//     * @param parameters parameters
//     */
//    public static void setParameters(List<Parameter> parameters){
//        parameterList = parameters;
//    }
//
//    public SwaggerListener(ApplicationContext mainApplicationContext) {
//        this.mainApplicationContext = mainApplicationContext;
//    }
//
//    @Override
//    public void startSuccess(PluginInfo pluginInfo) {
//        PluginDescriptor descriptor = pluginInfo.getPluginDescriptor();
//        try {
//            Docket docket = this.createDocket(descriptor);
//            String groupName = docket.getGroupName();
//            PluginRegistry<DocumentationPlugin, DocumentationType> pluginRegistry = this.getPluginRegistry();
//            List<DocumentationPlugin> plugins = pluginRegistry.getPlugins();
//            List<DocumentationPlugin> newPlugins = new ArrayList<>();
//            for(DocumentationPlugin plugin : plugins){
//                if(plugin.getGroupName().equals(groupName)){
//                    continue;
//                }
//                newPlugins.add(plugin);
//            }
//            newPlugins.add(docket);
//
//            Field field = PluginRegistrySupport.class.getDeclaredField("plugins");
//            field.setAccessible(true);
//            field.set(pluginRegistry, newPlugins);
//            // 如果第一次启动且为跟随系统启动的插件，减少刷新
//            if(!pluginInfo.isFollowSystem() || pluginInfo.getStopTime() != null){
//                this.refresh();
//            }
//            log.debug("插件[{}]注册到 Swagger 成功", MsgUtils.getPluginUnique(descriptor));
//        } catch (Exception e) {
//            log.error("插件[{}]注册到 Swagger 失败，错误为:{}", MsgUtils.getPluginUnique(descriptor) ,e.getMessage());
//        }
//    }
//
//    @Override
//    public void stopSuccess(PluginInfo pluginInfo) {
//        PluginDescriptor descriptor = pluginInfo.getPluginDescriptor();
//        String groupName = getGroupName(descriptor);
//        try{
//            PluginRegistry<DocumentationPlugin, DocumentationType> pluginRegistry = this.getPluginRegistry();
//            List<DocumentationPlugin> plugins = pluginRegistry.getPlugins();
//            List<DocumentationPlugin> newPlugins = new ArrayList<>();
//            for(DocumentationPlugin plugin : plugins){
//                if(groupName.equalsIgnoreCase(plugin.getGroupName())){
//                    continue;
//                }
//                newPlugins.add(plugin);
//            }
//
//            Field field = PluginRegistrySupport.class.getDeclaredField("plugins");
//            field.setAccessible(true);
//            field.set(pluginRegistry, newPlugins);
//
//            this.refresh();
//            log.debug("插件[{}]从 Swagger 移除成功", MsgUtils.getPluginUnique(descriptor));
//        } catch (Exception e) {
//            log.error("插件[{}]从 Swagger 移除失败，错误为:{}", MsgUtils.getPluginUnique(descriptor), e.getMessage());
//        }
//    }
//
//    void refresh(){
//        try {
//            DocumentationPluginsBootstrapper documentationPluginsBootstrapper = this.getDocumentationPluginsBootstrapper();
//            if(documentationPluginsBootstrapper != null){
//                documentationPluginsBootstrapper.stop();
//                documentationPluginsBootstrapper.start();
//            } else {
//                log.warn("Not found DocumentationPluginsBootstrapper, so cannot refresh swagger");
//            }
//        } catch (Exception e){
//            // ignore
//            log.warn("refresh swagger failure. {}", e.getMessage());
//        }
//    }
//
//    /**
//     * 获取文档 Bootstrapper
//     * @return DocumentationPluginsBootstrapper
//     */
//    private DocumentationPluginsBootstrapper getDocumentationPluginsBootstrapper(){
//        return SpringBeanUtils.getExistBean(mainApplicationContext, DocumentationPluginsBootstrapper.class);
//    }
//
//    /**
//     * 获取文档PluginRegistry
//     * @return PluginRegistry
//     */
//    private PluginRegistry<DocumentationPlugin, DocumentationType> getPluginRegistry(){
//        PluginRegistry<DocumentationPlugin, DocumentationType> registry =
//                SpringBeanUtils.getExistBean(mainApplicationContext, "documentationPluginRegistry");
//        if(registry != null){
//            return registry;
//        }
//        throw new IllegalStateException("项目依赖的 Swagger 版本不支持刷新插件接口, 请切换版本");
//    }
//
//    /**
//     * 创建swagger分组对象
//     *
//     * @param descriptor 插件信息
//     * @return Docket
//     */
//    private Docket createDocket(PluginDescriptor descriptor) {
//        String description = descriptor.getDescription();
//        if (ObjectUtils.isEmpty(description)) {
//            description = descriptor.getPluginId();
//        }
//
//        String provider = descriptor.getProvider();
//        String pluginBootstrapClass = descriptor.getPluginBootstrapClass();
//        String pluginClass = pluginBootstrapClass.substring(0, pluginBootstrapClass.lastIndexOf("."));
//        Contact contact = new Contact(provider, "", "");
//        ApiInfo apiInfo = new ApiInfoBuilder()
//                .title(getGroupName(descriptor))
//                .description(description)
//                .contact(contact)
//                .version(descriptor.getPluginVersion())
//                .build();
//
//        Docket docket = (new Docket(DocumentationType.SWAGGER_2))
//                .apiInfo(apiInfo).select()
//                .apis(RequestHandlerSelectors.basePackage(pluginClass))
//                .paths(PathSelectors.any()).build()
//                .groupName(getGroupName(descriptor));
//
//        if(parameterList != null && !parameterList.isEmpty()){
//            return docket.globalOperationParameters(parameterList);
//        }
//        return docket;
//    }
//
//    /**
//     * 获取组名称
//     * @param descriptor 插件信息
//     * @return 分组信息
//     */
//    private String getGroupName(PluginDescriptor descriptor){
//        return descriptor.getPluginId() +"@" + descriptor.getPluginVersion();
//    }
//}
