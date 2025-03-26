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

package com.gitee.starblues.loader.classloader.resource.loader;

import com.gitee.starblues.loader.classloader.resource.storage.ResourceStorage;

import java.net.URL;

/**
 * 基本 URL 资源加载
 *
 * @author starBlues
 * @since 3.1.2
 * @version 3.1.2
 */
public class BaseURLResourceLoader implements ResourceLoader{

    private final URL baseUrl;

    public BaseURLResourceLoader(URL baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public URL getBaseUrl() {
        return baseUrl;
    }

    @Override
    public void load(ResourceStorage resourceStorage) throws Exception {

    }

    @Override
    public void close() throws Exception {

    }
}
