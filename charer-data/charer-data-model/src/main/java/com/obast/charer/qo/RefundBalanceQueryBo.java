package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：退款记录查询视图
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RefundBalanceQueryBo extends BaseDto {

    private String refundId;

}
