package com.obast.charer.common.plugin.core.thing;

import com.obast.charer.common.IDeviceAction;
import com.obast.charer.common.ProductType;
import com.obast.charer.common.model.ActionResult;

/**
 * 设备服务接口
 *
 * @author sjg
 */
public interface IThingService {

    /**
     * 提交设备行为
     *
     * @param action IDeviceAction
     * @return result
     */
    ActionResult<?> post(String pluginId, IDeviceAction<?> action);

    void notify(String pluginId, IDeviceAction<?> action);

    /**
     * 获取产品信息
     *
     * @param pk 产品key
     * @return Product
     */
    ThingProduct getProduct(String pk);


    ThingDevice getDevice(ProductType productType, String dn);


}
