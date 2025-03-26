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

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

public class DeviceUtil {

    /**
     * 1-13位	时间戳
     * 14-29位	deviceNae，去除非字母和数字，不足16位补0，超过16位的mac取后16位，共16位
     * 30-31位	mac长度，共2位
     * 32位	随机一个0-f字符
     */
    public static String newDeviceId(String deviceNae) {
        int maxDnLen = 16;
        String dn = deviceNae.replaceAll("[^0-9A-Za-z]", "");
        if (dn.length() > maxDnLen) {
            dn = dn.substring(dn.length() - maxDnLen + 1);
        } else {
            dn = (dn + "00000000000000000000").substring(0, maxDnLen);
        }
        String len = StringUtils.leftPad(deviceNae.length() + "", 2, '0');
        String rnd = Integer.toHexString(RandomUtils.nextInt(0, 16));
        return (System.currentTimeMillis() + "0" + dn + len + rnd).toLowerCase();
    }
}
