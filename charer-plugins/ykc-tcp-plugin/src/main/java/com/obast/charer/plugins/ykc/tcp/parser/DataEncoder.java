package com.obast.charer.plugins.ykc.tcp.parser;


import com.obast.charer.common.enums.ErrCode;
import com.obast.charer.common.exception.BizException;
import com.obast.charer.common.model.dto.PriceFee;
import com.obast.charer.common.model.dto.PricePeriod;
import com.obast.charer.common.plugin.core.thing.charger.ChargerDirectiveEnum;
import com.obast.charer.common.plugin.core.thing.charger.message.*;
import com.obast.charer.plugins.ykc.tcp.utils.BcdUtil;
import com.obast.charer.plugins.ykc.tcp.utils.Cp56Time2aUtil;
import com.obast.charer.plugins.ykc.tcp.utils.CrcUtil;
import com.obast.charer.plugins.ykc.tcp.utils.MathUtil;
import io.vertx.core.buffer.Buffer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static com.obast.charer.plugins.ykc.tcp.parser.DataPackage.START_CHARGE_INVOKE;

/**
 * 数据编码
 *
 * @author sjg
 */
@Slf4j
public class DataEncoder {

    public static DataPackage builder(String deviceDn, short serial, ChargerDirectiveEnum directive, Object message) {

        short encrypt = 0;

        short code;
        byte[] data;

        switch (directive) {
            case START_CHARGE_INVOKE:
                data = packStartChargeMessage(deviceDn, (StartChargeInvokeMessage) message);
                code = START_CHARGE_INVOKE;
                break;
            case STOP_CHARGE_INVOKE:
                data = packStopChargeMessage(deviceDn, (StopChargeInvokeMessage) message);
                code = DataPackage.STOP_CHARGE_INVOKE;
                break;
            case BALANCE_UPDATE_INVOKE:
                data = packBalanceUpdateMessage(deviceDn, (BalanceUpdateInvokeMessage) message);
                code = DataPackage.BALANCE_UPDATE_INVOKE;
                break;
            case PARAM_CONFIG_INVOKE:
                data = packParamConfigMessage(deviceDn, (ParamConfigInvokeMessage) message);
                code = DataPackage.PARAM_CONFIG_INVOKE;
                break;
            case TIME_CONFIG_INVOKE:
                data = packTimingConfigMessage(deviceDn, (TimingConfigInvokeMessage) message);
                code = DataPackage.TIMEING_CONFIG_INVOKE;
                break;
            case PRICE_CONFIG_INVOKE:
                data = packPriceConfigMessage(deviceDn, (PriceConfigInvokeMessage) message);
                code = DataPackage.PRICE_CONFIG_INVOKE;
                break;
            case REBOOT_INVOKE:
                data = packRebootMessage(deviceDn, (RebootInvokeMessage) message);
                code = DataPackage.REBOOT_INVOKE;
                break;
            case QRCODE_INVOKE:
                data = packQrcodeMessage(deviceDn, (QrcodeInvokeMessage) message);
                code = DataPackage.QRCODE_INVOKE;
                break;

            case PRICE_VERIFY_ACK:
                data = packPriceVerifyAckMessage(deviceDn, (PriceVerifyAckMessage) message);
                code = DataPackage.PRICE_VERIFY_ACK;
                break;

            case PRICE_REQUEST_ACK:
                data = packPriceRequestAckMessage(deviceDn, (PriceRequestAckMessage) message);
                code = DataPackage.PRICE_REQUEST_ACK;
                break;

            case LOGIN_ACK:
                data = packLoginAckMessage(deviceDn, (LoginAckMessage) message);
                code = DataPackage.LOGIN_ACK;
                break;

            case PING_ACK:
                data = packPingAckMessage(deviceDn, (PingAckMessage) message);
                code = DataPackage.PING_ACK;
                break;

            case ORDER_REPORT_ACK:
                data = packOrderReportAckMessage((OrderReportAckMessage) message);
                code = DataPackage.ORDER_REPORT_ACK;
                break;




            default:
                throw new BizException(ErrCode.INVOKE_UNSUPPORTED_EXCEPTION);
        }

        return DataPackage.builder()
                .deviceDn(deviceDn)
                .code(code)
                .serial(serial)
                .encrypt(encrypt)
                .payload(data)
                .build();
    }

    public static byte[] packStartChargeMessage(String deviceDn, StartChargeInvokeMessage message) {

        Buffer buffer = Buffer.buffer();

        //交易流水号s
        buffer.appendBytes(BcdUtil.decimalStringToBcd(processTranId(message.getTranId())));
        //桩编号
        buffer.appendBytes(BcdUtil.decimalStringToBcd(processDeviceName(deviceDn)));
        //枪号
        buffer.appendByte((byte)Integer.parseInt(message.getGunNo()));
        //逻辑卡号
        buffer.appendBytes(BcdUtil.decimalStringToBcd(processPhysicalCardNo(message.getLogicalCardNo())));
        //物理卡号
        buffer.appendBytes(BcdUtil.decimalStringToBcd(processPhysicalCardNo(message.getPhysicalCardNo())));

        //账户余额
        ByteBuffer balanceBuffer = ByteBuffer.allocate(4);
        balanceBuffer.put(MathUtil.convertNumber2ByteArray(message.getBalance(), 2));
        buffer.appendBytes(balanceBuffer.array());

        return buffer.getBytes();
    }

    public static byte[] packStopChargeMessage(String deviceDn, StopChargeInvokeMessage message) {
        Buffer buffer = Buffer.buffer();
        //桩编号
        buffer.appendBytes(BcdUtil.decimalStringToBcd(processDeviceName(deviceDn)));
        //枪号
        buffer.appendByte((byte)Integer.parseInt(message.getGunNo()));
        return buffer.getBytes();
    }

    public static byte[] packBalanceUpdateMessage(String deviceDn, BalanceUpdateInvokeMessage message) {
        Buffer buffer = Buffer.buffer();
        //桩编号
        buffer.appendBytes(BcdUtil.decimalStringToBcd(processDeviceName(deviceDn)));
        //枪号
        buffer.appendByte((byte)Integer.parseInt(message.getGunNo()));
        //物理卡号
        buffer.appendBytes(BcdUtil.decimalStringToBcd(processPhysicalCardNo(message.getPhysicalCardNo())));
        //账户余额

        ByteBuffer balance = ByteBuffer.allocate(4);
        balance.put(MathUtil.convertNumber2ByteArray(message.getBalance(), 2));
        buffer.appendBytes(balance.array());

        //buffer.appendBytes(ByteArrayUtil.float2ByteArray_Little_Endian(message.getBalance().floatValue()));
        return buffer.getBytes();
    }

    public static byte[] packParamConfigMessage(String deviceDn, ParamConfigInvokeMessage message) {
        Buffer buffer = Buffer.buffer();
        //桩编号
        buffer.appendBytes(BcdUtil.decimalStringToBcd(processDeviceName(deviceDn)));
        //是否请允许工作
        buffer.appendShort(message.getStatus());
        //最大功率
        buffer.appendShort(message.getMaxPower());
        return buffer.getBytes();
    }

    public static byte[] packTimingConfigMessage(String deviceDn, TimingConfigInvokeMessage message) {

        Buffer buffer = Buffer.buffer();
        //桩编号
        buffer.appendBytes(BcdUtil.decimalStringToBcd(processDeviceName(deviceDn)));
        //时间
        buffer.appendBytes(Cp56Time2aUtil.toByteCp56Time2a(message.getTime()));
        return buffer.getBytes();
    }

    public static byte[] packPriceConfigMessage(String deviceDn, PriceConfigInvokeMessage message) {
        Buffer buffer = Buffer.buffer();

        //桩编号
        byte[] deviceDnByte = BcdUtil.decimalStringToBcd(processDeviceName(deviceDn));
        buffer.appendBytes(deviceDnByte);

        //计费模型编号
        ByteBuffer priceNoBuffer = ByteBuffer.allocate(2);
        priceNoBuffer.putShort(message.getNo());
        buffer.appendBytes(priceNoBuffer.array());

        //费
        for(PriceFee fee: message.getPriceProperties().getFee()) {
            //尖峰平谷电费费率
            ByteBuffer elecFeeBuffer = ByteBuffer.allocate(4);
            elecFeeBuffer.put(MathUtil.convertPrice2ByteArray(fee.getElecFee()));
            buffer.appendBytes(elecFeeBuffer.array());

            //尖峰平谷服务费费率
            ByteBuffer serviceFeeBuffer = ByteBuffer.allocate(4);
            serviceFeeBuffer.put(MathUtil.convertPrice2ByteArray(fee.getServiceFee()));
            buffer.appendBytes(serviceFeeBuffer.array());
        }

        //计损比例
        ByteBuffer lossRateBuffer = ByteBuffer.allocate(1);
        lossRateBuffer.put((byte) 0);
        buffer.appendBytes(lossRateBuffer.array());

        //周期
        for(PricePeriod interval: message.getPriceProperties().getPeriod()) {
            ByteBuffer intervalBuffer = ByteBuffer.allocate(1);
            intervalBuffer.put((byte)interval.getIndex().intValue());
            buffer.appendBytes(intervalBuffer.array());
        }

        return buffer.getBytes();
    }

    public static byte[] packRebootMessage(String deviceDn, RebootInvokeMessage message) {
        Buffer buffer = Buffer.buffer();
        //桩编号
        buffer.appendBytes(BcdUtil.decimalStringToBcd(processDeviceName(deviceDn)));
        buffer.appendShort(message.getExec());
        return buffer.getBytes();
    }

    public static byte[] packQrcodeMessage(String deviceDn, QrcodeInvokeMessage message) {
        Buffer buffer = Buffer.buffer();

        byte[] prefixByte = message.getPrefix().getBytes(StandardCharsets.US_ASCII);

        //桩编号
        buffer.appendBytes(BcdUtil.decimalStringToBcd(processDeviceName(deviceDn)));
        buffer.appendByte((byte) 1);
        buffer.appendByte((byte) prefixByte.length);
        buffer.appendBytes(prefixByte);
        return buffer.getBytes();
    }

    public static byte[] packPriceVerifyAckMessage(String deviceDn, PriceVerifyAckMessage message) {
        Buffer buffer = Buffer.buffer();

        //桩编号
        byte[] deviceDnByte = BcdUtil.decimalStringToBcd(processDeviceName(deviceDn));
        buffer.appendBytes(deviceDnByte);

        //计费模型编号
        ByteBuffer priceNoBuffer = ByteBuffer.allocate(2);
        priceNoBuffer.putShort(message.getPriceNo());
        buffer.appendBytes(priceNoBuffer.array());

        //结果
        ByteBuffer resultBuffer = ByteBuffer.allocate(1);
        resultBuffer.put(message.getResult());
        buffer.appendBytes(resultBuffer.array());

        return buffer.getBytes();
    }

    public static byte[] packPriceRequestAckMessage(String deviceDn, PriceRequestAckMessage message) {
        Buffer buffer = Buffer.buffer();

        //桩编号
        byte[] deviceDnByte = BcdUtil.decimalStringToBcd(processDeviceName(deviceDn));
        buffer.appendBytes(deviceDnByte);

        //计费模型编号
        ByteBuffer priceNoBuffer = ByteBuffer.allocate(2);
        priceNoBuffer.putShort(message.getNo());
        buffer.appendBytes(priceNoBuffer.array());

        //费
        for(PriceFee fee: message.getPriceProperties().getFee()) {
            //尖峰平谷电费费率
            ByteBuffer elecFeeBuffer = ByteBuffer.allocate(4);
            elecFeeBuffer.put(MathUtil.convertPrice2ByteArray(fee.getElecFee()));
            buffer.appendBytes(elecFeeBuffer.array());

            //尖峰平谷服务费费率
            ByteBuffer serviceFeeBuffer = ByteBuffer.allocate(4);
            serviceFeeBuffer.put(MathUtil.convertPrice2ByteArray(fee.getServiceFee()));
            buffer.appendBytes(serviceFeeBuffer.array());
        }

        //计损比例
        ByteBuffer lossRateBuffer = ByteBuffer.allocate(1);
        lossRateBuffer.put((byte) 0);
        buffer.appendBytes(lossRateBuffer.array());

        //周期
        for(PricePeriod interval: message.getPriceProperties().getPeriod()) {
            ByteBuffer intervalBuffer = ByteBuffer.allocate(1);
            intervalBuffer.put((byte)interval.getIndex().intValue());
            buffer.appendBytes(intervalBuffer.array());
        }

        return buffer.getBytes();
    }

    public static byte[] packLoginAckMessage(String deviceDn, LoginAckMessage message) {
        Buffer buffer = Buffer.buffer();

        //桩编号
        byte[] deviceDnByte = BcdUtil.decimalStringToBcd(processDeviceName(deviceDn));
        buffer.appendBytes(deviceDnByte);

        //登陆结果
        ByteBuffer resultBuffer = ByteBuffer.allocate(1);
        resultBuffer.put(message.getResult());
        buffer.appendBytes(resultBuffer.array());

        return buffer.getBytes();
    }

    public static byte[] packPingAckMessage(String deviceDn, PingAckMessage message) {
        Buffer buffer = Buffer.buffer();

        //桩编号
        byte[] deviceDnByte = BcdUtil.decimalStringToBcd(processDeviceName(deviceDn));
        buffer.appendBytes(deviceDnByte);

        //枪号
        ByteBuffer gunNoBuffer = ByteBuffer.allocate(1);
        byte[] gunNoByte = BcdUtil.decimalStringToBcd(String.valueOf(message.getGunNo()));
        assert gunNoByte != null;
        gunNoBuffer.put(gunNoByte);
        buffer.appendBytes(gunNoBuffer.array());

        //结果
        ByteBuffer resultBuffer = ByteBuffer.allocate(1);
        resultBuffer.put(message.getResult());
        buffer.appendBytes(resultBuffer.array());

        return buffer.getBytes();
    }

    public static byte[] packOrderReportAckMessage(OrderReportAckMessage message) {
        Buffer buffer = Buffer.buffer();

        //交易流水号
        buffer.appendBytes(BcdUtil.decimalStringToBcd(processTranId(message.getTranId())));

        //结果
        ByteBuffer resultBuffer = ByteBuffer.allocate(1);
        resultBuffer.put(message.getResult());
        buffer.appendBytes(resultBuffer.array());

        return buffer.getBytes();
    }


    public static String processDeviceName(String deviceDn) {
        return StringUtils.rightPad(deviceDn,14,"0");
    }

    public static String processTranId(String tranId) {
        return StringUtils.rightPad(tranId,32,"0");
    }

    public static String processPhysicalCardNo(String tranId) {
        return StringUtils.rightPad(tranId,16,"0");
    }


    public static Buffer encode(DataPackage dataPackage) {

        //数据区
        Buffer data = Buffer.buffer();
        data.appendShort(dataPackage.getSerial());
        data.appendByte((byte) dataPackage.getEncrypt());
        data.appendByte((byte) dataPackage.getCode());
        data.appendBytes(dataPackage.getPayload());

        //CRC校验区
        byte[] crc = CrcUtil.calculateCrc(data.getBytes());

        //数据包
        Buffer buffer = Buffer.buffer();
        byte prefix = DataPackage.PREFIX;
        buffer.appendByte(prefix);
        buffer.appendByte((byte) data.getBytes().length);
        buffer.appendBytes(data.getBytes());
        buffer.appendBytes(crc);

        return buffer;

    }
}
