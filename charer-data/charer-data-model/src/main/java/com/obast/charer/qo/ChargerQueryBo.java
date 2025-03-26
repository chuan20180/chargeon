package com.obast.charer.qo;

import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.OnlineStatusEnum;
import lombok.Data;

import java.util.Date;


@Data
public class ChargerQueryBo {

    private String id;

    private String productKey;

    private String stationId;

    private String priceId;

    private String name;

    private String address;

    private OnlineStatusEnum online;

    private EnableStatusEnum status;

    private Date[] createTime;

}
