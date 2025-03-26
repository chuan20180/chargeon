package com.obast.charer.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.qo.TaskInfoQueryBo;
import com.obast.charer.system.dto.bo.TaskInfoBo;
import com.obast.charer.system.dto.bo.TaskLogBo;
import com.obast.charer.system.dto.vo.taskinfo.TaskInfoVo;
import com.obast.charer.system.dto.vo.taskinfo.TaskLogVo;
import com.obast.charer.system.service.platform.ITaskManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：定时任务管理
 */
@Api(tags = {"定时任务管理"})
@Slf4j
@RestController
@RequestMapping("/admin/task")
public class SysTaskController {

    @Autowired
    ITaskManagerService taskManagerService;

    @ApiOperation("分页列表")
    @SaCheckPermission("platform:task:list")
    @PostMapping("/list")
    public Paging<TaskInfoVo> tasks(@Validated @RequestBody PageRequest<TaskInfoQueryBo> request) {
        return taskManagerService.queryPageList(request);
    }

    @ApiOperation("修改")
    @SaCheckPermission("platform:task:edit")
    @PostMapping("/save")
    public boolean saveTask(@Validated @RequestBody Request<TaskInfoBo> taskInfo) {
        return taskManagerService.saveTask(taskInfo.getData());
    }

    @ApiOperation("停止")
    @SaCheckPermission("platform:task:edit")
    @PostMapping("/pause")
    public boolean pauseTask(@Validated @RequestBody Request<String> request) {
        String taskId = request.getData();
        return taskManagerService.pauseTask(taskId);
    }

    @ApiOperation("恢复")
    @SaCheckPermission("platform:task:edit")
    @PostMapping("/resume")
    public boolean resumeTask(@Validated @RequestBody Request<String> request) {
        return taskManagerService.resumeTask(request.getData());
    }

    @ApiOperation("更新")
    @SaCheckPermission("platform:task:edit")
    @PostMapping("/renew")
    public boolean renewTask(@Validated @RequestBody Request<String> request) {
        String taskId = request.getData();
        return taskManagerService.renewTask(taskId);

    }

    @ApiOperation("删除")
    @SaCheckPermission("platform:task:delete")
    @PostMapping("/delete")
    public boolean deleteTask(@Validated @RequestBody Request<String> request) {
        String taskId = request.getData();
        return taskManagerService.deleteTask(taskId);

    }

    @ApiOperation("日志列表")
    @SaCheckPermission("platform:task:query")
    @PostMapping("/logs")
    public Paging<TaskLogVo> getTaskLogs(@Validated @RequestBody PageRequest<TaskLogBo> request) {
        return taskManagerService.selectTaskLogPageList(request);
    }

    @ApiOperation("清除定时任务日志")
    @SaCheckPermission("platform:task:delete")
    @PostMapping("/taskClear")
    public boolean clearTaskLogs(@Validated @RequestBody Request<String> request) {
        return taskManagerService.clearTaskLogs(request.getData());
    }
}
