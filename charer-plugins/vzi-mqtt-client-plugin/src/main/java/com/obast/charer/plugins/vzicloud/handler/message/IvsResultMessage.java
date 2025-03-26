package com.obast.charer.plugins.vzicloud.handler.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

/**
 * 数据包
 *
 * @author sjg
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class IvsResultMessage {

    @JsonProperty(value = "alarm_info")
    private IvsAlarmInfo alarmInfo;

    @JsonProperty(value = "bg_img")
    private List<Img> bgImg;

    @JsonProperty(value = "feture_img")
    private List<Img> fetureImg;

    @JsonProperty(value = "chnlid")
    private int chnlid;

    @JsonProperty(value = "device_info")
    private DeviceInfo deviceInfo;

    @JsonProperty(value = "product_h")
    private ProductH productH;

    @JsonProperty(value = "reco_id")
    private int recoId;

    @JsonProperty(value = "serial")
    private long serial;

    @JsonProperty(value = "time_s")
    private long timeS;

    @JsonProperty(value = "trigger_type")
    private int triggerType;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class IvsAlarmInfo {
        @JsonProperty(value = "alarm_status")
        private int alarmStatus;

        @JsonProperty(value = "alarm_whitelist")
        private int alarmWhitelist;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DeviceInfo {
        @JsonProperty(value = "dev_name")
        private String devName;

        @JsonProperty(value = "ip")
        private String ip;

        @JsonProperty(value = "sn")
        private String sn;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProductH {
        @JsonProperty(value = "parking")
        private Parking parking;

        @JsonProperty(value = "plate")
        private Plate plate;

        @JsonProperty(value = "reco")
        private Reco reco;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Parking {
        @JsonProperty(value = "parking_state")
        private int parkingState;

        @JsonProperty(value = "zone_id")
        private int zoneId;

        @JsonProperty(value = "zone_name")
        private String zoneName;

        public void setZoneName(String base64License) {
            this.zoneName = new String(Base64.getDecoder().decode(base64License), StandardCharsets.UTF_8);
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Plate {
        @JsonProperty(value = "color")
        private int color;

        @JsonProperty(value = "confidence")
        private int confidence;

        @JsonProperty(value = "plate")
        private String plate;

        public void setPlate(String base64License) {
            this.plate = new String(Base64.getDecoder().decode(base64License), StandardCharsets.UTF_8);
        }


        @JsonProperty(value = "plate_type")
        private int plateType;

        @JsonProperty(value = "type")
        private int type;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Reco {
        @JsonProperty(value = "car_brand")
        private String carBrand;

        public void setCarBrand(String base64License) {
            this.carBrand = new String(Base64.getDecoder().decode(base64License), StandardCharsets.UTF_8);
        }

        @JsonProperty(value = "car_type")
        private String carType;

        @JsonProperty(value = "group_id")
        private long groupId;

        @JsonProperty(value = "reco_flag")
        private int recoFlag;

        @JsonProperty(value = "reco_id")
        private int recoId;

        @JsonProperty(value = "reco_time")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date recoTime;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Img {
        @JsonProperty(value = "image")
        private String image;

        @JsonProperty(value = "image_length")
        private int imageLength;

        @JsonProperty(value = "image_send_flag")
        private int imageSendFlag;

        @JsonProperty(value = "key")
        private int key;

        @JsonProperty(value = "path")
        private String path;

        public void setPath(String base64License) {
            this.path = new String(Base64.getDecoder().decode(base64License), StandardCharsets.UTF_8);
        }

        @JsonProperty(value = "resolution")
        private Resolution resolution;

        @JsonProperty(value = "type")
        private int type;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Resolution {

        @JsonProperty(value = "height")
        private int height;

        @JsonProperty(value = "width")
        private int width;
    }
}
