package com.QRLanding;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.URL;
public class Request {
    public Request(){ }

    /**
     * 设置URL的请求头文件
     */
    public BufferedInputStream request(URL url) throws IOException {
        HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
        con.setRequestProperty("x-requested-with","XMLHttpRequest");
        return new BufferedInputStream(con.getInputStream());
    }
}
