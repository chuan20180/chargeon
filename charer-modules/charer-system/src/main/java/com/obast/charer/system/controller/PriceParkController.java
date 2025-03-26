package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.validate.AddGroup;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.common.validate.QueryGroup;
import com.obast.charer.system.dto.bo.PriceParkBo;
import com.obast.charer.system.dto.vo.price.PriceParkVo;
import com.obast.charer.system.service.business.IPriceParkManagerService;
import com.obast.charer.qo.PriceParkQueryBo;
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
 * @ Description：计价规则管理
 */
@Api(tags = {"占位计费规则管理"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/price/park")
public class PriceParkController {

    private final IPriceParkManagerService priceParkManagerService;

    @ApiOperation(value = "列表", notes = "列表", httpMethod = "POST")
    @SaCheckPermission("business:price_park:list")
    @PostMapping("/list")
    public Paging<PriceParkVo> list(@RequestBody @Validated(QueryGroup.class) PageRequest<PriceParkQueryBo> pageRequest) {
        return priceParkManagerService.queryPageList(pageRequest);
    }

    @ApiOperation(value = "创建")
    @SaCheckPermission("business:price_park:add")
    @Log(title = "计价规则", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public boolean add(@Validated(AddGroup.class) @RequestBody Request<PriceParkBo> bo) {
        return priceParkManagerService.add(bo.getData());
    }

    @ApiOperation(value = "修改")
    @SaCheckPermission("business:price_park:edit")
    @Log(title = "计价规则", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public boolean edit(@Validated(EditGroup.class) @RequestBody Request<PriceParkBo> bo) {
        return priceParkManagerService.update(bo.getData());
    }

    /**
     * 状态修改
     */
    @ApiOperation(value = "状态修改")
    @SaCheckPermission("business:price_park:edit")
    @Log(title = "计价规则", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    public void changeStatus(@RequestBody Request<PriceParkBo> bo) {
        priceParkManagerService.updateStatus(bo.getData());
    }

    @ApiOperation("删除")
    @SaCheckPermission("business:price_park:delete")
    @Log(title = "计价规则", businessType = BusinessType.DELETE)
    @PostMapping("/delete")
    public boolean delete(@Validated @RequestBody Request<String> request) {
        return priceParkManagerService.delete(request.getData());
    }

    @ApiOperation("批量删除")
    @SaCheckPermission("business:price_park:delete")
    @PostMapping("/batchDelete")
    public boolean batchDelete(@Validated @RequestBody Request<List<String>> request) {
        return priceParkManagerService.batchDelete(request.getData());
    }

    /**
     * 获取选择框列表
     */
    @ApiOperation(value = "获取计价规则选择框列表", notes = "获取计价规则选择框列表")
    @PostMapping("/optionSelect")
    public List<PriceParkVo> optionSelect(@RequestBody @Validated(QueryGroup.class) PageRequest<PriceParkQueryBo> pageRequest) {
        return priceParkManagerService.queryList(pageRequest);
    }
}
