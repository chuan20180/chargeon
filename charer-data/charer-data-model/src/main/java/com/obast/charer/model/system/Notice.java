package com.obast.charer.model.system;

import com.obast.charer.enums.NoticeStateEnum;
import com.obast.charer.enums.NoticeTypeEnum;
import com.obast.charer.common.i18n.I18nField;
import com.obast.charer.model.Id;
import com.obast.charer.model.TenantModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


/**
 * 通知公告视图对象 sys_notice
 *
 * @author Michelle.Chung
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Notice extends TenantModel implements Id<String>, Serializable {
    private static final long serialVersionUID = 1L;

    private String id;

    private I18nField title;

    private NoticeTypeEnum type;

    private I18nField content;

    private NoticeStateEnum state;

}
