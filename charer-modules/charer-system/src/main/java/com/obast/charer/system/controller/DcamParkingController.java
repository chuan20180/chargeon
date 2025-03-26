package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.system.dto.bo.DcamParkingBo;
import com.obast.charer.system.dto.vo.device.DcamParkingVo;
import com.obast.charer.system.service.business.IDcamParkingManagerService;
import com.obast.charer.qo.DcamParkingQueryBo;
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
 * @ Description：车位相机绑定管理
 */
@Api(tags = {"车位相机绑定管理"})
@Slf4j
@RestController
@RequestMapping("/admin/dcam/parking")
public class DcamParkingController {

    @Autowired
    private IDcamParkingManagerService dcamParkingManagerService;

    @ApiOperation(value = "车位相机绑定列表", notes = "车位相机绑定列表", httpMethod = "POST")
    @SaCheckPermission("business:dcam:query")
    @PostMapping("/list")
    public Paging<DcamParkingVo> getDevices(@Validated @RequestBody PageRequest<DcamParkingQueryBo> pageRequest) {
        return dcamParkingManagerService.queryPageList(pageRequest);
    }

    @ApiOperation(value = "添加车位相机绑定")
    @SaCheckPermission("business:dcam:add")
    @PostMapping("/add")
    public void add(@RequestBody @Validated Request<DcamParkingBo> bo) {
        dcamParkingManagerService.add(bo.getData());
    }

    @ApiOperation(value = "保存车位相机绑定")
    @SaCheckPermission("business:dcam:edit")
    @PostMapping("/edit")
    public void saveDevice(@RequestBody @Validated Request<DcamParkingBo> bo) {
        dcamParkingManagerService.update(bo.getData());
    }

    @ApiOperation("获取详情")
    @SaCheckPermission("business:dcam:query")
    @PostMapping("/detail")
    public DcamParkingVo getDetail(@RequestBody @Validated Request<String> request) {
        return dcamParkingManagerService.queryDetail(request.getData());
    }

    @ApiOperation("删除")
    @SaCheckPermission("business:dcam:delete")
    @PostMapping("/delete")
    public boolean deleteDevice(@Validated @RequestBody Request<String> request) {
        return dcamParkingManagerService.delete(request.getData());
    }

    @ApiOperation("批量删除")
    @SaCheckPermission("business:dcam:delete")
    @PostMapping("/batchDelete")
    public boolean batchDelete(@Validated @RequestBody Request<List<String>> request) {
        return dcamParkingManagerService.batchDelete(request.getData());
    }
}