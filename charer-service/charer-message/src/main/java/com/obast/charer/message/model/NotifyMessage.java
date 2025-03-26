package com.obast.charer.message.model;

import com.obast.charer.model.notify.NotifyConfig;
import com.obast.charer.model.push.PushConfig;
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
public class NotifyMessage {
    private Map<String, Object> param;
}
