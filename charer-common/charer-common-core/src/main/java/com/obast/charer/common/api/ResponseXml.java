package com.obast.charer.common.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseXml {

    private Object data;

   public static Object success(Object data) {
        return new ResponseXml(data);
    }

   public static Object error(Object data) {
        return new ResponseXml(data);
    }
}
