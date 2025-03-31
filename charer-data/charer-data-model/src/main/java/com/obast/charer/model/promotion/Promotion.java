package com.obast.charer.model.promotion;

import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.PromotionScopeEnum;
import com.obast.charer.enums.PromotionTypeEnum;
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
public class Promotion extends BaseModel implements Id<String>, Serializable {

    private String id;

    private String name;

    private PromotionScopeEnum scope;

    private List<String> stationIds;

    private PromotionTypeEnum type;

    private Date startTime;

    private Date endTime;

    private String properties;

    private EnableStatusEnum status;

    private String note;

    @Data
    public static class Discount {
        private BigDecimal discount;
    }
}