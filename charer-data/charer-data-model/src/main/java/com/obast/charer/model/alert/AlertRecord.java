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
public class AlertRecord implements Owned<String> {

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
     * 告警严重度（1-5）
     */
    private String level;

    /**
     * 告警时间
     */
    private Long alertTime;

    /**
     * 告警详情
     */
    private String details;

    /**
     * 是否已读
     */
    private Boolean readFlg;


}
