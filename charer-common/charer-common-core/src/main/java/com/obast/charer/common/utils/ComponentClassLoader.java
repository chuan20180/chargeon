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
package com.obast.charer.common.utils;

import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ComponentClassLoader {
    private static final Map<String, URLClassLoader> classLoaders = new HashMap<>();

    protected static <T> Class<T> findClass(String name, String clsName) throws ClassNotFoundException {
        ClassLoader classLoader = classLoaders.get(name);
        return (Class<T>) classLoader.loadClass(clsName);
    }

    private static String addUrl(String name, File jarPath) throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException, IOException {
        URLClassLoader classLoader = classLoaders.get(name);
        if (classLoader != null) {
            classLoader.close();
        }

        classLoader = URLClassLoader.newInstance(new URL[]{jarPath.toURI().toURL()},
                Thread.currentThread().getContextClassLoader());
        classLoaders.put(name, classLoader);

        Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        if (!method.canAccess(classLoader)) {
            method.setAccessible(true);
        }

        URL url = jarPath.toURI().toURL();
        method.invoke(classLoader, url);
        InputStream is = classLoader.getResourceAsStream("component.spi");
        if (is == null) {
            return null;
        }

        //多行只取第1行，并处理空格
        String[] lines = IOUtils.toString(is, StandardCharsets.UTF_8).split("\\s");
        if (lines.length == 0) {
            return null;
        }
        return lines[0].trim();
    }

    public static <T> T getComponent(String name, File jarFile) throws Exception {
        String className = addUrl(name, jarFile);
        if (StringUtils.isBlank(className)) {
            throw new BizException(ErrCode.GET_SPI_COMPONENT_ERROR);
        }
        Class<T> componentClass = findClass(name, className);
        return componentClass.getDeclaredConstructor().newInstance();
    }

    public static <T> T getConverter(String name) throws Exception {
        URLClassLoader classLoader = classLoaders.get(name);
        InputStream is = classLoader.getResourceAsStream("convert.spi");
        if (is == null) {
            return null;
        }

        //多行只取第1行，并处理空格
        String[] lines = IOUtils.toString(is, StandardCharsets.UTF_8).split("\\s");
        if (lines.length == 0) {
            throw new BizException(ErrCode.GET_SPI_CONVERT_ERROR);
        }
        String className = lines[0].trim();
        Class<T> converterClass = findClass(name, className);
        return converterClass.getDeclaredConstructor().newInstance();
    }

    public static void closeClassLoader(String name)  {
        try {
            URLClassLoader classLoader = classLoaders.get(name);
            if (classLoader != null){
                classLoader.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
