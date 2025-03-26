package com.obast.charer.system.operate.impl;

import com.obast.charer.common.constant.TenantConstants;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.plugin.core.StopReasonEnum;
import com.obast.charer.common.properties.CharerProperties;
import com.obast.charer.common.utils.JsonUtils;
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
import com.obast.charer.model.device.Charger;
import com.obast.charer.model.device.ChargerGun;
import com.obast.charer.model.device.Dcam;
import com.obast.charer.model.device.DcamParking;
import com.obast.charer.model.ledger.Ledger;
import com.obast.charer.model.order.Orders;
import com.obast.charer.model.order.OrdersSettle;
import com.obast.charer.model.park.Park;
import com.obast.charer.model.parking.Parking;
import com.obast.charer.model.price.PricePark;
import com.obast.charer.model.promotion.Promotion;
import com.obast.charer.model.station.Station;
import com.obast.charer.model.system.SysAgent;
import com.obast.charer.model.system.SysAgentStationDealer;
import com.obast.charer.model.system.SysDealer;
import com.obast.charer.model.system.SysTenant;
import com.obast.charer.system.operate.IOrdersOperateService;
import com.obast.charer.system.operate.IParkOperateService;
import com.obast.charer.system.utils.ParkUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
 * @ Description：占位费服务实现
 */
@Slf4j
@Service
public class ParkOperateServiceImpl implements IParkOperateService {

    @Autowired
    private IOrdersData ordersData;

    @Autowired
    private IOrdersSettleData ordersSettleData;

    @Autowired
    private IPromotionData promotionData;

    @Autowired
    private IChargerData chargerData;

    @Autowired
    private IChargerGunData chargerGunData;

    @Autowired
    private IParkData parkData;

    @Autowired
    private IParkingData parkingData;

    @Autowired
    private IDcamData dcamData;

    @Autowired
    private IPriceParkData priceParkData;

    @Autowired
    private ICustomerData customerData;

    @Autowired
    private IStationData stationData;

    @Autowired
    private ICouponCodeData couponCodeData;

    /**
     * 占位费结算
     */
    @Override
    public void settle(String parkId) {

        Park park = parkData.findById(parkId);
        if(park == null) {
            throw new BizException(ErrCode.PARK_NOT_FOUND);
        }

        log.info("[占位结算调试]占位结算开始: parkId={}", park.getId());

        try {
            Dcam dcam = dcamData.findById(park.getDcamId());
            if(dcam == null) {
                throw new BizException(ErrCode.DCAM_NOT_FOUND);
            }

            //未开启占位费直接完成
            if(dcam.getParkState().equals(OnOffEnum.Off)) {
                park.setSettle(ParkSettleEnum.Settled);
                parkData.save(park);
                return;
            }

            if(!park.getState().equals(ParkStateEnum.Appeared)) {
                throw new BizException(ErrCode.PARK_STATE_ERROR);
            }

            Parking parking = parkingData.findById(park.getParkingId());
            if(parking == null) {
                throw new BizException(ErrCode.PARKING_NOT_FOUND);
            }

            Station station = stationData.findById(parking.getStationId());
            if(station == null) {
                throw new BizException(ErrCode.STATION_NOT_FOUND);
            }

            PricePark pricePark = priceParkData.findById(dcam.getPriceParkId());
            if(pricePark == null) {
                throw new BizException(ErrCode.PRICE_PARK_NOT_FOUND);
            }

            Date inTime = park.getInTime();
            if(inTime == null) {
                throw new BizException(ErrCode.PARK_IN_TIME_EMPTY);
            }

            Date outTime = park.getOutTime();
            if(outTime == null) {
                throw new BizException(ErrCode.PARK_OUT_TIME_EMPTY);
            }

            ChargerGun chargerGun = chargerGunData.findByParkingId(park.getParkingId());
            if(chargerGun == null) {
                throw new BizException(ErrCode.CHARGER_GUN_NOT_FOUND);
            }

            Charger charger = chargerData.findById(chargerGun.getChargerId());
            if(charger == null) {
                throw new BizException(ErrCode.CHARGER_NOT_FOUND);
            }

            //未找到订单直接完成
            Orders order = ordersData.findByPark(charger.getId(), chargerGun.getNo(), park.getInTime(), park.getOutTime());
            if(order == null) {
                park.setSettle(ParkSettleEnum.Settled);
                parkData.save(park);
                return;
            }

            Date orderStartTime = order.getStartTime();
            if(orderStartTime == null) {
                throw new BizException(ErrCode.ORDER_START_TIME_EMPTY);
            }

            Date orderEndTime = order.getEndTime();
            if(orderEndTime == null) {
                throw new BizException(ErrCode.ORDER_END_TIME_EMPTY);
            }

            LocalDateTime entryTime = inTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime exitTime = orderStartTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            BigDecimal orderParkAmount = ParkUtil.calculateParkingFee(entryTime, exitTime, pricePark.getBeforeFreeMinute(), pricePark.getPrice(), pricePark.getMaxAmount());

            Customer customer = customerData.findByUserName(order.getUserName());
            if(customer == null) {
                throw new BizException(ErrCode.CUSTOMER_NOT_FOUND);
            }

            //客户账户余额
            BigDecimal customerBalanceAmount = customer.getBalanceAmount();
            BigDecimal customerGiveAmount = customer.getGiveAmount();
            List<Customer.Quota> customerQuotaAmount = customer.getQuotaAmount();

            /* **************************************************************************************** */
            /* 扣占位费开始 */
            /* **************************************************************************************** */

            OrdersSettle ordersParkSettle = new OrdersSettle();
            ordersParkSettle.setCustomerId(order.getCustomerId());
            ordersParkSettle.setOrderId(order.getId());
            ordersParkSettle.setType(OrderSettleTypeEnum.Park);
            ordersParkSettle.setAmount(orderParkAmount);
            ordersParkSettle.setTenantId(order.getTenantId());

            BigDecimal settledParkAmount = new BigDecimal(0);
            BigDecimal discountParkAmount = new BigDecimal(0);
            DiscountTypeEnum discountParkType = null;
            String discountParkRelateId = null;

            if (orderParkAmount.compareTo(new BigDecimal(0)) > 0) {
                /* 处理占位费优惠基础数据 */
                BigDecimal parkPromotionDiscountAmount = new BigDecimal(0);
                BigDecimal parkCouponDiscountAmount = new BigDecimal(0);
                BigDecimal parkGiveDiscountAmount = new BigDecimal(0);

                //场站优惠
                Promotion parkPromotion = promotionData.findMaxParkDiscountByStationId(station.getId());
                if (parkPromotion != null) {
                    Promotion.Discount promotionDiscount = null;
                    try {
                        promotionDiscount = JsonUtils.parse(parkPromotion.getProperties(), Promotion.Discount.class);
                    } catch (Exception e) {
                        log.error(ErrCode.PROMOTION_PROPERTIES_PARSE_EXCEPTION.getValue());
                    }
                    if ((promotionDiscount != null) && promotionDiscount.getDiscount() != null && promotionDiscount.getDiscount().compareTo(new BigDecimal(0)) > 0 && promotionDiscount.getDiscount().compareTo(new BigDecimal(1)) < 0) {
                        parkPromotionDiscountAmount = orderParkAmount.multiply(new BigDecimal(1).subtract(promotionDiscount.getDiscount())).setScale(2, RoundingMode.HALF_UP);
                    } else {
                        log.error(ErrCode.PROMOTION_PROPERTIES_DISCOUNT_VALUE_ERROR.getValue());
                    }
                }

                //用户优惠券
                CouponCode serviceCouponCode = couponCodeData.findMaxServiceDiscountByCustomerIdAndStationId(customer.getId(), station.getId());
                if(serviceCouponCode != null) {
                    if (serviceCouponCode.getApply().equals(CouponApplyEnum.ServiceAndPark)) {
                        if ((serviceCouponCode.getRemainedAmount() != null && serviceCouponCode.getRemainedAmount().compareTo(new BigDecimal(0)) > 0)) {
                            if (orderParkAmount.compareTo(serviceCouponCode.getRemainedAmount()) <= 0) {
                                parkCouponDiscountAmount = orderParkAmount;
                            } else {
                                parkCouponDiscountAmount = serviceCouponCode.getRemainedAmount();
                            }
                        }
                    }
                }

                //赠送金额
                if (customerGiveAmount.compareTo(new BigDecimal(0)) > 0) {
                    if (orderParkAmount.compareTo(customerGiveAmount) <= 0) {
                        parkGiveDiscountAmount = orderParkAmount;
                    } else {
                        parkGiveDiscountAmount = customerGiveAmount;
                    }
                }

                Map<DiscountTypeEnum, BigDecimal> parkMap = new HashMap<>();
                parkMap.put(DiscountTypeEnum.Promotion, parkPromotionDiscountAmount);
                parkMap.put(DiscountTypeEnum.Coupon, parkCouponDiscountAmount);
                parkMap.put(DiscountTypeEnum.Give, parkGiveDiscountAmount);

                DiscountTypeEnum parkMaxDiscountKey = null;
                BigDecimal parkMaxDiscountAmount = new BigDecimal(0);

                for (Map.Entry<DiscountTypeEnum, BigDecimal> entry : parkMap.entrySet()) {
                    if (entry.getValue().compareTo(parkMaxDiscountAmount) > 0) {
                        parkMaxDiscountAmount = entry.getValue();
                        parkMaxDiscountKey = entry.getKey();
                    }
                }

                if (parkMaxDiscountKey != null && parkMaxDiscountAmount.compareTo(new BigDecimal(0)) > 0) {
                    if (parkMaxDiscountKey.equals(DiscountTypeEnum.Promotion)) {
                        assert parkPromotion != null;
                        discountParkAmount = parkMaxDiscountAmount;
                        discountParkType = DiscountTypeEnum.Promotion;
                        discountParkRelateId = parkPromotion.getId();

                    } else if (parkMaxDiscountKey.equals(DiscountTypeEnum.Coupon)) {
                        assert serviceCouponCode != null;
                        discountParkAmount = parkMaxDiscountAmount;
                        discountParkType = DiscountTypeEnum.Coupon;
                        discountParkRelateId = serviceCouponCode.getId();

                    } else if (parkMaxDiscountKey.equals(DiscountTypeEnum.Give)) {
                        customerGiveAmount = customerGiveAmount.subtract(parkMaxDiscountAmount);
                        discountParkAmount = parkMaxDiscountAmount;
                        discountParkType = DiscountTypeEnum.Give;
                    }
                }

                BigDecimal parkRemainedAmount = orderParkAmount.subtract(discountParkAmount);

                //扣除占位剩余金额
                if (parkRemainedAmount.compareTo(new BigDecimal(0)) > 0) {
                    if (customerBalanceAmount.compareTo(new BigDecimal(0)) > 0) {
                        if (parkRemainedAmount.compareTo(customerBalanceAmount) <= 0) {
                            settledParkAmount = parkRemainedAmount;
                        } else {
                            settledParkAmount = customerBalanceAmount;
                        }
                    }
                }
            }

            ordersParkSettle.setSettledAmount(settledParkAmount);
            ordersParkSettle.setDiscountAmount(discountParkAmount);
            ordersParkSettle.setDiscountType(discountParkType);
            ordersParkSettle.setDiscountRelateId(discountParkRelateId);
            ordersSettleData.add(ordersParkSettle);

            customerBalanceAmount = customerBalanceAmount.subtract(settledParkAmount);


            //客户扣款
            customer.setBalanceAmount(customerBalanceAmount);
            customer.setGiveAmount(customerGiveAmount);
            customer.setQuotaAmount(customerQuotaAmount);
            customerData.save(customer);

            //结算订单
            order.setParkId(park.getId());
            ordersData.update(order);

            park.setSettle(ParkSettleEnum.Settled);
            parkData.save(park);

            log.info("[占位结算调试]占位结算完成: parkId={}", park.getId());

        } catch (Exception e) {
            log.error("[占位结算调试]占位结算异常: parkId={}, msg={}", park.getId(), e.getMessage());
        }
    }
}
