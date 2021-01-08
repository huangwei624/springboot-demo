package com.middleyun.color;

import java.io.IOException;
import java.util.Arrays;

/**
 * 颜色工具类
 */
public class ColorUtil {

    /**
     * hsb, s和b 的取值范围是 0.2 - 1
     */
    public static final float[] AVAILABLE_HSB_S_B_RANGE = {0.16f, 1.0f};

    /**
     * 红色hsb， h的范围
     */
    public static final float[][] RED_HSB_H_RANGE = {{280.0f, 360.0f}, {0, 50.0f}};

    /**
     * 绿色hsb， h的范围
     */
    public static final float[] GREEN_HSB_H_RANGE = {70.0f, 200.0f};

    /**
     * 黄hsb， h的范围
     */
    public static final float[] YELLOW_HSB_H_RANGE = {45.0f, 65.0f};

    /**
     * 通过传入图片上一个区域的几个点，判断出该区域可能性最大的那个颜色
     * @param rgb
     * @return
     */
    public static int getColor(int[][] rgb) {
        // 颜色比率，记录这些点出现红、绿、黄的次数
        int[] colorRate = {0, 0, 0};    // 以次代表黄、绿、黄
        for (int i = 0; i < rgb.length; i++) {
            float[] hsb = rgbToHsb(rgb[i][0], rgb[i][1], rgb[i][2]);
            if (isRed(hsb)) {
                colorRate[0] += 1;
            }
            if (isGreen(hsb)) {
                colorRate[1] += 1;
            }
            if (isYellow(hsb)) {
                colorRate[2] += 1;
            }
        }
        // 其他色
        if (colorRate[0] == colorRate[1] && colorRate[0] == colorRate[2]) {
            return 0;
        }
        int max = colorRate[0];
        int maxIndex = 0;
        for (int i = 1; i < colorRate.length; i++) {
            if (colorRate[i] > max) {
                max = colorRate[i];
                maxIndex = i;
            }
        }
        return maxIndex + 1;
    }

    /**
     * 通过rgb 色获取当前像素点是红绿黄中的哪种颜色, 1:红色   2，绿色   3，黄色  0:其他颜色
     * @param rgb rgb值
     * @return
     */
    public static int getColor(int[] rgb) {
        return getColor(rgb[0], rgb[1], rgb[2]);
    }

    /**
     * 通过图片上的一点，判断该点是偏向什么颜色
     * @param imgPath   图片路径
     * @param xPoint 该点的x坐标
     * @param yPoint 该点的y坐标
     * @return
     * @throws IOException
     */
    public static int getColor(String imgPath, Integer xPoint, Integer yPoint) throws IOException {
        int[] rgb = ImageUtil.getRgbFromImgPoint(imgPath, xPoint, yPoint);
        if (rgb == null) {
            throw new RuntimeException("get rgb fail");
        }
        return getColor(rgb);
    }

    /**
     * 通过图片某个区域的几个点，判断该区域最大可能的颜色
     * @param imgPath
     * @param point
     * @return
     * @throws IOException
     */
    public static int getColor(String imgPath, int[][] point) throws IOException {
        int[][] rgbs = ImageUtil.getRgbFromImgPoint(imgPath, point);
        if (rgbs == null) {
            throw new RuntimeException("get rgbs fail from image");
        }
        return getColor(rgbs);
    }

    /**
     * 通过rgb 色获取当前像素点是红绿黄中的哪种颜色, 1:红色   2，绿色   3，黄色  0:其他颜色
     * @return
     */
    public static int getColor(int r, int b, int g) {
        float[] hsb = rgbToHsb(r, b, g);
        if (isRed(hsb)) {
            return 1;
        }
        if (isGreen(hsb)) {
            return 2;
        }
        if (isYellow(hsb)) {
            return 3;
        }
        return 0;
    }

    /**
     * rgb 转 hsb
     * @param r 红
     * @param g 绿
     * @param b 蓝
     * @return
     */
    public static float[] rgbToHsb(int r, int g, int b) {
        assert 0 <= r && r <= 255;
        assert 0 <= g && g <= 255;
        assert 0 <= b && b <= 255;
        int[] rgb = new int[] { r, g, b };
        Arrays.sort(rgb);
        int max = rgb[2];
        int min = rgb[0];

        float hsbB = max / 255.0f;
        float hsbS = max == 0 ? 0 : (max - min) / (float) max;

        float hsbH = 0;
        if (max == r && g >= b) {
            hsbH = (g - b) * 60f / (max - min) + 0;
        } else if (max == r && g < b) {
            hsbH = (g - b) * 60f / (max - min) + 360;
        } else if (max == g) {
            hsbH = (b - r) * 60f / (max - min) + 120;
        } else if (max == b) {
            hsbH = (r - g) * 60f / (max - min) + 240;
        }
        return new float[] { hsbH, hsbS, hsbB };
    }

    /**
     * hsb 转 rgb
     * @param h
     * @param s
     * @param v
     * @return
     */
    public static int[] hsbToRgb(float h, float s, float v) {
        assert Float.compare(h, 0.0f) >= 0 && Float.compare(h, 360.0f) <= 0;
        assert Float.compare(s, 0.0f) >= 0 && Float.compare(s, 1.0f) <= 0;
        assert Float.compare(v, 0.0f) >= 0 && Float.compare(v, 1.0f) <= 0;

        float r = 0, g = 0, b = 0;
        int i = (int) ((h / 60) % 6);
        float f = (h / 60) - i;
        float p = v * (1 - s);
        float q = v * (1 - f * s);
        float t = v * (1 - (1 - f) * s);
        switch (i) {
            case 0:
                r = v;
                g = t;
                b = p;
                break;
            case 1:
                r = q;
                g = v;
                b = p;
                break;
            case 2:
                r = p;
                g = v;
                b = t;
                break;
            case 3:
                r = p;
                g = q;
                b = v;
                break;
            case 4:
                r = t;
                g = p;
                b = v;
                break;
            case 5:
                r = v;
                g = p;
                b = q;
                break;
            default:
                break;
        }
        return new int[] { (int) (r * 255.0), (int) (g * 255.0),
                (int) (b * 255.0) };
    }


    /**
     * 判断当前hsb 是否为黄色
     * @param hsb
     * @return
     */
    private static boolean isYellow(float[] hsb) {
        if (hsb[0] < 0 || hsb[1] < 0 || hsb[2] < 0) {
            return false;
        }
        return hsb[0] >= YELLOW_HSB_H_RANGE[0] && hsb[0] <= YELLOW_HSB_H_RANGE[1]
                && hsb[1] >= AVAILABLE_HSB_S_B_RANGE[0] && hsb[1] <= AVAILABLE_HSB_S_B_RANGE[1]
                && hsb[2] >= AVAILABLE_HSB_S_B_RANGE[0] && hsb[2] <= AVAILABLE_HSB_S_B_RANGE[1];
    }

    /**
     * 判断当前hsb 是否为绿色
     * @param hsb
     * @return
     */
     private static boolean isGreen(float[] hsb) {
        if (hsb[0] < 0 || hsb[1] < 0 || hsb[2] < 0) {
            return false;
        }
        return hsb[0] >= GREEN_HSB_H_RANGE[0] && hsb[0] <= GREEN_HSB_H_RANGE[1]
                && hsb[1] >= AVAILABLE_HSB_S_B_RANGE[0] && hsb[1] <= AVAILABLE_HSB_S_B_RANGE[1]
                && hsb[2] >= AVAILABLE_HSB_S_B_RANGE[0] && hsb[2] <= AVAILABLE_HSB_S_B_RANGE[1];
    }

    /**
     * 判断当前 hsb 是否为红色
     * @param hsb
     * @return
     */
    private static boolean isRed(float[] hsb) {
        if (hsb[0] < 0 || hsb[1] < 0 || hsb[2] < 0) {
            return false;
        }
        return (hsb[0] >= RED_HSB_H_RANGE[0][0] && hsb[0] <= RED_HSB_H_RANGE[0][1]
                && hsb[1] >= AVAILABLE_HSB_S_B_RANGE[0] && hsb[1] <= AVAILABLE_HSB_S_B_RANGE[1]
                && hsb[2] >= AVAILABLE_HSB_S_B_RANGE[0] && hsb[2] <= AVAILABLE_HSB_S_B_RANGE[1])
                ||
                (hsb[0] >= RED_HSB_H_RANGE[1][0] && hsb[0] <= RED_HSB_H_RANGE[1][1]
                && hsb[1] >= AVAILABLE_HSB_S_B_RANGE[0] && hsb[1] <= AVAILABLE_HSB_S_B_RANGE[1]
                && hsb[2] >= AVAILABLE_HSB_S_B_RANGE[0] && hsb[2] <= AVAILABLE_HSB_S_B_RANGE[1]);
    }
    
}
