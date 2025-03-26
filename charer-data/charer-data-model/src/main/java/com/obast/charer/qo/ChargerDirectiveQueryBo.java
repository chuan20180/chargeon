package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.ChargerDirectiveResultEnum;
import com.obast.charer.enums.ChargerDirectiveStateEnum;
import com.obast.charer.enums.ChargerDirectiveTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChargerDirectiveQueryBo extends BaseDto {

    private String id;

    private String dn;

    private String gunNo;

    private ChargerDirectiveTypeEnum type;

    private String relateId;

    private Integer serial;

    private String directive;

    private String data;

    private ChargerDirectiveStateEnum state;

    private ChargerDirectiveResultEnum result;

    private String reason;
}
