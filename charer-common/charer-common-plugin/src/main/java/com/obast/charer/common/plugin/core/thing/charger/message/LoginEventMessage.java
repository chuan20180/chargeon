package com.obast.charer.common.plugin.core.thing.charger.message;

import com.obast.charer.common.api.BaseMessage;
import com.obast.charer.common.api.IMessage;
import lombok.*;

/**
 * 数据包
 *
 * @author sjg
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginEventMessage extends BaseMessage implements IMessage {

    /**
     * 桩类型
     */
    private short chargerType;

    /**
     * 充电枪数量
     */
    private short gunQty;

    /**
     * 通信协议版本
     */
    private short protocolVer;

    /**
     * 程序版本
     */
    private String firmwareVer;

    /**
     * 网络链接类型
     */
    private short netType;

    /**
     * Sim 卡
     */
    private String sim;

    /**
     * 运营商
     */
    private short telcom;

    @Override
    public byte[] toByte() {
        return new byte[0];
    }
}
