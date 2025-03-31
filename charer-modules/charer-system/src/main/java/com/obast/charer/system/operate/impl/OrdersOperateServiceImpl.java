package com.obast.charer.system.operate.impl;

import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.plugin.core.StopReasonEnum;
import com.obast.charer.common.utils.JsonUtils;
import com.obast.charer.common.utils.SpringUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.business.*;
import com.obast.charer.enums.*;
import com.obast.charer.model.coupon.CouponCode;
import com.obast.charer.model.customer.Customer;
import com.obast.charer.model.customer.CustomerLogin;
import com.obast.charer.model.order.Orders;
import com.obast.charer.model.order.OrdersSettle;
import com.obast.charer.model.promotion.Promotion;
import com.obast.charer.model.station.Station;
import com.obast.charer.system.listener.event.OrderSettledEvent;
import com.obast.charer.system.operate.INotifyOperateService;
import com.obast.charer.system.operate.IOrdersOperateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：充电订单管理服务实现
 */
@Slf4j
@Service
public class OrdersOperateServiceImpl implements IOrdersOperateService {

    @Autowired
    private IOrdersData ordersData;

    @Autowired
    private IStationData stationData;

    @Autowired
    private IOrdersSettleData ordersSettleData;

    @Autowired
    private ICustomerData customerData;

    @Autowired
    private ICustomerLoginData customerLoginData;

    @Autowired
    private ICouponCodeData couponCodeData;

    @Autowired
    private IPromotionData promotionData;


    @Autowired
    private INotifyOperateService notifyOperateService;


    /**
     * 订单结算
     *
     * @param orderId 订单id
     * @param note    备注
     */

    @Override
    @Transactional
    public void settle(String orderId, String note) {
        Orders order = ordersData.findById(orderId);
        if(order == null) {
            throw new BizException(ErrCode.ORDER_NOT_FOUND);
        }
        log.info("[结算调试]订单结算开始, orderId={}", order.getId());

        if(order.getState().equals(OrderStateEnum.Settled)) {
            return;
        }

        if(!order.getState().equals(OrderStateEnum.Finished)) {
            throw new BizException(ErrCode.ORDER_STATE_ERROR);
        }

        order.setNote(note);

        Customer customer = customerData.findByUserName(order.getUserName());
        if(customer == null) {
            throw new BizException(ErrCode.CUSTOMER_NOT_FOUND);
        }

        Station station = stationData.findById(order.getStationId());
        if(station == null) {
            throw new BizException(ErrCode.STATION_NOT_FOUND);
        }

        //客户账户余额
        BigDecimal customerBalanceAmount = customer.getBalanceAmount();
        BigDecimal customerGiveAmount = customer.getGiveAmount();
        List<Customer.Quota> customerQuotaAmount = customer.getQuotaAmount();

        //订单的原始电费 服务费 占位费
        BigDecimal orderElecAmount = order.getElecAmount().setScale(2, RoundingMode.HALF_UP);
        BigDecimal orderServiceAmount = order.getServiceAmount().setScale(2, RoundingMode.HALF_UP);

        /* **************************************************************************************** */
        /* 结算电费开始 */
        /* **************************************************************************************** */
        OrdersSettle ordersElecSettle = new OrdersSettle();
        ordersElecSettle.setCustomerId(order.getCustomerId());
        ordersElecSettle.setOrderId(order.getId());
        ordersElecSettle.setType(OrderSettleTypeEnum.Elec);
        ordersElecSettle.setAmount(orderElecAmount);
        ordersElecSettle.setTenantId(order.getTenantId());


        BigDecimal settledElecAmount = new BigDecimal(0);
        BigDecimal discountElecAmount = new BigDecimal(0);

        if(orderElecAmount.compareTo(new BigDecimal(0)) > 0) {
            if(customerBalanceAmount.compareTo(new BigDecimal(0)) > 0) {
                if(orderElecAmount.compareTo(customerBalanceAmount) <= 0) {
                    settledElecAmount = orderElecAmount;
                } else {
                    settledElecAmount = customerBalanceAmount;
                }
            }
        }

        ordersElecSettle.setSettledAmount(settledElecAmount);
        ordersElecSettle.setDiscountAmount(discountElecAmount);
        ordersSettleData.add(ordersElecSettle);

        customerBalanceAmount = customerBalanceAmount.subtract(settledElecAmount);

        /* **************************************************************************************** */
        /* 结算服务费开始 */
        /* **************************************************************************************** */

        BigDecimal servicePromotionDiscountAmount = new BigDecimal(0);
        BigDecimal serviceCouponDiscountAmount = new BigDecimal(0);
        BigDecimal serviceGiveDiscountAmount = new BigDecimal(0);
        BigDecimal serviceQuotaDiscountAmount = new BigDecimal(0);

        /* 处理服务费优惠基础数据 */
        //场站优惠
        Promotion servicePromotion = promotionData.findMaxServiceDiscountByStationId(station.getId());
        if(servicePromotion != null) {
            Promotion.Discount promotionDiscount = null;
            try {
                promotionDiscount = JsonUtils.parse(servicePromotion.getProperties(), Promotion.Discount.class);
            } catch (Exception e) {
                log.error(ErrCode.PROMOTION_PROPERTIES_PARSE_EXCEPTION.getValue());
            }
            if((promotionDiscount != null) && promotionDiscount.getDiscount() != null && promotionDiscount.getDiscount().compareTo(new BigDecimal(0)) > 0 && promotionDiscount.getDiscount().compareTo(new BigDecimal(1)) < 0) {
                servicePromotionDiscountAmount = orderServiceAmount.multiply(new BigDecimal(1).subtract(promotionDiscount.getDiscount())).setScale(2, RoundingMode.HALF_UP);
            } else {
                log.error(ErrCode.PROMOTION_PROPERTIES_DISCOUNT_VALUE_ERROR.getValue());
            }
        }

        //用户优惠券
        CouponCode serviceCouponCode = couponCodeData.findMaxServiceDiscountByCustomerIdAndStationId(customer.getId(), station.getId());
        if(serviceCouponCode != null) {
            if ((serviceCouponCode.getRemainedAmount() != null && serviceCouponCode.getRemainedAmount().compareTo(new BigDecimal(0)) > 0)) {
                if (orderServiceAmount.compareTo(serviceCouponCode.getAmount()) <= 0) {
                    serviceCouponDiscountAmount = orderServiceAmount;
                } else {
                    serviceCouponDiscountAmount = serviceCouponCode.getRemainedAmount();
                }
            }
        }

        //赠送金额
        if (orderServiceAmount.compareTo(customerGiveAmount) <= 0) {
            serviceGiveDiscountAmount = orderServiceAmount;
        } else {
            serviceGiveDiscountAmount = customerGiveAmount;
        }

        //服务费打折
        Customer.Quota maxQuota = Customer.Quota.findMaxDiscount(customerQuotaAmount);
        if(maxQuota  != null) {
            BigDecimal rate = new BigDecimal(0);
            if(order.getTotalAmount().compareTo(new BigDecimal(0))>0) {
                rate = orderServiceAmount.divide(order.getTotalAmount(), 4, RoundingMode.HALF_UP);
            }
            //当前balance最大折扣数量
            BigDecimal maxChargeAmount = maxQuota.getAmount().multiply(rate).multiply(new BigDecimal(1).subtract(maxQuota.getDiscount())).setScale(2, RoundingMode.HALF_UP);
            //当前服务费最大能优惠多少
            BigDecimal serviceRemainedNeedDiscountAmount = orderServiceAmount.multiply(new BigDecimal(1).subtract(maxQuota.getDiscount())).setScale(2, RoundingMode.HALF_UP);
            //当前服务费最大优惠的余额小于当前balance最大优惠金额
            if (serviceRemainedNeedDiscountAmount.compareTo(maxChargeAmount) <= 0) {
                serviceQuotaDiscountAmount = serviceRemainedNeedDiscountAmount;
            } else {
                serviceQuotaDiscountAmount = maxChargeAmount;
            }
        }

        Map<DiscountTypeEnum, BigDecimal> map = new HashMap<>();
        map.put(DiscountTypeEnum.Promotion, servicePromotionDiscountAmount);
        map.put(DiscountTypeEnum.Coupon, serviceCouponDiscountAmount);
        map.put(DiscountTypeEnum.Give, serviceGiveDiscountAmount);
        map.put(DiscountTypeEnum.Quota, serviceQuotaDiscountAmount);

        DiscountTypeEnum serviceMaxDiscountKey = null;
        BigDecimal serviceMaxDiscountAmount = new BigDecimal(0);

        for (Map.Entry<DiscountTypeEnum, BigDecimal> entry : map.entrySet()) {
            if (entry.getValue().compareTo(serviceMaxDiscountAmount) > 0) {
                serviceMaxDiscountAmount = entry.getValue();
                serviceMaxDiscountKey = entry.getKey();
            }
        }

        OrdersSettle ordersServiceSettle = new OrdersSettle();
        ordersServiceSettle.setCustomerId(order.getCustomerId());
        ordersServiceSettle.setOrderId(order.getId());
        ordersServiceSettle.setType(OrderSettleTypeEnum.Service);
        ordersServiceSettle.setAmount(orderServiceAmount);
        ordersServiceSettle.setTenantId(order.getTenantId());

        BigDecimal settledServiceAmount = new BigDecimal(0);
        BigDecimal discountServiceAmount = new BigDecimal(0);
        DiscountTypeEnum discountServiceType = null;
        String discountServiceRelateId = null;
        Customer.Quota discountQuotaAmount = null;

        if(orderServiceAmount.compareTo(new BigDecimal(0)) > 0) {
            //找到服务费优惠
            if(serviceMaxDiscountKey != null && serviceMaxDiscountAmount.compareTo(new BigDecimal(0)) > 0) {
                //扣服务费(活动)
                if(serviceMaxDiscountKey.equals(DiscountTypeEnum.Promotion)) {
                    assert servicePromotion != null;
                    discountServiceAmount = serviceMaxDiscountAmount;
                    discountServiceType = DiscountTypeEnum.Promotion;
                    discountServiceRelateId = servicePromotion.getId();
                    //扣服务费(优惠券)
                } else if(serviceMaxDiscountKey.equals(DiscountTypeEnum.Coupon)) {
                    assert serviceCouponCode != null;

                    serviceCouponCode.setState(CouponCodeStateEnum.Used);
                    serviceCouponCode.setRemainedAmount(serviceCouponCode.getRemainedAmount().subtract(serviceMaxDiscountAmount));
                    couponCodeData.save(serviceCouponCode);

                    discountServiceAmount = serviceMaxDiscountAmount;
                    discountServiceType = DiscountTypeEnum.Coupon;
                    discountServiceRelateId = serviceCouponCode.getId();
                    //扣服务费(赠送金额)
                } else if(serviceMaxDiscountKey.equals(DiscountTypeEnum.Give)) {

                    customerGiveAmount = customerGiveAmount.subtract(serviceMaxDiscountAmount);

                    discountServiceAmount = serviceMaxDiscountAmount;
                    discountServiceType = DiscountTypeEnum.Give;

                    //扣服务费(服务费打折额度)
                } else if(serviceMaxDiscountKey.equals(DiscountTypeEnum.Quota)) {
                    BigDecimal rate = new BigDecimal(0);
                    if(order.getTotalAmount().compareTo(new BigDecimal(0))>0) {
                        rate = orderServiceAmount.divide(order.getTotalAmount(), 4, RoundingMode.HALF_UP);
                    }

                    assert maxQuota != null;
                    BigDecimal chargeQuotaRemainedAmount = serviceMaxDiscountAmount.divide(new BigDecimal(1).subtract(maxQuota.getDiscount()), 4, RoundingMode.HALF_UP).divide(rate, 4, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP);

                    if(chargeQuotaRemainedAmount.compareTo(maxQuota.getAmount()) > 0) {
                        chargeQuotaRemainedAmount = maxQuota.getAmount();
                    }

                    customerQuotaAmount = Customer.Quota.subtract(new Customer.Quota(chargeQuotaRemainedAmount, maxQuota.getDiscount()), customerQuotaAmount);

                    discountServiceAmount = serviceMaxDiscountAmount;
                    discountServiceType = DiscountTypeEnum.Quota;
                    discountQuotaAmount = new Customer.Quota(chargeQuotaRemainedAmount, maxQuota.getDiscount());
                }
            }

            BigDecimal serviceRemainedAmount = orderServiceAmount.subtract(discountServiceAmount);

            //扣除服务费剩余金额
            if(serviceRemainedAmount.compareTo(new BigDecimal(0)) > 0) {
                if(customerBalanceAmount.compareTo(new BigDecimal(0)) > 0) {
                    if(serviceRemainedAmount.compareTo(customerBalanceAmount) <= 0) {
                        settledServiceAmount = serviceRemainedAmount;
                    } else {
                        settledServiceAmount = customerBalanceAmount;
                    }
                }
            }
        }

        ordersServiceSettle.setSettledAmount(settledServiceAmount);
        ordersServiceSettle.setDiscountAmount(discountServiceAmount);
        ordersServiceSettle.setDiscountType(discountServiceType);
        ordersServiceSettle.setDiscountQuotaAmount(discountQuotaAmount);
        ordersServiceSettle.setDiscountRelateId(discountServiceRelateId);
        ordersSettleData.add(ordersServiceSettle);

        customerBalanceAmount = customerBalanceAmount.subtract(settledServiceAmount);

        order.setState(OrderStateEnum.Settled);
        order = ordersData.update(order);

        //客户扣款
        customer.setBalanceAmount(customerBalanceAmount);
        customer.setGiveAmount(customerGiveAmount);
        customer.setQuotaAmount(customerQuotaAmount);
        customerData.save(customer);

        log.info("[结算调试]订单结算结束, orderId={}", order.getId());

        //发送订单结算完成事件
        SpringUtils.context().publishEvent(new OrderSettledEvent(order));
    }

    /**
     * 发送订单通知
     */
    @Override
    @Transactional
    public void notify(String orderId) {
        log.info("[通知调试]订单通知开始: orderId={}", orderId);

        Orders order = ordersData.findById(orderId);
        if(order == null) {
            log.debug("[通知调试]发送订单通知失败, msg={}", ErrCode.ORDER_NOT_FOUND.getValue());
            return;
        }

        if(!order.getState().equals(OrderStateEnum.Settled)) {
            log.debug("[通知调试]发送订单通知失败, msg={}", ErrCode.ORDER_STATE_ERROR.getValue());
            return;
        }

        Customer customer = customerData.findById(order.getCustomerId());
        if(customer == null) {
            log.debug("[通知调试]发送订单通知失败, msg={}", ErrCode.CUSTOMER_NOT_FOUND.getValue());
            return;
        }

        if(StringUtils.isBlank(order.getCustomerLoginId())) {
            log.debug("[通知调试]发送订单通知失败, msg={}", ErrCode.CUSTOMER_LOGIN_NOT_FOUND.getValue());
            return;
        }

        CustomerLogin customerLogin = customerLoginData.findById(order.getCustomerLoginId());
        if(customerLogin == null) {
            log.debug("[通知调试]发送订单通知失败, msg={}", ErrCode.CUSTOMER_LOGIN_NOT_FOUND.getValue());
            return;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("mobile", customer.getMobile());
        params.put("customerId", customer.getId());
        params.put("deviceDn", order.getChargerDn());

        String stopReason = order.getStopReason();
        try {
            stopReason = StopReasonEnum.valueOf(order.getStopReason()).getData();
        } catch (Exception ignored) {}

        params.put("stopReason", stopReason);
        params.put("chargerQty", String.format("%s", order.getTotalQuantity().setScale(4, RoundingMode.HALF_UP)));
        params.put("orderId", order.getId());
        params.put("orderTranId", order.getTranId());
        params.put("settledAmount", String.format("%s元", order.getSettledAmount().setScale(2, RoundingMode.HALF_UP)));
        params.put("dn", customerLogin.getDn());

        notifyOperateService.sendNotify(NotifyIdentifierEnum.ChargeFinish, customerLogin.getPlatform(), params);

        order.setNotify(OrderNotifyEnum.hasNotify);
        ordersData.save(order);

        log.info("[通知调试]订单通知结束: orderId: {}", order.getId());

    }
}
