package com.obast.charer.message.model;

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
public class Message {
    private MessageChannel channel;
    private String title;
    private String content;
    private Map<String, Object> param;

    public Message(MessageChannel channel,String content, Map<String, Object> param) {
        this.channel = channel;
        this.content = content;
        this.param = param;
    }

    public Message(MessageChannel channel,Map<String, Object> param) {
        this.channel = channel;
        this.param = param;
    }


    public String getFormatContent() {
        String fmt = content;
        for (String key : param.keySet()) {
            Object val = param.get(key);
            fmt = fmt.replace("${" + key + "}", val == null ? "" : val.toString());
        }
        return fmt;
    }
}
