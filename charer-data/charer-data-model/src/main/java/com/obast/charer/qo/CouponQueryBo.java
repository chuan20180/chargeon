package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.CouponApplyEnum;
import com.obast.charer.enums.CouponScopeEnum;
import com.obast.charer.enums.EnableStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CouponQueryBo extends BaseDto {

    private String id;

    private String name;

    private CouponScopeEnum scope;

    private CouponApplyEnum apply;

    private EnableStatusEnum status;

    private String note;
}
