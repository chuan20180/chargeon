package com.obast.charer.data.model;

import com.obast.charer.data.base.BaseEntity;
import com.obast.charer.enums.NoticeStateEnum;
import com.obast.charer.enums.NoticeTypeEnum;
import com.obast.charer.common.i18n.I18nField;
import com.obast.charer.model.system.Notice;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * 通知公告表 sys_notice
 *
 * @author Lion Li
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "notice")
@AutoMapper(target = Notice.class)
@ApiModel(value = "通知公告表")
public class TbNotice extends BaseEntity {

    /**
     * 公告ID
     */
    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    @ApiModelProperty(value = "公告ID")
    private String id;

    @ApiModelProperty(value = "租户编号")
    private String tenantId;

    @ApiModelProperty(value = "公告标题")
    @Type(type = "json")
    private I18nField title;

    @ApiModelProperty(value = "公告类型（1通知 2公告）")
    @Convert(converter = NoticeTypeEnum.Converter.class)
    private NoticeTypeEnum type;

    @ApiModelProperty(value = "公告内容")
    @Type(type = "json")
    private I18nField content;

    @ApiModelProperty(value = "公告状态")
    @Convert(converter = NoticeStateEnum.Converter.class)
    private NoticeStateEnum state;

}
