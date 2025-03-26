package com.obast.charer.common.model.dto;

import com.obast.charer.common.Decimal4Serializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PriceFee implements Serializable {

    private static final long serialVersionUID = -1L;

    private String name;

    @JsonSerialize(using = Decimal4Serializer.class)
    private BigDecimal amount;

    @JsonSerialize(using = Decimal4Serializer.class)
    private BigDecimal elecFee;

    @JsonSerialize(using = Decimal4Serializer.class)
    private BigDecimal serviceFee;
}
