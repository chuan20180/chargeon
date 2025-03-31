package com.obast.charer.model.coupon;

import com.obast.charer.enums.CouponAcceptEnum;
import com.obast.charer.enums.CouponApplyEnum;
import com.obast.charer.enums.CouponScopeEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.Id;
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
public class Coupon extends BaseModel implements Id<String>, Serializable {

    private String id;

    private String name;

    private CouponScopeEnum scope;

    private CouponApplyEnum apply;

    private List<String> stationIds;

    private CouponAcceptEnum accept;

    private String acceptIds;

    private BigDecimal amount;

    private Date enableTime;

    private Date expireTime;

    private String note;
}