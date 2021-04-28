package com.QRLanding;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Request {
    public Request(){ }

    /**
     * 设置URL的请求头文件并请求，返回BufferedInputStream对象
     */
    public BufferedInputStream request(URL url, String method, Map<String,String> property) throws IOException {
        HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
        con.setRequestMethod(method);
        property.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
        StringBuilder json = new StringBuilder("");
        for(String key:property.keySet()) {
            if(!(key.equals("User-Agent") || key.equals("x-requested-with"))){
                json.append( key + "=" + property.get(key)  + "&");

            }
        }
        if(method.equals("POST")){
            System.out.println(property.get("oauthKey"));
            con.setDoOutput(true);
            con.setDoInput(true);
            BufferedOutputStream bos = new BufferedOutputStream(con.getOutputStream());
            bos.write(json.toString().replaceAll("/&","/").getBytes(StandardCharsets.UTF_8));
            bos.flush();
            bos.close();
        }
        return new BufferedInputStream(con.getInputStream());
    }
}
