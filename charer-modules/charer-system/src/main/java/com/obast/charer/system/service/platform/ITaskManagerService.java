package com.obast.charer.system.service.platform;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.qo.TaskInfoQueryBo;
import com.obast.charer.system.dto.bo.TaskInfoBo;
import com.obast.charer.system.dto.bo.TaskLogBo;
import com.obast.charer.system.dto.vo.taskinfo.TaskInfoVo;
import com.obast.charer.system.dto.vo.taskinfo.TaskLogVo;

public interface ITaskManagerService {

    Paging<TaskInfoVo> queryPageList(PageRequest<TaskInfoQueryBo> request);

    boolean saveTask(TaskInfoBo taskInfo);

    boolean pauseTask(String taskId);

    boolean resumeTask(String data);

    boolean renewTask(String taskId);

    boolean deleteTask(String taskId);

    Paging<TaskLogVo> selectTaskLogPageList(PageRequest<TaskLogBo> request);

    boolean clearTaskLogs(String taskId);
}
