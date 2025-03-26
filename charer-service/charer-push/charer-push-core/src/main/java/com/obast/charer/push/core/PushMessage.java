package com.obast.charer.push.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ Author：chuan
 * @ Date：2024-10-05-04:40
 * @ Version：1.0
 * @ Description：SmsConfig
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushMessage {
    private String type;
    private Object data;
    private String title;
    private String content;
}
