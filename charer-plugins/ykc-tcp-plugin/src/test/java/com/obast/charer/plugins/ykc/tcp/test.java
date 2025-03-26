package com.obast.charer.plugins.ykc.tcp;

import com.obast.charer.plugins.ykc.tcp.utils.ByteArrayUtil;
import com.obast.charer.plugins.ykc.tcp.utils.MathUtil;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * @ Author：chuan
 * @ Date：2025-02-26-08:07
 * @ Version：1.0
 * @ Description：test
 */
@Slf4j
public class test {

    public static void main(String[] args) {

        String hexString = "68 55 1a 00";
        hexString = hexString.replace(" ", "");


        int byteArrayLength = hexString.length() / 2;
        byte[] byteArray = new byte[byteArrayLength];


        for (int i = 0; i < byteArrayLength; i++) {
            int startIndex = i * 2;
            int endIndex = startIndex + 2;
            String byteString = hexString.substring(startIndex, endIndex);
            byteArray[i] = (byte) Integer.parseInt(byteString, 16);
        }


        int hexQty = ByteArrayUtil.byteArray2Int_Little_Endian(byteArray);

        BigDecimal totalQty = MathUtil.convertByteArray2Quantity(byteArray);


        log.debug("byteQty={}, hexQty={}, decQty ={}", byteArray, hexQty, totalQty);


    }
}
