package com.obast.charer.common.model.dto;


import lombok.Data;

import java.io.Serializable;


@Data

public class PricePeriod implements Serializable {
    private static final long serialVersionUID = -1L;
    private String label;
    private Integer index;
}
