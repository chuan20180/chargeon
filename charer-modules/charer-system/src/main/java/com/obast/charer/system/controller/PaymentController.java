package com.obast.charer.system.controller;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.api.Request;
import com.obast.charer.common.log.annotation.Log;
import com.obast.charer.common.log.enums.BusinessType;
import com.obast.charer.system.dto.bo.PaymentBo;
import com.obast.charer.system.dto.vo.payment.PaymentVo;
import com.obast.charer.system.service.system.IPaymentManagerService;
import com.obast.charer.qo.PaymentQueryBo;
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
 * @ Description：支付方式管理
 */
@Api(tags = {"支付方式"})
@Slf4j
@RestController
@RequestMapping("/admin/payment")
public class PaymentController {

    @Autowired
    private IPaymentManagerService paymentManagerService;

    @ApiOperation(value = "列表", notes = "列表", httpMethod = "POST")
    @SaCheckPermission("system:payment_config:list")
    @PostMapping("/list")
    public Paging<PaymentVo> queryPageList(@Validated @RequestBody PageRequest<PaymentQueryBo> pageRequest) {
        return paymentManagerService.queryPageList(pageRequest);
    }

    @ApiOperation(value = "设置属性")
    @SaCheckPermission("system:payment_config:setting")
    @Log(title = "支付方式", businessType = BusinessType.UPDATE)
    @PostMapping("/changeProperties")
    public void changeProperties(@RequestBody Request<PaymentBo> bo) {
        PaymentBo data = bo.getData();
        paymentManagerService.updateProperties(data);
    }

    @ApiOperation(value = "状态修改")
    @SaCheckPermission("system:payment_config:change_status")
    @Log(title = "支付方式", businessType = BusinessType.UPDATE)
    @PostMapping("/changeStatus")
    public void changeStatus(@RequestBody Request<PaymentBo> bo) {
        PaymentBo data = bo.getData();
        paymentManagerService.updateStatus(data);
    }

    /**
     * 获取选择框列表
     */
    @ApiOperation(value = "获取选择框列表", notes = "获取选择框列表")
    @PostMapping("/optionSelect")
    public List<PaymentVo> optionSelect(@Validated @RequestBody PageRequest<PaymentQueryBo> pageRequest) {
        return paymentManagerService.queryList(pageRequest);
    }
}
