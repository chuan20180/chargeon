package com.obast.charer.data.model;

import com.obast.charer.data.base.BaseEntity;
import com.obast.charer.enums.AdsTypeEnum;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.common.i18n.I18nField;
import com.obast.charer.model.ads.Ads;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "ads")
@ApiModel(value = "广告信息")
@AutoMapper(target = Ads.class)
public class TbAds extends BaseEntity {

    @Id
    @GeneratedValue(generator = "SnowflakeIdGenerator")
    @GenericGenerator(name = "SnowflakeIdGenerator", strategy = "com.obast.charer.data.config.id.SnowflakeIdGenerator")
    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "租户编号")
    private String tenantId;

    @ApiModelProperty(value = "名称")
    @Type(type = "json")
    private I18nField name;

    @ApiModelProperty(value = "类型")
    @Convert(converter = AdsTypeEnum.Converter.class)
    private AdsTypeEnum type;

    @ApiModelProperty(value = "html内容")
    @Type(type = "json")
    private I18nField content;

    @ApiModelProperty(value = "链接")
    private String link;

    @ApiModelProperty(value = "img")
    private String img;

    @ApiModelProperty(value = "描述")
    private String note;

    @ApiModelProperty(value = "状态")
    @Convert(converter = EnableStatusEnum.Converter.class)
    private EnableStatusEnum status;
}