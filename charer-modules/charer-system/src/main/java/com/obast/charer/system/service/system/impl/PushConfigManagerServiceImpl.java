package com.obast.charer.system.service.system.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.IPushConfigData;

import com.obast.charer.system.dto.bo.PushConfigBo;
import com.obast.charer.system.dto.vo.push.PushConfigVo;
import com.obast.charer.system.service.system.IPushConfigManagerService;

import com.obast.charer.model.push.PushConfig;
import com.obast.charer.qo.PushConfigQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：短信配置管理服务实现
 */
@Service
public class PushConfigManagerServiceImpl implements IPushConfigManagerService {

    @Autowired
    private IPushConfigData pushConfigData;

    @Override
    public Paging<PushConfigVo> queryPageList(PageRequest<PushConfigQueryBo> pageRequest) {
        return MapstructUtils.convert(pushConfigData.findPage(pageRequest), PushConfigVo.class);
    }

    @Override
    public List<PushConfigVo> queryList(PageRequest<PushConfigQueryBo> pageRequest) {
        return MapstructUtils.convert(pushConfigData.findList(pageRequest.getData()), PushConfigVo.class);
    }

    @Override
    public PushConfigVo queryDetail(String sysConfigId) {
        return MapstructUtils.convert(pushConfigData.findById(sysConfigId), PushConfigVo.class);
    }

    @Override
    public boolean update(PushConfigBo bo) {
        PushConfig di = bo.to(PushConfig.class);
        return pushConfigData.save(di) != null;
    }

    @Override
    public void updateStatus(PushConfigBo bo) {
        PushConfig pushConfig = pushConfigData.findById(bo.getId());
        pushConfig.setStatus(bo.getStatus());
        pushConfigData.save(pushConfig);
    }
}