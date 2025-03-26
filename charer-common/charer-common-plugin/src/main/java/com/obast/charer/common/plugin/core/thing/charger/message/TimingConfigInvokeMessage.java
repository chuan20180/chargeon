package com.obast.charer.common.plugin.core.thing.charger.message;

import com.obast.charer.common.api.BaseMessage;
import com.obast.charer.common.api.IMessage;
import io.vertx.core.buffer.Buffer;
import lombok.*;

import java.time.LocalDateTime;

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
public class TimingConfigInvokeMessage extends BaseMessage implements IMessage {

    private LocalDateTime time;

    @Override
    public byte[] toByte() {
        Buffer buffer = Buffer.buffer();
        return buffer.getBytes();
    }
}
