package com.obast.charer.common.plugin.core.thing.charger.message;

import com.obast.charer.common.api.BaseMessage;
import com.obast.charer.common.api.IMessage;
import io.vertx.core.buffer.Buffer;
import lombok.*;

import java.math.BigDecimal;

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
public class StartChargeInvokeMessage extends BaseMessage implements IMessage {

    private String tranId;
    private String deviceName;
    private String gunNo;
    private String logicalCardNo;
    private String physicalCardNo;
    private BigDecimal balance;

    @Override
    public byte[] toByte() {
        Buffer buffer = Buffer.buffer();
        return buffer.getBytes();
    }
}
