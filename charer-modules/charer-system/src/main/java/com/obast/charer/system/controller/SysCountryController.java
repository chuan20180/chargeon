package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.system.dto.bo.SysCountryBo;
import com.obast.charer.system.dto.vo.SysCountryVo;
import com.obast.charer.system.service.system.ISysCountryManagerService;
import com.obast.charer.qo.SysCountryQueryBo;
import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：国家管理
 */
@Api(tags = {"国家管理"})
@Slf4j
@RestController
@RequestMapping("/admin/system/country")
public class SysCountryController {

    @Autowired
    private ISysCountryManagerService sysCountryManagerService;

    @ApiOperation(value = "列表", notes = "列表", httpMethod = "POST")
    @SaCheckPermission("system:country:list")
    @PostMapping("/list")
    public Paging<SysCountryVo> queryPageList(@Validated @RequestBody PageRequest<SysCountryQueryBo> pageRequest) {
        return sysCountryManagerService.queryPageList(pageRequest);
    }

    @ApiOperation(value = "创建")
    @SaCheckPermission("system:country:add")
    @PostMapping("/add")
    public boolean create(@Validated @RequestBody Request<SysCountryBo> bo) {
        return sysCountryManagerService.add(bo.getData());
    }

    @ApiOperation(value = "修改")
    @SaCheckPermission("system:country:edit")
    @PostMapping("/edit")
    public boolean edit(@Validated @RequestBody Request<SysCountryBo> bo) {
        return sysCountryManagerService.update(bo.getData());
    }

    @ApiOperation(value = "状态修改")
    @SaCheckPermission("business:station:edit")
    @Log(title = "国家管理", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    public void changeStatus(@RequestBody Request<SysCountryBo> bo) {
        SysCountryBo data = bo.getData();
        sysCountryManagerService.updateStatus(data);
    }

    @ApiOperation("删除")
    @SaCheckPermission("system:country:delete")
    @PostMapping("/delete")
    public boolean delete(@Validated @RequestBody Request<String> request) {
        return sysCountryManagerService.delete(request.getData());
    }

    @ApiOperation("批量删除")
    @SaCheckPermission("system:country:delete")
    @PostMapping("/batchDelete")
    public boolean batchDelete(@Validated @RequestBody Request<List<String>> request) {
        return sysCountryManagerService.batchDelete(request.getData());
    }
}
