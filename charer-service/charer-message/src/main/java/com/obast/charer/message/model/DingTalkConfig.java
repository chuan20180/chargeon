package com.obast.charer.message.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author sjg
 */
@Data
public class DingTalkConfig implements Serializable {
    private String dingTalkWebhook;
}
