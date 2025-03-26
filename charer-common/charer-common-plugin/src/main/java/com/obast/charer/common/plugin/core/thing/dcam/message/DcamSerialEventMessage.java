package com.obast.charer.common.plugin.core.thing.dcam.message;

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
public class DcamSerialEventMessage extends BaseMessage implements IMessage {

    private Integer dataLen;
    private String rs485Data;

    private Integer source;

    @Override
    public byte[] toByte() {
        return new byte[0];
    }
}
