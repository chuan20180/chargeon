package com.obast.charer.common.plugin.core.thing;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 设备状态
 * @author sjg
 */
@Getter
@AllArgsConstructor
public enum DeviceStatus {

    //在线
    Enabled("enable"),
    //离线
    Disabled("disable");

    private final String status;

}
