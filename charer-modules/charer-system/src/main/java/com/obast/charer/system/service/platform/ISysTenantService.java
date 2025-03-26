package com.obast.charer.system.service.platform;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.system.dto.bo.SysTenantBo;
import com.obast.charer.system.dto.vo.tenant.SysTenantVo;
import com.obast.charer.qo.SysTenantQueryBo;

import java.util.List;

/**
 * 租户Service接口
 *
 * @author Michelle.Chung
 */
public interface ISysTenantService {

    /**
     * 查询租户
     */
    SysTenantVo queryById(String id);

    /**
     * 基于租户ID查询租户
     */
    SysTenantVo queryByTenantId(String tenantId);

    /**
     * 查询租户列表
     */
    Paging<SysTenantVo> queryPageList(  PageRequest<SysTenantQueryBo> query);

    /**
     * 查询租户列表
     */
    List<SysTenantVo> queryList(SysTenantQueryBo bo);

    /**
     * 新增租户
     */
    void insertByBo(SysTenantBo bo);

    /**
     * 修改租户
     */
    void updateByBo(SysTenantBo bo);

    /**
     * 修改租户状态
     */
    void updateStatus(SysTenantBo bo);

    /**
     * 校验租户是否允许操作
     */
    void checkTenantAllowed(String tenantId);

    /**
     * 删除租户信息
     */
    void deleteById(String id);

    /**
     * 校验企业名称是否唯一
     */
    boolean checkCompanyNameUnique(SysTenantBo bo);


    boolean checkUserNameUnique(SysTenantBo bo);
    /**
     * 校验账号余额
     */
    boolean checkAccountBalance(String tenantId);

    /**
     * 校验有效期
     */
    boolean checkExpireTime(String tenantId);

    /**
     * 同步租户套餐
     */
    Boolean syncTenantPackage(String tenantId, String packageId);


    /**
     * 同步租户套餐
     */
    Boolean resetAdminPwd(String tenantId, String password);
}
