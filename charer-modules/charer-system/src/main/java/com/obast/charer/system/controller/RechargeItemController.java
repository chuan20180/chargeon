package com.obast.charer.system.controller;

import com.obast.charer.system.dto.bo.RechargeItemBo;
import com.obast.charer.system.dto.vo.recharge.RechargeItemVo;
import com.obast.charer.qo.RechargeItemQueryBo;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.validate.AddGroup;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.common.validate.QueryGroup;

import com.obast.charer.system.service.business.IRechargeItemManagerService;
import cn.dev33.satoken.annotation.SaCheckPermission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
 * @ Description：充值方案管理
 */
@Api(tags = {"充值方案管理"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/rechargeItem")
public class RechargeItemController {

    private final IRechargeItemManagerService rechargeItemManagerService;

    @ApiOperation(value = "列表", notes = "列表", httpMethod = "POST")
    @SaCheckPermission("business:recharge:list")
    @PostMapping("/list")
    public Paging<RechargeItemVo> list(@RequestBody @Validated(QueryGroup.class) PageRequest<RechargeItemQueryBo> pageRequest) {
        return rechargeItemManagerService.queryPageList(pageRequest);
    }

    @ApiOperation(value = "创建")
    @SaCheckPermission("business:recharge:add")
    @Log(title = "充值方案", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public boolean add(@Validated(AddGroup.class) @RequestBody Request<RechargeItemBo> bo) {
        return rechargeItemManagerService.addRechargeItem(bo.getData());
    }

    @ApiOperation(value = "修改")
    @SaCheckPermission("business:recharge:edit")
    @Log(title = "充值方案", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public boolean edit(@Validated(EditGroup.class) @RequestBody Request<RechargeItemBo> bo) {
        return rechargeItemManagerService.updateRechargeItem(bo.getData());
    }

    @ApiOperation(value = "状态修改")
    @SaCheckPermission("business:recharge:edit")
    @Log(title = "充值方案", businessType = BusinessType.UPDATE)
    @PostMapping("/changeState")
    public boolean changeState(@RequestBody Request<RechargeItemBo> bo) {
        RechargeItemBo data = bo.getData();
        return rechargeItemManagerService.updateStatus(data);
    }

    @ApiOperation("删除")
    @SaCheckPermission("business:recharge:remove")
    @Log(title = "充值方案", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    public boolean remove(@Validated @RequestBody Request<String> request) {
        return rechargeItemManagerService.removeRechargeItem(request.getData());
    }

    @ApiOperation("批量删除")
    @SaCheckPermission("business:recharge:remove")
    @PostMapping("/batchRemove")
    public boolean batchRemove(@Validated @RequestBody Request<List<String>> request) {
        return rechargeItemManagerService.batchRemoveRechargeItem(request.getData());
    }

    /**
     * 获取选择框列表
     */
    @ApiOperation(value = "获取充值方案选择框列表", notes = "获取充值方案选择框列表")
    @PostMapping("/optionSelect")
    public List<RechargeItemVo> optionSelect(@RequestBody PageRequest<RechargeItemQueryBo> pageRequest) {
        return rechargeItemManagerService.queryList(pageRequest);
    }
}