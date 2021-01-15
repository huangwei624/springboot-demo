package com.middleyun.color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
            try{
                BufferedImage bimg = ImageIO.read(new File("F:\\桌面\\临时文件\\select-point\\end.jpg"));
                int [][] data = new int[bimg.getWidth()][bimg.getHeight()];
                //方式一：通过getRGB()方式获得像素矩阵
                //此方式为沿Height方向扫描
                for(int i=0;i<bimg.getWidth();i++){
                    for(int j=0;j<bimg.getHeight();j++){
                        data[i][j]=bimg.getRGB(i,j);
                        //输出一列数据比对
                        if(i==0)
                            System.out.printf("%x\t",data[i][j]);
                    }
                }
                Raster raster = bimg.getData();
                System.out.println("");
                int [] temp = new int[raster.getWidth()*raster.getHeight()*raster.getNumBands()];
                //方式二：通过getPixels()方式获得像素矩阵
                //此方式为沿Width方向扫描
                int [] pixels  = raster.getPixels(0,0,raster.getWidth(),raster.getHeight(),temp);
                for (int i=0;i<pixels.length;) {
                    //输出一列数据比对
                    if((i%raster.getWidth()*raster.getNumBands())==0)
                        System.out.printf("ff%x%x%x\t",pixels[i],pixels[i+1],pixels[i+2]);
                    i+=3;
                }
            }catch (IOException e){
                e.printStackTrace();
            }
    }
}
