package com.obast.charer.system.service.platform.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.satoken.util.LoginHelper;
import com.obast.charer.data.business.ITaskInfoData;
import com.obast.charer.model.task.TaskInfo;
import com.obast.charer.model.task.TaskLog;
import com.obast.charer.qo.TaskInfoQueryBo;
import com.obast.charer.system.dto.bo.TaskInfoBo;
import com.obast.charer.system.dto.bo.TaskLogBo;
import com.obast.charer.system.dto.vo.taskinfo.TaskInfoVo;
import com.obast.charer.system.dto.vo.taskinfo.TaskLogVo;
import com.obast.charer.system.service.platform.ITaskManagerService;
import com.obast.charer.system.task.TaskManager;
import com.obast.charer.temporal.ITaskLogData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class TaskManagerServiceImpl implements ITaskManagerService {
    @Autowired
    private ITaskInfoData taskInfoData;

    @Autowired
    private TaskManager taskManager;

    @Autowired
    private ITaskLogData taskLogData;

    @Override
    public Paging<TaskInfoVo> queryPageList(PageRequest<TaskInfoQueryBo> request) {
        return taskInfoData.findPage(request).to(TaskInfoVo.class);
    }

    @Override
    public boolean saveTask(TaskInfoBo bo) {
        TaskInfo taskInfo = bo.to(TaskInfo.class);
        taskInfoData.save(taskInfo);
        return true;
    }

    @Override
    public boolean pauseTask(String taskId) {
        TaskInfo taskInfo = taskInfoData.findById(taskId);
        if (taskInfo == null) {
            throw new BizException(ErrCode.TASK_NOT_FOUND);
        }
        taskManager.pauseTask(taskId, "stop by " + LoginHelper.getUserId());
        return true;
    }

    @Override
    public boolean resumeTask(String taskId) {
        TaskInfo taskInfo = taskInfoData.findById(taskId);
        if (taskInfo == null) {
            throw new BizException(ErrCode.TASK_NOT_FOUND);
        }
        taskManager.resumeTask(taskId, "resume by " + LoginHelper.getUserId());
        return true;
    }

    @Override
    public boolean renewTask(String taskId) {
        TaskInfo taskInfo = taskInfoData.findById(taskId);
        if (taskInfo == null) {
            throw new BizException(ErrCode.TASK_NOT_FOUND);
        }
        try {
            taskManager.renewTask(taskInfo);
            taskManager.updateTaskState(taskId, TaskInfo.STATE_RUNNING, "renew by " + LoginHelper.getUserId());
        } catch (SchedulerException e) {
            log.error("renew task error");
            throw new BizException(ErrCode.RENEW_TASK_ERROR);
        }
        return true;
    }

    @Override
    public boolean deleteTask(String taskId) {
        TaskInfo taskInfo = taskInfoData.findById(taskId);
        if (taskInfo == null) {
            throw new BizException(ErrCode.TASK_NOT_FOUND);
        }
        taskManager.deleteTask(taskId, "delete by " + LoginHelper.getUserId());
        taskInfoData.deleteById(taskId);
        try {
            taskLogData.deleteByTaskId(taskId);
        } catch (Throwable e) {
            log.error("delete task logs failed", e);
        }
        return true;
    }

    @Override
    public Paging<TaskLogVo> selectTaskLogPageList(PageRequest<TaskLogBo> request) {
        TaskLog taskLog = request.getData().to(TaskLog.class);
        Paging<TaskLog> byTaskId = taskLogData.findByTaskId(taskLog.getTaskId(), request.getPageNum(), request.getPageSize());
        return byTaskId.to(TaskLogVo.class);
    }

    @Override
    public boolean clearTaskLogs(String taskId) {
        taskLogData.deleteByTaskId(taskId);
        return true;
    }
}
