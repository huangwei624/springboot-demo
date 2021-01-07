package com.middleyun.color;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        int color = ColorUtil.getColor("G:\\hwpicture\\44.jpg", 136, 156);
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
