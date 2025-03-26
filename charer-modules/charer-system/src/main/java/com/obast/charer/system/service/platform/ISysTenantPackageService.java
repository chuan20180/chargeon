package com.obast.charer.system.service.platform;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.qo.SysTenantPackageQueryBo;
import com.obast.charer.system.dto.bo.SysTenantPackageBo;
import com.obast.charer.system.dto.vo.tenant.SysTenantPackageVo;

import java.util.List;

/**
 * 租户套餐Service接口
 *
 * @author Michelle.Chung
 */
public interface ISysTenantPackageService {

    /**
     * 查询租户套餐列表
     */
    Paging<SysTenantPackageVo> queryPageList( PageRequest<SysTenantPackageQueryBo> query);

    /**
     * 查询租户套餐列表
     */
    List<SysTenantPackageVo> queryList(SysTenantPackageQueryBo bo);

    SysTenantPackageVo queryDetail(String id);

    /**
     * 新增租户套餐
     */
    Boolean add(SysTenantPackageBo bo);

    /**
     * 修改租户套餐
     */
    Boolean update(SysTenantPackageBo bo);

    /**
     * 修改套餐状态
     */
    void updateStatus(SysTenantPackageBo bo);

    boolean delete(String id);

    boolean batchDelete(List<String> ids);
}
