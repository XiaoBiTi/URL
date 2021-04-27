package com.QRLanding;

import java.io.IOException;
import java.net.URL;

public class LAThread implements Runnable{
    private URL url;
    public void setUrl(URL url) { this.url = url; }

    @Override
    public void run() {

        LookAndListen lookAndListen = new LookAndListen();
        //获取html源文件
        String htmlResource = lookAndListen.Look(url);
        //获取二维码图片地址
        String QrcodeUrl = lookAndListen.getQrcodeUrl(htmlResource);
        //保存二维码图片
        String filename = null;
        try {
            filename = lookAndListen.createQrcodePicture(QrcodeUrl);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

}
