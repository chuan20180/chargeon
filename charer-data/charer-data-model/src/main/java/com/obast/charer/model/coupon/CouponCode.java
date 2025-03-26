package com.obast.charer.model.coupon;

import com.obast.charer.enums.CouponApplyEnum;
import com.obast.charer.enums.CouponCodeStateEnum;
import com.obast.charer.enums.CouponScopeEnum;
import com.obast.charer.model.Id;
import com.obast.charer.model.TenantModel;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponCode extends TenantModel implements Id<String>, Serializable {

    private String id;

    private String couponId;

    private String tranId;

    private BigDecimal amount;

    private BigDecimal remainedAmount;

    private CouponScopeEnum scope;

    private List<String> stationIds;

    private CouponApplyEnum apply;

    private Date enableTime;

    private Date expireTime;

    private CouponCodeStateEnum state;

    private String customerId;

    private String userName;
}