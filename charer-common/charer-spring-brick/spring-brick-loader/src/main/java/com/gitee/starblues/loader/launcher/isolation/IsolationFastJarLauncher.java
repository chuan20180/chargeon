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

package com.gitee.starblues.loader.launcher.isolation;

import com.gitee.starblues.loader.classloader.GenericClassLoader;
import com.gitee.starblues.loader.classloader.resource.loader.JarResourceLoader;
import com.gitee.starblues.loader.classloader.resource.loader.MainJarResourceLoader;
import com.gitee.starblues.loader.launcher.classpath.ClasspathResource;
import com.gitee.starblues.loader.launcher.classpath.FastJarClasspathResource;
import com.gitee.starblues.loader.launcher.runner.MethodRunner;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.gitee.starblues.loader.LoaderConstant.*;

/**
 * 主程序jar in jar 模式启动者
 *
 * @author starBlues
 * @since 3.0.2
 * @version 3.0.2
 */
public class IsolationFastJarLauncher extends IsolationBaseLauncher {

    private final ClasspathResource classpathResource;

    public IsolationFastJarLauncher(MethodRunner methodRunner, File rootJarFile) {
        super(methodRunner);
        Objects.requireNonNull(rootJarFile, "参数 rootJarFile 不能为空");
        this.classpathResource = new FastJarClasspathResource(rootJarFile);
    }

    @Override
    protected ClassLoader createClassLoader(String... args) throws Exception {
        GenericClassLoader classLoader = (GenericClassLoader) super.createClassLoader(args);
        addResource(classLoader);
        return classLoader;
    }

    @Override
    protected boolean resolveThreadClassLoader() {
        return true;
    }

    private void addResource(GenericClassLoader classLoader) throws Exception {
        Set<URL> baseResource = getBaseResource();
        List<URL> classpath = classpathResource.getClasspath();
        if(classpath != null){
            baseResource.addAll(classpath);
        }
        for (URL url : baseResource) {
            String path = url.getPath();
            if(path.contains(PROD_CLASSES_URL_SIGN)){
                classLoader.addResource(new MainJarResourceLoader(url));
            } else {
                classLoader.addResource(new JarResourceLoader(url));
            }
        }
    }


}
