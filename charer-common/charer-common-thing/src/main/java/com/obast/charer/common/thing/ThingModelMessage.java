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
package com.obast.charer.common.thing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 物模型消息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThingModelMessage {
    public static final String MODEL_CHARGER = "charger";
    public static final String TYPE_LIFETIME = "lifetime";
    public static final String TYPE_STATE = "state";
    public static final String TYPE_PROPERTY = "property";
    public static final String TYPE_EVENT = "push";
    public static final String TYPE_SERVICE = "service";
    public static final String TYPE_OTA = "ota";
    public static final String TYPE_CONFIG = "config";
    public static final String ID_PROPERTY_GET = "get";
    public static final String ID_PROPERTY_SET = "set";
    public static final String ID_PROPERTY_REPORT = "report";
    public static final String ID_CONFIG_GET = "get";
    public static final String ID_CONFIG_SET = "set";
    public static final String ID_DEREGISTER = "deregister";
    public static final String ID_ONLINE = "online";
    public static final String ID_OFFLINE = "offline";
    public static final String SERVICE_REPLY_SUFFIX = "_reply";

    private String id;

    private String mid;

    private String productKey;

    private String deviceDn;

    private String invoke;

    private String directive;

    private String uid;

    //产品类型
    private String type;

    private String identifier;

    /**
     * 消息状态码
     */
    private int code;

    private Object data;


    private String pack;

    /**
     * 时间戳，设备上的事件或数据产生的本地时间
     */
    private Long occurred;

    /**
     * 消息上报时间
     */
    private Long time;

    public Map<String, Object> dataToMap() {
        Map<String, Object> mapData = new HashMap<>();
        if (data instanceof Map) {
            ((Map<?, ?>) data).forEach((key, value) -> mapData.put(key.toString(), value));
        }
        return mapData;
    }
}
