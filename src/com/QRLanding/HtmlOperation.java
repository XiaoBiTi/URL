package com.QRLanding;

import com.swetake.util.Qrcode;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlOperation {
    public HtmlOperation(){}
    /**
     * 获取html源文件，返回String对象
     * @param url
     */
    public String Look(URL url,String method, Map<String,String> property) {
        BufferedInputStream bis = null;
        String htmlResource = null;
        try {
            bis = new Request().request(url,method,property);
            byte[] b = new byte[1024];
            int n = -1;
            while ((n = bis.read(b)) != -1) {
                htmlResource = new String(b, 0, n);
                System.out.println(htmlResource);
            }

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }
        }

        return htmlResource;
    }

    /**
     * 获取制作二维码的内容，返回String对象
     * @param htmlResource
     * @return
     */
    public String getUrl(String htmlResource){
        //匹配需要的内容
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
    }

    /**
     *返回oauthKey
     */
    public String getOauthKey(String htmlResource){
        //匹配所需内容
        String regex = "oauthKey\":\"[^\"]+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(htmlResource);
        matcher.find();
        return matcher.group().replaceAll("oauthKey\":\"","");
    }
}

