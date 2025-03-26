package com.obast.charer.temporal.es.service;

import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.model.task.TaskLog;
import com.obast.charer.temporal.ITaskLogData;
import com.obast.charer.temporal.es.dao.TaskLogRepository;
import com.obast.charer.temporal.es.document.DocTaskLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class TaskLogDataImpl implements ITaskLogData {

    @Autowired
    private TaskLogRepository taskLogRepository;

    @Override
    public void deleteByTaskId(String taskId) {
        taskLogRepository.deleteByTaskId(taskId);
    }

    @Override
    public Paging<TaskLog> findByTaskId(String taskId, int page, int size) {
        Page<DocTaskLog> paged = taskLogRepository.findByTaskIdOrderByLogAtDesc(taskId, Pageable.ofSize(size).withPage(page - 1));
        return new Paging<>(paged.getTotalElements(),
                paged.getContent().stream().map(o -> MapstructUtils.convert(o, TaskLog.class))
                        .collect(Collectors.toList()));
    }

    @Override
    public void add(TaskLog log) {
        taskLogRepository.save(MapstructUtils.convert(log, DocTaskLog.class));
    }
}
