package com.obast.charer.system.dto.vo.sms;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.TplTypeEnum;
import com.obast.charer.model.sms.SmsConfig;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "SmsConfigVo")
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SmsConfig.class)
public class SmsConfigVo extends BaseDto {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    @ExcelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "短信提供商名称")
    @ExcelProperty(value = "短信提供商名称")
    private String name;

    @ApiModelProperty(value = "标识符")
    @ExcelProperty(value = "标识符")
    private String identifier;

    @ApiModelProperty(value = "模板类型，1, id, 2, 内容")
    @ExcelProperty(value = "模板类型")
    private TplTypeEnum tplType;

    @ApiModelProperty(value = "属性")
    private String properties;

    @ApiModelProperty(value = "状态")
    private EnableStatusEnum status;
}