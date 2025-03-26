package com.obast.charer.data.business;

import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.task.TaskInfo;
import com.obast.charer.qo.TaskInfoQueryBo;

public interface ITaskInfoData extends ICommonData<TaskInfo, String>, IJPACommonData<TaskInfo, TaskInfoQueryBo, String> {
    TaskInfo update(TaskInfo taskInfo);
}
