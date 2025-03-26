package com.obast.charer.system.service.system.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.ISysCountryData;
import com.obast.charer.system.dto.bo.SysCountryBo;
import com.obast.charer.system.dto.vo.SysCountryVo;
import com.obast.charer.system.service.system.ISysCountryManagerService;
import com.obast.charer.model.system.SysCountry;
import com.obast.charer.qo.SysCountryQueryBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：场站管理服务实现
 */
@Service
public class SysCountryManagerServiceImpl implements ISysCountryManagerService {

    @Autowired
    private ISysCountryData sysCountryData;

    @Override
    public Paging<SysCountryVo> queryPageList(PageRequest<SysCountryQueryBo> pageRequest) {
        Paging<SysCountry> pageList = sysCountryData.findPage(pageRequest);
        Paging<SysCountryVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(SysCountry sysCountry: pageList.getRows()) {
            newPageList.getRows().add(fillData(sysCountry));
        }
        return newPageList;
    }

    @Override
    public List<SysCountryVo> queryList(PageRequest<SysCountryQueryBo> pageRequest) {
        List<SysCountry> list = sysCountryData.findList(pageRequest.getData());
        List<SysCountryVo> newList = new ArrayList<>();
        for(SysCountry sysCountry: list) {
            newList.add(fillData(sysCountry));
        }
        return newList;
    }

    @Override
    public SysCountryVo queryDetail(String sysCountryId) {
        return fillData(sysCountryData.findById(sysCountryId));
    }

    @Override
    public boolean add(SysCountryBo bo) {
        SysCountry di = bo.to(SysCountry.class);
        return sysCountryData.add(di) != null;
    }

    @Override
    public boolean update(SysCountryBo bo) {
        SysCountry di = bo.to(SysCountry.class);
        return sysCountryData.update(di) != null;
    }

    @Override
    public void updateStatus(SysCountryBo bo) {
        SysCountry sysCountry = sysCountryData.findById(bo.getId());
        sysCountry.setStatus(bo.getStatus());
        sysCountryData.save(sysCountry);
    }

    @Override
    public boolean delete(String id) {
        id = queryDetail(id).getId();
        sysCountryData.deleteById(id);
        return true;
    }

    @Override
    public boolean batchDelete(List<String> ids) {
        for(String id: ids) {
            sysCountryData.deleteById(id);
        }
        return true;
    }

    private SysCountryVo fillData(SysCountry sysCountry) {
        return MapstructUtils.convert(sysCountry, SysCountryVo.class);
    }
}