package com.obast.charer.plugins.ykc.tcp.server;


import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.model.ActionResult;
import com.obast.charer.common.plugin.core.thing.IDevice;
import com.obast.charer.common.plugin.core.thing.charger.ChargerDirectiveEnum;
import com.obast.charer.common.plugin.core.thing.charger.ChargerInvoke;
import com.obast.charer.plugins.ykc.tcp.parser.DataEncoder;
import com.obast.charer.plugins.ykc.tcp.parser.DataPackage;
import io.vertx.core.buffer.Buffer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * tcp设备下行接口
 *
 * @author sjg
 */
@Slf4j
@Service
public class TcpDevice implements IDevice {

    @Autowired
    private YkcTcpVerticle ykcTcpVerticle;

    @Override
    public ActionResult<?> invoke(ChargerInvoke<?> action) {
        //log.info("[平台指令调试] 收到平台指令 action: {}", action);
        String deviceDn = action.getDn();
        short serial = action.getSerial();
        ChargerDirectiveEnum directive = (ChargerDirectiveEnum) action.getDirective();
        DataPackage dataPackage = DataEncoder.builder(deviceDn, serial, directive, action.getData() );
        Buffer data = DataEncoder.encode(dataPackage);

        switch (directive) {
            case START_CHARGE_INVOKE:
                //log.info("[平台下发] 启动充电指令下发 [{}]", Hex.encodeHexString(data.getBytes(), false));
                break;
            case STOP_CHARGE_INVOKE:
                //log.info("[平台下发] 停止充电指令下发 [{}]", Hex.encodeHexString(data.getBytes(), false));
                break;
            case BALANCE_UPDATE_INVOKE:
                //log.info("[平台下发] 余额更新指令下发 [{}]", Hex.encodeHexString(data.getBytes(), false));
                break;
            case TIME_CONFIG_INVOKE:
                //log.info("[平台下发] 校时指令下发 [{}]", Hex.encodeHexString(data.getBytes(), false));
                break;
            case PARAM_CONFIG_INVOKE:
                //log.info("[平台下发] 参数配置指令下发 [{}]", Hex.encodeHexString(data.getBytes(), false));
                break;
            case PRICE_CONFIG_INVOKE:
                //log.info("[平台下发] 计价模型指令下发 [{}]", Hex.encodeHexString(data.getBytes(), false));
                break;

            case REBOOT_INVOKE:
                //log.info("[平台下发] 设备重起指令下发 [{}]", Hex.encodeHexString(data.getBytes(), false));
                break;
            case QRCODE_INVOKE:
                //log.info("[平台下发] 二维码指令下发 [{}]", Hex.encodeHexString(data.getBytes(), false));
                break;
            default:
                //log.error("[平台下发] 收到未知指令 [{}]", Hex.encodeHexString(data.getBytes(), false));
        }

        return send(action.getDn(), data);
    }

    private ActionResult<?> send(String deviceDn, Buffer msg) {
        //log.info("[平台指令调试] 平台发送指令到设备: deviceId: {} hex: {}", deviceName, Hex.encodeHexString(msg.getBytes()));
        try {
            ykcTcpVerticle.sendMsg(deviceDn, msg);
            return ActionResult.builder().code(0).reason("").build();
        } catch (BizException e) {
            return ActionResult.builder().code(e.getCode()).reason(e.getMessage()).build();
        } catch (Exception e) {
            return ActionResult.builder().code(ErrCode.UNKNOWN_EXCEPTION.getKey()).reason(e.getMessage()).build();
        }
    }
}
