package com.obast.charer.system.service.system.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 数据权限 实现
 * <p>
 * 注意: 此Service内不允许调用标注`数据权限`注解的方法
 * 例如: deptMapper.selectList 此 selectList 方法标注了`数据权限`注解 会出现循环解析的问题
 *
 * @author Lion Li
 */
@RequiredArgsConstructor
@Service("sdss")
public class SysDataScopeServiceImpl {
//
//    private final SysRoleDeptMapper roleDeptMapper;
//    private final SysDeptMapper deptMapper;
//
//    @Override
//    public String getRoleCustom(Long roleId) {
//        List<SysRoleDept> list = roleDeptMapper.selectList(
//            new LambdaQueryWrapper<SysRoleDept>()
//                .select(SysRoleDept::getDeptId)
//                .eq(SysRoleDept::getRoleId, roleId));
//        if (CollUtil.isNotEmpty(list)) {
//            return StreamUtils.join(list, rd -> Convert.toStr(rd.getDeptId()));
//        }
//        return null;
//    }
//
//    @Override
//    public String getDeptAndChild(Long deptId) {
//        List<SysDept> deptList = deptMapper.selectList(new LambdaQueryWrapper<SysDept>()
//            .select(SysDept::getDeptId)
//            .apply(DataBaseHelper.findInSet(deptId, "ancestors")));
//        List<Long> ids = StreamUtils.toList(deptList, SysDept::getDeptId);
//        ids.add(deptId);
//        List<SysDept> list = deptMapper.selectList(new LambdaQueryWrapper<SysDept>()
//            .select(SysDept::getDeptId)
//            .in(SysDept::getDeptId, ids));
//        if (CollUtil.isNotEmpty(list)) {
//            return StreamUtils.join(list, d -> Convert.toStr(d.getDeptId()));
//        }
//        return null;
//    }

}
