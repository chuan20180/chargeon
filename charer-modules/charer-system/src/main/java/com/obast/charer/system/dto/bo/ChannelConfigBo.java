package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.ChannelIdentifierEnum;
import com.obast.charer.model.notify.ChannelConfig;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@ApiModel(value = "ChannelConfigBo")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = ChannelConfig.class)
public class ChannelConfigBo extends BaseDto  {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value="id")
	private String id;

	@ApiModelProperty(value = "配置名称")
	private String name;

	@ApiModelProperty(value = "识别符")
	public ChannelIdentifierEnum identifier;

	@ApiModelProperty(value = "配置参数")
	private String properties;

	@ApiModelProperty(value = "描述")
	private String note;

}
