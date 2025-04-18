package com.obast.charer.model.alert;

import com.obast.charer.model.Owned;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 告警配置
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertConfig implements Owned<String> {

    private String id;

    /**
     * 配置所属用户
     */
    private String uid;

    /**
     * 告警名称
     */
    private String name;

    /**
     * 告警严重度
     */
    private String level;

    /**
     * 关联规则引擎ID
     */
    private String ruleInfoId;

    /**
     * 关联消息转发模板ID
     */
    private String messageTemplateId;

    /**
     * 描述
     */
    private String description;

    /**
     * 是否启用
     */
    private Boolean enable;

}
