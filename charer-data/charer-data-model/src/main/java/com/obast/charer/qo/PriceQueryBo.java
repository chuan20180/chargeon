package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.common.enums.PriceTypeEnum;
import com.obast.charer.enums.EnableStatusEnum;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Convert;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：计价规则查询视图
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PriceQueryBo extends BaseDto {

    private String name;

    /**
     * 计价方式
     */
    @Convert(converter = PriceTypeEnum.Converter.class)
    private PriceTypeEnum type;

    /**
     * 状态
     */
    @Convert(converter = EnableStatusEnum.Converter.class)
    private EnableStatusEnum status;
}
