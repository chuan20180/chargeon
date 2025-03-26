package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.common.validate.AddGroup;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.enums.NoticeStateEnum;
import com.obast.charer.enums.NoticeTypeEnum;
import com.obast.charer.common.i18n.I18nField;
import com.obast.charer.model.system.Notice;
import io.github.linpeilie.annotations.AutoMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通知公告业务对象 notice
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Notice.class)
public class NoticeBo extends BaseDto {

    @NotNull(message = "公告ID不能为空", groups = { EditGroup.class })
    private String id;

    @NotBlank(message = "公告标题不能为空", groups = { AddGroup.class, EditGroup.class })
    private I18nField title;

    @NotBlank(message = "公告类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private NoticeTypeEnum type;

    private I18nField content;

    private NoticeStateEnum state;
}
