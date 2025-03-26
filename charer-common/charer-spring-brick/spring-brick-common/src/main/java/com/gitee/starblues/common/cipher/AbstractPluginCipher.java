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

package com.gitee.starblues.common.cipher;

import com.gitee.starblues.utils.MapValueGetter;
import com.gitee.starblues.utils.ObjectUtils;

import java.util.Map;

/**
 * 抽象的插件解密
 *
 * @author starBlues
 * @since 3.0.1
 * @version 3.0.1
 */
public abstract class AbstractPluginCipher implements PluginCipher{

    protected MapValueGetter parameters;

    protected AbstractPluginCipher(){
    }

    public void initParams(Map<String, Object> params){
        parameters = new MapValueGetter(params);
    }

    @Override
    public String encrypt(String sourceStr) throws Exception {
        if(ObjectUtils.isEmpty(sourceStr)){
            return "";
        }
        return encryptImpl(sourceStr);
    }

    /**
     * 加密实现
     * @param sourceStr 原始字符串
     * @return 加密后的字节
     * @throws Exception 加密异常
     */
    protected abstract String encryptImpl(String sourceStr) throws Exception;


    @Override
    public String decrypt(String cryptoStr) throws Exception {
        if(ObjectUtils.isEmpty(cryptoStr)){
            return "";
        }
        return decryptImpl(cryptoStr);
    }

    /**
     * 解密实现
     * @param cryptoStr 解密字符串
     * @return 解密后的字符
     * @throws Exception 解密异常
     */
    protected abstract String decryptImpl(String cryptoStr) throws Exception;
}
