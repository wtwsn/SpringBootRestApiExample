package com.websystique.springboot.controller;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ImgBanerry {
    static BASE64Encoder encoder = new sun.misc.BASE64Encoder();
    static BASE64Decoder decoder = new sun.misc.BASE64Decoder();

    public static void main(String[] args) {
        System.out.println(getImageBinary());

        base64StringToImage(getImageBinary());
    }
    /**
     * 将图片转换成二进制
     * @return
     */
    static String getImageBinary(){
        File f = new File("D:/timg.jpg");
        BufferedImage bi;
        try {
            bi = ImageIO.read(f);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpg", baos);
            byte[] bytes = baos.toByteArray();
            System.out.println("--------");
            System.out.println(encoder.encodeBuffer(bytes).trim());
            System.out.println("------");
            return encoder.encodeBuffer(bytes).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将二进制转换为图片
     * @param base64String
     */
    static void base64StringToImage(String base64String){
        try {
            byte[] bytes1 = decoder.decodeBuffer(base64String);

            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            BufferedImage bi1 = ImageIO.read(bais);
            File w2 = new File("D:/ASC2.jpg");//可以是jpg,png,gif格式
            ImageIO.write(bi1, "jpg", w2);//
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
