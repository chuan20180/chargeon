package com.obast.charer.system.service.system;

import com.obast.charer.system.dto.bo.SysDeptBo;
import com.obast.charer.system.dto.vo.SysDeptVo;
import com.obast.charer.model.system.SysDept;
import com.obast.charer.qo.SysDeptQueryBo;
import cn.hutool.core.lang.tree.Tree;

import java.util.List;

/**
 * 部门管理 服务层
 *
 * @author Lion Li
 */
public interface ISysDeptService {

    List<SysDeptVo> queryList(SysDeptQueryBo queryBo);

    /**
     * 查询部门树结构信息
     *
     * @return 部门树信息集合
     */
    List<Tree<String>> selectDeptTreeList(SysDeptQueryBo queryBo);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param depts 部门列表
     * @return 下拉树结构列表
     */
    List<Tree<String>> buildDeptTreeSelect(List<SysDept> depts);

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    SysDeptVo selectDeptById(String deptId);


    /**
     * 是否存在部门子节点
     *
     * @param deptId 部门ID
     * @return 结果
     */
    boolean hasChildByDeptId(String deptId);

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    boolean checkDeptExistUser(String deptId);

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    boolean checkDeptNameUnique(SysDeptBo dept);

    /**
     * 校验部门是否有数据权限
     *
     * @param deptId 部门id
     */
    void checkDeptDataScope(String deptId);

    /**
     * 新增保存部门信息
     *
     * @param bo 部门信息
     */
    void insertDept(SysDeptBo bo);

    /**
     * 修改保存部门信息
     *
     * @param bo 部门信息
     */
    void updateDept(SysDeptBo bo);

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     */
    void deleteDeptById(String deptId);
}
