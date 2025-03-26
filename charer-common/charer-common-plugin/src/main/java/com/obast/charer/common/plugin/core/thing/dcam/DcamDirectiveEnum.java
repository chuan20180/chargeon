package com.obast.charer.common.plugin.core.thing.dcam;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 设备物行为类型
 *
 * @author sjg
 */
@Getter
@AllArgsConstructor
public enum DcamDirectiveEnum {

    //登陆
    REGISTER_EVENT,

    //心跳ping
    PING_EVENT,

    //状态改变
    STATE_EVENT,

    //入场事件
    CAR_IN_EVENT,

    //出场事件
    CAR_OUT_EVENT,

    //串口数据
    SERIAL_DATA_EVENT
}
