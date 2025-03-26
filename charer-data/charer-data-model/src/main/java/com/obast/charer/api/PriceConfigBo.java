package com.obast.charer.api;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.model.price.Price;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PriceConfigBo extends BaseDto {

    private String chargerDn;

    private Price price;

}
