package com.obast.charer.system.service.business.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.model.PaymentNotify;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.ICustomerData;
import com.obast.charer.data.business.ITopupData;
import com.obast.charer.enums.*;
import com.obast.charer.model.customer.Customer;
import com.obast.charer.model.topup.Topup;
import com.obast.charer.qo.TopupQueryBo;
import com.obast.charer.system.dto.bo.TopupBo;
import com.obast.charer.system.dto.vo.topup.TopupVo;
import com.obast.charer.system.service.business.ITopupManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充值记录服务实现
 */
@Slf4j
@Service
public class TopupManagerServiceImpl implements ITopupManagerService {

    @Autowired
    private ITopupData topupData;

    @Autowired
    private ICustomerData customerData;

    @Override
    public Paging<TopupVo> queryPageList(PageRequest<TopupQueryBo> pageRequest) {
        return MapstructUtils.convert(topupData.findPage(pageRequest), TopupVo.class);
    }

    @Override
    public List<TopupVo> queryList(PageRequest<TopupQueryBo> pageRequest) {
        return MapstructUtils.convert(topupData.findList(pageRequest.getData()), TopupVo.class);
    }

    @Override
    public TopupVo queryDetail(String stationId) {
        return MapstructUtils.convert(topupData.findById(stationId), TopupVo.class);
    }

    @Override
    public boolean removeTopup(String priceId) {
        priceId = getDetail(priceId).getId();
        topupData.deleteById(priceId);
        return true;
    }

    @Override
    public boolean batchRemoveTopup(List<String> ids) {
        topupData.deleteByIds(ids);
        return true;
    }

    @Override
    public Topup getDetail(String priceId) {
        return topupData.findById(priceId);
    }

    /**
     * 系统充值
     */
    @Transactional
    @Override
    public void add(TopupBo bo) {
        Customer customer = customerData.findById(bo.getCustomerId());
        if(customer == null) {
            throw new BizException(ErrCode.CUSTOMER_NOT_FOUND);
        }

        BigDecimal arrivalAmount = bo.getArrivalAmount();
        if(arrivalAmount.compareTo(new BigDecimal(0))<=0) {
            throw new BizException(ErrCode.TOPUP_AMOUNT_ERROR);
        }

        Topup topup = new Topup();
        topup.setSource(TopupSourceEnum.System);
        topup.setRechargeType(RechargeTypeEnum.Normal);
        topup.setCustomerId(customer.getId());
        topup.setUserName(customer.getUserName());
        topup.setTopupAmount(arrivalAmount);
        topup.setPaidAmount(arrivalAmount);
        topup.setGive(new BigDecimal(0));
        topup.setMinus(new BigDecimal(0));
        topup.setDiscount(new BigDecimal(1));
        topup.setArrivalAmount(arrivalAmount);
        topup.setRefundLocked(LockEnum.UnLocked);
        topup.setRefundedAmount(new BigDecimal(0));
        topup.setState(TopupStateEnum.Successful);
        topup.setTopupTime(new Date());
        topup.setPayTime(new Date());
        topup.setNote(bo.getNote());
        topupData.add(topup);

        //保存充值记录到余额
        customer.setBalanceAmount(customer.getBalanceAmount().add(arrivalAmount));
        customerData.save(customer);
    }

    /**
     * 充值成功
     */
    @Transactional
    @Override
    public void complete(PaymentNotify paymentNotify) {

        log.debug("[充值入帐]开始进行入帐， topupTranId={}", paymentNotify.getOutTradeNo());
        Topup topup = topupData.findByTranId(paymentNotify.getOutTradeNo());
        if(topup == null) {
            log.error("[充值入帐]充值订单不存在， topupTranId={}", paymentNotify.getOutTradeNo());
            throw new BizException(ErrCode.TOPUP_NOT_FOUND);
        }

        TopupStateEnum stateEnum = TopupStateEnum.valueOf(paymentNotify.getTradeState());

        if(stateEnum.equals(TopupStateEnum.Fail)) {
            log.debug("[充值入帐]充值失败， topupTranId={}", paymentNotify.getOutTradeNo());
            topup.setState(TopupStateEnum.Fail);
            topupData.save(topup);
            return;
        }

        Customer customer = customerData.findByUserName(topup.getUserName());
        if(customer == null) {
            log.error("[充值入帐]客户不存在， topupTranId={}", paymentNotify.getOutTradeNo());
            throw new BizException(ErrCode.CUSTOMER_NOT_FOUND);
        }

        topup.setState(TopupStateEnum.Successful);
        topup.setPayTime(new Date());
        topup.setPayId(paymentNotify.getTransactionId());
        topup.setTradeStateDesc(paymentNotify.getTradeStateDesc());
        topup.setTradeType(paymentNotify.getTradeType());
        topup.setBankType(paymentNotify.getBankType());

        topupData.save(topup);

        //保存充值记录到余额
        customer.setBalanceAmount(customer.getBalanceAmount().add(topup.getArrivalAmount()));

        switch (topup.getRechargeType()) {
            case Normal:
                break;
            case Give:
                customer.setGiveAmount(customer.getGiveAmount().add(topup.getGive()));
                break;
            case Minus:
                break;
            case Quota:

                if(topup.getDiscount().compareTo(new BigDecimal(1)) < 0) {
                    Customer.Quota quota = new Customer.Quota(topup.getArrivalAmount(), topup.getDiscount());
                    customer.setQuotaAmount(Customer.Quota.add(quota, customer.getQuotaAmount()));
                }
                break;
            default:
                throw new BizException(ErrCode.RECHARGE_TYPE_ERROR);
        }

        customerData.save(customer);

        log.debug("[充值入帐]入帐成功， topupTranId={}", paymentNotify.getOutTradeNo());
    }
}
