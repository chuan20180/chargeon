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

package com.gitee.starblues.core.launcher.plugin;

import com.gitee.starblues.core.PluginInsideInfo;
import com.gitee.starblues.core.exception.PluginException;
import com.gitee.starblues.core.launcher.plugin.involved.PluginLaunchInvolved;
import com.gitee.starblues.loader.launcher.AbstractLauncher;
import com.gitee.starblues.spring.SpringPluginHook;

/**
 * 插件启动者
 *
 * @author starBlues
 * @version 3.1.1
 * @since 3.1.1
 */
public abstract class AbstractPluginLauncher extends AbstractLauncher<SpringPluginHook> {

    protected final PluginInteractive pluginInteractive;
    protected final PluginLaunchInvolved pluginLaunchInvolved;

    public AbstractPluginLauncher(PluginInteractive pluginInteractive,
                                  PluginLaunchInvolved pluginLaunchInvolved) {
        this.pluginInteractive = pluginInteractive;
        this.pluginLaunchInvolved = pluginLaunchInvolved;
    }

    @Override
    protected final ClassLoader createClassLoader(String... args) throws Exception {
        ClassLoader pluginClassLoader = createPluginClassLoader(args);
        pluginInteractive.getPluginInsideInfo().setClassLoader(pluginClassLoader);
        return pluginClassLoader;
    }

    @Override
    protected SpringPluginHook launch(ClassLoader classLoader, String... args) throws Exception {
        PluginInsideInfo pluginInsideInfo = pluginInteractive.getPluginInsideInfo();
        pluginLaunchInvolved.before(pluginInsideInfo, classLoader);
        try {
            SpringPluginHook springPluginHook = (SpringPluginHook) new PluginMethodRunner(pluginInteractive)
                    .run(classLoader);
            if(springPluginHook == null){
                throw new PluginException("插件返回的 SpringPluginHook 不能为空");
            }
            pluginLaunchInvolved.after(pluginInsideInfo, classLoader, springPluginHook);
            return new SpringPluginHookWrapper(springPluginHook, pluginInsideInfo, pluginLaunchInvolved, classLoader);
        } catch (Throwable throwable){
            pluginLaunchInvolved.failure(pluginInsideInfo,classLoader, throwable);
            throw throwable;
        }
    }

    /**
     * 创建插件的classloader
     * @param args 参数
     * @return ClassLoader
     * @throws Exception 创建ClassLoader异常
     */
    protected abstract ClassLoader createPluginClassLoader(String... args) throws Exception;



}
