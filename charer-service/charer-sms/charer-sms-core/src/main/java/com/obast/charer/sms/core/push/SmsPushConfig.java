package com.obast.charer.sms.core.push;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SmsPushConfig {
    /**
     * 短信模板内容
     */
    private String content;
}
