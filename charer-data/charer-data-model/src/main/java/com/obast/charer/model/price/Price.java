package com.obast.charer.model.price;

import com.obast.charer.common.enums.PriceTypeEnum;
import com.obast.charer.common.model.dto.PriceFee;
import com.obast.charer.common.model.dto.PriceProperties;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.Id;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Price extends BaseModel implements Id<String>, Serializable {

    private String id;

    private String name;

    private Short no;

    private PriceTypeEnum type;

    private EnableStatusEnum status;

    private String note;

    private PriceProperties properties;

    private PriceFee currentPrice;

    private PriceFee minPrice;

    private PriceFee maxPrice;
}
