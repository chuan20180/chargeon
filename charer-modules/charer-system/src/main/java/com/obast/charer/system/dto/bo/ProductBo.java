package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.common.validate.AddGroup;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.ProductTypeEnum;
import com.obast.charer.model.product.Product;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@ApiModel(value = "ProductBo")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = Product.class, reverseConvertGenerate = false)
public class ProductBo extends BaseDto {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "productKey")
    private String productKey;

    @ApiModelProperty(value = "产品密钥")
    @Size(max = 255, message = "产品密钥长度不正确")
    private String productSecret;

    @ApiModelProperty(value = "协议Key")
    @Size(max = 255, message = "协议Key长度不正确")
    private String protocolKey;

    @ApiModelProperty(value = "品类")
    @Size(max = 255, message = "品类长度不正确")
    private String category;

    @ApiModelProperty(value = "产品类型")
    @NotNull(message = "保活时长不能为空",groups = { AddGroup.class, EditGroup.class })
    private ProductTypeEnum type;

    @ApiModelProperty(value = "图片")
    @Size(max = 255, message = "图片长度不正确")
    private String img;

    @ApiModelProperty(value = "产品名称")
    @Size(max = 255, message = "产品名称长度不正确")
    private String name;

    @ApiModelProperty(value = "生产商")
    private String manufacturer;

    @ApiModelProperty(value = "保活时长")
    @NotNull(message = "保活时长不能为空",groups = { AddGroup.class, EditGroup.class })
    private Long keepAliveTime;


    @ApiModelProperty(value = "状态")

    private EnableStatusEnum status;

}
