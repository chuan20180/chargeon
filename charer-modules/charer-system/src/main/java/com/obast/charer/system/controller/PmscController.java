package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.thing.ThingModelMessage;
import com.obast.charer.system.dto.bo.PmscBo;
import com.obast.charer.system.dto.vo.device.PmscVo;
import com.obast.charer.system.service.business.IPmscManagerService;
import com.obast.charer.qo.DeviceLogQueryBo;
import com.obast.charer.qo.PmscQueryBo;
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
 * @ Description：停车场管理相机管理
 */
@Api(tags = {"停车场管理相机管理"})
@Slf4j
@RestController
@RequestMapping("/admin/pmsc")
public class PmscController {

    @Autowired
    private IPmscManagerService pmscManagerService;

    @ApiOperation(value = "停车场管理相机列表", notes = "停车场管理相机列表", httpMethod = "POST")
    @SaCheckPermission("business:pmsc:list")
    @PostMapping("/list")
    public Paging<PmscVo> getDevices(@Validated @RequestBody PageRequest<PmscQueryBo> pageRequest) {
        return pmscManagerService.queryPageList(pageRequest);
    }

    @ApiOperation("停车场管理相机日志")
    @SaCheckPermission("business:pmsc:query")
    @PostMapping("/logs")
    public Paging<ThingModelMessage> logs(@Validated @RequestBody PageRequest<DeviceLogQueryBo> request) {
        return pmscManagerService.logs(request);
    }

    @ApiOperation(value = "创建停车场管理相机")
    @SaCheckPermission("business:pmsc:add")
    @PostMapping("/add")
    public void createDevice(@RequestBody @Validated Request<PmscBo> bo) {
        pmscManagerService.addPmsc(bo.getData());
    }

    @ApiOperation(value = "保存停车场管理相机")
    @SaCheckPermission("business:pmsc:edit")
    @PostMapping("/edit")
    public void saveDevice(@RequestBody @Validated Request<PmscBo> bo) {
        pmscManagerService.updatePmsc(bo.getData());
    }

    @ApiOperation(value = "状态修改")
    @SaCheckPermission("business:pmsc:edit")
    @Log(title = "停车场管理相机管理", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    public void changeStatus(@RequestBody Request<PmscBo> bo) {
        PmscBo data = bo.getData();
        pmscManagerService.updateStatus(data);
    }

    @ApiOperation("获取停车场管理相机详情")
    @SaCheckPermission("business:pmsc:query")
    @PostMapping("/detail")
    public PmscVo getDetail(@RequestBody @Validated Request<String> request) {
        return pmscManagerService.queryDetail(request.getData());
    }

    @ApiOperation("删除停车场管理相机")
    @SaCheckPermission("business:pmsc:delete")
    @PostMapping("/delete")
    public boolean deleteDevice(@Validated @RequestBody Request<String> request) {
        return pmscManagerService.deletePmsc(request.getData());
    }

    @ApiOperation("批量删除停车场管理相机")
    @SaCheckPermission("business:pmsc:delete")
    @PostMapping("/batchDelete")
    public boolean batchDelete(@Validated @RequestBody Request<List<String>> request) {
        return pmscManagerService.batchDeletePmsc(request.getData());
    }
}