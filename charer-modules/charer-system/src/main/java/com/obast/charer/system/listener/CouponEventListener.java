package com.obast.charer.system.listener;

import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.data.business.ICouponCodeData;
import com.obast.charer.data.business.ICustomerData;
import com.obast.charer.enums.CouponAcceptEnum;
import com.obast.charer.enums.CouponCodeStateEnum;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.coupon.Coupon;
import com.obast.charer.model.coupon.CouponCode;
import com.obast.charer.model.customer.Customer;
import com.obast.charer.model.order.Orders;
import com.obast.charer.qo.CustomerQueryBo;
import com.obast.charer.system.listener.event.CouponAddEvent;
import com.obast.charer.system.listener.event.OrderSettledEvent;
import com.obast.charer.system.operate.IOrdersOperateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CouponEventListener {

    @Autowired
    private ICouponCodeData couponCodeData;


    @Autowired
    private ICustomerData customerData;


    //优惠券添加事件
    @Async
    @EventListener
    public void doOrderSettledEvent(CouponAddEvent event) {
        Coupon coupon = event.getCoupon();
        log.info("[事件调试]收到优惠券添加事件, couponId={}", coupon.getId());

        if(coupon.getAccept().equals(CouponAcceptEnum.All)) {
            CustomerQueryBo customerQueryBo = new CustomerQueryBo();
            customerQueryBo.setStatus(EnableStatusEnum.Enabled);
            List<Customer> customers = customerData.findList(customerQueryBo);
            for(Customer customer: customers) {
                CouponCode couponCode = new CouponCode();
                couponCode.setScope(coupon.getScope());
                couponCode.setApply(coupon.getApply());
                couponCode.setStationIds(coupon.getStationIds());
                couponCode.setCouponId(coupon.getId());
                couponCode.setAmount(coupon.getAmount());
                couponCode.setRemainedAmount(coupon.getAmount());
                couponCode.setCustomerId(customer.getId());
                couponCode.setUserName(customer.getUserName());
                couponCode.setExpireTime(coupon.getExpireTime());
                couponCode.setEnableTime(coupon.getEnableTime());
                couponCode.setState(CouponCodeStateEnum.UnUsed);
                couponCodeData.add(couponCode);
            }
        } else if(coupon.getAccept().equals(CouponAcceptEnum.Part)) {
            if(coupon.getAcceptIds().isEmpty()) {
                throw new BizException(ErrCode.COUPON_CUSTOMER_EMPTY);
            }

            String[] acceptIds = coupon.getAcceptIds().split(",");
            if(acceptIds.length == 0) {
                throw new BizException(ErrCode.COUPON_CUSTOMER_EMPTY);
            }

            for(String customerId: acceptIds) {
                Customer customer = customerData.findById(customerId);
                if(customer == null) {
                    throw new BizException(ErrCode.CUSTOMER_NOT_FOUND);
                }
                CouponCode couponCode = new CouponCode();
                couponCode.setScope(coupon.getScope());
                couponCode.setApply(coupon.getApply());
                couponCode.setStationIds(coupon.getStationIds());
                couponCode.setCouponId(coupon.getId());
                couponCode.setAmount(coupon.getAmount());
                couponCode.setRemainedAmount(coupon.getAmount());
                couponCode.setCustomerId(customerId);
                couponCode.setUserName(customer.getUserName());
                couponCode.setExpireTime(coupon.getExpireTime());
                couponCode.setEnableTime(coupon.getEnableTime());
                couponCode.setState(CouponCodeStateEnum.UnUsed);
                couponCodeData.add(couponCode);
            }
        }
    }
}
