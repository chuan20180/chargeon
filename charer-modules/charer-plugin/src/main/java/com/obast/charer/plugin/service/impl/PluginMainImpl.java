package com.obast.charer.plugin.service.impl;


import com.gitee.starblues.integration.user.PluginUser;
import com.github.yitter.idgen.YitIdHelper;
import com.obast.charer.common.InvokeType;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.model.ActionResult;
import com.obast.charer.common.plugin.core.thing.IDevice;
import com.obast.charer.common.plugin.core.thing.charger.ChargerDirectiveEnum;
import com.obast.charer.common.plugin.core.thing.charger.ChargerInvoke;
import com.obast.charer.common.thing.DeviceService;
import com.obast.charer.common.thing.ThingModelMessage;
import com.obast.charer.common.thing.ThingService;
import com.obast.charer.common.utils.JsonUtils;
import com.obast.charer.data.business.IPluginInfoData;
import com.obast.charer.data.platform.IProductData;
import com.obast.charer.model.plugin.PluginInfo;
import com.obast.charer.plugin.DeviceRouter;
import com.obast.charer.plugin.PluginRouter;
import com.obast.charer.plugin.service.IPluginMain;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 插件主程序接口服务
 *
 * @author sjg
 */
@Slf4j
@Service
public class PluginMainImpl implements IPluginMain, DeviceService {

    @Autowired
    private IPluginInfoData pluginInfoData;

    @Autowired
    IProductData productData;

    @Autowired
    private DeviceRouter deviceRouter;

    @Autowired
    private PluginUser pluginUser;


    @Override
    public void invoke(@RequestBody ThingService<?> service) {

        short serial = service.getSerial();

        String deviceDn = service.getDeviceDn();

        ChargerDirectiveEnum actionService = ChargerDirectiveEnum.valueOf(service.getIdentifier());

        String routeId = String.format("%s-%s", service.getType(), service.getDeviceDn());

        PluginRouter router = deviceRouter.getRouter(routeId);
        if (router == null) {
            log.error("路由id: {}", routeId);
            throw new BizException(ErrCode.PLUGIN_ROUTER_NOT_FOUND);
        }

        //获取插件中的设备服务接口
        List<IDevice> deviceServices = pluginUser.getBeanByInterface(router.getPluginId(), IDevice.class);
        if (deviceServices.isEmpty()) {
            throw new BizException(ErrCode.PLUGIN_SERVICE_NOT_FOUND);
        }

        IDevice deviceService = deviceServices.get(0);

        String identifier = service.getIdentifier();

        //服务调用
        ChargerInvoke<?> action = ChargerInvoke.builder()
                .id(service.getMid())
                .pk(service.getProductKey())
                .dn(service.getDeviceDn())
                .directive(actionService)
                .name(identifier)
                .serial(serial)
                .data(service.getParams())
                .build();
        //调用插件设备服务接口
        ActionResult<?> result = deviceService.invoke(action);
        publish(service, deviceDn, result.getCode());

        if (result.getCode() != 0) {
            throw new BizException(ErrCode.DEVICE_ACTION_FAILED, result.getReason());
        }
    }

    private void publish(ThingService<?> service, String deviceDn, int code) {
        //产生下发消息作为下行日志保存
        ThingModelMessage message = ThingModelMessage.builder()
                .id(String.valueOf(YitIdHelper.nextId()))
                .deviceDn(deviceDn)
                .mid(service.getMid())
                .productKey(service.getProductKey())
                .identifier(service.getIdentifier())
                .directive(InvokeType.Invoke.name())
                .type(service.getType())
                .invoke(service.getInvoke())
                .data(service.getParams())
                .code(code)
                .occurred(System.currentTimeMillis())
                .time(System.currentTimeMillis())
                .build();

        //producer.publish(Constants.THING_MODEL_MESSAGE_TOPIC, message);
    }

    @Override
    public Map<String, Object> getConfig(String pluginId) {
        //获取系统插件配置项
        PluginInfo plugin = pluginInfoData.findByPluginId(pluginId);
        if (plugin == null) {
            return new HashMap<>(0);
        }
        return JsonUtils.parseObject(plugin.getConfig(), Map.class);
    }
}
