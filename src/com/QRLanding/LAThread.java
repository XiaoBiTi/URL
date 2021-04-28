package com.QRLanding;


import java.io.IOException;
import java.net.URL;
import java.util.Map;


public class LAThread implements Runnable{
    private URL url;
    private Map<String,String> property;
    private String method;
    private String oauthKey;

    public void setUrl(URL url) { this.url = url; }

    public void setProperty(Map<String,String> property){ this.property = property;}

    public void setMethod(String method){this.method = method;}

    public String getOauthKey() {return oauthKey; }

    @Override
    public void run(){
        HtmlOperation htmlOperation = new HtmlOperation();
        String htmlResource = htmlOperation.Look(url,method,property);
        String QrcodeUrl = htmlOperation.getUrl(htmlResource);
        oauthKey = htmlOperation.getOauthKey(htmlResource);
        try {
           htmlOperation.createQrcodePicture(QrcodeUrl);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
