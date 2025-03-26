package com.obast.charer.model.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskLog {

    private String id;

    private String taskId;

    private String content;

    private Boolean success;

    private Long logAt;
}
