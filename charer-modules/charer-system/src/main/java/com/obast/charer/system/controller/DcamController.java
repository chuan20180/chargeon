package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.thing.ThingModelMessage;
import com.obast.charer.system.dto.bo.DcamBo;
import com.obast.charer.system.dto.vo.device.DcamVo;
import com.obast.charer.system.service.business.IDcamManagerService;
import com.obast.charer.system.service.platform.IProductService;
import com.obast.charer.qo.DcamQueryBo;
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
 * @ Description：车位相机管理
 */
@Api(tags = {"车位相机管理"})
@Slf4j
@RestController
@RequestMapping("/admin/dcam")
public class DcamController {

    @Autowired
    IProductService productService;

    @Autowired
    private IDcamManagerService dcamManagerService;

    @ApiOperation(value = "车位相机列表", notes = "车位相机列表", httpMethod = "POST")
    @SaCheckPermission("business:dcam:list")
    @PostMapping("/list")
    public Paging<DcamVo> getDevices(@Validated @RequestBody PageRequest<DcamQueryBo> pageRequest) {
        return dcamManagerService.queryPageList(pageRequest);
    }

    @ApiOperation("车位相机日志")
    @SaCheckPermission("business:dcam:query")
    @PostMapping("/logs")
    public Paging<ThingModelMessage> logs(@Validated @RequestBody PageRequest<DeviceLogQueryBo> request) {
        return dcamManagerService.logs(request);
    }

    @ApiOperation(value = "创建车位相机")
    @SaCheckPermission("business:dcam:add")
    @PostMapping("/add")
    public void createDevice(@RequestBody @Validated Request<DcamBo> bo) {
        dcamManagerService.addDcam(bo.getData());
    }

    @ApiOperation(value = "修改车位相机")
    @SaCheckPermission("business:dcam:edit")
    @PostMapping("/edit")
    public void editDevice(@RequestBody @Validated Request<DcamBo> bo) {
        dcamManagerService.updateDcam(bo.getData());
    }

    @ApiOperation(value = "状态修改")
    @SaCheckPermission("business:dcam:edit")
    @Log(title = "车位相机管理", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    public void changeStatus(@RequestBody Request<DcamBo> bo) {
        DcamBo data = bo.getData();
        dcamManagerService.updateStatus(data);
    }

    @ApiOperation("获取车位相机详情")
    @SaCheckPermission("business:dcam:query")
    @PostMapping("/detail")
    public DcamVo getDetail(@RequestBody @Validated Request<String> request) {
        return dcamManagerService.queryDetail(request.getData());
    }

    @ApiOperation("删除车位相机")
    @SaCheckPermission("business:dcam:delete")
    @PostMapping("/delete")
    public boolean deleteDevice(@Validated @RequestBody Request<String> request) {
        return dcamManagerService.deleteDcam(request.getData());
    }

    @ApiOperation("批量删除车位相机")
    @SaCheckPermission("business:dcam:delete")
    @PostMapping("/batchDelete")
    public boolean batchDelete(@Validated @RequestBody Request<List<String>> request) {
        return dcamManagerService.batchDeleteDcam(request.getData());
    }
}