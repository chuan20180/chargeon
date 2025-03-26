package com.obast.charer.common.plugin.core.thing.charger.message;

import com.obast.charer.common.api.BaseMessage;
import com.obast.charer.common.api.IMessage;
import com.obast.charer.common.model.dto.PriceProperties;
import io.vertx.core.buffer.Buffer;
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
public class PriceConfigInvokeMessage extends BaseMessage implements IMessage {

    /**
     * 计价规则编号
     */
    private short no;
    /**
     * 计价规则
     */
    private PriceProperties priceProperties;


    @Override
    public byte[] toByte() {
        Buffer buffer = Buffer.buffer();
        return buffer.getBytes();
    }
}
