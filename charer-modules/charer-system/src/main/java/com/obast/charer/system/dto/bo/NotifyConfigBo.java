package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.NotifyIdentifierEnum;
import com.obast.charer.model.notify.NotifyConfig;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@ApiModel(value = "NotifyConfigBo")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = NotifyConfig.class)
public class NotifyConfigBo extends BaseDto  {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value="id")
	private String id;

	@ApiModelProperty(value="配置名称")
	private String name;

	@ApiModelProperty(value = "识别符")
	public NotifyIdentifierEnum identifier;

	@ApiModelProperty(value="配置参数")
	private NotifyConfig.Properties properties;

	@ApiModelProperty(value = "状态")
	private EnableStatusEnum status;

	@ApiModelProperty(value = "描述")
	private String note;

}
