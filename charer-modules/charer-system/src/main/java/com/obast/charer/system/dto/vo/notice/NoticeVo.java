package com.obast.charer.system.dto.vo.notice;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.NoticeStateEnum;
import com.obast.charer.enums.NoticeTypeEnum;
import com.obast.charer.common.i18n.I18nField;
import com.obast.charer.model.system.Notice;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 通知公告视图对象 sys_notice
 *
 * @author Michelle.Chung
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AutoMapper(target = Notice.class)
public class NoticeVo extends BaseDto {
    private static final long serialVersionUID = 1L;

    private String id;

    private I18nField title;

    private NoticeTypeEnum type;

    private I18nField content;

    private NoticeStateEnum state;
}
