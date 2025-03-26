package com.obast.charer.system.service.system.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.log.event.OperLogEvent;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.common.utils.ip.AddressUtils;
import com.obast.charer.data.system.ISysOperLogData;
import com.obast.charer.system.dto.bo.SysOperLogBo;
import com.obast.charer.system.dto.vo.SysOperLogVo;
import com.obast.charer.model.system.SysOperLog;
import com.obast.charer.system.service.system.ISysOperLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 操作日志 服务层处理
 *
 * @author Lion Li
 */
@RequiredArgsConstructor
@Service
public class SysOperLogServiceImpl implements ISysOperLogService {

    private final ISysOperLogData sysOperLogData;

    /**
     * 操作日志记录
     *
     * @param operLogEvent 操作日志事件
     */
    @Override
    public void recordLog(OperLogEvent operLogEvent) {
        SysOperLogBo operLog = MapstructUtils.convert(operLogEvent, SysOperLogBo.class);
        // 远程查询操作地点
        assert operLog != null;
        operLog.setOperLocation(AddressUtils.getRealAddressByIP(operLog.getOperIp()));
        insertOperlog(operLog);
    }

    @Override
    public Paging<SysOperLogVo> selectPageOperLogList(PageRequest<?> query) {
        return sysOperLogData.findAll(query.to(SysOperLog.class)).to(SysOperLogVo.class);
    }

    /**
     * 新增操作日志
     *
     * @param bo 操作日志对象
     */
    @Override
    public void insertOperlog(SysOperLogBo bo) {
        bo.setOperTime(new Date());
        sysOperLogData.save(bo.to(SysOperLog.class));
    }

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    @Override
    public List<SysOperLogVo> selectOperLogList(SysOperLogBo operLog) {
        return MapstructUtils.convert(sysOperLogData.findAllByCondition(operLog.to(SysOperLog.class)),SysOperLogVo.class);
    }

    /**
     * 批量删除系统操作日志
     *
     * @param operIds 需要删除的操作日志ID
     * @return 结果
     */
    @Override
    public void deleteOperLogByIds(Collection<String> operIds) {
        sysOperLogData.deleteByIds(operIds);
    }

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    @Override
    public SysOperLogVo selectOperLogById(String operId) {
        return sysOperLogData.findById(operId).to(SysOperLogVo.class);
    }

    /**
     * 清空操作日志
     */
    @Override
    public void cleanOperLog() {

    }
}
