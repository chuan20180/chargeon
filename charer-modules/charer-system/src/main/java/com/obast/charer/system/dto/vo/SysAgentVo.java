package com.obast.charer.system.dto.vo;

import com.obast.charer.common.Decimal2Serializer;
import com.obast.charer.system.dto.vo.station.StationVo;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.TenantModel;
import com.obast.charer.model.system.SysAgent;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysAgent.class)
public class SysAgentVo extends TenantModel {
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "id")
    private String id;

    @ExcelProperty(value = "分帐名称")
    private String name;

    @ApiModelProperty(value = "联系人")
    private String contactPerson;

    @ApiModelProperty(value = "联系电话")
    private String contactMobile;

    @ApiModelProperty(value = "状态")
    private EnableStatusEnum status;

    @ApiModelProperty(value = "关联菜单id")
    private List<String> menuIds;

    @ApiModelProperty(value = "运营商分成比例")
    @JsonSerialize(using = Decimal2Serializer.class)
    private BigDecimal tenantProfitPercent;

    @ApiModelProperty(value = "备注")
    private String note;

    @ApiModelProperty(value = "适用场站名称")
    private List<StationVo> stations;
}
