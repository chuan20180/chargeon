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
public class NoticeMessage {
    private String title;
    private String content;
    private Map<String, Object> param;
    
    public String getFormatContent() {
        String fmt = content;
        for (String key : param.keySet()) {
            Object val = param.get(key);
            fmt = fmt.replace("${" + key + "}", val == null ? "" : val.toString());
        }
        return fmt;
    }
}
