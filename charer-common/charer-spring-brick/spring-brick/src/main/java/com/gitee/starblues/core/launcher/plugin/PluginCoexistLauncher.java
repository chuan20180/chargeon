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

import com.gitee.starblues.core.classloader.PluginGeneralUrlClassLoader;
import com.gitee.starblues.core.launcher.plugin.involved.PluginLaunchInvolved;
import com.gitee.starblues.loader.classloader.GeneralUrlClassLoader;
import com.gitee.starblues.loader.launcher.LauncherContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 插件共享式启动引导
 *
 * @author starBlues
 * @since 3.0.4
 * @version 3.1.0
 */
@Slf4j
public class PluginCoexistLauncher extends AbstractPluginLauncher {


    public PluginCoexistLauncher(PluginInteractive pluginInteractive,
                                 PluginLaunchInvolved pluginLaunchInvolved) {
        super(pluginInteractive, pluginLaunchInvolved);
    }

    @Override
    protected ClassLoader createPluginClassLoader(String... args) throws Exception {
        PluginGeneralUrlClassLoader classLoader = new PluginGeneralUrlClassLoader(
                pluginInteractive.getPluginDescriptor().getPluginId(),
                getParentClassLoader());
        classLoader.addResource(pluginInteractive.getPluginDescriptor());
        return classLoader;
    }

    protected GeneralUrlClassLoader getParentClassLoader() throws Exception {
        ClassLoader contextClassLoader = LauncherContext.getMainClassLoader();
        if(contextClassLoader instanceof GeneralUrlClassLoader){
            return (GeneralUrlClassLoader) contextClassLoader;
        } else {
            throw new Exception("非法父类加载器: " + contextClassLoader.getClass().getName());
        }
    }

}
