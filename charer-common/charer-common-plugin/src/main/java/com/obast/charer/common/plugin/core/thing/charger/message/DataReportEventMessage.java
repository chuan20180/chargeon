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
public class DataReportEventMessage extends BaseMessage implements IMessage {

    private String tranId;

    private String gunNo;
    private short status;
    private short back;
    private short slot;
    private float voltage;
    private float current;
    private short gunLineTemp;

    private short gunLineNo;
    private short soc;
    private short batteryHighestTemp;
    private short chargeMinute;
    private short remainMinute;
    private BigDecimal totalQty;
    private BigDecimal totalQuantity;
    private BigDecimal totalAmount;
    private short fail;

    @Override
    public byte[] toByte() {
        Buffer buffer = Buffer.buffer();
        return buffer.getBytes();
    }

}
