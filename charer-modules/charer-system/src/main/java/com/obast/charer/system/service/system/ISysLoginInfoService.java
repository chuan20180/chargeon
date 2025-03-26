package com.obast.charer.system.service.system;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.log.event.LogininforEvent;
import com.obast.charer.system.dto.bo.SysLoginInfoBo;
import com.obast.charer.system.dto.vo.SysLoginInfoVo;
import com.obast.charer.qo.SysLoginInfoQueryBo;

import java.util.Collection;
import java.util.List;

/**
 * 系统访问日志情况信息 服务层
 *
 * @author Lion Li
 */
public interface ISysLoginInfoService {

    List<SysLoginInfoVo> queryList(SysLoginInfoQueryBo bo);


    void recordLoginInfo(LogininforEvent logininforEvent);

    /**
     * 新增系统登录日志
     *
     * @param bo 访问日志对象
     */
    void insertLogininfor(SysLoginInfoBo bo);

    /**
     * 查询系统登录日志集合
     *
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */


    /**
     * 批量删除系统登录日志
     *
     * @param infoIds 需要删除的登录日志ID
     * @return 结果
     */
    void deleteLogininforByIds(Collection<String> infoIds);


    Paging<SysLoginInfoVo> findAll(PageRequest<SysLoginInfoBo> query);
}
