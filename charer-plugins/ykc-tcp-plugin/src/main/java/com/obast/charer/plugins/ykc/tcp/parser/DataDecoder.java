package com.obast.charer.plugins.ykc.tcp.parser;


import com.obast.charer.common.api.IMessage;
import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.plugin.core.thing.charger.ChargerStopCode;
import com.obast.charer.common.plugin.core.thing.charger.message.*;
import com.obast.charer.plugins.ykc.tcp.utils.BcdUtil;
import com.obast.charer.plugins.ykc.tcp.utils.ByteArrayUtil;
import com.obast.charer.plugins.ykc.tcp.utils.Cp56Time2aUtil;
import com.obast.charer.plugins.ykc.tcp.utils.MathUtil;
import io.vertx.core.buffer.Buffer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * 数据解码
 *
 * @author sjg
 */
@Slf4j
public class DataDecoder {

    public static DataPackage unpack(Buffer buffer) {

        //log.info("[消息解析]收到客户端完整报文[{}] ", Hex.encodeHexString(buffer.getBytes()));


        int len = Integer.valueOf(Hex.encodeHexString(buffer.getBytes(1,2)),16);

        //log.info("[消息解析]长度={}, 长度2={}", len, buffer.getBytes().length);

        short serial = ByteBuffer.wrap(buffer.getBytes(2,4)).getShort();

        //log.info("[消息解析]serial={}", serial);


        byte encrypt = buffer.getByte(4);

        //log.info("[消息解析]encrypt={}",encrypt);


        ByteBuffer codeBuffer = ByteBuffer.allocate(1);
        codeBuffer.put(buffer.getByte(5));


        short code = ByteArrayUtil.byteArray2Short_Little_Endian(codeBuffer.array());

        //log.info("[消息解析]code={}",code);

        byte[] payload = buffer.getBytes(6,len+2);


        String crc = Hex.encodeHexString(buffer.getBytes(len+2, len+4));


        //log.info("[消息解析] unpack 内容 长度={}, serial={}, encrypt={}, code={}, payload={}, crc={}   ", len, serial, encrypt, code, Hex.encodeHexString(payload), crc);

        String deviceName;

        switch(code) {
            case DataPackage.START_CHARGE_REPLY:
            case DataPackage.ORDER_REPORT_EVENT:
            case DataPackage.DATA_REPORT_EVENT:
                deviceName = BcdUtil.bcdToDecimalString(buffer.getBytes(22,29));
                break;
            default:
                deviceName = BcdUtil.bcdToDecimalString(buffer.getBytes(6,13));
                break;
        }

        return new DataPackage(deviceName, code, serial, encrypt, payload, buffer.getBytes(), crc);
    }

    public static IMessage decode(short code, byte[] payload) {
        IMessage message;
        switch (code) {
            case DataPackage.LOGIN_EVENT:
                message = parseLoginMessage(payload);
                break;

            case DataPackage.PING_EVENT:
                message = parsePingMessage(payload);
                break;

            case DataPackage.PRICE_VERIFY_EVENT:
                message = parsePriceVerifyMessage(payload);
                break;

            case DataPackage.PRICE_REQUEST_EVENT:
                message = parsePriceRequestMessage(payload);
                break;

            case DataPackage.DATA_REPORT_EVENT:
                message = parseDataReportMessage(payload);
                break;

            case DataPackage.ORDER_REPORT_EVENT:
                message = parseOrderReportMessage(payload);
                break;

            case DataPackage.START_CHARGE_REPLY:
                message = parseStartChargeReplyMessage(payload);
                break;

            case DataPackage.STOP_CHARGE_REPLY:
                message = parseStopChargeReplyMessage(payload);
                break;

            case DataPackage.BALANCE_UPDATE_REPLY:
                message = parseBalanceUpdateReplyMessage(payload);
                break;

            case DataPackage.PARAM_CONFIG_REPLY:
                message = parseParamConfigReplyMessage(payload);
                break;

            case DataPackage.TIMEING_CONFIG_REPLY:
                message = parseTimeingConfigReplyMessage(payload);
                break;

            case DataPackage.PRICE_CONFIG_REPLY:
                message = parsePriceConfigReplyMessage(payload);
                break;

            case DataPackage.REBOOT_REPLY:
                message = parseRebootReplyMessage(payload);
                break;
            case DataPackage.QRCODE_REPLY:
                message = parseQrcodeReplyMessage(payload);
                break;

            default:
                throw new BizException(ErrCode.PLUGIN_UNSUPPORTED_DIRECTIVE__EXCEPTION);
        }
        return message;
    }

    /**
     * 解析登陆消息
     */
    static LoginEventMessage parseLoginMessage(byte[] payload) {
        //log.info("[消息解析]解析登陆消息 [{}] ", Hex.encodeHexString(payload));
        short chargerType = Short.parseShort(Hex.encodeHexString(Arrays.copyOfRange(payload, 7, 8)), 16);
        short gunQty = Short.parseShort(Hex.encodeHexString(Arrays.copyOfRange(payload, 8, 9)), 16);
        short protocolVer = Short.parseShort(Hex.encodeHexString(Arrays.copyOfRange(payload, 9, 10)), 16);

        String firmwareVer = Hex.encodeHexString(Arrays.copyOfRange(payload, 11, 19));
        short net = Short.parseShort(Hex.encodeHexString(Arrays.copyOfRange(payload, 19, 20)), 16);
        String sim = BcdUtil.bcdToDecimalString(Arrays.copyOfRange(payload, 20, 30));
        short telcom = Short.parseShort(Hex.encodeHexString(Arrays.copyOfRange(payload, 30, 31)), 16);
        return new LoginEventMessage(chargerType, gunQty, protocolVer, firmwareVer, net, sim, telcom);
    }

    /**
     * 解析心跳消息
     */
    static PingEventMessage parsePingMessage(byte[] payload) {
        //log.info("[消息解析]解析心跳消息 [{}] ", Hex.encodeHexString(payload));

        String gunNo = BcdUtil.bcdToDecimalString(Arrays.copyOfRange(payload, 7, 8));
        gunNo = StringUtils.leftPad(gunNo,2,"0");
        short gunStatus = Short.parseShort(Hex.encodeHexString(Arrays.copyOfRange(payload, 8, 9)), 16);
        return new PingEventMessage(gunNo, gunStatus);
    }

    /**
     * 解析计费模型验证消息
     */
    static PriceVerifyEventMessage parsePriceVerifyMessage(byte[] payload) {
        //log.info("[消息解析]解析计费模型验证消息 [{}] ", Hex.encodeHexString(payload));
        short priceNo = Short.parseShort(BcdUtil.bcdToDecimalString(Arrays.copyOfRange(payload, 7, 9)));
        return new PriceVerifyEventMessage(priceNo);
    }

    /**
     * 解析计费模型请求消息
     */
    static PriceRequestEventMessage parsePriceRequestMessage(byte[] payload) {
        //log.info("[消息解析]解析计费模型请求消息 [{}] ", Hex.encodeHexString(payload));
        return new PriceRequestEventMessage();
    }

    /**
     * 解析数据上报消息
     */
    static DataReportEventMessage parseDataReportMessage(byte[] payload) {
        //log.info("[消息解析]解析数据上报消息 [{}] ", Hex.encodeHexString(payload));

        DataReportEventMessage message = new DataReportEventMessage();

        String transId = BcdUtil.bcdToDecimalString(Arrays.copyOfRange(payload, 0, 16)).substring(0,20);;
        message.setTranId(transId);

        String gunNo = BcdUtil.bcdToDecimalString(Arrays.copyOfRange(payload, 23, 24));
        gunNo = StringUtils.leftPad(gunNo,2,"0");
        message.setGunNo(gunNo);

        short status = Short.parseShort(Hex.encodeHexString(Arrays.copyOfRange(payload, 24, 25)), 16);
        message.setStatus(status);

        short back = Short.parseShort(Hex.encodeHexString(Arrays.copyOfRange(payload, 25, 26)), 16);
        message.setBack(back);

        short slot = Short.parseShort(Hex.encodeHexString(Arrays.copyOfRange(payload, 26, 27)), 16);
        message.setSlot(slot);

        BigDecimal voltage = MathUtil.convertByteArray2Voltage(Arrays.copyOfRange(payload, 27, 29));
        message.setVoltage(voltage.floatValue());

        BigDecimal current = MathUtil.convertByteArray2Current(Arrays.copyOfRange(payload, 29, 31));
        message.setCurrent(current.floatValue());

//        short gunLineTemp = Arrays.copyOfRange(payload, 31, 32)[0];
//        message.setGunLineTemp(gunLineTemp);
//
//        short gunLineNo = Short.parseShort(Hex.encodeHexString(Arrays.copyOfRange(payload, 32, 40)), 16);
//        message.setGunLineNo(gunLineNo);

        short soc = Short.parseShort(Hex.encodeHexString(Arrays.copyOfRange(payload, 40, 41)), 16);
        message.setSoc(soc);

        short batteryHighestTemp = Short.parseShort(Hex.encodeHexString(Arrays.copyOfRange(payload, 41, 42)), 16);
        message.setBatteryHighestTemp(batteryHighestTemp);

        //已充电分钟数
        BigDecimal chargeMinute = MathUtil.convertByteArray2Minute(Arrays.copyOfRange(payload, 42, 44));;
        message.setChargeMinute(chargeMinute.shortValue());

        //剩余分钟数
        BigDecimal remainMinute = MathUtil.convertByteArray2Minute(Arrays.copyOfRange(payload, 44, 46));
        message.setRemainMinute(remainMinute.shortValue());

        BigDecimal totalQty = MathUtil.convertByteArray2Quantity(Arrays.copyOfRange(payload, 46, 50));
        message.setTotalQty(totalQty);

        BigDecimal totalQuantity = MathUtil.convertByteArray2Quantity(Arrays.copyOfRange(payload, 50, 54));
        message.setTotalQuantity(totalQuantity);

        BigDecimal totalAmount = MathUtil.convertByteArray2Amount(Arrays.copyOfRange(payload, 54, 58));
        message.setTotalAmount(totalAmount);

        short fail = Short.parseShort(Hex.encodeHexString(Arrays.copyOfRange(payload, 58, 60)), 16);
        message.setFail(fail);

        return message;
    }

    /**
     * 解析订单上报消息
     */
    static OrderReportEventMessage parseOrderReportMessage(byte[] payload) {
        log.info("[消息解析]解析订单上报消息 [{}] ", Hex.encodeHexString(payload));

        OrderReportEventMessage message = new OrderReportEventMessage();
        try {
            String tranId = BcdUtil.bcdToDecimalString(Arrays.copyOfRange(payload, 0, 16)).substring(0,20);;
            message.setTranId(tranId);

            //String deviceName = BcdUtil.bcdToDecimalString(Arrays.copyOfRange(payload, 16, 23));
            String gunNo = BcdUtil.bcdToDecimalString(Arrays.copyOfRange(payload, 23, 24));
            gunNo = StringUtils.leftPad(gunNo,2,"0");
            message.setGunNo(gunNo);

            String startTime = Cp56Time2aUtil.getTimeByCp56Time2a(Arrays.copyOfRange(payload, 24, 31));
            message.setStartTime(startTime);

            String endTime = Cp56Time2aUtil.getTimeByCp56Time2a(Arrays.copyOfRange(payload, 31, 38));
            message.setEndTime(endTime);

            BigDecimal sharpPrice = MathUtil.convertByteArray2Price(Arrays.copyOfRange(payload, 38, 42));
            message.setSharpPrice(sharpPrice);

            BigDecimal sharpQty = MathUtil.convertByteArray2Quantity(Arrays.copyOfRange(payload, 42, 46));
            message.setSharpQty(sharpQty);

            BigDecimal sharpQuantity = MathUtil.convertByteArray2Quantity(Arrays.copyOfRange(payload, 46, 50));
            message.setSharpQuantity(sharpQuantity);

            BigDecimal sharpAmount = MathUtil.convertByteArray2Amount(Arrays.copyOfRange(payload, 50, 54));
            message.setSharpAmount(sharpAmount);

            BigDecimal peakPrice = MathUtil.convertByteArray2Price(Arrays.copyOfRange(payload, 54, 58));
            message.setPeakPrice(peakPrice);

            BigDecimal peakQty = MathUtil.convertByteArray2Quantity(Arrays.copyOfRange(payload, 58, 62));
            message.setPeakQty(peakQty);

            BigDecimal peakQuantity = MathUtil.convertByteArray2Quantity(Arrays.copyOfRange(payload, 62, 66));
            message.setPeakQuantity(peakQuantity);

            BigDecimal peakAmount = MathUtil.convertByteArray2Amount(Arrays.copyOfRange(payload, 66, 70));
            message.setPeakAmount(peakAmount);

            BigDecimal flatPrice =  MathUtil.convertByteArray2Price(Arrays.copyOfRange(payload, 70, 74));
            message.setFlatPrice(flatPrice);

            BigDecimal flatQty = MathUtil.convertByteArray2Quantity(Arrays.copyOfRange(payload, 74, 78));
            message.setFlatQty(flatQty);

            BigDecimal flatQuantity = MathUtil.convertByteArray2Quantity(Arrays.copyOfRange(payload, 78, 82));
            message.setFlatQuantity(flatQuantity);

            BigDecimal flatAmount = MathUtil.convertByteArray2Amount(Arrays.copyOfRange(payload, 82, 86));
            message.setFlatAmount(flatAmount);

            BigDecimal valleyPrice = MathUtil.convertByteArray2Price(Arrays.copyOfRange(payload, 86, 90));
            message.setValleyPrice(valleyPrice);

            BigDecimal valleyQty = MathUtil.convertByteArray2Quantity(Arrays.copyOfRange(payload, 90, 94));
            message.setValleyQty(valleyQty);

            BigDecimal valleyQuantity = MathUtil.convertByteArray2Quantity(Arrays.copyOfRange(payload, 94, 98));
            message.setValleyQuantity(valleyQuantity);

            BigDecimal valleyAmount = MathUtil.convertByteArray2Amount(Arrays.copyOfRange(payload, 98, 102));
            message.setValleyAmount(valleyAmount);

            BigDecimal quantityStart = MathUtil.convertByteArray2Quantity(Arrays.copyOfRange(payload, 102, 107));
            message.setQuantityStart(quantityStart);

            BigDecimal quantityEnd = MathUtil.convertByteArray2Quantity(Arrays.copyOfRange(payload, 107, 112));
            message.setQuantityEnd(quantityEnd);

            BigDecimal totalQty = MathUtil.convertByteArray2Quantity(Arrays.copyOfRange(payload, 112, 116));
            message.setTotalQty(totalQty);

            BigDecimal totalQuantity = MathUtil.convertByteArray2Quantity(Arrays.copyOfRange(payload, 116, 120));
            message.setTotalQuantity(totalQuantity);

            BigDecimal totalAmount = MathUtil.convertByteArray2Amount(Arrays.copyOfRange(payload, 120, 124));
            message.setTotalAmount(totalAmount);

            String vin = Hex.encodeHexString(Arrays.copyOfRange(payload, 124, 141));
            message.setVin(vin);

            short tranType = Short.parseShort(Hex.encodeHexString(Arrays.copyOfRange(payload, 141, 142)), 16);
            message.setTranType(tranType);

            String tranTime = Cp56Time2aUtil.getTimeByCp56Time2a(Arrays.copyOfRange(payload, 142, 149));
            message.setTranTime(tranTime);

            String stopReason = Integer.toHexString(Short.parseShort(Hex.encodeHexString(Arrays.copyOfRange(payload, 149, 150)), 16)).toLowerCase();

            ChargerStopCode stopEnum = ChargerStopCode.fromCode(stopReason);
            if(stopEnum != null) {
                message.setStopReason(stopEnum.name());
            } else {
                message.setStopReason(stopReason);
            }

            //log.error("[消息解析]停止原因 [{}] ", Hex.encodeHexString(Arrays.copyOfRange(payload, 149, 150)));

            String physicalCardNo = Hex.encodeHexString(Arrays.copyOfRange(payload, 150, 158));
            message.setPhysicalCardNo(physicalCardNo);
            return message;
        } catch (Exception e) {
            //e.printStackTrace();
            log.error("[消息解析]解析订单上报消息失败 [{}] ",e.getCause().getMessage());
            return null;
        }
    }

    /**
     * 解析启动充电回复消息
     */
    static StartChargeReplyMessage parseStartChargeReplyMessage(byte[] payload) {
        log.info("[消息解析]解析启动充电回复消息 [{}] ", Hex.encodeHexString(payload));
        String tranId = BcdUtil.bcdToDecimalString(Arrays.copyOfRange(payload, 0, 16)).substring(0,20);
        String deviceName = BcdUtil.bcdToDecimalString(Arrays.copyOfRange(payload, 16, 23));

        String gunNo = BcdUtil.bcdToDecimalString(Arrays.copyOfRange(payload, 23, 24));
        gunNo = StringUtils.leftPad(gunNo,2,"0");
        short result = Short.parseShort(Hex.encodeHexString(Arrays.copyOfRange(payload, 24, 25)), 16);
        short reason = Short.parseShort(Hex.encodeHexString(Arrays.copyOfRange(payload, 25, 26)), 16);
        return new StartChargeReplyMessage(tranId, deviceName, gunNo, result, reason);
    }

    /**
     * 解析停止充电回复消息
     */
    static StopChargeReplyMessage parseStopChargeReplyMessage(byte[] payload) {
        //log.info("[消息解析]解析停止充电回复消息 [{}] ", Hex.encodeHexString(payload));
        String deviceName = BcdUtil.bcdToDecimalString(Arrays.copyOfRange(payload, 0, 7));

        String gunNo = BcdUtil.bcdToDecimalString(Arrays.copyOfRange(payload, 7, 8));
        gunNo = StringUtils.leftPad(gunNo,2,"0");
        short result = Short.parseShort(Hex.encodeHexString(Arrays.copyOfRange(payload, 8, 9)), 16);
        short reason = Short.parseShort(Hex.encodeHexString(Arrays.copyOfRange(payload, 9, 10)), 16);
        return new StopChargeReplyMessage(deviceName, gunNo, result, reason);
    }


    /**
     * 解析余额更新回复消息
     */
    static BalanceUpdateReplyMessage parseBalanceUpdateReplyMessage(byte[] payload) {
        //log.info("[消息解析]解析余额更新回复消息 [{}] ", Hex.encodeHexString(payload));
        String physicalCardNo = BcdUtil.bcdToDecimalString(Arrays.copyOfRange(payload, 7, 15));
        short result = Short.parseShort(Hex.encodeHexString(Arrays.copyOfRange(payload, 15, 16)), 16);
        return new BalanceUpdateReplyMessage(physicalCardNo, result);
    }

    /**
     * 解析参数设置回复消息
     */
    static ParamConfigReplyMessage parseParamConfigReplyMessage(byte[] payload) {
        //log.info("[消息解析]解析参数设置回复消息 [{}] ", Hex.encodeHexString(payload));
        short result = Short.parseShort(Hex.encodeHexString(Arrays.copyOfRange(payload, 7, 8)), 16);
        return new ParamConfigReplyMessage(result);
    }

    /**
     * 解析对时设置回复消息
     */
    static TimeingConfigReplyMessage parseTimeingConfigReplyMessage(byte[] payload) {
        //log.info("[消息解析]解析对时设置回复消息 [{}] ", Hex.encodeHexString(payload));
        String now = Cp56Time2aUtil.getTimeByCp56Time2a(Arrays.copyOfRange(payload, 7, 14));
        return new TimeingConfigReplyMessage(now);
    }

    /**
     * 解析计价模型设置回复消息
     */
    static PriceConfigReplyMessage parsePriceConfigReplyMessage(byte[] payload) {
        //log.info("[消息解析]解析计价模型设置回复消息 [{}] ", Hex.encodeHexString(payload));
        short result = Short.parseShort(Hex.encodeHexString(Arrays.copyOfRange(payload, 7, 8)), 16);
        return new PriceConfigReplyMessage(result);
    }

    /**
     * 解析重起回复消息
     */
    static RebootReplyMessage parseRebootReplyMessage(byte[] payload) {
        //log.info("[消息解析]解析计价模型设置回复消息 [{}] ", Hex.encodeHexString(payload));
        short result = Short.parseShort(Hex.encodeHexString(Arrays.copyOfRange(payload, 7, 8)), 16);
        return new RebootReplyMessage(result);
    }

    /**
     * 解析下发二维码回复消息
     */
    static QrcodeReplyMessage parseQrcodeReplyMessage(byte[] payload) {
        //log.info("[消息解析]解析计价模型设置回复消息 [{}] ", Hex.encodeHexString(payload));
        short result = Short.parseShort(Hex.encodeHexString(Arrays.copyOfRange(payload, 7, 8)), 16);
        return new QrcodeReplyMessage(result);
    }
}
