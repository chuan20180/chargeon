package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.validate.AddGroup;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.common.validate.QueryGroup;
import com.obast.charer.system.dto.bo.PriceBo;
import com.obast.charer.system.dto.vo.price.PriceVo;
import com.obast.charer.system.service.business.IPriceManagerService;
import com.obast.charer.qo.PriceQueryBo;
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
@Api(tags = {"计价规则管理"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/price/charge")
public class PriceChargeController {

    private final IPriceManagerService priceManagerService;

    @ApiOperation(value = "列表", notes = "列表", httpMethod = "POST")
    @SaCheckPermission("business:price_charge:list")
    @PostMapping("/list")
    public Paging<PriceVo> list(@RequestBody @Validated(QueryGroup.class) PageRequest<PriceQueryBo> pageRequest) {
        return priceManagerService.queryPageList(pageRequest);
    }

    @ApiOperation(value = "创建")
    @SaCheckPermission("business:price_charge:add")
    @Log(title = "计价规则", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public boolean add(@Validated(AddGroup.class) @RequestBody Request<PriceBo> bo) {
        return priceManagerService.addPrice(bo.getData());
    }

    @ApiOperation(value = "修改")
    @SaCheckPermission("business:price_charge:edit")
    @Log(title = "计价规则", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public boolean edit(@Validated(EditGroup.class) @RequestBody Request<PriceBo> bo) {
        return priceManagerService.updatePrice(bo.getData());
    }

    @ApiOperation(value = "状态修改")
    @SaCheckPermission("business:price_charge:edit")
    @Log(title = "计价规则", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    public void changeStatus(@RequestBody Request<PriceBo> bo) {
        PriceBo data = bo.getData();
        priceManagerService.updateStatus(data);
    }

    @ApiOperation(value = "下发")
    @SaCheckPermission("business:price_charge:edit")
    @Log(title = "计价规则", businessType = BusinessType.UPDATE)
    @PostMapping("/issue")
    public boolean issue(@Validated(EditGroup.class) @RequestBody Request<String> bo) {
        return priceManagerService.issuePrice(bo.getData());
    }

    @ApiOperation("删除")
    @SaCheckPermission("business:price_charge:delete")
    @Log(title = "计价规则", businessType = BusinessType.DELETE)
    @PostMapping("/delete")
    public boolean delete(@Validated @RequestBody Request<String> request) {
        return priceManagerService.deletePrice(request.getData());
    }

    @ApiOperation("批量删除")
    @SaCheckPermission("business:price_charge:delete")
    @PostMapping("/batchDelete")
    public boolean batchDelete(@Validated @RequestBody Request<List<String>> request) {
        return priceManagerService.batchDeletePrice(request.getData());
    }

    /**
     * 获取选择框列表
     */
    @ApiOperation(value = "获取计价规则选择框列表", notes = "获取计价规则选择框列表")
    @PostMapping("/optionSelect")
    public List<PriceVo> optionSelect(@RequestBody @Validated(QueryGroup.class) PageRequest<PriceQueryBo> pageRequest) {
        return priceManagerService.queryList(pageRequest);
    }

}
