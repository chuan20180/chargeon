package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.common.validate.AddGroup;
import com.obast.charer.common.validate.EditGroup;
import com.obast.charer.common.validate.QueryGroup;
import com.obast.charer.system.dto.bo.CouponCodeBo;
import com.obast.charer.system.dto.vo.coupon.CouponCodeVo;
import com.obast.charer.qo.CouponCodeQueryBo;
import com.obast.charer.system.service.business.ICouponCodeManagerService;
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
 * @ Description：优惠券管理
 */
@Api(tags = {"优惠券管理"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/coupon/code")
public class CouponCodeController {

    private final ICouponCodeManagerService couponCodeManagerService;

    @ApiOperation(value = "列表", notes = "列表", httpMethod = "POST")
    @SaCheckPermission("business:coupon:list")
    @PostMapping("/list")
    public Paging<CouponCodeVo> list(@RequestBody @Validated(QueryGroup.class) PageRequest<CouponCodeQueryBo> pageRequest) {
        return couponCodeManagerService.queryPageList(pageRequest);
    }

    @ApiOperation("删除")
    @SaCheckPermission("business:coupon:delete")
    @Log(title = "优惠券", businessType = BusinessType.DELETE)
    @PostMapping("/remove")
    public boolean remove(@Validated @RequestBody Request<String> request) {
        return couponCodeManagerService.removeCouponCode(request.getData());
    }

    @ApiOperation("批量删除")
    @SaCheckPermission("business:coupon:delete")
    @PostMapping("/batchRemove")
    public boolean batchRemove(@Validated @RequestBody Request<List<String>> request) {
        return couponCodeManagerService.batchRemoveCouponCode(request.getData());
    }

    @ApiOperation(value = "获取优惠券选择框列表", notes = "获取优惠券选择框列表")
    @PostMapping("/optionSelect")
    public List<CouponCodeVo> optionSelect(PageRequest<CouponCodeQueryBo> pageRequest) {
        return couponCodeManagerService.queryList(pageRequest);
    }
}