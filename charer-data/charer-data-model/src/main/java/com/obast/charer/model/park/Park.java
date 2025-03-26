package com.obast.charer.model.park;

import com.obast.charer.enums.ParkSettleEnum;
import com.obast.charer.enums.ParkStateEnum;
import com.obast.charer.common.i18n.I18nField;
import com.obast.charer.model.AgentModel;
import com.obast.charer.model.Id;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Park extends AgentModel implements Id<String>, Serializable {

    private String id;

    private String tranId;

    private String stationId;

    private I18nField stationName;

    private I18nField stationAddress;

    private String dcamId;

    private String dcamDn;

    private String parkingId;

    private String parkingNo;

    private I18nField parkingName;

    private String carPlate;

    private Integer carPlateType;

    private Date inTime;
    private List<String> inBgImage;
    private List<String> inFetureImage;

    private Date outTime;
    private List<String> outBgImage;
    private List<String> outFetureImage;


    private ParkStateEnum state;

    private ParkSettleEnum settle;

    private String note;
}
