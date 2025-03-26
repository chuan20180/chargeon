package com.obast.charer.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author: Gabriel
 * @date: 2020/3/7 15:28
 * @description 小数保留2位返回给前端序列化器
 */
public class Decimal2Serializer extends JsonSerializer<Object> {

    /**
     * 将返回的BigDecimal保留两位小数，再返回给前端
     */
    @Override
    public void serialize(Object value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (value != null) {
            BigDecimal bigDecimal = new BigDecimal(value.toString()).setScale(2, RoundingMode.HALF_UP);
            jsonGenerator.writeString(bigDecimal.toString());
        }
    }
}