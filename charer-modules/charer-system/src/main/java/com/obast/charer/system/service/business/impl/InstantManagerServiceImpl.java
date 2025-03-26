package com.obast.charer.system.service.business.impl;

import com.obast.charer.common.api.PageRequest;
import com.obast.charer.common.api.Paging;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.model.PaymentNotify;
import com.obast.charer.common.utils.MapstructUtils;
import com.obast.charer.data.business.ICustomerData;
import com.obast.charer.data.business.IInstantData;

import com.obast.charer.enums.InstantStateEnum;
import com.obast.charer.model.Instant;
import com.obast.charer.model.customer.Customer;

import com.obast.charer.qo.InstantQueryBo;


import com.obast.charer.system.dto.vo.InstantVo;
import com.obast.charer.system.service.business.IInstantManagerService;
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
public class InstantManagerServiceImpl implements IInstantManagerService {

    @Autowired
    private IInstantData instantData;

    @Autowired
    private ICustomerData customerData;

    @Override
    public Paging<InstantVo> queryPageList(PageRequest<InstantQueryBo> pageRequest) {
        return MapstructUtils.convert(instantData.findPage(pageRequest), InstantVo.class);
    }

    @Override
    public List<InstantVo> queryList(PageRequest<InstantQueryBo> pageRequest) {
        return MapstructUtils.convert(instantData.findList(pageRequest.getData()), InstantVo.class);
    }

    @Override
    public InstantVo queryDetail(String stationId) {
        return MapstructUtils.convert(instantData.findById(stationId), InstantVo.class);
    }

    @Override
    public boolean removeInstant(String priceId) {
        priceId = getDetail(priceId).getId();
        instantData.deleteById(priceId);
        return true;
    }

    @Override
    public boolean batchRemoveInstant(List<String> ids) {
        instantData.deleteByIds(ids);
        return true;
    }

    @Override
    public Instant getDetail(String priceId) {
        return instantData.findById(priceId);
    }

    /**
     * 充值成功
     */
    @Transactional
    @Override
    public void complete(PaymentNotify paymentNotify) {

        log.debug("[充值入帐]开始进行入帐， instantTranId={}", paymentNotify.getOutTradeNo());
        Instant instant = instantData.findByTranId(paymentNotify.getOutTradeNo());
        if(instant == null) {
            log.error("[充值入帐]充值订单不存在， instantTranId={}", paymentNotify.getOutTradeNo());
            throw new BizException(ErrCode.TOPUP_NOT_FOUND);
        }

        InstantStateEnum stateEnum = InstantStateEnum.valueOf(paymentNotify.getTradeState());

        if(stateEnum.equals(InstantStateEnum.Fail)) {
            log.debug("[充值入帐]充值失败， instantTranId={}", paymentNotify.getOutTradeNo());
            instant.setState(InstantStateEnum.Fail);
            instantData.save(instant);
            return;
        }

        Customer customer = customerData.findByUserName(instant.getUserName());
        if(customer == null) {
            log.error("[充值入帐]客户不存在， instantTranId={}", paymentNotify.getOutTradeNo());
            throw new BizException(ErrCode.CUSTOMER_NOT_FOUND);
        }

        instant.setState(InstantStateEnum.Successful);
        instant.setPayTime(new Date());
        instant.setPayId(paymentNotify.getTransactionId());
        instant.setTradeStateDesc(paymentNotify.getTradeStateDesc());
        instant.setTradeType(paymentNotify.getTradeType());
        instant.setBankType(paymentNotify.getBankType());

        instantData.save(instant);

        log.debug("[充值入帐]入帐成功， instantTranId={}", paymentNotify.getOutTradeNo());
    }
}
