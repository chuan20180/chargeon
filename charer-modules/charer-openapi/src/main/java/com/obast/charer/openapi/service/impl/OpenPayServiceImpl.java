package com.obast.charer.openapi.service.impl;

import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.model.LoginUser;
import com.obast.charer.common.satoken.util.LoginHelper;
import com.obast.charer.common.utils.JsonUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.business.*;
import com.obast.charer.data.platform.IPaymentData;
import com.obast.charer.data.platform.IRechargeItemData;
import com.obast.charer.enums.*;
import com.obast.charer.model.customer.Customer;
import com.obast.charer.model.customer.CustomerLogin;
import com.obast.charer.model.payment.Payment;
import com.obast.charer.model.platform.Recharge;
import com.obast.charer.model.platform.RechargeItem;
import com.obast.charer.model.topup.Topup;
import com.obast.charer.openapi.service.IOpenPayService;
import com.obast.charer.payment.core.PaymentProvider;
import com.obast.charer.payment.core.PaymentResult;
import com.obast.charer.payment.core.config.PaymentConfig;
import com.obast.charer.payment.core.service.IPaymentService;
import com.obast.charer.qo.PrePayQueryBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Slf4j
@Service
public class OpenPayServiceImpl implements IOpenPayService {

    @Autowired
    private IPaymentData paymentData;

    @Autowired
    private ICustomerData customerData;

    @Autowired
    private ICustomerLoginData customerLoginData;

    @Autowired
    private ITopupData topupData;

    @Autowired
    private IRechargeData rechargeData;

    @Autowired
    private IRechargeItemData rechargeItemData;

    @Override
    public PaymentResult prepay(PrePayQueryBo bo) {

        PaymentIdentifierEnum paymentIdentifierEnum = null;
        try {
            paymentIdentifierEnum = PaymentIdentifierEnum.valueOf(bo.getPayment());
        } catch (Exception e) {
            log.error("支付方式不在枚举值中 {}", bo.getPayment());
        }

        if(paymentIdentifierEnum == null) {
            throw new BizException(ErrCode.PAYMENT_TYPE_NOT_FOUND);
        }

        IPaymentService paymentService = PaymentProvider.getPayment(paymentIdentifierEnum);
        if(paymentService == null) {
            throw new BizException(ErrCode.PAYMENT_SERVICE_NOT_FOUND);
        }

        Payment payment = paymentData.findByIdentifier(paymentIdentifierEnum);
        if(payment == null) {
            throw new BizException(ErrCode.PAYMENT_CONFIG_NOT_FOUND);
        }

        BigDecimal amount = bo.getAmount();

        String rechargeItemId = bo.getRechargeItemId();

        RechargeTypeEnum rechargeTypeEnum = null;

        BigDecimal topupAmount = null;
        BigDecimal arrivalAmount = null;
        BigDecimal paidAmount = null;
        BigDecimal giveAmount = null;
        BigDecimal minusAmount = null;
        BigDecimal discountAmount = null;

        if(StringUtils.isBlank(rechargeItemId) && amount.compareTo(new BigDecimal(0)) == 0) {
            throw new BizException(ErrCode.TOPUP_AMOUNT_ERROR);
        }

        if(StringUtils.isNoneBlank(rechargeItemId)) {
            RechargeItem rechargeItem = rechargeItemData.findById(bo.getRechargeItemId());
            if(rechargeItem == null) {
                throw new BizException(ErrCode.RECHARGE_ITEM_NOT_FOUND);
            }

            Recharge recharge = rechargeData.findById(rechargeItem.getRechargeId());
            if(recharge == null) {
                throw new BizException(ErrCode.RECHARGE_NOT_FOUND);
            }

            rechargeTypeEnum = recharge.getType();

            switch (recharge.getType()) {
                case Normal:
                    topupAmount = rechargeItem.getAmount();
                    arrivalAmount = rechargeItem.getAmount();
                    paidAmount = rechargeItem.getAmount();
                    giveAmount = new BigDecimal(0);
                    minusAmount = new BigDecimal(0);
                    discountAmount = new BigDecimal(1);
                    break;
                case Give:
                    topupAmount = rechargeItem.getAmount();
                    arrivalAmount = rechargeItem.getAmount();
                    paidAmount = rechargeItem.getAmount();
                    giveAmount = rechargeItem.getGive();
                    minusAmount = new BigDecimal(0);
                    discountAmount = new BigDecimal(1);
                    break;
                case Minus:
                    topupAmount = rechargeItem.getAmount();
                    arrivalAmount = rechargeItem.getAmount();
                    paidAmount = rechargeItem.getAmount().subtract(rechargeItem.getMinus());
                    giveAmount = new BigDecimal(0);
                    minusAmount = new BigDecimal(0);
                    discountAmount = new BigDecimal(1);
                    break;
                case Quota:
                    topupAmount = rechargeItem.getAmount();
                    arrivalAmount = rechargeItem.getAmount();
                    paidAmount = rechargeItem.getAmount();
                    giveAmount = new BigDecimal(0);
                    minusAmount = new BigDecimal(0);
                    discountAmount = rechargeItem.getDiscount();
                    break;
            }
        } else {
            if(amount.compareTo(new BigDecimal(0)) <= 0) {
                throw new BizException(ErrCode.TOPUP_AMOUNT_ERROR);
            }

            topupAmount = amount;
            arrivalAmount = amount;
            paidAmount = amount;
            giveAmount = new BigDecimal(0);
            minusAmount = new BigDecimal(0);
            discountAmount = new BigDecimal(1);
        }

        LoginUser loginUser = LoginHelper.getLoginUser();

        Customer customer = customerData.findById(loginUser.getUserId());
        if(customer == null) {
            throw new BizException(ErrCode.CUSTOMER_NOT_FOUND);
        }

        CustomerLogin customerLogin = customerLoginData.findById(loginUser.getCustomerLoginId());
        if(customerLogin == null) {
            throw new BizException(ErrCode.CUSTOMER_LOGIN_NOT_FOUND);
        }

        //生成充值订单
        Topup topup = new Topup();
        topup.setArrivalAmount(arrivalAmount);
        topup.setPaidAmount(paidAmount);
        topup.setGive(giveAmount);
        topup.setMinus(minusAmount);
        topup.setDiscount(discountAmount);

        topup.setState(TopupStateEnum.Pending);
        topup.setCustomerId(customer.getId());
        topup.setUserName(customer.getUserName());
        topup.setRechargeType(rechargeTypeEnum);
        topup.setSource(TopupSourceEnum.Online);

        topup.setPaymentIdentifier(paymentIdentifierEnum.name());
        topup.setPaymentName(paymentIdentifierEnum.getMsg());
        topup.setTopupTime(new Date());
        topup.setTopupAmount(topupAmount);
        topup.setRefundLocked(LockEnum.UnLocked);
        topup.setRefundedAmount(new BigDecimal(0));

        topup = topupData.add(topup);

        log.debug("Topup实体: {}", JsonUtils.toJsonString(topup));

        PaymentConfig paymentConfig = new PaymentConfig();
        paymentConfig.setProperties(payment.getProperties());

        paymentConfig.setOutTradeNo(topup.getTranId());
        paymentConfig.setSubject("会员充值");
        paymentConfig.setAmount(topup.getPaidAmount());

        paymentConfig.setPayer(customerLogin.getDn());

        log.debug("paymentConfig实体: {}", JsonUtils.toJsonString(paymentConfig));
        return paymentService.prepay(paymentConfig);
    }


}
