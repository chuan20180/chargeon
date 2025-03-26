package com.obast.charer.system.service.platform.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.system.ISysTenantData;
import com.obast.charer.data.system.ISysTenantPackageData;
import com.obast.charer.model.system.SysTenant;
import com.obast.charer.model.system.SysTenantPackage;
import com.obast.charer.qo.SysTenantPackageQueryBo;
import com.obast.charer.system.dto.bo.SysTenantPackageBo;
import com.obast.charer.system.dto.vo.tenant.SysTenantPackageVo;
import com.obast.charer.system.service.platform.ISysTenantPackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 租户套餐Service业务层处理
 *
 * @author Michelle.Chung
 */
@RequiredArgsConstructor
@Service
public class SysTenantPackageServiceImpl implements ISysTenantPackageService {

    private final ISysTenantPackageData sysTenantPackageData;


    private final ISysTenantData sysTenantData;


    @Override
    public Paging<SysTenantPackageVo> queryPageList(PageRequest<SysTenantPackageQueryBo> pageRequest) {
        Paging<SysTenantPackage> pageList = sysTenantPackageData.findPage(pageRequest);
        Paging<SysTenantPackageVo> newPageList = new Paging<>(pageList.getTotal(), new ArrayList<>());
        for(SysTenantPackage sysTenantPackage: pageList.getRows()) {
            newPageList.getRows().add(fillData(sysTenantPackage));
        }
        return newPageList;
    }

    @Override
    public List<SysTenantPackageVo> queryList(SysTenantPackageQueryBo bo) {
        List<SysTenantPackage> list = sysTenantPackageData.findList(bo);
        List<SysTenantPackageVo> chargerVos = new ArrayList<>();
        for(SysTenantPackage sysTenantPackage: list) {
            chargerVos.add(fillData(sysTenantPackage));
        }
        return chargerVos;
    }

    @Override
    public SysTenantPackageVo queryDetail(String id) {
        SysTenantPackage entity = sysTenantPackageData.findById(id);
        return MapstructUtils.convert(entity,SysTenantPackageVo.class);
    }

    private SysTenantPackageVo fillData(SysTenantPackage sysTenantPackage) {
        SysTenantPackageVo vo = MapstructUtils.convert(sysTenantPackage, SysTenantPackageVo.class);
        if(vo == null) {
            return null;
        }

        List<String> checkedMenuIds = sysTenantPackageData.findMenuListByPackageId(vo.getId());
        vo.setCheckedMenuIds(checkedMenuIds);
        return vo;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean add(SysTenantPackageBo bo) {
        sysTenantPackageData.add(bo.to(SysTenantPackage.class));
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean update(SysTenantPackageBo bo) {
        sysTenantPackageData.update(bo.to(SysTenantPackage.class));
        return true;
    }

    @Override
    public void updateStatus(SysTenantPackageBo bo) {
        sysTenantPackageData.save(bo.to(SysTenantPackage.class));
    }

    @Override
    @Transactional
    public boolean delete(String id) {
        SysTenantPackage sysTenantPackage = sysTenantPackageData.findById(id);
        if(sysTenantPackage == null) {
            throw new BizException(ErrCode.TENANT_PACKAGE_NOT_FOUND);
        }

        //查看是否存在租户
        List<SysTenant> sysTenants = sysTenantData.findListByPackageId(sysTenantPackage.getId());
        if (!sysTenants.isEmpty()) {
            throw new BizException(ErrCode.TENANT_PACKAGE__EXISTS_TENANT);
        }

        sysTenantPackageData.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public boolean batchDelete(List<String> ids) {
        for(String id: ids) {
            sysTenantPackageData.deleteById(id);
        }
        return true;
    }
}
