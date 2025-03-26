package com.obast.charer.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @ Author：chuan
 * @ Date：2024-10-13-23:31
 * @ Version：1.0
 * @ Description：小数保留2位返回给前端序列化器
 */
public class DecimalSerializer extends JsonSerializer<Object> {

    /**
     * 将返回的BigDecimal保留两位小数，再返回给前端
     */
    @Override
    public void serialize(Object value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (value != null) {
            BigDecimal bigDecimal = new BigDecimal(value.toString()).setScale(0, RoundingMode.HALF_UP);
            jsonGenerator.writeString(bigDecimal.toString());
        }
    }
}