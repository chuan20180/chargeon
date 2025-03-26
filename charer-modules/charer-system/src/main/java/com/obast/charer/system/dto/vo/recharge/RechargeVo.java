package com.obast.charer.system.dto.vo.recharge;

import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.RechargeTypeEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.platform.Recharge;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充值方案视图
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "RechargeVo")
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = Recharge.class,convertGenerate = false)
public class RechargeVo extends BaseModel implements Serializable {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "id")
    @ExcelProperty(value = "id")
    private String id;

    @ApiModelProperty(value = "名称")
    @ExcelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "方案类型")
    private RechargeTypeEnum type;

    @ApiModelProperty(value = "状态")
    private EnableStatusEnum status;

    @ApiModelProperty(value = "描述")
    @ExcelProperty(value = "描述")
    private String note;

    @ApiModelProperty(value = "项目")
    @ExcelProperty(value = "项目")
    private List<RechargeItemVo> items;
}
