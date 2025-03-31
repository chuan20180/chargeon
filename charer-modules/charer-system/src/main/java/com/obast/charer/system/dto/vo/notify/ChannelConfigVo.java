package com.obast.charer.system.dto.vo.notify;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.ChannelIdentifierEnum;
import com.obast.charer.model.notify.ChannelConfig;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ChannelConfigVo")
@Data
@AutoMapper(target = ChannelConfig.class)
public class ChannelConfigVo extends BaseDto {

    @ApiModelProperty(value="id")
    private Long id;

    @ApiModelProperty(value="配置名称")
    private String name;

    @ApiModelProperty(value = "识别符")
    public ChannelIdentifierEnum identifier;

    @ApiModelProperty(value="通道配置参数")
    private String properties;

    @ApiModelProperty(value = "描述")
    private String note;

}
