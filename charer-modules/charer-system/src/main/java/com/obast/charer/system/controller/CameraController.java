package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.thing.ThingModelMessage;
import com.obast.charer.system.dto.bo.CameraBo;
import com.obast.charer.system.dto.vo.device.CameraVo;
import com.obast.charer.system.service.business.ICameraManagerService;
import com.obast.charer.system.service.platform.IProductService;
import com.obast.charer.qo.CameraQueryBo;
import com.obast.charer.qo.DeviceLogQueryBo;
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
 * @ Description：监控相机管理
 */
@Api(tags = {"监控相机管理"})
@Slf4j
@RestController
@RequestMapping("/admin/camera")
public class CameraController {

    @Autowired
    IProductService productService;

    @Autowired
    private ICameraManagerService cameraManagerService;

    @ApiOperation(value = "监控相机列表", notes = "监控相机列表", httpMethod = "POST")
    @SaCheckPermission("business:camera:list")
    @PostMapping("/list")
    public Paging<CameraVo> getDevices(@Validated @RequestBody PageRequest<CameraQueryBo> pageRequest) {
        return cameraManagerService.queryPageList(pageRequest);
    }

    @ApiOperation("监控相机日志")
    @SaCheckPermission("business:camera:query")
    @PostMapping("/logs")
    public Paging<ThingModelMessage> logs(@Validated @RequestBody PageRequest<DeviceLogQueryBo> request) {
        return cameraManagerService.logs(request);
    }

    @ApiOperation(value = "创建监控相机")
    @SaCheckPermission("business:camera:add")
    @PostMapping("/add")
    public void createDevice(@RequestBody @Validated Request<CameraBo> bo) {
        cameraManagerService.addCamera(bo.getData());
    }

    @ApiOperation(value = "修改监控相机")
    @SaCheckPermission("business:camera:edit")
    @PostMapping("/edit")
    public void editDevice(@RequestBody @Validated Request<CameraBo> bo) {
        cameraManagerService.updateCamera(bo.getData());
    }

    @ApiOperation(value = "状态修改")
    @SaCheckPermission("business:camera:edit")
    @Log(title = "监控相机管理", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    public void changeStatus(@RequestBody Request<CameraBo> bo) {
        CameraBo data = bo.getData();
        cameraManagerService.updateStatus(data);
    }

    @ApiOperation("获取监控相机详情")
    @SaCheckPermission("business:camera:query")
    @PostMapping("/detail")
    public CameraVo getDetail(@RequestBody @Validated Request<String> request) {
        return cameraManagerService.queryDetail(request.getData());
    }

    @ApiOperation("删除监控相机")
    @SaCheckPermission("business:camera:delete")
    @PostMapping("/delete")
    public boolean deleteDevice(@Validated @RequestBody Request<String> request) {
        return cameraManagerService.deleteCamera(request.getData());
    }

    @ApiOperation("批量删除监控相机")
    @SaCheckPermission("business:camera:delete")
    @PostMapping("/batchDelete")
    public boolean batchDelete(@Validated @RequestBody Request<List<String>> request) {
        return cameraManagerService.batchDeleteCamera(request.getData());
    }
}