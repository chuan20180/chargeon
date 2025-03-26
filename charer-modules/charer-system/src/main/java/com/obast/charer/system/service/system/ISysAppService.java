package com.obast.charer.system.service.system;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.qo.SysAppQueryBo;
import com.obast.charer.system.dto.bo.SysAppBo;
import com.obast.charer.system.dto.vo.SysAppVo;

import java.util.List;

/**
 * 应用信息Service接口
 *
 * @author tfd
 * @date 2023-08-10
 */
public interface ISysAppService {

    /**
     * 查询应用信息
     */
    SysAppVo queryById(String id);


    /**
     * 查询应用信息列表
     */
    Paging<SysAppVo> queryPageList(PageRequest<SysAppQueryBo> pageQuery);

    /**
     * 查询应用信息列表
     */
    List<SysAppVo> queryList(SysAppQueryBo bo);

    /**
     * 新增应用信息
     */
    String insertByBo(SysAppBo bo);

    /**
     * 修改应用信息
     */
    Boolean updateByBo(SysAppBo bo);

    void updateStatus(SysAppBo bo);


    /**
     * 修改应用配置
     */
    void updateConfig(SysAppBo bo);

    /**
     * 删除应用信息信息
     */
    Boolean deleteById(String id);
}
