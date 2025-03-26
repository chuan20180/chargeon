package com.obast.charer.system.dto.vo;

import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.AgentModel;
import com.obast.charer.model.system.SysDealer;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysDealer.class)
public class SysDealerVo extends AgentModel {
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "id")
    private String id;

    @ExcelProperty(value = "分帐名称")
    private String name;

    @ApiModelProperty(value = "联系人")
    private String contactPerson;

    @ApiModelProperty(value = "联系电话")
    private String contactMobile;

    @ApiModelProperty(value = "关联菜单id")
    private List<String> menuIds;

    @ApiModelProperty(value = "状态")
    private EnableStatusEnum status;

    @ApiModelProperty(value = "备注")
    private String note;
}
