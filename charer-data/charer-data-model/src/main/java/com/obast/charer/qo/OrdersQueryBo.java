package com.obast.charer.qo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.obast.charer.enums.OrderStateEnum;
import lombok.Data;

import java.util.Date;

@Data
public class OrdersQueryBo {
    private String keyword;
    private String userName;
    private String customerId;
    private OrderStateEnum state;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Date[] createTime;
}