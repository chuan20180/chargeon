package com.obast.charer.system.dto.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.obast.charer.model.TenantModel;
import com.obast.charer.model.Storage;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "StorageVo")
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Storage.class,convertGenerate = false)
public class StorageVo extends TenantModel {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    @ExcelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "产品Key")
    @ExcelProperty(value = "产品Key")
    private String productKey;

    @ApiModelProperty(value = "设备名称")
    @ExcelProperty(value = "设备名称")
    private String name;

    @ApiModelProperty(value = "设备dn")
    @ExcelProperty(value = "设备dn")
    private String dn;

    @ApiModelProperty(value = "备注")
    @ExcelProperty(value = "备注")
    private String note;

}
