package com.obast.charer.model.device;

import com.obast.charer.enums.ChargerDirectiveResultEnum;
import com.obast.charer.enums.ChargerDirectiveStateEnum;
import com.obast.charer.enums.ChargerDirectiveTypeEnum;
import com.obast.charer.model.BaseModel;
import com.obast.charer.model.Id;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChargerDirective extends BaseModel implements Id<String>, Serializable {

    private String id;

    private String dn;

    private ChargerDirectiveTypeEnum type;

    private String relateId;

    private Integer serial;

    private String directive;

    private String data;

    private ChargerDirectiveStateEnum state;

    private ChargerDirectiveResultEnum result;

    private String reason;
}
