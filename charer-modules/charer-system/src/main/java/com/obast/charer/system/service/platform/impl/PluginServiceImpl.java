package com.obast.charer.system.service.platform.impl;


import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.business.IPluginInfoData;
import com.obast.charer.model.plugin.PluginInfo;
import com.obast.charer.plugin.feign.IRemotePluginService;
import com.obast.charer.system.dto.bo.PluginInfoBo;
import com.obast.charer.system.dto.vo.PluginInfoVo;
import com.obast.charer.system.service.platform.IPluginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author sjg
 */
@Slf4j
@Service
public class PluginServiceImpl implements IPluginService {


    @Autowired
    private IPluginInfoData pluginInfoData;

    @Autowired
    private IRemotePluginService remotePluginService;

    @Override
    public void modifyPlugin(PluginInfoBo plugin) {
        pluginInfoData.save(plugin.to(PluginInfo.class));
    }

    @Override
    public PluginInfoVo getPlugin(String id) {
        return pluginInfoData.findById(id).to(PluginInfoVo.class);
    }

    @Override
    public Paging<PluginInfoVo> findPagePluginList(PageRequest<PluginInfoBo> query) {
        return pluginInfoData.findAll(query.to(PluginInfo.class)).to(PluginInfoVo.class);
    }

    @Override
    public void changeState(PluginInfoBo plugin) {
        String state = plugin.getState();
        if (!PluginInfo.STATE_RUNNING.equals(state) && !PluginInfo.STATE_STOPPED.equals(state)) {
            throw new BizException(ErrCode.PARAMS_EXCEPTION, "插件状态错误");
        }

        PluginInfo old = pluginInfoData.findById(plugin.getId());
        if (old == null) {
            throw new BizException(ErrCode.DATA_NOT_EXIST);
        }
        if (StringUtils.isBlank(old.getFile())) {
            throw new BizException(ErrCode.DATA_BLANK, "插件包为空");
        }

        String pluginId = old.getPluginId();

        if (state.equals(PluginInfo.STATE_RUNNING)) {
            //启动插件
            remotePluginService.start(pluginId);
        } else {
            //停止插件
            remotePluginService.stop(pluginId);
        }

        old.setState(state);
        pluginInfoData.save(old);
    }
}
