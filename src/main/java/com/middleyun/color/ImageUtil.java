package com.middleyun.color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 图片工具类
 */
public class ImageUtil {

    /**
     * 获取图像上某个点的rgb值
     * @param imagePath 图片路径
     * @param x x方向上的像素点
     * @param y y方向上的像素点
     * @return
     * @throws IOException
     */
    public static int[] getRgbFromImgPoint(String imagePath, int x, int y) throws IOException {
        File imageFile = new File(imagePath);
        if (!imageFile.exists()) {
            return null;
        }
        BufferedImage readBuffer = ImageIO.read(imageFile);
        if (readBuffer == null || x >= readBuffer.getWidth() || y >= readBuffer.getHeight()) {
            return null;
        }
        int[] rgb = new int[3];
        int pixel = readBuffer.getRGB(x, y);
        rgb[0] = (pixel & 0xff0000) >> 16;
        rgb[1] = (pixel & 0xff00) >> 8;
        rgb[2] = (pixel & 0xff);
        return rgb;
    }

    /**
     * 获取图片上多个点的rgb值
     * @param imagePath
     * @param point
     * @return
     * @throws IOException
     */
    public static int[][] getRgbFromImgPoint(String imagePath, int[][] point) throws IOException {
        File imageFile = new File(imagePath);
        if (!imageFile.exists()) {
            return null;
        }
        // 存放多个rgb值
        int[][] rgbs = new int[point.length][3];
        BufferedImage readBuffer = ImageIO.read(imageFile);
        if (readBuffer == null) {
            throw new RuntimeException("file is not image");
        }
        int imgWidth = readBuffer.getWidth();
        int imgHeight = readBuffer.getHeight();
        for (int i = 0; i < point.length; i++) {
            if (point[i][0] >= imgWidth || point[i][1] >= imgHeight) {
                continue;
            }
            int[] rgb = new int[3];
            int pixel = readBuffer.getRGB(point[i][0], point[i][1]);
            rgb[0] = (pixel & 0xff0000) >> 16;
            rgb[1] = (pixel & 0xff00) >> 8;
            rgb[2] = (pixel & 0xff);
            rgbs[i] = rgb;
        }
        return rgbs;
    }

    /**
     * 获取图片的像素
     * @param imagePath
     * @return {宽度， 高度}
     */
    public static int[] getImagePixel(String imagePath) throws IOException {
        File imageFile = new File(imagePath);
        if (!imageFile.exists()) {
            return null;
        }
        BufferedImage readBuffer = ImageIO.read(imageFile);
        if (readBuffer == null) {
            return null;
        }
        return new int[]{readBuffer.getWidth(), readBuffer.getHeight()};
    }
}
