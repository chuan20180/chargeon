package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.common.web.core.BaseController;
import com.obast.charer.system.dto.bo.SysDeptBo;
import com.obast.charer.system.dto.vo.SysDeptVo;
import com.obast.charer.system.service.system.ISysDeptService;
import com.obast.charer.qo.SysDeptQueryBo;
import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.hutool.core.convert.Convert;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 部门信息
 *
 * @author Lion Li
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/system/dept")
public class SysDeptController extends BaseController {

    private final ISysDeptService deptService;

    /**
     * 获取部门列表
     */
    @SaCheckPermission("system:dept:list")
    @ApiOperation("获取部门列表")
    @PostMapping("/list")
    public List<SysDeptVo> list(@RequestBody @Validated PageRequest<SysDeptQueryBo> pageRequest) {
        return deptService.queryList(pageRequest.getData());
    }

    /**
     * 查询部门列表（排除节点）
     */
    @ApiOperation("查询部门列表（排除节点）")
    @SaCheckPermission("system:dept:list")
    @PostMapping("/list/exclude")
    public List<SysDeptVo> excludeChild(@Validated @RequestBody Request<Long> request) {
        Long deptId = request.getData();
        List<SysDeptVo> depts = deptService.queryList(new SysDeptQueryBo());
        depts.removeIf(d -> d.getId().equals(deptId)
                || StringUtils.splitList(d.getAncestors()).contains(Convert.toStr(deptId)));
        return depts;
    }

    /**
     * 根据部门编号获取详细信息
     */
    @SaCheckPermission("system:dept:query")
    @ApiOperation("根据部门编号获取详细信息")
    @PostMapping(value = "/getInfo")
    public SysDeptVo getInfo(@Validated @RequestBody Request<String> bo) {
        String deptId = bo.getData();
        deptService.checkDeptDataScope(deptId);
        return deptService.selectDeptById(deptId);
    }

    /**
     * 新增部门
     */
    @SaCheckPermission("system:dept:add")
    @ApiOperation("新增部门")
    @Log(title = "部门管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public void add(@Validated @RequestBody Request<SysDeptBo> bo) {
        SysDeptBo dept = bo.getData();
        if (!deptService.checkDeptNameUnique(dept)) {
            fail("新增部门'" + dept.getDeptName() + "'失败，部门名称已存在");
        }
        deptService.insertDept(dept);
    }

    /**
     * 修改部门
     */
    @ApiOperation("修改部门")
    @SaCheckPermission("system:dept:edit")
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public void edit(@Validated @RequestBody Request<SysDeptBo> bo) {
        SysDeptBo dept = bo.getData();
        String deptId = dept.getId();
        deptService.checkDeptDataScope(deptId);
        if (dept.getParentId().equals(deptId)) {
            fail("修改部门'" + dept.getDeptName() + "'失败，上级部门不能是自己");
        }
        deptService.updateDept(dept);
    }

    /**
     * 删除部门
     */
    @SaCheckPermission("system:dept:delete")
    @ApiOperation("删除部门")
    @Log(title = "部门管理", businessType = BusinessType.DELETE)
    @PostMapping("/delete")
    public void remove(@Validated @RequestBody Request<String> bo) {
        String deptId = bo.getData();
        if (deptService.hasChildByDeptId(deptId)) {
            warn("存在下级部门,不允许删除");
        }
        if (deptService.checkDeptExistUser(deptId)) {
            warn("部门存在用户,不允许删除");
        }
        deptService.checkDeptDataScope(deptId);
        deptService.deleteDeptById(deptId);
    }
}
