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
public class OrderReportEventMessage extends BaseMessage implements IMessage {

    private String tranId;

    private int tranType;

    private String tranTime;

    private String gunNo;

    private String startTime;
    private String endTime;

    private BigDecimal sharpPrice;
    private BigDecimal sharpQty;
    private BigDecimal sharpQuantity;
    private BigDecimal sharpAmount;

    private BigDecimal peakPrice;
    private BigDecimal peakQty;
    private BigDecimal peakQuantity;
    private BigDecimal peakAmount;

    private BigDecimal flatPrice;
    private BigDecimal flatQty;
    private BigDecimal flatQuantity;
    private BigDecimal flatAmount;

    private BigDecimal valleyPrice;
    private BigDecimal valleyQty;
    private BigDecimal valleyQuantity;
    private BigDecimal valleyAmount;

    private BigDecimal quantityStart;
    private BigDecimal quantityEnd;

    private BigDecimal totalQty;
    private BigDecimal totalQuantity;
    private BigDecimal totalAmount;

    private String vin;

    private String stopReason;

    private String physicalCardNo;

    @Override
    public byte[] toByte() {
        Buffer buffer = Buffer.buffer();
        return buffer.getBytes();
    }

}
