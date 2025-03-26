package com.obast.charer.openapi.dto.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.ProductTypeEnum;
import com.obast.charer.model.product.Product;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "OpenProductVo")
@Data
@AutoMapper(target = Product.class, convertGenerate = false)
public class OpenProductVo extends BaseDto {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "产品id")
    @ExcelProperty(value = "产品id")
    private String id;

    @ApiModelProperty(value = "产品Key")
    @ExcelProperty(value = "产品Key")
    private String productKey;

    @ApiModelProperty(value = "产品密钥")
    @ExcelProperty(value = "产品密钥")
    private String productSecret;

    @ApiModelProperty(value = "协议Key")
    @ExcelProperty(value = "协议Key")
    private String protocolKey;

    @ApiModelProperty(value = "品类")
    @ExcelProperty(value = "品类")
    private String category;

    @ApiModelProperty(value = "产品类型")
    private ProductTypeEnum type;

    @ApiModelProperty(value = "图片")
    @ExcelProperty(value = "图片")
    private String img;

    @ApiModelProperty(value = "产品名称")
    @ExcelProperty(value = "产品名称")
    private String name;

    @ApiModelProperty(value = "生产商")
    @ExcelProperty(value = "生产商")
    private String manufacturer;

    @ApiModelProperty(value = "保活时长（秒）")
    @ExcelProperty(value = "保活时长（秒）")
    private Long keepAliveTime;

    @ApiModelProperty(value = "状态")
    @ExcelProperty(value = "状态")
    private EnableStatusEnum status;
}