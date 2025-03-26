package com.obast.charer.model.product;

import com.obast.charer.common.Decimal2Serializer;
import com.obast.charer.enums.ChargerGunCurrentEnum;
import com.obast.charer.enums.ChargerGunSpeedEnum;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.ProductTypeEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.Id;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import java.io.Serializable;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseModel implements Id<String>, Serializable {

    private String id;

    private String productKey;

    private String productSecret;

    private String protocolKey;

    private String name;

    private String manufacturer;

    private String category;

    private ProductTypeEnum type;

    private String img;

    private Long keepAliveTime;

    private EnableStatusEnum status;

    private Charger charger;

    private Dcam dcam;

    @Data
    public static class Charger {
        private Integer qty;
        private ChargerGunCurrentEnum current;
        private ChargerGunSpeedEnum speed;
        private Integer power;

        @JsonSerialize(using = Decimal2Serializer.class)
        private BigDecimal lowBalance;
    }

    @Data
    public static class Dcam {

    }
}