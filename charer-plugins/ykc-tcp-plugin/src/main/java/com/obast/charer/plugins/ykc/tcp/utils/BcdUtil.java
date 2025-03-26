package com.obast.charer.plugins.ykc.tcp.utils;

/**
 * 类中使用的BCD码是8421 BCD码
 * @author thb *
 */
public class BcdUtil {

    /**
     * 将BCD码转换为十进制的字符串。举例：
     * 如果BCD码为00010010，转换后的十进制字符串为"12"
     * 如果BCD码为00000010，转换后的十进制字符串为"2"
     * 如果BCD码为00000001 00100011，转换后的十进制字符串为"123"
     * @param bcd BCD码
     * @return 十进制字符串
     */
    public static String bcdToDecimalString(byte[] bcd) {

        StringBuilder sb = new StringBuilder();
        // 存放转化后的十进制数字的字符串
        String decStr;

        for (byte b : bcd) {
            // 每个字节的前四位的值右移4位转化为十进制数
            sb.append((b & 0XF0) >> 4);
            sb.append(b & 0X0F);
        }

        // 如果转化后的字符串首字母为0，那么去掉
        if (sb.charAt(0) == '0') {
            decStr = sb.substring(1);
        }else {
            decStr = sb.toString();
        }

        return decStr;
    }

    /**
     * 十进制字符串转BCD码。举例：
     * 如果十进制字符串为"2"，转换后的BCD码为00000010
     * 如果十进制字符串为"12"，转换后的BCD码为00010010
     * 如果十进制字符串为"123"，转换后的BCD码为00000001 00100011
     * @param decStr 十进制字符串
     * @return BCD码
     */
    public static byte[] decimalStringToBcd(String decStr) {
        // 因为可能修改字符串的内容，所以构造StringBuffer
        StringBuilder sb = new StringBuilder(decStr);
        // 一个字节包含两个4位的BCD码，byte数组中要包含偶数个BCD码
        // 一个十进制字符对应4位BCD码，所以如果十进制字符串的长度是奇数，要在前面补一个0使长度成为偶数
        if ((sb.length() % 2) != 0) {
            sb.insert(0, '0');
        }

        // 两个十进制数字转换为BCD码后占用一个字节，所以存放BCD码的字节数等于十进制字符串长度的一半
        byte[] bcd = new byte[sb.length() / 2];
        for (int i = 0; i < sb.length();) {
            if (!Character.isDigit(sb.charAt(i)) || !Character.isDigit(sb.charAt(i + 1))) {
                return null;
            }
            // 每个字节的构成：用两位十进制数字运算的和填充，高位十进制数字左移4位+低位十进制数字
            bcd[i/2] = (byte)((Character.digit(sb.charAt(i), 10) << 4) + Character.digit(sb.charAt(i + 1), 10));
            // 字符串的每两个字符取出来一起处理，所以此处i的自增长要加2，而不是加1
            i += 2;
        }
        return bcd;
    }
}