package com.obast.charer.openapi.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.obast.charer.common.satoken.util.LoginHelper;
import com.obast.charer.openapi.dto.vo.OpenCouponCodeVo;
import com.obast.charer.qo.CouponCodeQueryBo;
import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.openapi.service.IOpenCouponCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"openapi-优惠码"})
@Slf4j
@RestController
@RequestMapping("/openapi/v1/coupon")
public class OpenCouponController {

    @Autowired
    private IOpenCouponCodeService couponCodeService;

    @ApiOperation("列表")
    @PostMapping("/list")
    public Paging<OpenCouponCodeVo> list(@RequestBody @Validated PageRequest<CouponCodeQueryBo> pageRequest) {
        String customerId = LoginHelper.getUserId();
        pageRequest.getData().setCustomerId(customerId);
        return couponCodeService.queryPage(pageRequest);
    }

    @ApiOperation("详情")
    @PostMapping("/detail")
    public OpenCouponCodeVo getDetail(@RequestBody @Validated Request<String> bo) {
        return couponCodeService.queryDetail(bo.getData());
    }
}
