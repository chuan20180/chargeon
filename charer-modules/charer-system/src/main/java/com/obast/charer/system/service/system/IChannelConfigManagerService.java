package com.obast.charer.system.service.system;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;

import com.obast.charer.system.dto.bo.ChannelConfigBo;
import com.obast.charer.system.dto.vo.notify.ChannelConfigVo;
import com.obast.charer.qo.ChannelConfigQueryBo;

import java.util.List;

public interface IChannelConfigManagerService {
    Paging<ChannelConfigVo> queryPageList(PageRequest<ChannelConfigQueryBo> pageRequest);

    List<ChannelConfigVo> queryList(PageRequest<ChannelConfigQueryBo> pageRequest);

    ChannelConfigVo queryDetail(String sysConfigId);

    boolean add(ChannelConfigBo data);

    boolean update(ChannelConfigBo data);

}
