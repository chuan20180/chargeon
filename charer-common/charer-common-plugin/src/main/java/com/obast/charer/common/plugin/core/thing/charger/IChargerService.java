package com.obast.charer.common.plugin.core.thing.charger;

import com.obast.charer.common.plugin.core.thing.ThingDevice;
import com.obast.charer.common.plugin.core.thing.ThingProduct;
import com.obast.charer.common.model.ActionResult;

/**
 * 设备服务接口
 *
 * @author sjg
 */
public interface IChargerService {

    /**
     * 提交设备行为
     *
     * @param action IDeviceAction
     * @return result
     */
    ActionResult<?> post(String pluginId, ChargerAction<?> action);

    void notify(String pluginId, ChargerAction<?> action);

    /**
     * 获取产品信息
     *
     * @param pk 产品key
     * @return Product
     */
    ThingProduct getProduct(String pk);


    ThingDevice getDevice(String dn);


}
