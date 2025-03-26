package com.obast.charer.common.plugin.core.thing;

import com.obast.charer.common.ProductType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 设备信息
 *
 * @author sjg
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThingDevice {

    /**
     * 设备DN
     */

    private String deviceDn;

    private ProductType productType;

    /**
     * 产品key
     */
    private String productKey;


    /**
     * 设备型号
     */
    private String model;

    /**
     * 设备密钥
     */
    private String secret;

    private DeviceState state;

    private DeviceStatus status;
}
