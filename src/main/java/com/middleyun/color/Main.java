package com.middleyun.color;

import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        int[] rgb = ImageUtil.getRgbFromImgPoint("G:\\hwpicture\\44.jpg", 136, 156);
        if (rgb == null) {
            System.out.println("获取rgb失败");
            return;
        }
        int[] imagePixel = ImageUtil.getImagePixel("G:\\hwpicture\\6.jpg");
        System.out.println(Arrays.toString(imagePixel));
        System.out.println(Arrays.toString(rgb));
        int color = ColorUtil.getColor(rgb[0], rgb[1], rgb[2]);
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
