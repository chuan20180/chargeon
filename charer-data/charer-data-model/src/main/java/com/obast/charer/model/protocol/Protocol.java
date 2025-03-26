package com.obast.charer.model.protocol;

import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.ProductTypeEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.Id;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Protocol extends BaseModel implements Id<String>, Serializable {

    private String id;

    private String protocolKey;

    private ProductTypeEnum type;

    private String name;

    private String version;

    private String manufacturer;

    private EnableStatusEnum status;

    private Charger charger;

    private Dcam dcam;

    @Data
    public static class Charger {
        private Integer dnLength;
    }

    @Data
    public static class Dcam {
    }
}