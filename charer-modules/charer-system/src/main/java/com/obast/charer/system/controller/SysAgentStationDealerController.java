package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.web.core.BaseController;
import com.obast.charer.system.dto.bo.SysAgentStationDealerBo;
import com.obast.charer.system.dto.bo.SysAgentStationDealerSaveBo;
import com.obast.charer.system.dto.vo.SysAgentStationDealerVo;
import com.obast.charer.system.service.system.ISysAgentStationDealerService;
import com.obast.charer.qo.SysAgentStationDealerQueryBo;
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
@Api(tags = {"代理商、场站、合作商绑定管理"})
@Slf4j
@RestController
@RequestMapping("/admin/system/agent/station/dealer")
public class SysAgentStationDealerController extends BaseController {

    @Autowired
    private ISysAgentStationDealerService sysAgentStationDealerService;

    @ApiOperation("列表")
    @SaCheckPermission("system:agent_station_dealer:list")
    @PostMapping("/list")
    public Paging<SysAgentStationDealerVo> list(@RequestBody PageRequest<SysAgentStationDealerQueryBo> pageRequest) {
        return sysAgentStationDealerService.queryPageList(pageRequest);
    }

    @ApiOperation(value = "修改")
    @SaCheckPermission("system:agent_station_dealer:list")
    @PostMapping("/save")
    public boolean save(@Validated @RequestBody Request<SysAgentStationDealerSaveBo> bo) {
        return sysAgentStationDealerService.save(bo.getData());
    }

    @ApiOperation(value = "状态修改")
    @SaCheckPermission("system:agent_station_dealer:list")
    @Log(title = "代理商管理", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    public void changeStatus(@RequestBody Request<SysAgentStationDealerBo> bo) {
        SysAgentStationDealerBo data = bo.getData();
        sysAgentStationDealerService.updateStatus(data);
    }

    @ApiOperation("删除")
    @SaCheckPermission("system:agent_station_dealer:list")
    @PostMapping("/delete")
    public boolean delete(@Validated @RequestBody Request<String> request) {
        return sysAgentStationDealerService.delete(request.getData());
    }

    @ApiOperation("批量删除")
    @SaCheckPermission("system:agent_station_dealer:list")
    @PostMapping("/batchDelete")
    public boolean batchDelete(@Validated @RequestBody Request<List<String>> request) {
        return sysAgentStationDealerService.batchDelete(request.getData());
    }
}
