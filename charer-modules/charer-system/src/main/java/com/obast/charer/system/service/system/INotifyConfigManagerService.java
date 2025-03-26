package com.obast.charer.system.service.system;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;

import com.obast.charer.system.dto.bo.NotifyConfigBo;
import com.obast.charer.system.dto.vo.notify.NotifyConfigVo;
import com.obast.charer.qo.NotifyConfigQueryBo;

import java.util.List;

public interface INotifyConfigManagerService {
    Paging<NotifyConfigVo> queryPageList(PageRequest<NotifyConfigQueryBo> pageRequest);

    List<NotifyConfigVo> queryList(PageRequest<NotifyConfigQueryBo> pageRequest);

    NotifyConfigVo queryDetail(String sysConfigId);

    boolean add(NotifyConfigBo data);

    boolean update(NotifyConfigBo data);

    void updateStatus(NotifyConfigBo bo);
}
