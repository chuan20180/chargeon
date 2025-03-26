package com.obast.charer.system.service.system.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.system.ISysAppData;
import com.obast.charer.model.system.SysApp;
import com.obast.charer.qo.SysAppQueryBo;
import com.obast.charer.system.dto.bo.SysAppBo;
import com.obast.charer.system.dto.vo.SysAppVo;
import com.obast.charer.system.service.system.ISysAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 应用信息Service业务层处理
 *
 * @author tfd
 * @date 2023-08-10
 */
@RequiredArgsConstructor
@Service
public class SysAppServiceImpl implements ISysAppService {

    private final ISysAppData sysAppData;

    /**
     * 查询应用信息
     */
    @Override
    public SysAppVo queryById(String id){
        return MapstructUtils.convert(sysAppData.findById(id), SysAppVo.class);
    }

    /**
     * 查询应用信息列表
     */
    @Override
    public Paging<SysAppVo> queryPageList(PageRequest<SysAppQueryBo> pageRequest) {
        return sysAppData.findPage(pageRequest).to(SysAppVo.class);
    }

    /**
     * 查询应用信息列表
     */
    @Override
    public List<SysAppVo> queryList(SysAppQueryBo bo) {
        return MapstructUtils.convert(sysAppData.findList(bo), SysAppVo.class);
    }

    /**
     * 新增应用信息
     */
    @Override
    public String insertByBo(SysAppBo bo) {
        SysApp add = MapstructUtils.convert(bo, SysApp.class);
        SysApp saved = sysAppData.add(add);
        return saved.getId();
    }

    /**
     * 修改应用信息
     */
    @Override
    public Boolean updateByBo(SysAppBo bo) {
        SysApp update = MapstructUtils.convert(bo, SysApp.class);
        validEntityBeforeSave(update);
        SysApp ret = sysAppData.save(update);
        if(ret == null){
            return false;
        }
        return true;
    }

    @Override
    public void updateStatus(SysAppBo bo) {
        SysApp sysApp = sysAppData.findById(bo.getId());
        sysApp.setStatus(bo.getStatus());
        sysAppData.save(sysApp);
    }

    @Override
    public void updateConfig(SysAppBo bo) {
        SysApp update = MapstructUtils.convert(bo, SysApp.class);
        validEntityBeforeSave(update);
        sysAppData.update(update);
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysApp entity){
    }

    /**
     * 批量删除应用信息
     */
    @Override
    public Boolean deleteById(String id) {
        sysAppData.deleteById(id);
        return true;
    }
}
