package com.obast.charer.plugins.vzicloud.handler.message;

import lombok.Data;

@Data
public class SerialDataMessage {
    private SerialDataPayloadBody body;

    @Data
    public static class SerialDataPayloadBody {
        private SerialDataPayloadBodySerialData serialData;
    }

    @Data
    public static class SerialDataPayloadBodySerialData {
        private String data;
        private Integer dataLen;
        private String deviceName;
        private String ipaddr;
        private Integer serialChannel;
        private String serialno;
    }
}
