package com.obast.charer.common.plugin.core.thing.charger.message;

import com.obast.charer.common.api.BaseMessage;
import com.obast.charer.common.api.IMessage;
import io.vertx.core.buffer.Buffer;
import lombok.*;

/**
 * @ Author：chuan
 * @ Date：2024-09-26-10:32
 * @ Version：1.0
 * @ Description：二维码下发消息
 */

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QrcodeInvokeMessage extends BaseMessage implements IMessage {

    private byte format;
    private String prefix;


    @Override
    public byte[] toByte() {
        Buffer buffer = Buffer.buffer();
        return buffer.getBytes();
    }

}
