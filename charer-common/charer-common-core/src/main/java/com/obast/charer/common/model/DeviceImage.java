package com.obast.charer.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceImage {
        private String name;

        private String type;

        private String base64Image;

        private int width;

        private int height;

        private int length;
}