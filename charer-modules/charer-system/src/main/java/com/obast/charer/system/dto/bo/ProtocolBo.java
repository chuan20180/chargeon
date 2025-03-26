package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.ProductTypeEnum;
import com.obast.charer.model.protocol.Protocol;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;


@ApiModel(value = "ProtocolBo")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Protocol.class, reverseConvertGenerate = false)
public class ProtocolBo extends BaseDto {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "protocolKey")
    private String protocolKey;

    @ApiModelProperty(value = "类型")
    private ProductTypeEnum type;

    @ApiModelProperty(value = "名称")
    @Size(max = 255, message = "名称长度不正确")
    private String name;

    @ApiModelProperty(value = "生产商")
    private String manufacturer;

    @ApiModelProperty(value = "状态")

    private EnableStatusEnum status;

}
