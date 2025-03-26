package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.RefundStateEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：退款记录查询视图
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RefundQueryBo extends BaseDto {


    private String keyword;


    private RefundStateEnum state;
}
