package com.obast.charer.system.dto.vo;

import com.obast.charer.system.dto.vo.station.StationVo;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.TenantModel;
import com.obast.charer.model.system.SysAgentStation;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = SysAgentStation.class)
public class SysAgentStationVo extends TenantModel {
    private static final long serialVersionUID = 1L;

    @ExcelProperty(value = "id")
    private String id;

    @ExcelProperty(value = "代理商id")
    private String agentId;

    @ExcelProperty(value = "场站id")
    private String stationId;

    @ApiModelProperty(value = "状态")
    private EnableStatusEnum status;

    @ApiModelProperty(value = "备注")
    private String note;

    private StationVo station;
}
