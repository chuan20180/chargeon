package com.obast.charer.system.service.system.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.INotifyConfigData;

import com.obast.charer.system.dto.bo.NotifyConfigBo;
import com.obast.charer.system.dto.vo.notify.NotifyConfigVo;
import com.obast.charer.system.service.system.INotifyConfigManagerService;

import com.obast.charer.model.notify.NotifyConfig;
import com.obast.charer.qo.NotifyConfigQueryBo;
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
public class NotifyConfigManagerServiceImpl implements INotifyConfigManagerService {

    @Autowired
    private INotifyConfigData notifyConfigData;

    @Override
    public Paging<NotifyConfigVo> queryPageList(PageRequest<NotifyConfigQueryBo> pageRequest) {
        return MapstructUtils.convert(notifyConfigData.findPage(pageRequest), NotifyConfigVo.class);
    }

    @Override
    public List<NotifyConfigVo> queryList(PageRequest<NotifyConfigQueryBo> pageRequest) {
        return MapstructUtils.convert(notifyConfigData.findList(pageRequest.getData()), NotifyConfigVo.class);
    }

    @Override
    public NotifyConfigVo queryDetail(String sysConfigId) {
        return MapstructUtils.convert(notifyConfigData.findById(sysConfigId), NotifyConfigVo.class);
    }

    @Override
    public boolean add(NotifyConfigBo bo) {
        NotifyConfig di = bo.to(NotifyConfig.class);
        return notifyConfigData.save(di) != null;
    }

    @Override
    public boolean update(NotifyConfigBo bo) {
        NotifyConfig di = bo.to(NotifyConfig.class);
        return notifyConfigData.save(di) != null;
    }

    @Override
    public void updateStatus(NotifyConfigBo bo) {
        NotifyConfig notifyConfig = notifyConfigData.findById(bo.getId());
        notifyConfig.setStatus(bo.getStatus());
        notifyConfigData.save(notifyConfig);
    }
}