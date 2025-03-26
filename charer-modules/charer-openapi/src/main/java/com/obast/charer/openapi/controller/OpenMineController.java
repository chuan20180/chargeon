package com.obast.charer.openapi.controller;

import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.satoken.util.LoginHelper;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.common.web.core.BaseController;
import com.obast.charer.openapi.dto.vo.OpenCouponCodeVo;
import com.obast.charer.openapi.dto.vo.OpenCustomerVo;
import com.obast.charer.openapi.dto.vo.OpenOrdersVo;
import com.obast.charer.openapi.service.IOpenCouponCodeService;
import com.obast.charer.openapi.service.IOpenCustomerService;
import com.obast.charer.openapi.service.IOpenOrdersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = {"openapi-我的"})
@Slf4j
@RestController
@RequestMapping("/openapi/v1/mine")
public class OpenMineController  extends BaseController {

    @Autowired
    private IOpenCustomerService customerService;

    @Autowired
    private IOpenOrdersService ordersService;

    @Autowired
    private IOpenCouponCodeService couponCodeService;

    @ApiOperation("首页")
    @PostMapping("/index")
    public Map<?,?> index() {

        Map<String, Object> result = new HashMap<>();
        String customerId = LoginHelper.getUserId();
        String userName = LoginHelper.getUserName();

        OpenCustomerVo customer = customerService.queryDetail(customerId);
        if(customer == null) {
            throw new BizException(ErrCode.CUSTOMER_NOT_FOUND);
        }
        result.put("customer", customer);

        boolean needFillCustomerInfo = false;

        if(StringUtils.isBlank(customer.getMobile()) || StringUtils.isBlank(customer.getNickName())) {
            needFillCustomerInfo = true;
        }

        result.put("needFillCustomerInfo", needFillCustomerInfo);

        List<OpenOrdersVo> orders = ordersService.queryByUserName(userName);
        result.put("orderTotal", orders.size());

        List<OpenCouponCodeVo> couponCodes = couponCodeService.queryByCustomerId(customerId);
        result.put("couponCodeTotal", couponCodes.size());

        return result;
    }

}
