package com.obast.charer.system.dto.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.ProductTypeEnum;
import com.obast.charer.model.protocol.Protocol;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ProtocolVo")
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Protocol.class)
public class ProtocolVo extends BaseDto {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "协议id")
    @ExcelProperty(value = "协议id")
    private String id;

    @ApiModelProperty(value = "协议Key")
    @ExcelProperty(value = "协议Key")
    private String protocolKey;

    @ApiModelProperty(value = "类型")
    private ProductTypeEnum type;

    @ApiModelProperty(value = "协议名称")
    @ExcelProperty(value = "协议名称")
    private String name;

    @ApiModelProperty(value = "生产商")
    @ExcelProperty(value = "生产商")
    private String manufacturer;

    @ApiModelProperty(value = "状态")
    @ExcelProperty(value = "状态")
    private EnableStatusEnum status;

}
