package com.obast.charer.push.wechat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * author: 石恒
 * date: 2023-05-08 15:15
 * description:
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WechatPushMessage {
    private Map<String, Object> param;
}
