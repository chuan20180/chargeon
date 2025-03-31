package com.obast.charer.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.qo.MapConfigQueryBo;
import com.obast.charer.system.dto.bo.MapConfigBo;
import com.obast.charer.system.dto.vo.map.MapConfigVo;
import com.obast.charer.system.service.system.IMapConfigManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：地图配置
 */
@Api(tags = {"地图配置"})
@Slf4j
@RestController
@RequestMapping("/admin/mapConfig")
public class MapConfigController {

    @Autowired
    private IMapConfigManagerService mapConfigManagerService;

    @ApiOperation(value = "列表", notes = "列表", httpMethod = "POST")
    @SaCheckPermission("system:map_config:list")
    @PostMapping("/list")
    public Paging<MapConfigVo> queryPageList(@Validated @RequestBody PageRequest<MapConfigQueryBo> pageRequest) {
        return mapConfigManagerService.queryPageList(pageRequest);
    }

    @ApiOperation(value = "设置参数")
    @SaCheckPermission("system:map_config:setting")
    @Log(title = "地图配置", businessType = BusinessType.UPDATE)
    @PostMapping("/changeProperties")
    public void changeProperties(@RequestBody Request<MapConfigBo> bo) {
        mapConfigManagerService.update(bo.getData());
    }

    @ApiOperation(value = "状态修改")
    @SaCheckPermission("system:map_config:change_status")
    @Log(title = "地图配置", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    public void changeStatus(@RequestBody Request<MapConfigBo> bo) {
        MapConfigBo data = bo.getData();
        mapConfigManagerService.updateStatus(data);
    }

    @ApiOperation(value = "查询可用的配置", notes = "查询可用的配置", httpMethod = "POST")
    @PostMapping("/available")
    public MapConfigVo queryAvailable() {
        //return mapConfigManagerService.queryByTenantId( LoginHelper.getTenantId());
        //todo

        return null;
    }
}
