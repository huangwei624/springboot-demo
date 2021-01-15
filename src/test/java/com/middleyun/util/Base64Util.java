package com.middleyun.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @title
 * @description
 * @author huangwei
 * @createDate 2021/1/15
 * @version 1.0
 */
public class Base64Util {
    public static void main(String[] args) throws IOException {
        String image = "F:\\桌面\\临时文件\\select-point\\end.jpg";
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        BufferedImage read = ImageIO.read(new File(image));
        ImageIO.write(read, "jpg", byteArrayOutputStream);
        String encode = Base64.encode(byteArrayOutputStream.toByteArray());
        System.out.println("data:image/gif;base64," + encode);
    }
}
