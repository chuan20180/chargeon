package com.obast.charer.system.dto.vo.taskinfo;

import com.obast.charer.model.task.TaskLog;
import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.io.Serializable;

@ApiModel(value = "TaskLogVo")
@Data
@AutoMapper(target = TaskLog.class)
public class TaskLogVo implements Serializable {
    private static final long serialVersionUID = -1L;

    private String id;

    private String taskId;

    private String content;

    private Boolean success;

    private Long logAt;
}
