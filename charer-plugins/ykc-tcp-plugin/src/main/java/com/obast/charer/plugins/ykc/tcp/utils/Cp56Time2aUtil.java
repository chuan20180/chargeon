package com.obast.charer.plugins.ykc.tcp.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @ Author：chuan
 * @ Date：2024-09-23-17:17
 * @ Version：1.0
 * @ Description：Cp56Time2aUtil
 */
public class Cp56Time2aUtil {

    /**
     * 本地时间转Cp56Time2a
     */
    public static byte[] toByteCp56Time2a(LocalDateTime date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        //System.out.println("下发时间：" + dtf.format(date));
        byte[] result = new byte[7];

        int seconds = date.getSecond();
        int milliseconds = seconds * 1000;

        result[0] = (byte) (milliseconds % 256);
        result[1] = (byte) (milliseconds / 256);

        result[2] = (byte) date.getMinute();
        result[3] = (byte) date.getHour();
        result[4] = (byte) date.getDayOfMonth();
        result[5] = (byte) date.getMonthValue();
        result[6] = (byte) (date.getYear() % 100);
        return result;
    }


    /**
     * Cp56Time2a转时间字符串
     */
    public static String getTimeByCp56Time2a(byte[] b) {
        if (b.length == 7) {
            String str = "";
            int year = b[6] & 0x7F;
            int month = b[5] & 0x0F;
            int day = b[4] & 0x1F;
            int hour = b[3] & 0x1F;
            int minute = b[2] & 0x3F;
            int s0 = b[0] & 0xff;
            int s1 = ((b[1] & 0xff) * 256);
            int second = s0 + s1;

            str += "20" + year + "-"
                    + String.format("%02d", month) + "-"
                    + String.format("%02d", day) + " "
                    + String.format("%02d", hour) + ":"
                    + String.format("%02d", minute) + ":"
                    + String.format("%02d", second / 1000);
            return str + "\n";

        } else {
            System.out.println("无效格式");
            return "";
        }


    }
}