package com.obast.charer.common.thing;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThingService<T> {

    private String mid;

    private String productKey;

    private String deviceDn;

    private String type;

    private String invoke;

    private String directive;

    private String identifier;

    private short serial;

    private T params;

}
