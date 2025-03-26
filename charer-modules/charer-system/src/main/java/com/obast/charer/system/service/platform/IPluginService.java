package com.obast.charer.system.service.platform;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;


import com.obast.charer.system.dto.bo.PluginInfoBo;
import com.obast.charer.system.dto.vo.PluginInfoVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author sjg
 */
public interface IPluginService {


    /**
     * 修改插件信息
     *
     * @param plugin 插件信息
     */
    void modifyPlugin(PluginInfoBo plugin);

    /**
     * 获取插件信息
     *
     * @param id 插件id
     * @return 插件信息
     */
    PluginInfoVo getPlugin(String id);


    /**
     * 分页查询
     *
     * @param query 查询条件
     */
    Paging<PluginInfoVo> findPagePluginList(PageRequest<PluginInfoBo> query);

    /**
     * 修改插件状态
     *
     * @param plugin 插件信息
     */
    void changeState(PluginInfoBo plugin);
}
