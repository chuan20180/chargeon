package com.obast.charer.qo;

import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.OnlineStatusEnum;
import lombok.Data;

import java.util.Date;


@Data
public class DcamQueryBo {

    private String id;

    private String productKey;

    private String stationId;

    private String priceParkId;

    private String name;

    private String dn;

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
