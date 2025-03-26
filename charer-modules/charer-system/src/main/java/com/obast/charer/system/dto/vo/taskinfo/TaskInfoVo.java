package com.obast.charer.system.dto.vo.taskinfo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.obast.charer.common.api.BaseDto;
import com.obast.charer.model.rule.RuleAction;
import com.obast.charer.model.task.TaskInfo;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "TaskInfoVo")
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = TaskInfo.class)
public class TaskInfoVo extends BaseDto {

    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value = "主键")
    @ExcelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "任务输出")
    @ExcelProperty(value = "任务输出")
    private List<RuleAction> actions;

    @ApiModelProperty(value = "描述")
    @ExcelProperty(value = "描述")
    private String note;

    @ApiModelProperty(value = "表达式")
    @ExcelProperty(value = "表达式")
    private String expression;

    @ApiModelProperty(value = "任务名称")
    @ExcelProperty(value = "任务名称")
    private String name;

    @ApiModelProperty(value = "操作备注")
    @ExcelProperty(value = "操作备注")
    private String reason;

    @ApiModelProperty(value = "任务状态")
    @ExcelProperty(value = "任务状态")
    private String state;

    @ApiModelProperty(value = "任务类型")
    @ExcelProperty(value = "任务类型")
    private String type;

    public Integer getSeconds() {
        if (TaskInfo.TYPE_DELAY.equals(getType())) {
            return Integer.parseInt(getExpression());
        }
        return null;
    }

}
