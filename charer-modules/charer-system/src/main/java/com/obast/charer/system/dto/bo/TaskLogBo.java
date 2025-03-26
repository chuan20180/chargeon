package com.obast.charer.system.dto.bo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.model.task.TaskLog;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel(value = "TaskLogBo")
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = TaskLog.class, reverseConvertGenerate = false)
public class TaskLogBo extends BaseDto {

    private String id;

    private String taskId;

    private String content;

    private Boolean success;

    private Long logAt;
}
