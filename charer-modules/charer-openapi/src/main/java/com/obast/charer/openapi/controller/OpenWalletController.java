package com.obast.charer.openapi.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.satoken.util.LoginHelper;
import com.obast.charer.common.web.core.BaseController;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.TopupStateEnum;
import com.obast.charer.qo.PaymentQueryBo;
import com.obast.charer.qo.PrePayQueryBo;
import com.obast.charer.qo.RechargeQueryBo;
import com.obast.charer.openapi.dto.vo.*;
import com.obast.charer.openapi.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = {"openapi-钱包"})
@Slf4j
@RestController
@RequestMapping("/openapi/v1/wallet")
public class OpenWalletController extends BaseController {

    @Autowired
    private IOpenRechargeService openRechargeService;

    @Autowired
    private IOpenPaymentService openPaymentService;

    @Autowired
    private IOpenTopupService openTopupService;

    @Autowired
    private IOpenPayService openPayService;

    @Autowired
    private IOpenCustomerService customerService;

    @Autowired
    private IOpenCouponCodeService couponCodeService;

    @ApiOperation("首页")
    @PostMapping("/index")
    public Map<?,?> index() {

        Map<String, Object> result = new HashMap<>();
        String customerId = LoginHelper.getUserId();

        OpenCustomerVo customer = customerService.queryDetail(customerId);
        if(customer == null) {
            throw new BizException(ErrCode.CUSTOMER_NOT_FOUND);
        }
        result.put("customer", customer);

        List<OpenCouponCodeVo> couponCodes = couponCodeService.queryByCustomerId(customerId);
        result.put("couponCodeTotal", couponCodes.size());

        return result;
    }

    @ApiOperation("充值页面")
    @PostMapping("/topup")
    public Map<String, Object> topup() {
        Map<String, Object> result = new HashMap<>();
        PaymentQueryBo paymentQueryBo = new PaymentQueryBo();
        paymentQueryBo.setStatus(EnableStatusEnum.Enabled);
        List<OpenPaymentVo> payments = openPaymentService.queryList(paymentQueryBo);
        result.put("payments", payments);

        RechargeQueryBo rechargeQueryBo = new RechargeQueryBo();
        rechargeQueryBo.setStatus(EnableStatusEnum.Enabled);
        List<OpenRechargeVo> recharges = openRechargeService.queryList(rechargeQueryBo);
        result.put("recharges", recharges);

        return result;
    }

    @ApiOperation("预支付")
    @PostMapping("/prepay")
    public Object prepay(@RequestBody @Validated Request<PrePayQueryBo> bo) {
         return openPayService.prepay(bo.getData());
    }

    @ApiOperation("支付结果")
    @PostMapping("/result")
    public Object result(@RequestBody @Validated Request<String> bo) {
        for(int i = 0; i < 30; i++) {
            log.info("[支付调试]查询支付结果执行 第{}次", i);
            OpenTopupVo topup = openTopupService.queryDetailByTranId(bo.getData());
            if(topup == null) {
                throw new BizException(ErrCode.TOPUP_NOT_FOUND);
            }

           if(topup.getState().equals(TopupStateEnum.Successful)) {
               return topup;
           }
           try {
               Thread.sleep(1000);
           } catch (Exception e) {
               log.error("线程sleep异常 {}", e.getLocalizedMessage());
           }
        }

        throw new BizException(ErrCode.PAYMENT_QUERY_RESPONSE_PARSE_EMPTY, "查询支付结果超时");
    }
}