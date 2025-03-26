package com.obast.charer.plugins.ykc.tcp.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @ Author：chuan
 * @ Date：2024-09-23-18:16
 * @ Version：1.0
 * @ Description：MathUtil
 */
public class MathUtil {

    /**
     * 四舍五入
     */
    public static float round(int data, int len) {
        double t = Math.pow(10,len);
        return (float) (Math.round((double) data /t * t) / t);
    }

    /**
     * 价格四舍五入
     */
    public static float numberRound(int data) {
        double t = Math.pow(10, 4);
        return (float) (Math.round((double) data /t * t) / t);
    }

    /**
     * 价格四舍五入
     */
    public static float priceRound(int data) {
        double t = Math.pow(10, 5);
        return (float) (Math.round((double) data /t * t) / t);
    }


    public static BigDecimal convertByteArray2Minute(byte[] current) { return convertByteArray2Number(current, 0); }


    public static BigDecimal convertByteArray2Quantity(byte[] current) { return convertByteArray2Number(current, 4); }

    public static BigDecimal convertByteArray2Voltage(byte[] voltage) {
        return convertByteArray2Number(voltage, 1);
    }

    public static BigDecimal convertByteArray2Current(byte[] current) {
        return convertByteArray2Number(current, 1);
    }

    public static BigDecimal convertByteArray2Amount(byte[] current) {
        return convertByteArray2Number(current, 4);
    }


    public static BigDecimal convertByteArray2Price(byte[] current) {
        return convertByteArray2Number(current, 5);
    }

    public static byte[] convertPrice2ByteArray(BigDecimal price) {
        return convertNumber2ByteArray(price, 5);
    }

    public static BigDecimal convertByteArray2Number(byte[] value, int decimal) {
        int intVoltage = ByteArrayUtil.byteArray2Int_Little_Endian(value);
        return BigDecimal.valueOf(intVoltage).divide(BigDecimal.valueOf(Math.pow(10, decimal)), decimal, RoundingMode.HALF_UP ).setScale(decimal, RoundingMode.HALF_UP);
    }

    public static byte[] convertNumber2ByteArray(BigDecimal value, int decimal) {
        BigDecimal decimalPrice = value.multiply(BigDecimal.valueOf(Math.pow(10, decimal)));
        String hexPrice =  StringUtils.leftPad(Integer.toHexString(decimalPrice.intValue()), 8, "0") ;
        List<String> hexList = new ArrayList<>();
        for(int i = 0; i< hexPrice.length(); i+=2 ) {
            hexList.add(hexPrice.substring(i, i+2));
        }
        Collections.reverse(hexList);
        String hexString = String.join("", hexList);
        // 步骤2: 创建byte数组
        int length = hexString.length(); // 获取输入字符串的长度
        byte[] byteArray = new byte[length / 2]; // 创建byte数组，长度为输入字符串长度的一半
        // 步骤3: 转换字符并存储到byte数组中
        for (int i = 0; i < length; i += 2) {
            String subString = hexString.substring(i, i + 2); // 取出两个字符
            byte byteValue = (byte) Integer.parseInt(subString, 16); // 转换为byte值
            byteArray[i / 2] = byteValue; // 存储到byte数组中
        }
        return byteArray;
    }
}
