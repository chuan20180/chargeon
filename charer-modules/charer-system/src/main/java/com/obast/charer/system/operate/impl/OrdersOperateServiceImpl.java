package com.obast.charer.system.operate.impl;

import com.obast.charer.common.constant.TenantConstants;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.plugin.core.StopReasonEnum;
import com.obast.charer.common.properties.CharerProperties;
import com.obast.charer.common.utils.JsonUtils;
import com.obast.charer.common.utils.SpringUtils;
import com.obast.charer.common.utils.StringUtils;
import com.obast.charer.data.business.*;
import com.obast.charer.data.system.ISysAgentStationData;
import com.obast.charer.data.system.ISysAgentStationDealerData;
import com.obast.charer.data.system.ISysDealerData;
import com.obast.charer.data.system.ISysTenantData;
import com.obast.charer.enums.*;
import com.obast.charer.model.coupon.CouponCode;
import com.obast.charer.model.customer.Customer;
import com.obast.charer.model.customer.CustomerLogin;
import com.obast.charer.model.device.Dcam;
import com.obast.charer.model.device.DcamParking;
import com.obast.charer.model.ledger.Ledger;
import com.obast.charer.model.order.Orders;
import com.obast.charer.model.order.OrdersSettle;
import com.obast.charer.model.park.Park;
import com.obast.charer.model.price.PricePark;
import com.obast.charer.model.promotion.Promotion;
import com.obast.charer.model.station.Station;
import com.obast.charer.model.system.SysAgent;
import com.obast.charer.model.system.SysAgentStationDealer;
import com.obast.charer.model.system.SysDealer;
import com.obast.charer.model.system.SysTenant;
import com.obast.charer.push.wechat.WechatPushEvent;
import com.obast.charer.system.listener.event.OrderSettledEvent;
import com.obast.charer.system.operate.INotifyOperateService;
import com.obast.charer.system.operate.IOrdersOperateService;
import com.obast.charer.system.service.platform.NotifyService;
import com.obast.charer.system.utils.ParkUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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
    private ILedgerData ledgerData;


    @Autowired
    private ISysDealerData sysDealerData;

    @Autowired
    private ISysTenantData sysTenantData;

    @Autowired
    private ISysAgentStationData sysAgentStationData;

    @Autowired
    private ISysAgentStationDealerData sysAgentStationDealerData;

    @Autowired
    private INotifyOperateService notifyOperateService;

    @Autowired
    private CharerProperties charerProperties;

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
     * 订单分润
     */
    @Override
    @Transactional
    public void deal(String orderId) {
        Orders order = ordersData.findById(orderId);
        if(order == null) {
            throw new BizException(ErrCode.ORDER_NOT_FOUND);
        }

        log.info("[分润调试]开始订单分润, orderId={}", orderId);

        if(!order.getState().equals(OrderStateEnum.Settled)) {
            log.error("[分润调试]分润失败，订单未结算, state={}", order.getState());
            throw new BizException(ErrCode.ORDER_STATE_ERROR);
        }

        if(!order.getDeal().equals(OrderDealEnum.NoDeal)) {
            log.error("[分润调试]分润失败，订单已分润, deal={}", order.getDeal());
            throw new BizException(ErrCode.ORDER_DEAL_ERROR);
        }

        //订单分润金额
        BigDecimal orderSettledElecAmount = order.getSettledElecAmount();
        BigDecimal orderSettledServiceAmount = order.getSettledServiceAmount();
        BigDecimal orderSettledParkAmount = order.getSettledParkAmount();

        //服务费和占位费参与分润
        BigDecimal orderProfitAmount = orderSettledServiceAmount.add(orderSettledParkAmount);

        BigDecimal totalProfitAmount = orderProfitAmount;

        //订单分润金额为0，自动结算
        if(orderProfitAmount.compareTo(new BigDecimal(0)) <= 0) {
            log.info("[分润调试]分润成功, 分润金额为0");
            order.setDeal(OrderDealEnum.Dealed);
            ordersData.save(order);
            return;
        }

        SysTenant sysTenant = sysTenantData.findById(order.getTenantId());
        //没有找到运营商
        if(sysTenant == null) {
            log.error("[分润调试]分润失败, 订单运营商未找到 tenantId={}", order.getTenantId());
            throw new BizException(ErrCode.TENANT_NOT_FOUND);
        }

        Station station = stationData.findById(order.getStationId());
        if(station == null) {
            log.error("[分润调试]分润失败, 场站未找到 stationId={}", order.getStationId());
            throw new BizException(ErrCode.STATION_NOT_FOUND);
        }

        //平台未开启分润, 全部结算给平台
        if(!charerProperties.getProfit().getEnabled()) {
            Ledger ledger = new Ledger();
            ledger.setDealerName(LedgerTypeEnum.Platform.getMsg());
            ledger.setType(LedgerTypeEnum.Platform);
            ledger.setCustomerId(order.getCustomerId());
            ledger.setUserName(order.getUserName());
            ledger.setOrderId(order.getId());
            ledger.setOrderTranId(order.getTranId());
            ledger.setStationId(station.getId());
            ledger.setStationName(station.getName());
            ledger.setStationAddress(station.getAddress());
            ledger.setChargerDn(order.getChargerDn());
            ledger.setGunNo(order.getGunNo());
            ledger.setStartTime(order.getStartTime());
            ledger.setEndTime(order.getEndTime());
            ledger.setTotalAmount(order.getTotalAmount());
            ledger.setTotalQuantity(order.getTotalQuantity());
            ledger.setChargeMinute(order.getChargeMinute());

            ledger.setSettledAmount(order.getSettledAmount());
            ledger.setSettledElecAmount(orderSettledElecAmount);
            ledger.setSettledServiceAmount(orderSettledServiceAmount);
            ledger.setSettledParkAmount(orderSettledParkAmount);

            ledger.setAmount(orderProfitAmount);
            ledger.setPercent(new BigDecimal(1));
            ledger.setState(LedgerStateEnum.Pending);
            ledger.setDealerId("-1");
            ledger.setAgentId("-1");
            ledger.setTenantId(TenantConstants.DEFAULT_TENANT_ID);
            ledger.setDealerName(null);
            ledger.setAgentName(null);
            ledger.setTenantName(null);
            ledgerData.add(ledger);
            log.info("[分润调试](平台未开启分润) 分润完成[平台],分润金额={}", orderProfitAmount);
            order.setDeal(OrderDealEnum.Dealed);
            ordersData.save(order);
            return;
        }

        //结算平台分润
        BigDecimal platformProfitPercent = new BigDecimal(0);
        if(sysTenant.getPlatformProfitPercent() != null) {
            platformProfitPercent = sysTenant.getPlatformProfitPercent();
        }

        if(platformProfitPercent.compareTo(new BigDecimal(1)) > 0 || platformProfitPercent.compareTo(new BigDecimal(0)) < 0) {
            log.error("[分润调试]分润失败,平台分润比例设置错误, 分润比例={}", platformProfitPercent);
            throw new BizException(ErrCode.TENANT_PROFIT_PERCENT_INVALID);
        }
        if(platformProfitPercent.compareTo(new BigDecimal(0)) > 0) {
            if(orderProfitAmount.compareTo(new BigDecimal(0)) > 0) {
                BigDecimal platformSettleProfitAmount = orderProfitAmount.multiply(platformProfitPercent).setScale(2, RoundingMode.HALF_UP);
                BigDecimal platformSettleProfitPercent = platformSettleProfitAmount.divide(totalProfitAmount, 2, RoundingMode.HALF_UP);

                orderProfitAmount = orderProfitAmount.subtract(platformSettleProfitAmount);

                Ledger ledger = new Ledger();
                ledger.setDealerName(LedgerTypeEnum.Platform.getMsg());
                ledger.setType(LedgerTypeEnum.Platform);
                ledger.setCustomerId(order.getCustomerId());
                ledger.setUserName(order.getUserName());
                ledger.setOrderId(order.getId());
                ledger.setOrderTranId(order.getTranId());
                ledger.setStationId(station.getId());
                ledger.setStationName(station.getName());
                ledger.setStationAddress(station.getAddress());
                ledger.setChargerDn(order.getChargerDn());
                ledger.setGunNo(order.getGunNo());
                ledger.setStartTime(order.getStartTime());
                ledger.setEndTime(order.getEndTime());
                ledger.setTotalAmount(order.getTotalAmount());
                ledger.setTotalQuantity(order.getTotalQuantity());
                ledger.setChargeMinute(order.getChargeMinute());

                ledger.setSettledAmount(order.getSettledAmount());
                ledger.setSettledElecAmount(orderSettledElecAmount);
                ledger.setSettledServiceAmount(orderSettledServiceAmount);
                ledger.setSettledParkAmount(orderSettledParkAmount);

                ledger.setAmount(platformSettleProfitAmount);
                ledger.setPercent(platformSettleProfitPercent);
                ledger.setState(LedgerStateEnum.Pending);
                ledger.setDealerId("-1");
                ledger.setAgentId("-1");
                ledger.setTenantId(TenantConstants.DEFAULT_TENANT_ID);
                ledger.setDealerName(null);
                ledger.setAgentName(null);
                ledger.setTenantName(null);
                ledgerData.add(ledger);

                log.error("[分润调试]分润完成[平台],分润金额={}", platformSettleProfitAmount);
            }
        }

        SysAgent sysAgent = sysAgentStationData.findAgentByStationId(station.getId());

        //场站没有绑定代理商则全部结算给运营商
        if(sysAgent == null) {
            if(orderProfitAmount.compareTo(new BigDecimal(0)) > 0) {

                BigDecimal tenantSettleProfitPercent = orderProfitAmount.divide(totalProfitAmount, 2, RoundingMode.HALF_UP);

                Ledger ledger = new Ledger();
                ledger.setType(LedgerTypeEnum.Tenant);
                ledger.setCustomerId(order.getCustomerId());
                ledger.setUserName(order.getUserName());
                ledger.setOrderId(order.getId());
                ledger.setOrderTranId(order.getTranId());
                ledger.setStationId(station.getId());
                ledger.setStationName(station.getName());
                ledger.setStationAddress(station.getAddress());
                ledger.setChargerDn(order.getChargerDn());
                ledger.setGunNo(order.getGunNo());
                ledger.setStartTime(order.getStartTime());
                ledger.setEndTime(order.getEndTime());
                ledger.setTotalAmount(order.getTotalAmount());
                ledger.setTotalQuantity(order.getTotalQuantity());
                ledger.setChargeMinute(order.getChargeMinute());

                ledger.setSettledAmount(order.getSettledAmount());
                ledger.setSettledElecAmount(orderSettledElecAmount);
                ledger.setSettledServiceAmount(orderSettledServiceAmount);
                ledger.setSettledParkAmount(orderSettledParkAmount);

                ledger.setAmount(orderProfitAmount);
                ledger.setPercent(tenantSettleProfitPercent);
                ledger.setState(LedgerStateEnum.Pending);

                ledger.setDealerId("-1");
                ledger.setAgentId("-1");

                ledger.setDealerName(null);
                ledger.setAgentName(null);


                ledger.setTenantId(sysTenant.getTenantId());
                ledger.setTenantName(sysTenant.getCompanyName());

                ledgerData.add(ledger);

                log.error("[分润调试]分润完成[运营商],分润金额={}", orderProfitAmount);
            }

            order.setDeal(OrderDealEnum.Dealed);
            ordersData.save(order);
            return;
        }

        //结算运营商分润
        BigDecimal tenantProfitPercent = sysAgent.getTenantProfitPercent();
        if(tenantProfitPercent.compareTo(new BigDecimal(1)) > 0 || tenantProfitPercent.compareTo(new BigDecimal(0)) < 0) {
            log.error("[分润调试]分润失败, 运营商分润比例设置错误, 分润比例={}", tenantProfitPercent);
            throw new BizException(ErrCode.TENANT_PROFIT_PERCENT_INVALID);
        }

        if(tenantProfitPercent.compareTo(new BigDecimal(0)) > 0) {
            if(orderProfitAmount.compareTo(new BigDecimal(0)) > 0) {
                BigDecimal tenantSettleProfitAmount = orderProfitAmount.multiply(tenantProfitPercent).setScale(2, RoundingMode.HALF_UP);
                BigDecimal tenantSettleProfitPercent = tenantSettleProfitAmount.divide(totalProfitAmount, 2, RoundingMode.HALF_UP);
                orderProfitAmount = orderProfitAmount.subtract(tenantSettleProfitAmount);

                Ledger ledger = new Ledger();
                ledger.setType(LedgerTypeEnum.Tenant);
                ledger.setCustomerId(order.getCustomerId());
                ledger.setUserName(order.getUserName());
                ledger.setOrderId(order.getId());
                ledger.setOrderTranId(order.getTranId());
                ledger.setStationId(station.getId());
                ledger.setStationName(station.getName());
                ledger.setStationAddress(station.getAddress());
                ledger.setChargerDn(order.getChargerDn());
                ledger.setGunNo(order.getGunNo());
                ledger.setStartTime(order.getStartTime());
                ledger.setEndTime(order.getEndTime());
                ledger.setTotalAmount(order.getTotalAmount());
                ledger.setTotalQuantity(order.getTotalQuantity());
                ledger.setChargeMinute(order.getChargeMinute());

                ledger.setSettledAmount(order.getSettledAmount());
                ledger.setSettledElecAmount(orderSettledElecAmount);
                ledger.setSettledServiceAmount(orderSettledServiceAmount);
                ledger.setSettledParkAmount(orderSettledParkAmount);

                ledger.setAmount(tenantSettleProfitAmount);
                ledger.setPercent(tenantSettleProfitPercent);
                ledger.setState(LedgerStateEnum.Pending);



                ledger.setDealerId("-1");
                ledger.setAgentId("-1");

                ledger.setDealerName(null);
                ledger.setAgentName(null);

                ledger.setTenantId(sysTenant.getTenantId());
                ledger.setTenantName(sysTenant.getCompanyName());

                ledgerData.add(ledger);
            }
        }


        //结算合作商商分润
        if(orderProfitAmount.compareTo(new BigDecimal(0)) > 0) {
            List<SysAgentStationDealer>  sysAgentStationDealers = sysAgentStationDealerData.findByAgentId(sysAgent.getId());
            BigDecimal totalPercent = new BigDecimal(0);
            if(!sysAgentStationDealers.isEmpty()) {
                for(SysAgentStationDealer sysAgentStationDealer: sysAgentStationDealers) {
                    totalPercent = totalPercent.add(sysAgentStationDealer.getPercent());
                }

                if(totalPercent.compareTo(new BigDecimal(1)) > 0 || totalPercent.compareTo(new BigDecimal(0)) < 0) {
                    log.error("[分润调试]分润失败,合作商分润比例设置错误, 分润比例总和={}", totalPercent);
                    throw new BizException(ErrCode.SYS_AGENT_TENANT_PROFIT_PERCENT_INVALID);
                }

                for(SysAgentStationDealer sysAgentStationDealer: sysAgentStationDealers) {
                    BigDecimal dealerProfitPercent = sysAgentStationDealer.getPercent();
                    if(dealerProfitPercent.compareTo(new BigDecimal(0)) > 0) {
                        SysDealer sysDealer = sysDealerData.findById(sysAgentStationDealer.getDealerId());
                        if(sysDealer == null) {
                            throw new BizException(ErrCode.SYS_DEALER_NOT_FOUND);
                        }

                        BigDecimal dealerSettleProfitAmount = orderProfitAmount.multiply(dealerProfitPercent).setScale(2, RoundingMode.HALF_UP);
                        BigDecimal dealerSettleProfitPercent = dealerSettleProfitAmount.divide(totalProfitAmount, 2, RoundingMode.HALF_UP);
                        orderProfitAmount = orderProfitAmount.subtract(dealerSettleProfitAmount);


                        Ledger ledger = new Ledger();
                        ledger.setType(LedgerTypeEnum.Dealer);
                        ledger.setCustomerId(order.getCustomerId());
                        ledger.setUserName(order.getUserName());
                        ledger.setOrderId(order.getId());
                        ledger.setOrderTranId(order.getTranId());
                        ledger.setStationId(station.getId());
                        ledger.setStationName(station.getName());
                        ledger.setStationAddress(station.getAddress());
                        ledger.setChargerDn(order.getChargerDn());
                        ledger.setGunNo(order.getGunNo());
                        ledger.setStartTime(order.getStartTime());
                        ledger.setEndTime(order.getEndTime());
                        ledger.setTotalAmount(order.getTotalAmount());
                        ledger.setTotalQuantity(order.getTotalQuantity());
                        ledger.setChargeMinute(order.getChargeMinute());

                        ledger.setSettledAmount(order.getSettledAmount());
                        ledger.setSettledElecAmount(orderSettledElecAmount);
                        ledger.setSettledServiceAmount(orderSettledServiceAmount);
                        ledger.setSettledParkAmount(orderSettledParkAmount);

                        ledger.setAmount(dealerSettleProfitAmount);
                        ledger.setPercent(dealerSettleProfitPercent);
                        ledger.setState(LedgerStateEnum.Pending);

                        ledger.setDealerId(sysDealer.getId());
                        ledger.setDealerName(sysDealer.getName());

                        ledger.setAgentId(sysAgent.getId());
                        ledger.setAgentName(sysAgent.getName());

                        ledger.setTenantId(sysTenant.getTenantId());
                        ledger.setTenantName(sysTenant.getCompanyName());
                        ledgerData.add(ledger);
                    }
                }
            }
        }

        //结算代理商分润
        if(orderProfitAmount.compareTo(new BigDecimal(0)) > 0) {

            BigDecimal agentSettleProfitPercent = orderProfitAmount.divide(totalProfitAmount, 2, RoundingMode.HALF_UP);

            Ledger ledger = new Ledger();
            ledger.setType(LedgerTypeEnum.Agent);
            ledger.setCustomerId(order.getCustomerId());
            ledger.setUserName(order.getUserName());
            ledger.setOrderId(order.getId());
            ledger.setOrderTranId(order.getTranId());
            ledger.setStationId(station.getId());
            ledger.setStationName(station.getName());
            ledger.setStationAddress(station.getAddress());
            ledger.setChargerDn(order.getChargerDn());
            ledger.setGunNo(order.getGunNo());
            ledger.setStartTime(order.getStartTime());
            ledger.setEndTime(order.getEndTime());
            ledger.setTotalAmount(order.getTotalAmount());
            ledger.setTotalQuantity(order.getTotalQuantity());
            ledger.setChargeMinute(order.getChargeMinute());

            ledger.setSettledAmount(order.getSettledAmount());
            ledger.setSettledElecAmount(orderSettledElecAmount);
            ledger.setSettledServiceAmount(orderSettledServiceAmount);
            ledger.setSettledParkAmount(orderSettledParkAmount);

            ledger.setAmount(orderProfitAmount);
            ledger.setPercent(agentSettleProfitPercent);
            ledger.setState(LedgerStateEnum.Pending);

            ledger.setDealerId("-1");

            ledger.setDealerName(null);

            ledger.setAgentId(sysAgent.getId());
            ledger.setAgentName(sysAgent.getName());
            ledger.setTenantId(sysTenant.getTenantId());
            ledger.setTenantName(sysTenant.getCompanyName());
            ledgerData.add(ledger);
        }

        order.setDeal(OrderDealEnum.Dealed);
        ordersData.save(order);
        log.info("[分润调试]结束订单分润, orderId={}", order.getId());

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
