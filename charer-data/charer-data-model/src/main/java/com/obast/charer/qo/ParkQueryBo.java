package com.obast.charer.qo;

import com.obast.charer.enums.ParkStateEnum;
import lombok.Data;

import java.util.Date;

@Data
public class ParkQueryBo {

    private String carPlate;

    private String stationId;

    private String parkingId;

    private ParkStateEnum state;

    private String keyword;

    private Date[] createTime;
}
