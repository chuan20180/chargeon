package com.obast.charer.plugins.ykc.tcp.parser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据包
 *
 * @author sjg
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder()
public class DataPackage {

    public static final short PREFIX = 104; // 前缀 16进制 68

    //登陆请求与应答
    public static final short LOGIN_EVENT = 0x01;
    public static final short LOGIN_ACK = 0x02;
    
    //心跳包请求与应答
    public static final short PING_EVENT = 0x03;
    public static final short PING_ACK = 0x04;

    //计费模型验证请求与应答
    public static final short PRICE_VERIFY_EVENT = 0x05;
    public static final short PRICE_VERIFY_ACK = 0x06;

    //充电桩计费模型请求与应答
    public static final short PRICE_REQUEST_EVENT = 0x09;
    public static final short PRICE_REQUEST_ACK = 0x0a;
    
    //读取实时监测数据与应答
    public static final short DATA_REPORT_INVOKE = 0x12;
    public static final short DATA_REPORT_EVENT = 0x13;

    //上报交易记录与应答
    public static final short ORDER_REPORT_EVENT = 0x3b;
    public static final short ORDER_REPORT_ACK = 0x40;

    //运营平台远程控制启机与应答
    public static final short START_CHARGE_INVOKE = 0x34;
    public static final short START_CHARGE_REPLY = 0x33;
    
    //运营平台远程停机与应答
    public static final short STOP_CHARGE_INVOKE = 0x36;
    public static final short STOP_CHARGE_REPLY = 0x35;

    //远程账户余额更新与应答
    public static final short BALANCE_UPDATE_INVOKE = 0x42;
    public static final short BALANCE_UPDATE_REPLY = 0x41;

    //充电桩工作参数设置与应答
    public static final short PARAM_CONFIG_INVOKE = 0x52;
    public static final short PARAM_CONFIG_REPLY = 0x51;
    
    //对时设置与应答
    public static final short TIMEING_CONFIG_INVOKE = 0x56;
    public static final short TIMEING_CONFIG_REPLY = 0x55;

    //计费模型设置与应答
    public static final short PRICE_CONFIG_INVOKE = 0x58;
    public static final short PRICE_CONFIG_REPLY = 0x57;

    //远程重启与应答
    public static final short REBOOT_INVOKE = 0x92;
    public static final short REBOOT_REPLY = 0x91;

    //二维码下发与应答
    public static final short QRCODE_INVOKE = 0xf0;
    public static final short QRCODE_REPLY = 0xf1;


    /**
     * 设备编号
     */
    private String deviceDn;

    /**
     * 功能码
     */
    private short code;

    /**
     * 消息序号
     */
    private short serial;

    /**
     * 加密标志位
     */
    private short encrypt;

    /**
     * 包体数据
     */

    private byte[] payload;

    /**
     * 完整数据包
     */

    private byte[] pack;

    private String crc;

}
