package com.obast.charer.plugins.vzicloud.handler.message;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckOfflineInvokeMessage {

    private String type;

    private Body body;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Body {

        @JsonProperty(value = "enable")
        private String enable;

        @JsonProperty(value = "max_count")
        private int maxCount;

        @JsonProperty(value = "min_id")
        private int minId;



    }
}