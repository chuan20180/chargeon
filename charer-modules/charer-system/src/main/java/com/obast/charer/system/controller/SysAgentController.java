package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.web.core.BaseController;
import com.obast.charer.system.dto.bo.SysAgentBo;
import com.obast.charer.system.dto.vo.SysAgentVo;
import com.obast.charer.system.service.system.ISysAgentService;
import com.obast.charer.qo.SysAgentQueryBo;
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
 * @ Description：代理商管理
 */
@Api(tags = {"代理商管理"})
@Slf4j
@RestController
@RequestMapping("/admin/system/agent")
public class SysAgentController extends BaseController {

    @Autowired
    private ISysAgentService sysAgentService;

    @ApiOperation("列表")
    @SaCheckPermission("system:agent:list")
    @PostMapping("/list")
    public Paging<SysAgentVo> list(@RequestBody PageRequest<SysAgentQueryBo> pageRequest) {
        return sysAgentService.queryPageList(pageRequest);
    }

    @ApiOperation(value = "创建")
    @SaCheckPermission("system:agent:add")
    @PostMapping("/add")
    public boolean create(@Validated @RequestBody Request<SysAgentBo> bo) {
        return sysAgentService.addAgent(bo.getData());
    }

    @ApiOperation(value = "修改")
    @SaCheckPermission("system:agent:edit")
    @PostMapping("/edit")
    public boolean edit(@Validated @RequestBody Request<SysAgentBo> bo) {
        return sysAgentService.updateAgent(bo.getData());
    }

    @ApiOperation(value = "状态修改")
    @SaCheckPermission("system:agent:edit")
    @Log(title = "代理商管理", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    public void changeStatus(@RequestBody Request<SysAgentBo> bo) {
        SysAgentBo data = bo.getData();
        sysAgentService.updateStatus(data);
    }

    @ApiOperation("删除")
    @SaCheckPermission("system:agent:delete")
    @PostMapping("/delete")
    public void delete(@Validated @RequestBody Request<String> request) {
        sysAgentService.deleteAgent(request.getData());
    }

    @ApiOperation("批量删除")
    @SaCheckPermission("system:agent:delete")
    @PostMapping("/batchDelete")
    public boolean batchDelete(@Validated @RequestBody Request<List<String>> request) {
        return sysAgentService.batchDeleteAgent(request.getData());
    }

    @ApiOperation("详细")
    @SaCheckPermission("system:agent:query")
    @PostMapping("/detail")
    public SysAgentVo detail(@RequestBody Request<String> bo) {
        return sysAgentService.queryDetail(bo.getData());
    }

    /**
     * 获取选择框列表
     */
    @ApiOperation(value = "获取选择框列表", notes = "获取选择框列表")
    @PostMapping("/optionSelect")
    public List<SysAgentVo> optionSelect(@Validated @RequestBody PageRequest<SysAgentQueryBo> pageRequest) {
        return sysAgentService.queryList(pageRequest);
    }
}
