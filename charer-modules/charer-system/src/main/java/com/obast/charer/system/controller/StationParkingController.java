package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.system.dto.bo.ParkingBo;
import com.obast.charer.system.dto.vo.parking.ParkingVo;
import com.obast.charer.system.service.business.IParkingManagerService;
import com.obast.charer.qo.ParkingQueryBo;
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
 * @ Description：车位管理
 */
@Api(tags = {"车位"})
@Slf4j
@RestController
@RequestMapping("/admin/station/parking")
public class StationParkingController {

    @Autowired
    private IParkingManagerService parkingManagerService;

    @ApiOperation(value = "列表", notes = "列表", httpMethod = "POST")
    @SaCheckPermission("business:station:parking:list")
    @PostMapping("/list")
    public Paging<ParkingVo> list(@Validated @RequestBody PageRequest<ParkingQueryBo> pageRequest) {
        return parkingManagerService.queryPageList(pageRequest);
    }

    @ApiOperation(value = "创建")
    @SaCheckPermission("business:station:parking:add")
    @PostMapping("/add")
    public boolean create(@Validated @RequestBody Request<ParkingBo> bo) {
        return parkingManagerService.add(bo.getData());
    }

    @ApiOperation(value = "修改")
    @SaCheckPermission("business:station:parking:edit")
    @PostMapping("/edit")
    public boolean edit(@Validated @RequestBody Request<ParkingBo> bo) {
        return parkingManagerService.update(bo.getData());
    }

    @ApiOperation("删除")
    @SaCheckPermission("business:station:parking:delete")
    @PostMapping("/delete")
    public boolean delete(@Validated @RequestBody Request<String> request) {
        return parkingManagerService.delete(request.getData());
    }

    @ApiOperation("批量删除")
    @SaCheckPermission("business:station:parking:delete")
    @PostMapping("/batchDelete")
    public boolean batchDelete(@Validated @RequestBody Request<List<String>> request) {
        return parkingManagerService.batchDelete(request.getData());
    }

    /**
     * 获取选择框列表
     */
    @ApiOperation(value = "获取选择框列表", notes = "获取选择框列表")
    @PostMapping("/optionSelect")
    public List<ParkingVo> optionSelect(@Validated @RequestBody PageRequest<ParkingQueryBo> pageRequest) {
        return parkingManagerService.queryList(pageRequest);
    }

}
