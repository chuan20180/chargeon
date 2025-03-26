package com.obast.charer.system.service.system;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.log.event.OperLogEvent;
import com.obast.charer.system.dto.bo.SysOperLogBo;
import com.obast.charer.system.dto.vo.SysOperLogVo;

import java.util.Collection;
import java.util.List;

/**
 * 操作日志 服务层
 *
 * @author Lion Li
 */
public interface ISysOperLogService {

    Paging<SysOperLogVo> selectPageOperLogList(PageRequest<?> query);

    void recordLog(OperLogEvent operLogEvent);
    /**
     * 新增操作日志
     *
     * @param bo 操作日志对象
     */
    void insertOperlog(SysOperLogBo bo);

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    List<SysOperLogVo> selectOperLogList(SysOperLogBo operLog);

    /**
     * 批量删除系统操作日志
     *
     * @param operIds 需要删除的操作日志ID
     * @return 结果
     */
    void deleteOperLogByIds(Collection<String> operIds);

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    SysOperLogVo selectOperLogById(String operId);

    /**
     * 清空操作日志
     */
    void cleanOperLog();
}
