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
public class PingEventMessage extends BaseMessage implements IMessage {

    /**
     * 充电枪数量
     */
    private String gunNo;

    /**
     * 通信协议版本
     */
    private short gunStatus;

    @Override
    public byte[] toByte() {
        return new byte[0];
    }
}
