package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.system.dto.bo.ChargerGunBo;
import com.obast.charer.system.dto.vo.device.ChargerGunVo;
import com.obast.charer.system.service.business.IChargerGunManagerService;
import com.obast.charer.qo.ChargerGunQueryBo;
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
 * @ Description：充电枪管理
 */
@Api(tags = {"充电枪管理"})
@Slf4j
@RestController
@RequestMapping("/admin/charger/gun")
public class ChargerGunController {

    @Autowired
    private IChargerGunManagerService chargerGunManagerService;

    @ApiOperation(value = "充电枪列表", notes = "充电枪列表", httpMethod = "POST")
    @SaCheckPermission("business:charger:list")
    @PostMapping("/list")
    public Paging<ChargerGunVo> queryPageList(@Validated @RequestBody PageRequest<ChargerGunQueryBo> pageRequest) {
        return chargerGunManagerService.queryPageList(pageRequest);
    }

    @ApiOperation(value = "创建充电枪")
    @SaCheckPermission("business:charger:add")
    @PostMapping("/add")
    public void createDevice(@RequestBody @Validated Request<ChargerGunBo> bo) {
        chargerGunManagerService.add(bo.getData());
    }

    @ApiOperation(value = "保存充电枪")
    @SaCheckPermission("business:charger:edit")
    @PostMapping("/edit")
    public void saveDevice(@RequestBody @Validated Request<ChargerGunBo> bo) {
        chargerGunManagerService.update(bo.getData());
    }

    @ApiOperation(value = "绑定车位")
    @SaCheckPermission("business:charger:edit")
    @PostMapping("/bindParking")
    public void bindParking(@RequestBody @Validated Request<ChargerGunBo> bo) {
        chargerGunManagerService.bindParking(bo.getData());
    }

    @ApiOperation("获取充电枪详情")
    @SaCheckPermission("business:charger:query")
    @PostMapping("/detail")
    public ChargerGunVo getDetail(@RequestBody @Validated Request<String> request) {
        return chargerGunManagerService.queryDetail(request.getData());
    }

    @ApiOperation("删除充电枪")
    @SaCheckPermission("business:charger:delete")
    @PostMapping("/delete")
    public boolean deleteDevice(@Validated @RequestBody Request<String> request) {
        return chargerGunManagerService.delete(request.getData());
    }

    @ApiOperation("批量删除充电枪")
    @SaCheckPermission("business:charger:delete")
    @PostMapping("/batchDelete")
    public boolean batchDelete(@Validated @RequestBody Request<List<String>> request) {
        return chargerGunManagerService.batchDelete(request.getData());
    }

    @ApiOperation(value = "充电枪选项列表", notes = "充电枪选项列表", httpMethod = "POST")
    @SaCheckPermission("business:charger:query")
    @PostMapping("/optionSelect")
    public List<ChargerGunVo> optionSelect(@Validated @RequestBody PageRequest<ChargerGunQueryBo> pageRequest) {
        return chargerGunManagerService.queryList(pageRequest);
    }
}