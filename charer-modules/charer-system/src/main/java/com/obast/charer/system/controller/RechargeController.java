package com.obast.charer.system.controller;

import com.obast.charer.system.dto.bo.RechargeBo;
import com.obast.charer.system.dto.vo.recharge.RechargeVo;
import com.obast.charer.qo.RechargeQueryBo;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.validate.AddGroup;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.common.validate.QueryGroup;

import com.obast.charer.system.service.business.IRechargeManagerService;
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
@RequestMapping("/admin/recharge")
public class RechargeController {

    private final IRechargeManagerService rechargeManagerService;

    @ApiOperation(value = "列表", notes = "列表", httpMethod = "POST")
    @SaCheckPermission("business:recharge:list")
    @PostMapping("/list")
    public Paging<RechargeVo> list(@RequestBody @Validated(QueryGroup.class) PageRequest<RechargeQueryBo> pageRequest) {
        return rechargeManagerService.queryPageList(pageRequest);
    }

    @ApiOperation(value = "创建")
    @SaCheckPermission("business:recharge:add")
    @Log(title = "充值方案", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public boolean add(@Validated(AddGroup.class) @RequestBody Request<RechargeBo> bo) {
        return rechargeManagerService.addRecharge(bo.getData());
    }

    @ApiOperation(value = "修改")
    @SaCheckPermission("business:recharge:edit")
    @Log(title = "充值方案", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public boolean edit(@Validated(EditGroup.class) @RequestBody Request<RechargeBo> bo) {
        return rechargeManagerService.updateRecharge(bo.getData());
    }

    @ApiOperation(value = "状态修改")
    @SaCheckPermission("business:recharge:edit")
    @Log(title = "充电桩管理", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    public void changeStatus(@RequestBody Request<RechargeBo> bo) {
        RechargeBo data = bo.getData();
        rechargeManagerService.updateStatus(data);
    }

    @ApiOperation(value = "状态修改")
    @SaCheckPermission("business:recharge:edit")
    @Log(title = "充值方案", businessType = BusinessType.UPDATE)
    @PostMapping("/changeState")
    public boolean changeState(@RequestBody Request<RechargeBo> bo) {
        RechargeBo data = bo.getData();
        return rechargeManagerService.updateStatus(data);
    }

    @ApiOperation("删除")
    @SaCheckPermission("business:recharge:delete")
    @Log(title = "充值方案", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    public boolean remove(@Validated @RequestBody Request<String> request) {
        return rechargeManagerService.removeRecharge(request.getData());
    }

    @ApiOperation("批量删除")
    @SaCheckPermission("business:recharge:delete")
    @PostMapping("/batchRemove")
    public boolean batchRemove(@Validated @RequestBody Request<List<String>> request) {
        return rechargeManagerService.batchRemoveRecharge(request.getData());
    }

    /**
     * 获取选择框列表
     */
    @ApiOperation(value = "获取充值方案选择框列表", notes = "获取充值方案选择框列表")
    @PostMapping("/optionSelect")
    public List<RechargeVo> optionSelect(@RequestBody PageRequest<RechargeQueryBo> pageRequest) {
        return rechargeManagerService.queryList(pageRequest);
    }
}