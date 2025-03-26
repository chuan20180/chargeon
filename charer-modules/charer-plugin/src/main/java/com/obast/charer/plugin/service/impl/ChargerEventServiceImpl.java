package com.obast.charer.plugin.service.impl;


import com.obast.charer.api.system.feign.IRemoteOrderService;
import com.obast.charer.common.IDeviceAction;
import com.obast.charer.common.InvokeType;
import com.obast.charer.common.ProductType;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.enums.PriceTypeEnum;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.model.ActionResult;
import com.obast.charer.common.model.dto.PriceFee;
import com.obast.charer.common.model.dto.PriceProperties;
import com.obast.charer.common.plugin.core.thing.DeviceState;
import com.obast.charer.common.plugin.core.thing.DeviceStatus;
import com.obast.charer.common.plugin.core.thing.ThingDevice;
import com.obast.charer.common.plugin.core.thing.ThingProduct;
import com.obast.charer.common.plugin.core.thing.charger.ChargerAction;
import com.obast.charer.common.plugin.core.thing.charger.ChargerDirectiveEnum;
import com.obast.charer.common.plugin.core.thing.charger.IChargerService;
import com.obast.charer.common.plugin.core.thing.charger.actions.*;
import com.obast.charer.common.plugin.core.thing.charger.message.*;
import com.obast.charer.common.thing.ThingModelMessage;
import com.obast.charer.common.utils.JsonUtils;
import com.obast.charer.common.utils.UniqueIdUtil;
import com.obast.charer.data.business.*;
import com.obast.charer.data.platform.IProductData;
import com.obast.charer.enums.*;
import com.obast.charer.model.customer.Customer;
import com.obast.charer.model.device.Charger;
import com.obast.charer.model.device.ChargerDirective;
import com.obast.charer.model.device.ChargerGun;
import com.obast.charer.model.order.Orders;
import com.obast.charer.model.price.Price;
import com.obast.charer.model.product.Product;
import com.obast.charer.plugin.DeviceRouter;
import com.obast.charer.plugin.IPluginMain;
import com.obast.charer.plugin.PluginRouter;
import com.obast.charer.plugin.service.IChargerInvokeService;
import com.obast.charer.qo.ChargerDirectiveQueryBo;
import com.obast.charer.qo.ChargerGunQueryBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ChargerEventServiceImpl implements IChargerService {

    private final ProductType productType = ProductType.CHARGER;

    @Autowired
    private IPriceData priceData;

    @Autowired
    private IProductData productData;

    @Autowired
    private IOrdersData ordersData;

    @Autowired
    private IChargerData chargerData;

    @Autowired
    private IChargerGunData chargerGunData;

    @Autowired
    private IChargerDirectiveData chargerDirectiveData;

    @Autowired
    private DeviceRouter deviceRouter;

    @Autowired
    private ICustomerData customerData;

    @Autowired
    private IChargerInvokeService chargerInvokeService;

    @Autowired
    private IRemoteOrderService remoteOrderService;

    @Override
    public ActionResult<?> post(String pluginId, ChargerAction<?> action) {
        try {
            //log.info("[服务器端调试]收到设备消息:{}, type: {} action:{}", pluginId, action.getType().name(), action);

            String deviceDn = action.getDn();

            String routeId = String.format("%s-%s", productType.name(), deviceDn);
            //添加设备路由
            deviceRouter.putRouter(routeId, new PluginRouter(IPluginMain.MAIN_ID, pluginId));

            ThingDevice thingDevice = getDevice(deviceDn);
            if (thingDevice == null) {
                log.debug("[服务器端调试]设备不存在 dn={}", deviceDn);
                return ActionResult.builder().code(ErrCode.DEVICE_NOT_FOUND.getKey()).reason(ErrCode.DEVICE_NOT_FOUND.getValue()).build();
            }

            if(thingDevice.getStatus() != DeviceStatus.Enabled) {
                log.debug("[服务器端调试]设备未启用 dn={}", deviceDn);
                return ActionResult.builder().code(ErrCode.DEVICE_NOT_ENABLED.getKey()).reason(ErrCode.DEVICE_NOT_ENABLED.getValue()).build();
            }

            ChargerDirectiveEnum directive = (ChargerDirectiveEnum) action.getDirective();
            ActionResult<?> result;
            switch (directive) {
                //设备登陆
                case LOGIN_EVENT:
                    result = login(thingDevice, (LoginEventAction<?>) action);
                    break;

                //设备心跳
                case PING_EVENT:
                    result = ping(thingDevice, (PingEventAction<?>) action);
                    break;

                //上下线
                case STATE_CHANGE_EVENT:
                    result = deviceStateChange(thingDevice, (StateChangeEventAction<?>) action);
                    break;

                //计价模型验证
                case PRICE_VERIFY_EVENT:
                    result = priceVerify(thingDevice, (PriceVerifyEventAction<?>) action);
                    break;

                //计费模型请求
                case PRICE_REQUEST_EVENT:
                    result = priceRequest(thingDevice, (PriceRequestEventAction<?>) action);
                    break;

                //上传实时监测数据
                case DATA_REPORT_EVENT:
                    result = dataReport(thingDevice, (DataReportEventAction<?>) action);
                    break;

                //上传订单数据
                case ORDER_REPORT_EVENT:
                    result = orderReport(thingDevice, (OrderReportEventAction<?>) action);
                    break;

                //启动充电回复
                case START_CHARGE_REPLY:
                    result = startChargeReply(thingDevice, (StartChargeReplyAction<?>) action);
                    break;

                //停止充电回复
                case STOP_CHARGE_REPLY:
                    result = stopChargeReply(thingDevice, (StopChargeReplyAction<?>) action);
                    break;

                //余额更新回复
                case BALANCE_UPDATE_REPLY:
                    result = balanceUpdateReply(thingDevice, (BalanceUpdateReplyAction<?>) action);
                    break;

                //参数配置回复
                case PARAM_CONFIG_REPLY:
                    result = paramConfigReply(thingDevice, (ParamConfigReplyAction<?>) action);
                    break;

                //校时回复
                case TIME_CONFIG_REPLY:
                    result = timingConfigReply(thingDevice, (TimingConfigReplyAction<?>) action);
                    break;

                //计价规则设置回复
                case PRICE_CONFIG_REPLY:
                    result = priceConfigReply(thingDevice, (PriceConfigReplyAction<?>) action);
                    break;

                //重起回复
                case REBOOT_REPLY:
                    result = rebootReply(thingDevice, (RebootReplyAction<?>) action);
                    break;

                //二维码下发回复
                case QRCODE_REPLY:
                    result = qrcodeConfigReply(thingDevice, (QrcodeReplyAction<?>) action);
                    break;

                default:
                    result = ActionResult.builder().code(ErrCode.PARAMS_EXCEPTION.getKey()).build();
            }

            publishMsg(
                    thingDevice,
                    action,
                    ThingModelMessage.builder()
                            .invoke(InvokeType.Event.name())
                            .directive(InvokeType.Event.name())
                            .type(ProductType.CHARGER.name())
                            .identifier(directive.name())
                            .pack(action.getPack())
                            .data(action.getData())
                            .time(System.currentTimeMillis())
                            .build()
            );


            return result;

        } catch (Throwable e) {
            log.error("action process error", e);
            return ActionResult.builder().code(1).reason(e.getMessage()).build();
        }
    }

    @Override
    public void notify(String pluginId, ChargerAction<?> action) {
        try {
            //log.info("[服务器端调试]收到设备消息:{}, type: {} action:{}", pluginId, action.getType().name(), action);
            String deviceDn = action.getDn();


            ThingDevice thingDevice = getDevice(deviceDn);
            if (thingDevice == null) {
                log.debug("[服务器端调试]设备不存在(device dn: {})", deviceDn);
                return;
            }

            if(thingDevice.getStatus() != DeviceStatus.Enabled) {
                log.debug("[服务器端调试]设备未启用(device dn:{})", deviceDn);
                return;
            }

            ChargerDirectiveEnum directive = (ChargerDirectiveEnum) action.getDirective();
            switch (directive) {
                //设备成功事件
                case LOGIN_ACK:
                    loginAck(thingDevice, (LoginAckAction<?>) action);
                    break;

                default:
                    log.error("[服务器端调试]收到未知ack事件 {})", action);
            }

            publishMsg(
                    thingDevice,
                    action,
                    ThingModelMessage.builder()
                            .invoke(InvokeType.Notify.name())
                            .type(ProductType.CHARGER.name())
                            .directive(action.getDirective().name())
                            .pack(action.getPack())
                            .data(action.getData())
                            .time(System.currentTimeMillis())
                            .build()
            );
        } catch (Throwable e) {
            log.error("action process error", e);
        }
    }


    /*
     * 登陆Ack事件
     */
    private void loginAck(ThingDevice thingDevice, LoginAckAction<?> action) {
        log.debug("[服务器端调试]登陆成功Ack事件 dn={}, action: {}", thingDevice.getDeviceDn(), action);

        //登陆成功后下发校时
        chargerInvokeService.timingConfig(thingDevice.getDeviceDn());

        //登陆成功后下发二维码
        chargerInvokeService.qrcodeConfig(thingDevice.getDeviceDn());
    }

    /*
     * 登陆请求事件
     */
    private ActionResult<?> login(ThingDevice thingDevice, LoginEventAction<?> action) {
        log.debug("[服务器端调试]登陆请求事件 dn={}, action: {}", thingDevice.getDeviceDn(), action);
        LoginAckMessage ack = new LoginAckMessage((byte) 0);
        return new ActionResult<>(0, ack);
    }

    /*
     * 心跳请求事件
     */
    private ActionResult<?> ping(ThingDevice thingDevice, PingEventAction<?> action) {
        log.debug("[服务器端调试]心跳请求事件 dn={}, action={}", thingDevice.getDeviceDn(), action);

        Charger charger = chargerData.findByDn(thingDevice.getDeviceDn());
        if(charger == null) {
            log.error("[服务器端调试]心跳请求事件异常, code={}, msg={}", ErrCode.CHARGER_NOT_FOUND.getKey(), ErrCode.CHARGER_NOT_FOUND.getValue());
            return ActionResult.builder().code(ErrCode.CHARGER_NOT_FOUND.getKey()).reason(ErrCode.CHARGER_NOT_FOUND.getValue()).build();
        }
        charger.setLastUpdateTime(System.currentTimeMillis());
        chargerData.save(charger);

        PingEventMessage message = (PingEventMessage) action.getData();
        PingAckMessage ack = new PingAckMessage(message.getGunNo(), (byte)0);
        return new ActionResult<>(0, ack);
    }

    /*
     * 计价模型验证事件
     */
    private ActionResult<?> priceVerify(ThingDevice thingDevice, PriceVerifyEventAction<?> action) {
        log.debug("[服务器端调试]计价模型验证事件 dn={}, action={}", thingDevice.getDeviceDn(), action);

        Charger charger = chargerData.findByDn(thingDevice.getDeviceDn());
        if(charger == null) {
            log.error("[服务器端调试]计价模型验证事件异常, code={}, msg={}", ErrCode.CHARGER_NOT_FOUND.getKey(), ErrCode.CHARGER_NOT_FOUND.getValue());
            return ActionResult.builder().code(ErrCode.CHARGER_NOT_FOUND.getKey()).reason(ErrCode.CHARGER_NOT_FOUND.getValue()).build();
        }

        Price price = priceData.findById(charger.getPriceId());
        if(price == null) {
            log.error("[服务器端调试]计价模型验证事件异常, code={}, msg={}", ErrCode.PRICE_NOT_FOUND.getKey(), ErrCode.PRICE_NOT_FOUND.getValue());
            return ActionResult.builder().code(ErrCode.PRICE_NOT_FOUND.getKey()).build();
        }
        PriceVerifyAckMessage ack = new PriceVerifyAckMessage();
        ack.setPriceNo(price.getNo());
        //ack.setResult((byte) (price.getNo() == priceNo ? 0 : 1));
        ack.setResult((byte) 1);

        return new ActionResult<>(0, ack);
    }

    /*
     * 计价模型请求事件
     */
    private ActionResult<?> priceRequest(ThingDevice thingDevice, PriceRequestEventAction<?> action) {
        log.debug("[服务器端调试]计价模型请求事件 dn={}, action={}", thingDevice.getDeviceDn(), action);

        Charger charger = chargerData.findByDn(thingDevice.getDeviceDn());
        if(charger == null) {
            log.error("[服务器端调试]计价模型请求事件异常 code={}, msg={}", ErrCode.CHARGER_NOT_FOUND.getKey(), ErrCode.CHARGER_NOT_FOUND.getValue());
            return ActionResult.builder().code(ErrCode.CHARGER_NOT_FOUND.getKey()).reason(ErrCode.CHARGER_NOT_FOUND.getValue()).build();
        }

        Price price = priceData.findById(charger.getPriceId());
        if(price == null) {
            log.error("[服务器端调试]计价模型请求事件异常 code={}, msg={}", ErrCode.PRICE_NOT_FOUND.getKey(), ErrCode.PRICE_NOT_FOUND.getValue());
            return ActionResult.builder().code(ErrCode.PRICE_NOT_FOUND.getKey()).reason(ErrCode.PRICE_NOT_FOUND.getValue()).build();
        }
        PriceRequestAckMessage message = new PriceRequestAckMessage(price.getNo(), price.getProperties());
        //log.debug("[服务器端调试] 计价模型请求处理回复 message={}", message);
        return new ActionResult<>(0, message);
    }

    /*
     * 数据上报
     */
    private ActionResult<?> dataReport(ThingDevice thingDevice, DataReportEventAction<?> action) {
        log.debug("[服务器端调试]数据上报事件 dn={}, action={}", thingDevice.getDeviceDn(), JsonUtils.toJsonString(action));

        DataReportEventMessage message = (DataReportEventMessage) action.getData();

        Charger charger = chargerData.findByDn(thingDevice.getDeviceDn());
        if(charger == null) {
            log.error("[服务器端调试] code={}, msg={}", ErrCode.CHARGER_NOT_FOUND.getKey(), ErrCode.CHARGER_NOT_FOUND.getValue());
            return ActionResult.builder().code(ErrCode.CHARGER_NOT_FOUND.getKey()).reason(ErrCode.CHARGER_NOT_FOUND.getValue()).build();
        }

        String tranId = message.getTranId();
        String gunNo = message.getGunNo();
        short status = message.getStatus();

        ChargerGun chargerGun = chargerGunData.findByChargerIdAndGunNo(charger.getId(), gunNo);
        if(chargerGun == null) {
            log.error("[服务器端调试]数据上报请求处理异常 {}", ErrCode.CHARGER_GUN_NOT_FOUND);
            return ActionResult.builder().code(ErrCode.CHARGER_GUN_NOT_FOUND.getKey()).build();
        }

        ChargerGunStateEnum chargerGunState = null;
        for(ChargerGunStateEnum state: ChargerGunStateEnum.values()) {
            if(state.getCode() == status) {
                chargerGunState = state;
            }
        }

        if(chargerGunState == null) {
            log.error("[服务器端调试]数据上报请求处理异常 {}", ErrCode.CHARGER_GUN_STATE_ERROR);
            return ActionResult.builder().code(ErrCode.CHARGER_GUN_STATE_ERROR.getKey()).build();
        }

        chargerGun.setState(chargerGunState);
        chargerGun.setBack((int) message.getBack());
        chargerGun.setSlot((int) message.getSlot());

        chargerGunData.save(chargerGun);

        //充电中查找订单
        if(chargerGunState == ChargerGunStateEnum.Charging) {
            Orders orders = ordersData.findByTranId(tranId);
            if(orders == null) {
                log.error("[服务器端调试]数据上报请求处理异常 {}", ErrCode.ORDER_NOT_FOUND);
                return ActionResult.builder().code(ErrCode.ORDER_NOT_FOUND.getKey()).build();
            }

            orders.setSoc(message.getSoc());
            orders.setChargeMinute(message.getChargeMinute());
            orders.setRemainMinute(message.getRemainMinute());
            orders.setTotalQty(message.getTotalQty());
            orders.setTotalQuantity(message.getTotalQuantity());
            orders.setTotalAmount(message.getTotalAmount());
            orders.setVoltage(message.getVoltage());
            orders.setCurrent(message.getCurrent());

            float power = message.getVoltage() * message.getCurrent()/1000;

            BigDecimal b = new BigDecimal(power);
            orders.setPower(b.setScale(2, RoundingMode.HALF_UP).floatValue());

            if(orders.getState().equals(OrderStateEnum.Pending)) {
                orders.setState(OrderStateEnum.Processing);
            }

            ordersData.save(orders);
        }

        return new ActionResult<>(0);
    }

    /*
     * 订单上报
     */
    private ActionResult<?> orderReport(ThingDevice thingDevice, OrderReportEventAction<?> action) {
        log.debug("[服务器端调试]订单上报事件 dn={}, action={}", thingDevice.getDeviceDn(), JsonUtils.toJsonString(action));

        OrderReportEventMessage message = (OrderReportEventMessage) action.getData();

        String tranId = message.getTranId();
        byte result = 0; //0成功,1失败
        OrderReportAckMessage ackMessage = new OrderReportAckMessage(tranId, result);

        Orders orders = ordersData.findByTranId(tranId);
        if(orders == null) {
            log.error("[服务器端调试]订单上报事件异常 {}", ErrCode.ORDER_NOT_FOUND);
            return new ActionResult<>(0, ackMessage);
        }

        Customer customer = customerData.findById(orders.getCustomerId());
        if(customer == null) {
            log.error("[服务器端调试]订单上报事件错误 {}", ErrCode.CUSTOMER_NOT_FOUND);
            return new ActionResult<>(0, ackMessage);
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            orders.setEndTime(sdf.parse(message.getEndTime()));
            orders.setTranTime(sdf.parse(message.getTranTime()));

            for(OrderTranTypeEnum typeEnum: OrderTranTypeEnum.values()) {
                if(typeEnum.getCode() == message.getTranType()) {
                    orders.setTranType(typeEnum);
                    break;
                }
            }

            orders.setSharpPrice(message.getSharpPrice());
            orders.setSharpQty(message.getSharpQty());
            orders.setSharpQuantity(message.getSharpQuantity());
            orders.setSharpAmount(message.getSharpAmount());

            orders.setPeakPrice(message.getPeakPrice());
            orders.setPeakQty(message.getPeakQty());
            orders.setPeakQuantity(message.getPeakQuantity());
            orders.setPeakAmount(message.getPeakAmount());

            orders.setFlatPrice(message.getFlatPrice());
            orders.setFlatQty(message.getFlatQty());
            orders.setFlatQuantity(message.getFlatQuantity());
            orders.setFlatAmount(message.getFlatAmount());

            orders.setValleyPrice(message.getValleyPrice());
            orders.setValleyQty(message.getValleyQty());
            orders.setValleyQuantity(message.getValleyQuantity());
            orders.setValleyAmount(message.getValleyAmount());

            orders.setQuantityStart(message.getQuantityStart());
            orders.setQuantityEnd(message.getQuantityEnd());

            orders.setTotalQty(message.getTotalQty());
            orders.setTotalQuantity(message.getTotalQuantity());
            orders.setTotalAmount(message.getTotalAmount());

            orders.setVin(message.getVin());

            orders.setStopReason(message.getStopReason());
            orders.setPhysicalCardNo(message.getPhysicalCardNo());

            //计算服务费和电费
            PriceProperties priceProperties = orders.getPriceProperties();
            List<PriceFee> priceFees = priceProperties.getFee();

            PriceTypeEnum priceTypeEnum = null;

            if(priceFees.size() == 1) {
                priceTypeEnum = PriceTypeEnum.Standard;
            }

            if(priceFees.size() == 4) {
                priceTypeEnum = PriceTypeEnum.Tou;
            }

            if(priceTypeEnum == null) {
                log.error("[服务器端调试]订单上报事件错误 {}", ErrCode.PRICE_PROPERTIES_EXCEPTION);
                throw new BizException(ErrCode.PRICE_PROPERTIES_EXCEPTION);
            }

            PriceFee sharpFee;
            PriceFee peakFee;
            PriceFee flatFee;
            PriceFee valleyFee;
            if(priceTypeEnum.equals(PriceTypeEnum.Standard)) {
                sharpFee = priceFees.get(0);
                peakFee = priceFees.get(0);
                flatFee = priceFees.get(0);
                valleyFee = priceFees.get(0);
            } else {
                sharpFee = priceFees.get(0);
                peakFee = priceFees.get(1);
                flatFee = priceFees.get(2);
                valleyFee = priceFees.get(3);
            }

            BigDecimal sharpQuantity = message.getSharpQuantity();
            BigDecimal sharpElecAmount = sharpQuantity.multiply(sharpFee.getElecFee());

            BigDecimal peakQuantity = message.getPeakQuantity();
            BigDecimal peakElecAmount = peakQuantity.multiply(peakFee.getElecFee());

            BigDecimal flatQuantity = message.getFlatQuantity();
            BigDecimal flatElecAmount = flatQuantity.multiply(flatFee.getElecFee());

            BigDecimal valleyQuantity = message.getValleyQuantity();
            BigDecimal valleyElecAmount = valleyQuantity.multiply(valleyFee.getElecFee());

            BigDecimal totalElecAmount = sharpElecAmount.add(peakElecAmount).add(flatElecAmount).add(valleyElecAmount).setScale(2, RoundingMode.HALF_UP);

            orders.setElecAmount(totalElecAmount);
            orders.setServiceAmount(orders.getTotalAmount().subtract(totalElecAmount).setScale(2, RoundingMode.HALF_UP));

            orders.setState(OrderStateEnum.Finished);

            ordersData.save(orders);

            //自动结算
            remoteOrderService.settle(orders.getId());

            return new ActionResult<>(0, ackMessage);

        } catch (Exception e) {
            log.error("[服务器端调试]订单上报事件异常 {}", e.getMessage());
            return new ActionResult<>(-1, e.getMessage());
        }
    }

    /*
     * 启动充电指令应答
     */
    private ActionResult<?> startChargeReply(ThingDevice thingDevice, StartChargeReplyAction<?> action) {
        log.debug("[服务器端调试]启动充电指令应答 dn={}, action={}", thingDevice.getDeviceDn(), JsonUtils.toJsonString(action));

        StartChargeReplyMessage message = (StartChargeReplyMessage) action.getData();

        String gunNo = message.getGunNo();

        ChargerDirectiveQueryBo queryBo = new ChargerDirectiveQueryBo();
        queryBo.setDn(thingDevice.getDeviceDn());
        queryBo.setGunNo(gunNo);
        queryBo.setDirective(ChargerDirectiveEnum.START_CHARGE_INVOKE.name());
        queryBo.setState(ChargerDirectiveStateEnum.Pending);

        ChargerDirective chargerDirective = chargerDirectiveData.findOne(queryBo);
        if(chargerDirective == null) {
            log.error("[服务器端调试]启动充电应答异常 {}", ErrCode.CHARGER_DIRECTIVE_NOT_FOUND);
            return ActionResult.builder().code(ErrCode.CHARGER_DIRECTIVE_NOT_FOUND.getKey()).build();
        }

        Orders orders = ordersData.findByTranId(chargerDirective.getRelateId());
        if(orders == null) {
            log.error("[服务器端调试]启动充电应答处理异常 {}", ErrCode.ORDER_NOT_FOUND);
            return ActionResult.builder().code(ErrCode.ORDER_NOT_FOUND.getKey()).build();
        }

        chargerDirective.setState(ChargerDirectiveStateEnum.Finished);

        /*
         * 0x01成功 0x00失败
         */
        if(message.getResult() == 1) {
            orders.setState(OrderStateEnum.Processing);
            chargerDirective.setResult(ChargerDirectiveResultEnum.Successful);
        } else {
            log.error("[服务器端调试]启动充电应答结果失败 [{}]", message.getReason());
            orders.setState(OrderStateEnum.Canceled);
            chargerDirective.setResult(ChargerDirectiveResultEnum.Fail);
            chargerDirective.setReason(String.valueOf(message.getReason()));
        }

        ordersData.save(orders);
        chargerDirectiveData.save(chargerDirective);

        return new ActionResult<>(0);
    }

    /*
     * 停止充电指令应答
     */
    private ActionResult<?> stopChargeReply(ThingDevice thingDevice, StopChargeReplyAction<?> action) {
        log.debug("[服务器端调试]停止充电指令应答 dn={}, action={}", thingDevice.getDeviceDn(), JsonUtils.toJsonString(action));

        StopChargeReplyMessage message = (StopChargeReplyMessage) action.getData();

        String chargerDn = message.getDeviceDn();
        String gunNo = message.getGunNo();

        ChargerDirectiveQueryBo queryBo = new ChargerDirectiveQueryBo();
        queryBo.setDn(chargerDn);
        queryBo.setGunNo(gunNo);
        queryBo.setDirective(ChargerDirectiveEnum.STOP_CHARGE_INVOKE.name());
        queryBo.setState(ChargerDirectiveStateEnum.Pending);

        ChargerDirective chargerDirective = chargerDirectiveData.findOne(queryBo);
        if(chargerDirective == null) {
            log.error("[服务器端调试]停止充电应答异常 {}", ErrCode.CHARGER_DIRECTIVE_NOT_FOUND);
            return ActionResult.builder().code(ErrCode.CHARGER_DIRECTIVE_NOT_FOUND.getKey()).build();
        }

        chargerDirective.setState(ChargerDirectiveStateEnum.Finished);
        /*
         * 0x01成功 0x00失败
         */
        if(message.getResult() == 1) {
            chargerDirective.setResult(ChargerDirectiveResultEnum.Successful);
            chargerDirectiveData.save(chargerDirective);
        } else {
            log.error("[服务器端调试]停止充电应答结果失败");
            chargerDirective.setResult(ChargerDirectiveResultEnum.Fail);
            chargerDirective.setReason(String.valueOf(message.getReason()));
            chargerDirectiveData.save(chargerDirective);
        }
        return new ActionResult<>(0);
    }

    /*
     * 余额更新指令应答
     */
    private ActionResult<?> balanceUpdateReply(ThingDevice thingDevice, BalanceUpdateReplyAction<?> action) {
        log.debug("[服务器端调试]余额更新指令应答 dn={}, action={}", thingDevice.getDeviceDn(), JsonUtils.toJsonString(action));

        BalanceUpdateReplyMessage message = (BalanceUpdateReplyMessage) action.getData();

        ChargerDirectiveQueryBo queryBo = new ChargerDirectiveQueryBo();
        queryBo.setDn(thingDevice.getDeviceDn());
        queryBo.setDirective(ChargerDirectiveEnum.BALANCE_UPDATE_INVOKE.name());
        queryBo.setState(ChargerDirectiveStateEnum.Pending);

        ChargerDirective chargerDirective = chargerDirectiveData.findOne(queryBo);
        if(chargerDirective == null) {
            log.error("[服务器端调试]余额更新指令应答异常 {}", ErrCode.CHARGER_DIRECTIVE_NOT_FOUND);
            return ActionResult.builder().code(ErrCode.CHARGER_DIRECTIVE_NOT_FOUND.getKey()).build();
        }

        chargerDirective.setState(ChargerDirectiveStateEnum.Finished);
        /*
          0x00-修改成功
          0x01-设备编号错误
          0x02-卡号错误
         */
        if(message.getResult() == 0) {
            chargerDirective.setResult(ChargerDirectiveResultEnum.Successful);
        } else {
            log.error("[服务器端调试]余额更新指令应答结果失败");
            chargerDirective.setResult(ChargerDirectiveResultEnum.Fail);
            chargerDirective.setReason(String.valueOf(message.getResult()));
        }
        chargerDirectiveData.save(chargerDirective);

        return new ActionResult<>(0);
    }

    /*
     * 参数配置指令应答
     */
    private ActionResult<?> paramConfigReply(ThingDevice thingDevice, ParamConfigReplyAction<?> action) {
        log.debug("[服务器端调试]参数配置指令应答 dn={}, action={}", thingDevice.getDeviceDn(), JsonUtils.toJsonString(action));

        ChargerDirectiveQueryBo queryBo = new ChargerDirectiveQueryBo();
        queryBo.setDn(thingDevice.getDeviceDn());
        queryBo.setDirective(ChargerDirectiveEnum.PARAM_CONFIG_INVOKE.name());
        queryBo.setState(ChargerDirectiveStateEnum.Pending);

        ChargerDirective chargerDirective = chargerDirectiveData.findOne(queryBo);
        if(chargerDirective == null) {
            log.error("[服务器端调试]参数配置指令应答异常 {}", ErrCode.CHARGER_DIRECTIVE_NOT_FOUND);
            return ActionResult.builder().code(ErrCode.CHARGER_DIRECTIVE_NOT_FOUND.getKey()).build();
        }

        ParamConfigReplyMessage message = (ParamConfigReplyMessage) action.getData();

        chargerDirective.setState(ChargerDirectiveStateEnum.Finished);

        /*
          0x00 失败 0x01 成功
         */
        if(message.getResult() == 1) {
            chargerDirective.setResult(ChargerDirectiveResultEnum.Successful);
        } else {
            log.error("[服务器端调试]参数配置指令应答结果失败");
            chargerDirective.setResult(ChargerDirectiveResultEnum.Fail);
        }
        chargerDirectiveData.save(chargerDirective);

        return new ActionResult<>(0);
    }

    /*
     * 校时指令应答
     */
    private ActionResult<?> timingConfigReply(ThingDevice thingDevice, TimingConfigReplyAction<?> action) {
        log.debug("[服务器端调试]校时指令应答 dn={}, action={}", thingDevice.getDeviceDn(), JsonUtils.toJsonString(action.getData()));

        ChargerDirectiveQueryBo queryBo = new ChargerDirectiveQueryBo();
        queryBo.setDn(thingDevice.getDeviceDn());
        queryBo.setDirective(ChargerDirectiveEnum.TIME_CONFIG_INVOKE.name());
        queryBo.setState(ChargerDirectiveStateEnum.Pending);

        ChargerDirective chargerDirective = chargerDirectiveData.findOne(queryBo);
        if(chargerDirective == null) {
            log.error("[服务器端调试]校时指令应答异常 {}", ErrCode.CHARGER_DIRECTIVE_NOT_FOUND);
            return ActionResult.builder().code(ErrCode.CHARGER_DIRECTIVE_NOT_FOUND.getKey()).build();
        }

        TimeingConfigReplyMessage message = (TimeingConfigReplyMessage) action.getData();

        chargerDirective.setState(ChargerDirectiveStateEnum.Finished);

        if(message.getNow() != null) {
            chargerDirective.setResult(ChargerDirectiveResultEnum.Successful);
        } else {
            log.error("[服务器端调试]校时指令应答结果失败");
            chargerDirective.setResult(ChargerDirectiveResultEnum.Fail);
        }
        chargerDirectiveData.save(chargerDirective);

        return new ActionResult<>(0);
    }

    /*
     * 计价模型指令应答
     */
    private ActionResult<?> priceConfigReply(ThingDevice thingDevice, PriceConfigReplyAction<?> action) {
        log.debug("[服务器端调试]计价模型指令应答 dn={}, action={}", thingDevice.getDeviceDn(), JsonUtils.toJsonString(action));

        ChargerDirectiveQueryBo queryBo = new ChargerDirectiveQueryBo();
        queryBo.setDn(thingDevice.getDeviceDn());
        queryBo.setDirective(ChargerDirectiveEnum.PRICE_CONFIG_INVOKE.name());
        queryBo.setState(ChargerDirectiveStateEnum.Pending);

        ChargerDirective chargerDirective = chargerDirectiveData.findOne(queryBo);
        if(chargerDirective == null) {
            log.error("[服务器端调试]计价模型指令应答异常 {}", ErrCode.CHARGER_DIRECTIVE_NOT_FOUND);
            return ActionResult.builder().code(ErrCode.CHARGER_DIRECTIVE_NOT_FOUND.getKey()).build();
        }

        PriceConfigReplyMessage message = (PriceConfigReplyMessage) action.getData();

        chargerDirective.setState(ChargerDirectiveStateEnum.Finished);

        /*
         * 0x00 失败 0x01 成功
         */
        if(message.getResult() == 1) {
            chargerDirective.setResult(ChargerDirectiveResultEnum.Successful);
        } else {
            log.error("[服务器端调试]计价模型指令应答结果失败");
            chargerDirective.setResult(ChargerDirectiveResultEnum.Fail);
        }
        chargerDirectiveData.save(chargerDirective);

        return new ActionResult<>(0);
    }

    /*
     * 重起指令应答
     */
    private ActionResult<?> rebootReply(ThingDevice thingDevice, RebootReplyAction<?> action) {
        log.debug("[服务器端调试]重起指令应答 dn={}, action={}", thingDevice.getDeviceDn(), JsonUtils.toJsonString(action));

        ChargerDirectiveQueryBo queryBo = new ChargerDirectiveQueryBo();
        queryBo.setDn(thingDevice.getDeviceDn());
        queryBo.setDirective(ChargerDirectiveEnum.REBOOT_INVOKE.name());
        queryBo.setState(ChargerDirectiveStateEnum.Pending);

        ChargerDirective chargerDirective = chargerDirectiveData.findOne(queryBo);
        if(chargerDirective == null) {
            log.error("[服务器端调试]重起指令应答异常 {}", ErrCode.CHARGER_DIRECTIVE_NOT_FOUND);
            return ActionResult.builder().code(ErrCode.CHARGER_DIRECTIVE_NOT_FOUND.getKey()).build();
        }

        RebootReplyMessage message = (RebootReplyMessage) action.getData();

        chargerDirective.setState(ChargerDirectiveStateEnum.Finished);

        /*
         * 0x00 失败 0x01 成功
         */
        if(message.getResult() == 1) {
            chargerDirective.setResult(ChargerDirectiveResultEnum.Successful);
        } else {
            log.error("[服务器端调试]重起指令应答结果失败");
            chargerDirective.setResult(ChargerDirectiveResultEnum.Fail);
        }
        chargerDirectiveData.save(chargerDirective);

        return new ActionResult<>(0);

    }

    /*
     * 二维码指令应答
     */
    private ActionResult<?> qrcodeConfigReply(ThingDevice thingDevice, QrcodeReplyAction<?> action) {
        log.debug("[服务器端调试]二维码指令应答 dn={}, action={}", thingDevice.getDeviceDn(), JsonUtils.toJsonString(action));

        ChargerDirectiveQueryBo queryBo = new ChargerDirectiveQueryBo();
        queryBo.setDn(thingDevice.getDeviceDn());
        queryBo.setDirective(ChargerDirectiveEnum.QRCODE_INVOKE.name());
        queryBo.setState(ChargerDirectiveStateEnum.Pending);

        ChargerDirective chargerDirective = chargerDirectiveData.findOne(queryBo);
        if(chargerDirective == null) {
            log.error("[服务器端调试]二维码指令应答异常 {}", ErrCode.CHARGER_DIRECTIVE_NOT_FOUND);
            return ActionResult.builder().code(ErrCode.CHARGER_DIRECTIVE_NOT_FOUND.getKey()).build();
        }

        QrcodeReplyMessage message = (QrcodeReplyMessage) action.getData();

        chargerDirective.setState(ChargerDirectiveStateEnum.Finished);

        //0x00：成功 0x01:失败
        if(message.getResult() == 0) {
            chargerDirective.setResult(ChargerDirectiveResultEnum.Successful);
        } else {
            log.error("[服务器端调试]二维码指令应答结果失败");
            chargerDirective.setResult(ChargerDirectiveResultEnum.Fail);
        }

        chargerDirectiveData.save(chargerDirective);

        return new ActionResult<>(0);
    }

    /*
     * 上下线消息
     */
    private ActionResult<?> deviceStateChange(ThingDevice thingDevice, StateChangeEventAction<?> action) {
        log.debug("[服务器端调试]设备上下线事件 dn={}, action={}", thingDevice.getDeviceDn(), JsonUtils.toJsonString(action));

        Charger charger = chargerData.findByDn(thingDevice.getDeviceDn());
        if(charger == null) {
            log.error("[服务器端调试]设备上下线事件异常 code={}, msg={}", ErrCode.CHARGER_NOT_FOUND.getKey(), ErrCode.CHARGER_NOT_FOUND.getValue());
            return ActionResult.builder().code(ErrCode.CHARGER_NOT_FOUND.getKey()).reason(ErrCode.CHARGER_NOT_FOUND.getValue()).build();
        }

        DeviceState state = action.getState();
        if (state == DeviceState.ONLINE) {
            charger.setOnline(OnlineStatusEnum.Online);
            charger.setOnlineTime(System.currentTimeMillis());
            charger.setLastUpdateTime(System.currentTimeMillis());
        } else {
            charger.setOnline(OnlineStatusEnum.Offline);
            charger.setOfflineTime(System.currentTimeMillis());
            charger.setLastUpdateTime(System.currentTimeMillis());

            ChargerGunQueryBo queryBo = new ChargerGunQueryBo();
            queryBo.setChargerId(charger.getId());
            List<ChargerGun> chargerGuns = chargerGunData.findList(queryBo);
            for(ChargerGun chargerGun: chargerGuns) {
                chargerGun.setState(ChargerGunStateEnum.Unknow);
                chargerGun.setBack(2);
                chargerGun.setSlot(0);
                chargerGunData.save(chargerGun);
            }
        }

        chargerData.save(charger);
        return new ActionResult<>(0);
    }

    private void publishMsg(ThingDevice thingDevice, IDeviceAction<?> action, ThingModelMessage message) {
        try {
            message.setId(UUID.randomUUID().toString());
            message.setMid(UniqueIdUtil.newRequestId());
            message.setDeviceDn(thingDevice.getDeviceDn());
            message.setProductKey(thingDevice.getProductKey());
            message.setDirective(action.getDirective().name());

            if (message.getOccurred() == null) {
                message.setOccurred(action.getTime());
            }
            if (message.getTime() == null) {
                message.setTime(System.currentTimeMillis());
            }
            if (message.getData() == null) {
                message.setData("");
            }

           // producer.publish(Constants.THING_MODEL_MESSAGE_TOPIC, message);
        } catch (Throwable e) {
            log.error("send thing model message error", e);
        }
    }

    @Override
    public ThingProduct getProduct(String pk) {
        try {
            Product product = productData.findByProductKey(pk);
            if (product == null) {
                return null;
            }
            return ThingProduct.builder()
                    .category(product.getCategory())
                    .productKey(product.getProductKey())
                    .productSecret(product.getProductSecret())
                    .name(product.getName())
                    .build();
        } catch (Throwable e) {
            log.error("get product error", e);
            return null;
        }
    }

    @Override
    public ThingDevice getDevice(String dn) {
        try {
            Charger charger = chargerData.findByDn(dn);
            if (charger == null) {
                return null;
            }
            return ThingDevice.builder()
                    .productKey(charger.getProductKey())
                    .productType(productType)
                    .deviceDn(charger.getDn())
                    .model(charger.getModel())
                    .secret(charger.getSecret())
                    .status(DeviceStatus.valueOf(charger.getStatus().name()))
                    .build();
        } catch (Throwable e) {
            log.error("get device error", e);
            return null;
        }
    }


}
