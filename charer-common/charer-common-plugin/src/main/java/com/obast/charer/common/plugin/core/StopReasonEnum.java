package com.obast.charer.common.plugin.core;

import com.obast.charer.common.converter.AbstractEnumConverter;
import com.obast.charer.common.enums.IBaseEnum;
import lombok.Getter;

/**
 * @ Author：chuan
 * @ Date：2024-09-10-15:11
 * @ Version：1.0
 * @ Description：计价规则类型枚举
 */
@Getter
public enum StopReasonEnum implements IBaseEnum<String> {

    Finish40( "结束充电，APP 远程停止"),
    Finish41( "结束充电，SOC 达到 100%"),
    Finish42( "结束充电，充电电量满足设定条件"),
    Finish43( "结束充电，充电金额满足设定条件"),
    Finish44( "结束充电，充电时间满足设定条件"),
    Finish45( "结束充电，手动停止充电"),
    Finish46( "结束充电，其他方式"),
    Finish47( "结束充电，其他方式"),
    Finish48( "结束充电，其他方式"),
    Finish49( "结束充电，其他方式"),
    //充电启动失败
    Fail4a( "充电启动失败，充电桩控制系统故障( 需要重启或自动恢复"),
    Fail4b( "充电启动失败，控制导引断开"),
    Fail4c( "充电启动失败，断路器跳位"),
    Fail4d( "充电启动失败，电表通信中断"),
    Fail4e( "充电启动失败，余额不足"),
    Fail4f( "充电启动失败，充电模块故障"),
    Fail50( "充电启动失败，急停开入"),
    Fail51( "充电启动失败，防雷器异常"),
    Fail52( "充电启动失败，BMS 未就绪"),
    Fail53( "充电启动失败，温度异常"),
    Fail54( "充电启动失败，电池反接故障"),
    Fail55( "充电启动失败，电子锁异常"),
    Fail56( "充电启动失败，合闸失败"),
    Fail57( "充电启动失败，绝缘异常"),
    Fail58( "预留"),
    Fail59( "充电启动失败，接收 BMS 握手报文 BHM 超时"),
    Fail5a( "充电启动失败，接收 BMS 和车辆的辨识报文超时 BRM"),
    Fail5b( "充电启动失败，接收电池充电参数报文超时 BCP"),
    Fail5c( "充电启动失败，接收 BMS 完成充电准备报文超时 BRO AA"),
    Fail5d( "充电启动失败，接收电池充电总状态报文超时 BCS"),
    Fail5e( "充电启动失败，接收电池充电要求报文超时 BCL"),
    Fail5f( "充电启动失败，接收电池状态信息报文超时 BSM"),
    Fail60( "充电启动失败，GB2015 电池在 BHM 阶段有电压不允许充电"),
    Fail61( "充电启动失败，GB2015 辨识阶段在 BRO_AA 时候电池实际电压 与 BCP 报文电池电压差距大于 5%"),
    Fail62( "充电启动失败，B2015 充电机在预充电阶段从 BRO_AA 变成 BRO_00 状态"),
    Fail63( "充电启动失败，接收主机配置报文超时"),
    Fail64( "充电启动失败，充电机未准备就绪),我们没有回 CRO AA，对应 老国标"),
    Fail65( "其他原因"),
    Fail66( "其他原因"),
    Fail67( "其他原因"),
    Fail68( "其他原因"),
    Fail69( "其他原因"),
    //充电异常中止
    Stop6a( "充电异常中止，系统闭锁"),
    Stop6b( "充电异常中止，导引断开"),
    Stop6c( "充电异常中止，断路器跳位"),
    Stop6d( "充电异常中止，电表通信中断"),
    Stop6e( "充电异常中止，余额不足"),
    Stop6f( "充电异常中止，交流保护动作"),
    Stop70( "充电异常中止，直流保护动作"),
    Stop71( "充电异常中止，充电模块故障"),
    Stop72( "充电异常中止，急停开入"),
    Stop73( "充电异常中止，防雷器异常"),
    Stop74( "充电异常中止，温度异常"),
    Stop75( "充电异常中止，输出异常"),
    Stop76( "充电异常中止，充电无流"),
    Stop77( "充电异常中止，电子锁异常"),
    Stop78( "预留"),
    Stop79( "充电异常中止，总充电电压异常"),
    Stop7a( "充电异常中止，总充电电流异常"),
    Stop7b( "充电异常中止，单体充电电压异常"),
    Stop7c( "充电异常中止，电池组过温"),
    Stop7d( "充电异常中止，最高单体充电电压异常"),
    Stop7e( "充电异常中止，最高电池组过温"),
    Stop7f( "充电异常中止，BMV单体充电电压异常"),
    Stop80( "充电异常中止，BMT电池组过温"),
    Stop81( "充电异常中止，电池状态异常停止充电"),
    Stop82( "充电异常中止，车辆发报文禁止充电"),
    Stop83( "充电异常中止，充电桩断电"),
    Stop84( "充电异常中止，接收电池充电总状态报文超时"),
    Stop85( "充电异常中止，接收电池充电要求报文超时"),
    Stop86( "充电异常中止，接收电池状态信息报文超时"),
    Stop87( "充电异常中止，接收BMS中止充电报文超时"),
    Stop88( "充电异常中止，接收BMS充电统计报文超时"),
    Stop89( "充电异常中止，接收对侧CCS报文超时"),
    Stop8a( "其他原因"),
    Stop8b( "其他原因"),
    Stop8c( "其他原因"),
    Stop8d( "其他原因"),
    Stop8e( "其他原因"),
    Stop8f( "其他原因"),
    Stop90( "其他原因"),
    ;


    private final String msg;


    StopReasonEnum(String msg) {
        this.msg = msg;

    }

    @Override
    public String getData() {
        return msg;
    }

    public static class Converter extends AbstractEnumConverter<StopReasonEnum, String> {

        public Converter() {
            super(StopReasonEnum.class);
        }
    }
}