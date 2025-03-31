package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.model.LoginUser;
import com.obast.charer.common.satoken.util.LoginHelper;
import com.obast.charer.system.dto.bo.StationBo;
import com.obast.charer.system.dto.vo.station.StationVo;
import com.obast.charer.system.service.business.IStationManagerService;
import com.obast.charer.qo.StationQueryBo;
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
 * @ Description：场站管理
 */
@Api(tags = {"场站管理"})
@Slf4j
@RestController
@RequestMapping("/admin/station")
public class StationController {

    @Autowired
    private IStationManagerService stationManagerService;

    @ApiOperation(value = "列表", notes = "列表", httpMethod = "POST")
    @SaCheckPermission("business:station:list")
    @PostMapping("/list")
    public Paging<StationVo> queryPageList(@Validated @RequestBody PageRequest<StationQueryBo> pageRequest) {


        LoginUser loginUser = LoginHelper.getLoginUser();

        log.debug("Login {}", loginUser);

        return stationManagerService.queryPageList(pageRequest);
    }

    @ApiOperation(value = "创建")
    @SaCheckPermission("business:station:add")
    @PostMapping("/add")
    public boolean create(@Validated @RequestBody Request<StationBo> bo) {
        return stationManagerService.addStation(bo.getData());
    }

    @ApiOperation(value = "修改")
    @SaCheckPermission("business:station:edit")
    @PostMapping("/edit")
    public boolean edit(@Validated @RequestBody Request<StationBo> bo) {
        return stationManagerService.updateStation(bo.getData());
    }

    @ApiOperation(value = "状态修改")
    @SaCheckPermission("business:station:edit")
    @Log(title = "场站管理", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    public void changeStatus(@RequestBody Request<StationBo> bo) {
        StationBo data = bo.getData();
        stationManagerService.updateStatus(data);
    }

    @ApiOperation("删除")
    @SaCheckPermission("business:station:delete")
    @PostMapping("/delete")
    public boolean delete(@Validated @RequestBody Request<String> request) {
        return stationManagerService.deleteStation(request.getData());
    }

    @ApiOperation("批量删除")
    @SaCheckPermission("business:station:delete")
    @PostMapping("/batchDelete")
    public boolean batchDelete(@Validated @RequestBody Request<List<String>> request) {
        return stationManagerService.batchDeleteStation(request.getData());
    }

    /**
     * 获取选择框列表
     */
    @ApiOperation(value = "获取选择框列表", notes = "获取选择框列表")
    @PostMapping("/optionSelect")
    public List<StationVo> optionSelect(@Validated @RequestBody PageRequest<StationQueryBo> pageRequest) {
        return stationManagerService.queryList(pageRequest);
    }

}
