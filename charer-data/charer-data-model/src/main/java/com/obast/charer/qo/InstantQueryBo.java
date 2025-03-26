package com.obast.charer.qo;

import com.obast.charer.enums.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class InstantQueryBo {

    private String id;

    private String userName;

    private String tranId;

    private RechargeTypeEnum rechargeType;

    private String paymentName;

    private InstantStateEnum state;

    private LockEnum refundLocked;

}
