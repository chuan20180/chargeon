package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.system.dto.bo.AdsBo;
import com.obast.charer.system.dto.vo.ads.AdsVo;
import com.obast.charer.system.service.business.IAdsManagerService;
import com.obast.charer.qo.AdsQueryBo;
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
 * @ Description：广告管理
 */
@Api(tags = {"广告管理"})
@Slf4j
@RestController
@RequestMapping("/admin/ads")
public class AdsController {

    @Autowired
    private IAdsManagerService adsManagerService;

    @ApiOperation(value = "列表", notes = "列表", httpMethod = "POST")
    @SaCheckPermission("business:ads:list")
    @PostMapping("/list")
    public Paging<AdsVo> queryPageList(@Validated @RequestBody PageRequest<AdsQueryBo> pageRequest) {
        return adsManagerService.queryPageList(pageRequest);
    }

    @ApiOperation(value = "创建")
    @SaCheckPermission("business:ads:add")
    @PostMapping("/add")
    public boolean create(@Validated @RequestBody Request<AdsBo> bo) {
        return adsManagerService.addAds(bo.getData());
    }

    @ApiOperation(value = "修改")
    @SaCheckPermission("business:ads:edit")
    @PostMapping("/edit")
    public boolean edit(@Validated @RequestBody Request<AdsBo> bo) {
        return adsManagerService.updateAds(bo.getData());
    }

    @ApiOperation(value = "状态修改")
    @SaCheckPermission("business:ads:edit")
    @Log(title = "充电桩管理", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    public void changeStatus(@RequestBody Request<AdsBo> bo) {
        adsManagerService.updateStatus(bo.getData());
    }

    @ApiOperation("删除")
    @SaCheckPermission("business:ads:delete")
    @PostMapping("/delete")
    public boolean delete(@Validated @RequestBody Request<String> request) {
        return adsManagerService.deleteAds(request.getData());
    }

    @ApiOperation("批量删除")
    @SaCheckPermission("business:ads:delete")
    @PostMapping("/batchDelete")
    public boolean batchDelete(@Validated @RequestBody Request<List<String>> request) {
        return adsManagerService.batchDeleteAds(request.getData());
    }

    /**
     * 获取选择框列表
     */
    @ApiOperation(value = "获取选择框列表", notes = "获取选择框列表")
    @PostMapping("/optionSelect")
    public List<AdsVo> optionSelect(@Validated @RequestBody PageRequest<AdsQueryBo> pageRequest) {
        return adsManagerService.queryList(pageRequest);
    }
}
