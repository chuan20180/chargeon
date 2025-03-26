package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.web.core.BaseController;
import com.obast.charer.system.dto.bo.SysAgentStationBo;
import com.obast.charer.system.dto.vo.SysAgentStationVo;
import com.obast.charer.system.service.system.ISysAgentStationService;
import com.obast.charer.qo.SysAgentStationQueryBo;
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
@Api(tags = {"代理商和场站绑定管理"})
@Slf4j
@RestController
@RequestMapping("/admin/system/agent/station")
public class SysAgentStationController extends BaseController {

    @Autowired
    private ISysAgentStationService sysAgentStationService;

    @ApiOperation("列表")
    @SaCheckPermission("system:agent_station:list")
    @PostMapping("/list")
    public Paging<SysAgentStationVo> list(@RequestBody PageRequest<SysAgentStationQueryBo> pageRequest) {
        return sysAgentStationService.queryPageList(pageRequest);
    }

    @ApiOperation(value = "创建")
    @SaCheckPermission("system:agent_station:add")
    @PostMapping("/add")
    public boolean create(@Validated @RequestBody Request<SysAgentStationBo> bo) {
        return sysAgentStationService.add(bo.getData());
    }

    @ApiOperation(value = "状态修改")
    @SaCheckPermission("system:agent_station:edit")
    @Log(title = "代理商管理", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    public void changeStatus(@RequestBody Request<SysAgentStationBo> bo) {
        SysAgentStationBo data = bo.getData();
        sysAgentStationService.updateStatus(data);
    }

    @ApiOperation("删除")
    @SaCheckPermission("system:agent_station:delete")
    @PostMapping("/delete")
    public boolean delete(@Validated @RequestBody Request<String> request) {
        return sysAgentStationService.delete(request.getData());
    }

    @ApiOperation("批量删除")
    @SaCheckPermission("system:agent_dealer:delete")
    @PostMapping("/batchDelete")
    public boolean batchDelete(@Validated @RequestBody Request<List<String>> request) {
        return sysAgentStationService.batchDelete(request.getData());
    }
}
