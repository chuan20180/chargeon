package com.obast.charer.system.service.platform;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.IChannelConfigData;
import com.obast.charer.data.business.INotifyMessageData;
import com.obast.charer.system.dto.bo.ChannelConfigBo;
import com.obast.charer.system.dto.vo.notify.ChannelConfigVo;
import com.obast.charer.model.notify.ChannelConfig;
import com.obast.charer.model.notify.NotifyMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * author: 石恒
 * date: 2023-05-11 15:21
 * description:
 **/
@Slf4j
@Service
public class NotifyService {

    @Resource
    private IChannelConfigData iChannelConfigData;

    @Resource
    private INotifyMessageData iNotifyMessageData;

    public Paging<ChannelConfigVo> getChannelConfigList(PageRequest<ChannelConfigBo> request) {
        return iChannelConfigData.findAll(request.to(ChannelConfig.class)).to(ChannelConfigVo.class);
    }

    public List<ChannelConfigVo> getChannelConfigAll() {
        return MapstructUtils.convert(iChannelConfigData.findAll(),ChannelConfigVo.class);
    }

    public ChannelConfig addChannelConfig(ChannelConfig channelConfig) {
        return iChannelConfigData.save(channelConfig);
    }

    public ChannelConfig getChannelConfigById(String id) {
        return iChannelConfigData.findById(id);
    }

    public ChannelConfig updateChannelConfigById(ChannelConfig channelConfig) {
        return iChannelConfigData.save(channelConfig);
    }

    public Boolean delChannelConfigById(String id) {
        iChannelConfigData.deleteById(id);
        return Boolean.TRUE;
    }


    public Paging<NotifyMessage> getNotifyMessageList(PageRequest<NotifyMessage> request) {
        return iNotifyMessageData.findAll(request);
    }
}
