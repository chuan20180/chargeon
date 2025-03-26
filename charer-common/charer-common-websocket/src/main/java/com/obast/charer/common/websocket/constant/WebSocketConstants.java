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

package com.obast.charer.common.websocket.constant;

/**
 * websocket的常量配置
 *
 * @author zendwang
 */
public interface WebSocketConstants {
    /**
     * websocketSession中的参数的key
     */
    String LOGIN_USER_KEY = "loginUser";

    /**
     * 订阅的频道
     */
    String WEB_SOCKET_TOPIC = "global:websocket";

    /**
     * 前端心跳检查的命令
     */
    String PING = "ping";

    /**
     * 服务端心跳恢复的字符串
     */
    String PONG = "pong";
}
