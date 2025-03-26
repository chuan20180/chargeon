package com.obast.charer.common.plugin.core.thing.charger.message;

import com.obast.charer.common.api.BaseMessage;
import com.obast.charer.common.api.IMessage;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 数据包
 *
 * @author sjg
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Builder
public class PriceRequestEventMessage extends BaseMessage implements IMessage {

    @Override
    public byte[] toByte() {
        return new byte[0];
    }
}
