package com.obast.charer.system.service.platform;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.system.dto.bo.RuleInfoBo;
import com.obast.charer.system.dto.bo.RuleLogBo;
import com.obast.charer.system.dto.bo.TaskInfoBo;
import com.obast.charer.system.dto.bo.TaskLogBo;
import com.obast.charer.system.dto.vo.ruleinfo.RuleInfoVo;
import com.obast.charer.system.dto.vo.ruleinfo.RuleLogVo;
import com.obast.charer.system.dto.vo.taskinfo.TaskInfoVo;
import com.obast.charer.system.dto.vo.taskinfo.TaskLogVo;

/**
 * @Author: jay
 * @Date: 2023/5/30 18:14
 * @Version: V1.0
 * @Description: 规则引擎服务接口
 */
public interface IRuleEngineService {
    Paging<RuleInfoVo> selectPageList(PageRequest<RuleInfoBo> request);

    boolean saveRule(RuleInfoBo ruleInfoBo);

    boolean pauseRule(String ruleId);

    boolean resumeRule(String ruleId);

    boolean deleteRule(String ruleId);

    Paging<RuleLogVo> selectRuleLogPageList(PageRequest<RuleLogBo> request);

    boolean clearRuleLogs(String ruleId);

    Paging<TaskInfoVo> selectTaskPageList(PageRequest<TaskInfoBo> request);

    boolean saveTask(TaskInfoBo taskInfo);

    boolean pauseTask(String taskId);

    boolean resumeTask(String data);

    boolean renewTask(String taskId);

    boolean deleteTask(String taskId);

    Paging<TaskLogVo> selectTaskLogPageList(PageRequest<TaskLogBo> request);

    boolean clearTaskLogs(String taskId);
}
