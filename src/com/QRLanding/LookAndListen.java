package com.QRLanding;

import com.swetake.util.Qrcode;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LookAndListen {
    private String htmlResource = null;
    public LookAndListen(){}

    /**
     * 获取html源文件
     * @param url
     */
    public String Look(URL url) {
        BufferedInputStream bis = null;
        try {
            bis = new Request().request(url);
            byte[] b = new byte[1024];
            int n = -1;
            while ((n = bis.read(b)) != -1) {
                htmlResource = new String(b, 0, n);
            }

        } catch (IOException io) {

            System.out.println(io.getMessage());

        } finally {

            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException io) {
                    System.out.println(io.getMessage());
                }
            }

        }
        return htmlResource;
    }

    /**
     * 获取二维码图片地址
     * @param htmlResource
     */
    public String getQrcodeUrl(String htmlResource){
        String regex = "https://[^\"]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(htmlResource);
        matcher.find();
        return matcher.group();
    }

    /**
     * 制作二维码图片
     */
    public void createQrcodePicture(String QrcodeUrl) throws IOException {
        int width=175,height=175;
        Qrcode qrcode = new Qrcode();
        qrcode.setQrcodeErrorCorrect('H');
        qrcode.setQrcodeEncodeMode('B');
        qrcode.setQrcodeVersion(10);
        byte[] d = QrcodeUrl.getBytes(StandardCharsets.UTF_8);

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);

        //绘图
        Graphics2D gs = bufferedImage.createGraphics();
        gs.setBackground(Color.WHITE);
        gs.setColor(Color.BLACK);
        gs.clearRect(0, 0, width, height);

        //偏移量
        int pixoff = 2;

        if (d.length > 0 && d.length < 120) {
            boolean[][] s = qrcode.calQrcode(d);

            for (int i = 0; i < s.length; i++) {
                for (int j = 0; j < s.length; j++) {
                    if (s[j][i]) {
                        gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
                    }
                }
            }
        }
        gs.dispose();
        bufferedImage.flush();
        //设置图片格式，与输出的路径
        ImageIO.write(bufferedImage, "jpg", new File("img\\qrcode.jpg"));
        System.out.println("二维码生成完毕");

    }

}

