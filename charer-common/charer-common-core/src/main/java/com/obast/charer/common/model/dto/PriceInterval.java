package com.obast.charer.common.model.dto;

import com.obast.charer.common.Decimal4Serializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;


@Data
public class PriceInterval implements Serializable {
    private static final long serialVersionUID = -1L;

    private String name;

    private String label;

    @JsonSerialize(using = Decimal4Serializer.class)
    private BigDecimal amount;

    @JsonSerialize(using = Decimal4Serializer.class)
    private BigDecimal elecFee;

    @JsonSerialize(using = Decimal4Serializer.class)
    private BigDecimal serviceFee;

    private Integer index;

    private Boolean isCurrent;
}
