package com.obast.charer.temporal;

import com.obast.charer.common.api.Paging;
import com.obast.charer.model.task.TaskLog;

public interface ITaskLogData {
    void deleteByTaskId(String taskId);

    Paging<TaskLog> findByTaskId(String taskId, int page, int size);

    void add(TaskLog log);
}
