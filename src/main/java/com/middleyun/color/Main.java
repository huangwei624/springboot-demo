package com.middleyun.color;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
//        int color = ColorUtil.getColor("G:\\hwpicture\\traffic\\1.jpg", 1267, 97); // 红色
//        int color = ColorUtil.getColor("G:\\hwpicture\\traffic\\2.jpg", 119, 160);    // 绿色
//        int color = ColorUtil.getColor("G:\\hwpicture\\traffic\\3.jpg", 1133, 62);    // 绿色
//        int color = ColorUtil.getColor("G:\\hwpicture\\traffic\\4.jpg", 821, 140);    // 绿色
//        int color = ColorUtil.getColor("G:\\hwpicture\\traffic\\5.jpg", 1747, 44);    // 红色

        int[][] points = {{1726, 28}, {1746, 40}, {1500, 70}};
        int color = ColorUtil.getColor("G:\\hwpicture\\traffic\\5.jpg", points);    // 红色
        switch (color) {
            case 1:
                System.out.println("红色");
                break;
            case 2:
                System.out.println("绿色");
                break;
            case 3:
                System.out.println("黄色");
                break;
            default:
                System.out.println("其他色");
        }
    }
}
