package com.obast.charer.plugins.ykc.tcp.parser;

import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.parsetools.RecordParser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;

import java.util.function.Consumer;

/**
 * 数据包读取器
 *
 * @author sjg
 */
@Slf4j
public class DataReader {


    public static RecordParser getParser(Consumer<Buffer> receiveHandler) {
        RecordParser parser = RecordParser.newFixed(2);
        // 设置处理器
        parser.setOutput(new Handler<>() {
            // 表示当前数据长度
            int size = -1;

            @Override
            public void handle(Buffer buffer) {
                if (-1 == size) {
                    size = Integer.valueOf(Hex.encodeHexString(buffer.getBytes(1,2)),16) + 2;
                    parser.fixedSizeMode(size);
                } else {
                    receiveHandler.accept(buffer);
                    parser.fixedSizeMode(2);
                    size = -1;
                }
            }
        });
        return parser;
    }
}
