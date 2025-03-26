package com.obast.charer.qo;

import com.obast.charer.enums.LockEnum;
import com.obast.charer.enums.RechargeTypeEnum;
import com.obast.charer.enums.TopupSourceEnum;
import com.obast.charer.enums.TopupStateEnum;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TopupQueryBo {

    private String id;

    private String userName;

    private String tranId;

    private RechargeTypeEnum rechargeType;

    private TopupSourceEnum source;

    private String paymentName;

    private TopupStateEnum state;

    private List<TopupStateEnum> states;

    private LockEnum refundLocked;

    private Date[] createTime;

}
