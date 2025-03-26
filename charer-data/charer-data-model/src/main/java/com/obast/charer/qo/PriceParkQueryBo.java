package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;
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
public class PriceParkQueryBo extends BaseDto {

    private String name;

    /**
     * 状态
     */
    @Convert(converter = EnableStatusEnum.Converter.class)
    private EnableStatusEnum status;
}
