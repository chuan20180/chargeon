package com.obast.charer.common.model.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class PriceProperties implements Serializable {

    private static final long serialVersionUID = -1L;

    private List<PriceFee> fee;

    private List<PricePeriod> period;

}
