package com.obast.charer.system.service.system.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.IChannelConfigData;
import com.obast.charer.system.dto.bo.ChannelConfigBo;
import com.obast.charer.system.dto.vo.notify.ChannelConfigVo;

import com.obast.charer.system.service.system.IChannelConfigManagerService;

import com.obast.charer.model.notify.ChannelConfig;
import com.obast.charer.qo.ChannelConfigQueryBo;
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
public class ChannelConfigManagerServiceImpl implements IChannelConfigManagerService {

    @Autowired
    private IChannelConfigData channelConfigData;

    @Override
    public Paging<ChannelConfigVo> queryPageList(PageRequest<ChannelConfigQueryBo> pageRequest) {
        return MapstructUtils.convert(channelConfigData.findPage(pageRequest), ChannelConfigVo.class);
    }

    @Override
    public List<ChannelConfigVo> queryList(PageRequest<ChannelConfigQueryBo> pageRequest) {
        return MapstructUtils.convert(channelConfigData.findList(pageRequest.getData()), ChannelConfigVo.class);
    }

    @Override
    public ChannelConfigVo queryDetail(String sysConfigId) {
        return MapstructUtils.convert(channelConfigData.findById(sysConfigId), ChannelConfigVo.class);
    }

    @Override
    public boolean add(ChannelConfigBo bo) {
        ChannelConfig di = bo.to(ChannelConfig.class);
        return channelConfigData.save(di) != null;
    }

    @Override
    public boolean update(ChannelConfigBo bo) {
        ChannelConfig di = bo.to(ChannelConfig.class);
        return channelConfigData.save(di) != null;
    }

}