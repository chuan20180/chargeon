package com.obast.charer.system.service.system;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;

import com.obast.charer.system.dto.bo.PushConfigBo;
import com.obast.charer.system.dto.vo.push.PushConfigVo;
import com.obast.charer.qo.PushConfigQueryBo;

import java.util.List;

public interface IPushConfigManagerService {
    Paging<PushConfigVo> queryPageList(PageRequest<PushConfigQueryBo> pageRequest);

    List<PushConfigVo> queryList(PageRequest<PushConfigQueryBo> pageRequest);

    PushConfigVo queryDetail(String sysConfigId);

    boolean update(PushConfigBo data);

    void updateStatus(PushConfigBo bo);
}
