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

package com.obast.charer.common.websocket.utils;

import com.obast.charer.common.model.LoginUser;
import com.obast.charer.common.redis.utils.RedisUtils;
import com.obast.charer.common.websocket.dto.WebSocketMessageDto;
import cn.hutool.core.collection.CollUtil;
import com.obast.charer.common.websocket.holder.WebSocketSessionHolder;
import com.obast.charer.common.websocket.constant.WebSocketConstants;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 工具类
 *
 * @author zendwang
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WebSocketUtils {

    /**
     * 发送消息
     *
     * @param sessionKey session主键 一般为用户id
     * @param message    消息文本
     */
    public static void sendMessage(String sessionKey, String message) {
        WebSocketSession session = WebSocketSessionHolder.getSessions(sessionKey);
        sendMessage(session, message);
    }

    /**
     * 订阅消息
     *
     * @param consumer 自定义处理
     */
    public static void subscribeMessage(Consumer<WebSocketMessageDto> consumer) {
        RedisUtils.subscribe(WebSocketConstants.WEB_SOCKET_TOPIC, WebSocketMessageDto.class, consumer);
    }

    /**
     * 发布订阅的消息
     *
     * @param webSocketMessage 消息对象
     */
    public static void publishMessage(WebSocketMessageDto webSocketMessage) {
        List<String> unsentSessionKeys = new ArrayList<>();
        // 当前服务内session,直接发送消息
        for (String sessionKey : webSocketMessage.getSessionKeys()) {
            if (WebSocketSessionHolder.existSession(sessionKey)) {
                WebSocketUtils.sendMessage(sessionKey, webSocketMessage.getMessage());
                continue;
            }
            unsentSessionKeys.add(sessionKey);
        }
        // 不在当前服务内session,发布订阅消息
        if (CollUtil.isNotEmpty(unsentSessionKeys)) {
            WebSocketMessageDto broadcastMessage = new WebSocketMessageDto();
            broadcastMessage.setMessage(webSocketMessage.getMessage());
            broadcastMessage.setSessionKeys(unsentSessionKeys);
            RedisUtils.publish(WebSocketConstants.WEB_SOCKET_TOPIC, broadcastMessage, consumer -> {
                log.info(" WebSocket发送主题订阅消息topic:{} session keys:{} message:{}",
                    WebSocketConstants.WEB_SOCKET_TOPIC, unsentSessionKeys, webSocketMessage.getMessage());
            });
        }
    }

    public static void sendPongMessage(WebSocketSession session) {
        sendMessage(session, new PongMessage());
    }

    public static void sendMessage(WebSocketSession session, String message) {
        sendMessage(session, new TextMessage(message));
    }

    private static void sendMessage(WebSocketSession session, WebSocketMessage<?> message) {
        if (session == null || !session.isOpen()) {
            log.error("[send] session会话已经关闭");
        } else {
            try {
                // 获取当前会话中的用户
                LoginUser loginUser = (LoginUser) session.getAttributes().get(WebSocketConstants.LOGIN_USER_KEY);
                session.sendMessage(message);
                log.info("[send] sessionId: {},userId:{},userType:{},message:{}", session.getId(), loginUser.getUserId(), loginUser.getUserType(), message);
            } catch (IOException e) {
                log.error("[send] session({}) 发送消息({}) 异常", session, message, e);
            }
        }
    }
}
