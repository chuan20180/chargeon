package com.obast.charer.plugin.service.impl;


import com.obast.charer.common.InvokeType;
import com.obast.charer.common.ProductType;
import com.obast.charer.common.api.IMessage;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.plugin.core.thing.charger.ChargerDirectiveEnum;
import com.obast.charer.common.thing.DeviceService;
import com.obast.charer.common.thing.ThingService;
import com.obast.charer.common.utils.UniqueIdUtil;
import com.obast.charer.data.business.IChargerData;
import com.obast.charer.enums.OnlineStatusEnum;
import com.obast.charer.model.device.Charger;
import com.obast.charer.plugin.service.IChargerCtrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ChargerCtrlServiceImpl implements IChargerCtrlService {

    @Autowired
    private IChargerData chargerData;


    @Autowired
    private DeviceService deviceService;


    /**
     * 设备服务调用
     */
    public void invokeService(String chargerDn, short serial, ChargerDirectiveEnum invoke, IMessage message) {
        Charger charger = getAndCheckDevice(chargerDn);
        send(charger.getProductKey(), charger.getDn(), serial, message, invoke);
    }

    /**
     * 检查设备操作权限和状态
     */
    private Charger getAndCheckDevice(String chargerDn) {
        Charger charger = chargerData.findByDn(chargerDn);
        if (charger == null) {
            throw new BizException(ErrCode.CHARGER_NOT_FOUND);
        }

        if (charger.getOnline().equals(OnlineStatusEnum.Offline)) {
            throw new BizException(ErrCode.DEVICE_OFFLINE);
        }
        return charger;
    }

    /**
     * 数据下发
     */
    private void send(String pk, String dn, short serial, IMessage message, ChargerDirectiveEnum invoke) {
        ThingService<Object> thingService = ThingService.builder()
                .mid(UniqueIdUtil.newRequestId())
                .productKey(pk)
                .invoke(InvokeType.Invoke.name())
                .directive(invoke.name())
                .deviceDn(dn)
                .serial(serial)
                .type(ProductType.CHARGER.name())
                .identifier(invoke.name())
                .params(message)
                .build();
        //设备指令下发
        deviceService.invoke(thingService);
    }
}
