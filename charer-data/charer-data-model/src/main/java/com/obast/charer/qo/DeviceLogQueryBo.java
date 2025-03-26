package com.obast.charer.qo;

import com.obast.charer.common.api.BaseDto;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class DeviceLogQueryBo extends BaseDto {

    private String invoke;

    private String deviceDn;

    private String type;

    private String identifier;
}
