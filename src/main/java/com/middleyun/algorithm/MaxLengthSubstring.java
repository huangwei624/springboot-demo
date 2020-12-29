package com.middleyun.algorithm;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 最长子串
 */
public class MaxLengthSubstring {


    /**
     * 两层循环获取最长子串
     *
     * @return
     */
    private static String maxLengthSubstring1(String str1, String str2) {
        if (str1 == null || "".equals(str1) || str2 == null || "".equals(str2)) {
            return "";
        }
        char[] charArray1 = str1.toCharArray();
        char[] charArray2 = str2.toCharArray();
        int m, n;
        String currentStr = "";
        for (int i = 0; i < charArray1.length; i++) {
            for (int j = 0; j < charArray2.length; j++) {
                if (charArray1[i] == charArray2[j]) {
                    m = i + 1;
                    n = j + 1;
                    while (m < charArray1.length && n < charArray2.length) {
                        if (charArray1[m] != charArray2[n]) {
                            currentStr = currentStr.length() > m - i ? currentStr : String.valueOf(charArray1, i, m - i);
                            break;
                        }
                        if (m == charArray1.length - 1 || n == charArray2.length - 1) {
                            currentStr = currentStr.length() > m - i + 1 ? currentStr : String.valueOf(charArray1, i, m - i + 1);
                            break;
                        }
                        m++;
                        n++;
                    }
                }
            }
        }
        return currentStr;
    }

    /**
     * 使用map 存储一个字符串，避免每次都循环charArray2
     * @param str1
     * @param str2
     * @return
     */
    private static String maxLengthSubstring2(String str1, String str2) {
        if (str1 == null || "".equals(str1) || str2 == null || "".equals(str2)) {
            return "";
        }
        char[] charArray1 = str1.toCharArray();
        char[] charArray2 = str2.toCharArray();

        HashMap<Character, String> str2Map = new HashMap<>();
        for (int i = 0; i < charArray2.length; i++) {
            String index = str2Map.get(charArray2[i]);
            if(index == null) {
                str2Map.put(charArray2[i], i + "");
            } else {
                str2Map.put(charArray2[i], index + "," + i);
            }
        }

        int m, n;
        String currentStr = "";

        for (int i = 0; i < charArray1.length; i++) {
            if (str2Map.containsKey(charArray1[i])) {
                String index = str2Map.get(charArray1[i]);
                List<Integer> index2List = Stream.of(index.split(",")).map(Integer::valueOf).collect(Collectors.toList());
                for (Integer item : index2List) {
                    m = i + 1;
                    n = item + 1;
                    while (m < charArray1.length && n < charArray2.length) {
                        if (!(str2Map.containsKey(charArray1[m]) && str2Map.get(charArray1[m]).contains(n + ""))) {
                            currentStr = currentStr.length() > m - i ? currentStr : String.valueOf(charArray1, i, m - i);
                            break;
                        }
                        if (m == charArray1.length - 1 || n == charArray2.length - 1) {
                            currentStr = currentStr.length() > m - i + 1 ? currentStr : String.valueOf(charArray1, i, m - i + 1);
                            break;
                        }
                        m++;
                        n++;
                    }
                }
            }
        }
        return currentStr;
    }

    public static void main(String[] args) {
        String str = maxLengthSubstring2("abcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcab" +
                "cabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabcabc", "abcbabcabcabcabcde");
        System.out.println(str);
    }

}
