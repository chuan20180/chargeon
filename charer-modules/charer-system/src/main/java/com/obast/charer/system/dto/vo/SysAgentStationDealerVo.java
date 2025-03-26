package com.obast.charer.system.dto.vo;

import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.TenantModel;
import com.obast.charer.model.system.SysAgentStationDealer;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysAgentStationDealer.class)
public class SysAgentStationDealerVo extends TenantModel {
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "id")
    private String id;

    @ExcelProperty(value = "代理商id")
    private String agentId;

    @ExcelProperty(value = "合作商id")
    private String agentStationId;

    @ExcelProperty(value = "合作商id")
    private String dealerId;

    @ExcelProperty(value = "分润比例")
    private BigDecimal percent;

    @ApiModelProperty(value = "状态")
    private EnableStatusEnum status;

    @ApiModelProperty(value = "备注")
    private String note;
}
