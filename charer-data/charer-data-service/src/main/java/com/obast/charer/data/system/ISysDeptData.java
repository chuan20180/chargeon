package com.obast.charer.data.system;


import com.obast.charer.data.ICommonData;
import com.obast.charer.data.IJPACommonData;
import com.obast.charer.model.system.SysDept;
import com.obast.charer.model.system.SysRole;
import com.obast.charer.qo.SysDeptQueryBo;

import java.util.List;

/**
 * 部门数据接口
 *
 * @author sjg
 */
public interface ISysDeptData extends ICommonData<SysDept, String>, IJPACommonData<SysDept, SysDeptQueryBo, String> {

    SysDept add(SysDept sysDept);

    SysDept update(SysDept sysDept);


    /**
     * 根据ID查询所有子部门数（正常状态）
     *
     * @param parentId 部门ID
     * @return 子部门数
     */
    long countByParentId(String parentId);

    /**
     * 根据ID查询所有子部门数（所有状态）
     *
     * @param deptId 部门ID
     * @return 部门列表
     */
    List<SysDept> findByDeptId(String deptId);


    boolean checkDeptNameUnique(String deptName, String parentId, String deptId);



    List<SysDept> findAllByTenantId(String id);

    List<SysDept> findAllByAgentId(String id);

}
