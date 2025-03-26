package com.obast.charer.common.plugin.core.thing.charger;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 设备物行为类型
 *
 * @author sjg
 */
@Getter
@AllArgsConstructor
public enum ChargerDirectiveEnum {

    //登陆
    LOGIN_EVENT,
    LOGIN_ACK,

    //心跳ping
    PING_EVENT,
    PING_ACK,

    //状态改变
    STATE_CHANGE_EVENT,

    //计费模型验证
    PRICE_VERIFY_EVENT,
    PRICE_VERIFY_ACK,

    //计费模型请求
    PRICE_REQUEST_EVENT,
    PRICE_REQUEST_ACK,

    //上传实时监测数据
    DATA_REPORT_EVENT,

    //上传订单数据
    ORDER_REPORT_EVENT,
    ORDER_REPORT_ACK,

    //开始充电
    START_CHARGE_INVOKE,

    //开始充电回复
    START_CHARGE_REPLY,

    //停止充电
    STOP_CHARGE_INVOKE,

    //停止充电回复
    STOP_CHARGE_REPLY,

    //余额更新
    BALANCE_UPDATE_INVOKE,
    BALANCE_UPDATE_REPLY,

    //参数设置
    PARAM_CONFIG_INVOKE,
    PARAM_CONFIG_REPLY,

    //对时
    TIME_CONFIG_INVOKE,
    TIME_CONFIG_REPLY,

    //计价模型设置
    PRICE_CONFIG_INVOKE,
    PRICE_CONFIG_REPLY,

    //重起
    REBOOT_INVOKE,
    REBOOT_REPLY,

    //二维码下发
    QRCODE_INVOKE,
    QRCODE_REPLY,
}
