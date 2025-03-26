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

package com.gitee.starblues.plugin.pack.encrypt;

import com.gitee.starblues.common.cipher.AbstractPluginCipher;
import com.gitee.starblues.common.cipher.AesPluginCipher;
import com.gitee.starblues.plugin.pack.PluginInfo;
import com.gitee.starblues.utils.ObjectUtils;
import org.apache.maven.plugin.MojoExecutionException;

import java.util.HashMap;
import java.util.Map;

/**
 * rsa 加密者
 *
 * @author starBlues
 * @since 3.0.1
 * @version 3.0.1
 */
public class AesEncryptPlugin implements EncryptPlugin{


    @Override
    public PluginInfo encrypt(EncryptConfig encryptConfig, PluginInfo pluginInfo) throws Exception{
        AesConfig aesConfig = encryptConfig.getAes();
        if(aesConfig == null){
            return null;
        }

        String secretKey = aesConfig.getSecretKey();
        if(ObjectUtils.isEmpty(secretKey)){
            throw new MojoExecutionException("encryptConfig.aes.secretKey can't be empty");
        }
        AbstractPluginCipher pluginCipher = new AesPluginCipher();
        Map<String, Object> params = new HashMap<>();
        params.put(AesPluginCipher.SECRET_KEY, secretKey);
        pluginCipher.initParams(params);

        String bootstrapClass = pluginInfo.getBootstrapClass();
        String encrypt = pluginCipher.encrypt(bootstrapClass);
        pluginInfo.setBootstrapClass(encrypt);
        return pluginInfo;
    }
}
