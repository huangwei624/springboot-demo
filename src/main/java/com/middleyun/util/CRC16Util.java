package com.middleyun.util;

/**
 * CRC16 校验
 */
public class CRC16Util {


    /**
     * crc 校验值
     * @param data
     * @return
     */
    public static Byte[] crc16(byte[] data) {
        int[] byteToInt = ArrayUtil.byteToInt(data);
        return ArrayUtil.toPrimitive(charToByte(crc16(byteToInt, byteToInt.length)));
    }

    /**
     * 将字节数组以16进制格式输出
     * @param data
     * @return
     */
    public static String byte2HexStr(byte[] data) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            String hexString = data[i] < 0 ? Integer.toHexString(data[i] + 256) : Integer.toHexString(data[i]);
            stringBuilder.append(hexString.length() == 1 ? "0"+hexString : hexString);
            stringBuilder.append(" ");
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1).toUpperCase();
    }

    /**
     * crc16 计算
     * @param data
     * @param size
     * @return 返回char 字符
     */
    private static char crc16(int data[], int size) {
        char crc = 0x0;
        int i, j;
        int data_t;
        i = j = 0;
        if (data == null) {
            return 0;
        }
        for (j = 0; j < size; j++) {
            data_t = data[j];
            crc = (char) ((data_t & 0xFF) ^ (crc));
            for (i = 0; i < 8; i++) {
                if ((crc & 0x1) == 1) {
                    crc = (char) ((crc >> 1) ^ (0xa001));
                } else {
                    crc >>= 1;
                }
            }
        }
        return (char) ((crc >> 8) | (crc << 8));
    }

    /**
     * char 字符转换成 byte 数组
     * @param c
     * @return
     */
    private static byte[] charToByte(char c) {
        byte[] b = new byte[2];
        b[0] = (byte) ((c & 0xFF00) >> 8);
        b[1] = (byte) (c & 0xFF);
        return b;
    }
}