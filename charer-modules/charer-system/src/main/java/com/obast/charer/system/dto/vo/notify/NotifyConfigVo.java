package com.obast.charer.system.dto.vo.notify;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.NotifyIdentifierEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.notify.NotifyConfig;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "NotifyConfigVo")
@Data
@AutoMapper(target = NotifyConfig.class)
public class NotifyConfigVo  extends BaseDto {

    @ApiModelProperty(value="id")
    private Long id;

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
