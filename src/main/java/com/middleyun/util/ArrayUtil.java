package com.middleyun.util;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 数组工具类
 */
public class ArrayUtil {

    // 允许数组的最大尺寸
    private static final Integer MAX_LENGTH = 500;

    /**
     * 合并数组
     * @param t
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T>  T[] mergeArray(T[]... t) {
        // 记录所有成员的数量
        int members = 0;
        // 记录新数组下一次存放数据的位置
        int nextIndex = 0;
        // 创建一个足够大的数组
        T[] ts = (T[]) Array.newInstance(t[0][0].getClass(), MAX_LENGTH);
        for (int i = 0; i < t.length; i++) {
            if (t[i] == null) {
                continue;
            }
            int length = t[i].length;
            members += length;
            if (members > MAX_LENGTH) {
                return (T[])Array.newInstance(t[0][0].getClass(), 0);
            }
            // 拷贝currentT 到新数组中，并记录下一次再存放数组的索引位置
            System.arraycopy(t[i], 0, ts, nextIndex, length);
            nextIndex = nextIndex + length;
        }
        return Arrays.copyOfRange(ts, 0, members);
    }

    /**
     * 将包装类型数组(Byte)装换成基本类型数组(byte)
     *
     * @param original
     */
    public static byte[] toPrimitive(Byte[] original) {
        int length = original.length;
        byte[] dest = new byte[length];
        for (int i = 0; i < length; i++) {
            dest[i] = original[i];
        }
        return dest;
    }

    /**
     * 将基本类型数组(byte)装换成包装类型数组(Byte)
     *
     * @param original
     */
    public static Byte[] toPrimitive(byte[] original) {
        int length = original.length;
        Byte[] dest = new Byte[length];
        for (int i = 0; i < length; i++) {
            dest[i] = original[i];
        }
        return dest;
    }

    /**
     * 将byte 数组转换成int 数组
     * @param original
     * @return
     */
    public static int[] byteToInt(byte[] original) {
        int length = original.length;
        int[] dest = new int[length];
        for (int i = 0; i < length; i++) {
            dest[i] = original[i];
        }
        return dest;
    }

}
