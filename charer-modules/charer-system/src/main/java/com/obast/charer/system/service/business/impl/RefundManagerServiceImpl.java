package com.obast.charer.system.service.business.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.model.RefundNotify;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.ICustomerData;
import com.obast.charer.data.business.IRefundBalanceData;
import com.obast.charer.data.business.IRefundData;
import com.obast.charer.data.business.ITopupData;
import com.obast.charer.data.platform.IPaymentData;
import com.obast.charer.enums.LockEnum;
import com.obast.charer.enums.PaymentIdentifierEnum;
import com.obast.charer.enums.RefundStateEnum;
import com.obast.charer.model.customer.Customer;
import com.obast.charer.model.payment.Payment;
import com.obast.charer.model.refund.Refund;
import com.obast.charer.model.refund.RefundBalance;
import com.obast.charer.model.topup.Topup;
import com.obast.charer.payment.core.PaymentProvider;
import com.obast.charer.payment.core.RefundResult;
import com.obast.charer.payment.core.config.RefundConfig;
import com.obast.charer.payment.core.service.IPaymentService;
import com.obast.charer.qo.RefundQueryBo;
import com.obast.charer.system.dto.bo.RefundBo;
import com.obast.charer.system.dto.vo.refund.RefundVo;
import com.obast.charer.system.service.business.IRefundManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：退款记录服务实现
 */
@Slf4j
@Service
public class RefundManagerServiceImpl implements IRefundManagerService {


    @Autowired
    private IRefundData refundData;

    @Autowired
    private IRefundBalanceData refundBalanceData;

    @Autowired
    private ICustomerData customerData;

    @Autowired
    private IPaymentData paymentData;

    @Autowired
    private ITopupData topupData;

    @Override
    public Paging<RefundVo> queryPageList(PageRequest<RefundQueryBo> pageRequest) {
        return MapstructUtils.convert(refundData.findPage(pageRequest), RefundVo.class);
    }

    @Override
    public List<RefundVo> queryList(PageRequest<RefundQueryBo> pageRequest) {
        return MapstructUtils.convert(refundData.findList(pageRequest.getData()), RefundVo.class);
    }

    @Override
    public Refund queryDetail(String stationId) {
        return refundData.findById(stationId);
    }

    @Override
    @Transactional
    public boolean addRefund(RefundBo bo) {
        Customer customer = customerData.findById(bo.getCustomerId());
        if(customer == null) {
            throw new BizException(ErrCode.CUSTOMER_NOT_FOUND);
        }

        BigDecimal refundAmount = bo.getAmount();
        if(refundAmount.compareTo(new BigDecimal(0)) <= 0) {
            throw new BizException(ErrCode.REFUND_AMOUNT_CAN_NOT_ZERO);
        }

        List<Topup> availableRefundTopups = topupData.findRefundableByCustomerId(customer.getId());
        if(availableRefundTopups.isEmpty()) {
            throw new BizException(ErrCode.TOPUP_NOT_FOUND);
        }

        BigDecimal availableRefundAmount = new BigDecimal(0);
        for(Topup topup: availableRefundTopups) {
            availableRefundAmount = availableRefundAmount.add(topup.getPaidAmount().subtract(topup.getRefundedAmount()));
        }

        if(availableRefundAmount.compareTo(refundAmount) < 0) {
            throw new BizException(ErrCode.REFUND_AMOUNT_CAN_NOT_GREAT_THAN_AVAILABLE_REFUND_AMOUNT);
        }

        Refund refund = bo.to(Refund.class);
        refund.setUserName(customer.getUserName());
        refund = refundData.add(refund);


        for(Topup topup : availableRefundTopups) {
            if(refundAmount.compareTo(new BigDecimal(0)) <= 0)  {
                break;
            }

            BigDecimal refundableAmount = topup.getPaidAmount().subtract(topup.getRefundedAmount());
            if(refundableAmount.compareTo(new BigDecimal(0)) <= 0) {
                continue;
            }

            RefundBalance refundBalance = new RefundBalance();
            refundBalance.setRefundId(refund.getId());
            refundBalance.setState(RefundStateEnum.Pending);

            BigDecimal refundingAmount;

            if(refundAmount.compareTo(refundableAmount) <= 0) {
                refundingAmount = refundAmount;
            } else {
                refundingAmount = refundableAmount;
            }

            refundBalance.setAmount(refundingAmount);
            refundBalance.setTopupId(topup.getId());

            refundBalanceData.add(refundBalance);

            topup.setRefundLocked(LockEnum.Locked);
            topupData.save(topup);

            refundAmount = refundAmount.subtract(refundingAmount);
        }

        if(refundAmount.compareTo(new BigDecimal(0)) > 0) {
            throw new BizException(ErrCode.REFUND_AMOUNT_CAN_NOT_GREAT_THAN_BALANCE);
        }

        return true;
    }

    @Override
    @Transactional
    public boolean doRefund(String id) {
        Refund refund = refundData.findById(id);
        if(refund == null) {
            throw new BizException(ErrCode.REFUND_NOT_FOUND);
        }

        if(!refund.getState().equals(RefundStateEnum.Pending)) {
            throw new BizException(ErrCode.REFUND_STATE_ERROR);
        }

        List<RefundBalance> refundBalances = refundBalanceData.findListByRefundId(refund.getId());

        for(RefundBalance refundBalance: refundBalances) {
            Topup topup = topupData.findById(refundBalance.getTopupId());
            if(topup == null) {
                throw new BizException(ErrCode.TOPUP_NOT_FOUND);
            }

            PaymentIdentifierEnum paymentIdentifierEnum;
            try {
                paymentIdentifierEnum = PaymentIdentifierEnum.valueOf(topup.getPaymentIdentifier());
            } catch (Exception e) {
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

            if(!topup.getRefundLocked().equals(LockEnum.Locked)) {
                throw new BizException(ErrCode.CUSTOMER_BALANCE_NOT_LOCKED);
            }

            RefundConfig refundConfig = new RefundConfig();
            refundConfig.setProperties(payment.getProperties());

            refundConfig.setOutRefundNo(refundBalance.getTranId());
            refundConfig.setOutTradeNo(topup.getPayId());
            refundConfig.setTotal(topup.getPaidAmount());
            refundConfig.setReason("用户退款");
            refundConfig.setAmount(refundBalance.getAmount());

            RefundResult result =  paymentService.refund(refundConfig);
            log.debug("[微信退款]退款服务器返回信息={}", result);

            refundBalance.setState(RefundStateEnum.valueOf(result.getStatus()));
            refundBalance.setPayId(result.getTransactionId());

            refundBalanceData.save(refundBalance);

            log.debug("[微信退款]退款请求成功 refundTranId={}", refundBalance.getTranId());
        }

        return true;
    }

    @Override
    @Transactional
    public boolean removeRefund(String id) {
        Refund refund = refundData.findById(id);
        if(refund == null) {
            throw new BizException(ErrCode.REFUND_NOT_FOUND);
        }

        if(!refund.getState().equals(RefundStateEnum.Pending)) {
            throw new BizException(ErrCode.REFUND_STATE_ERROR);
        }

        refundData.deleteById(refund.getId());

        List<RefundBalance> refundBalances = refundBalanceData.findListByRefundId(refund.getId());

        for(RefundBalance refundBalance: refundBalances) {
            Topup topup = topupData.findById(refundBalance.getTopupId());
            if(topup == null) {
                throw new BizException(ErrCode.TOPUP_NOT_FOUND);
            }

            if(!topup.getRefundLocked().equals(LockEnum.Locked)) {
                throw new BizException(ErrCode.CUSTOMER_BALANCE_NOT_LOCKED);
            }

            topup.setRefundLocked(LockEnum.UnLocked);
            topupData.save(topup);

            refundBalanceData.deleteById(refundBalance.getId());
        }

        return true;
    }

    @Transactional
    @Override
    public void complete(RefundNotify refundNotify) {

        log.debug("[退款出帐]出帐开始， refundTranId={}", refundNotify.getOutTradeNo());
        RefundBalance refundBalance = refundBalanceData.findByTranId(refundNotify.getOutRefundNo());
        if(refundBalance == null) {
            log.error("[退款出帐]退款失败, refund balance 未找到");
            throw new BizException(ErrCode.REFUND_BALANCE_NOT_FOUND);
        }

        Refund refund = refundData.findById(refundBalance.getRefundId());
        if(refund == null) {
            throw new BizException(ErrCode.REFUND_NOT_FOUND);
        }

        Customer customer = customerData.findById(refund.getCustomerId());
        if(customer == null) {
            throw new BizException(ErrCode.CUSTOMER_NOT_FOUND);
        }

        if(!refundBalance.getState().equals(RefundStateEnum.Processing)) {
            log.error("[退款出帐]退款失败, refund balance 状态错误, state={}", refundBalance.getState());
            throw new BizException(ErrCode.REFUND_STATE_ERROR);
        }

        refundBalance.setPayId(refundNotify.getRefundId());
        refundBalance.setSuccessTime(refundNotify.getSuccessTime());
        refundBalance.setUserReceivedAccount(refundNotify.getUserReceivedAccount());
        refundBalance.setState(RefundStateEnum.valueOf(refundNotify.getRefundStatus()));
        refundBalanceData.save(refundBalance);

        log.debug("[退款出帐]出帐结束， refundTranId={}", refundNotify.getOutTradeNo());

        if(!refundBalance.getState().equals(RefundStateEnum.Successful)) {
            log.debug("[退款出帐]出帐完成, 未成功，state={}, refundTranId={}", refundBalance.getState(), refundNotify.getOutTradeNo());
            return;
        }

        /* 退款成功操作 */
        if(customer.getBalanceAmount().compareTo(refundBalance.getAmount()) < 0) {
            log.error("[退款出帐]退款失败, customer 余额不足, customerId={}", customer.getId());
            throw new BizException(ErrCode.CUSTOMER_BALANCE_NOT_ENOUGH);
        }

        //解除topup锁定
        Topup topup = topupData.findById(refundBalance.getTopupId());
        if(topup == null) {
            log.error("[退款出帐]退款失败, 充值记录不存在, customerBalanceId={}", refundBalance.getTopupId());
            throw new BizException(ErrCode.TOPUP_NOT_FOUND);
        }
        topup.setRefundedAmount(topup.getRefundedAmount().add(refundBalance.getAmount()));
        topup.setRefundLocked(LockEnum.UnLocked);
        topupData.save(topup);


        //客户扣款
        customer.setBalanceAmount(customer.getBalanceAmount().subtract(refundBalance.getAmount()));
        customerData.save(customer);

        boolean completed = true;
        List<RefundBalance> balances = refundBalanceData.findListByRefundId(refund.getId());
        for(RefundBalance balance: balances) {
            if (!balance.getState().equals(RefundStateEnum.Successful)) {
                completed = false;
                break;
            }
        }

        if(completed) {
            refund.setState(RefundStateEnum.Successful);
            refundData.save(refund);
        }

        log.debug("[退款出帐]出帐成功， refundTranId={}", refundNotify.getOutTradeNo());
    }
}
