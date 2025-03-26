package com.obast.charer.common.plugin.core.thing;

import com.obast.charer.common.plugin.core.thing.charger.ChargerInvoke;
import com.obast.charer.common.model.ActionResult;


/**
 * 设备接口
 *
 * @author sjg
 */
public interface IDevice {

    /**
     * 执行设备服务调用动作
     *
     * @param action 动作
     * @return result
     */
    ActionResult invoke(ChargerInvoke<?> action);

}
