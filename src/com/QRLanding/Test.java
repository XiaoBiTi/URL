package com.QRLanding;

import java.io.IOException;
import java.net.URL;
public class Test {

    public static void main(String[] args) throws IOException {

        URL url = new URL("https://passport.bilibili.com/qrcode/getLoginUrl");
        CreateAndShowGUI createAndShowGUI = new CreateAndShowGUI(url,"img\\qrcode.jpg");
        LAThread laThread = new LAThread();
        laThread.setUrl(url);
        Thread thread = new Thread(laThread);
        thread.start();



    }

}
