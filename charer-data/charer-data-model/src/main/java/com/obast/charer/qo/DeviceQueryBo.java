package com.obast.charer.qo;

import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.OnlineStatusEnum;
import lombok.Data;

import java.util.Date;


@Data
public class DeviceQueryBo {

    private String id;

    private String parentId;

    private String productId;

    private String stationId;

    private String priceId;

    private String name;

    private String model;

    private String address;

    private String secret;

    private String longitude;

    private String latitude;

    private OnlineStatusEnum online;

    private Long onlineTime;

    private Long offlineTime;

    private EnableStatusEnum status;

    private String note;

    private Date[] createTime;

}
