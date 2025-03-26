package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.PromotionScopeEnum;
import com.obast.charer.enums.PromotionTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PromotionQueryBo extends BaseDto {

    private String id;

    private String name;

    private PromotionScopeEnum scope;

    private PromotionTypeEnum type;

    private EnableStatusEnum status;

}
