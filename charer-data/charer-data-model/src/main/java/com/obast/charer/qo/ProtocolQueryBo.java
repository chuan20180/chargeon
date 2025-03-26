package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;
import com.obast.charer.enums.EnableStatusEnum;
import com.obast.charer.enums.ProductTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Convert;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：协议查询视图
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProtocolQueryBo extends BaseDto {

    private String name;

    private String protocolKey;

    private String manufacturer;

    @Convert(converter = ProductTypeEnum.Converter.class)
    private ProductTypeEnum type;

    /**
     * 状态
     */
    @Convert(converter = EnableStatusEnum.Converter.class)
    private EnableStatusEnum status;
}
