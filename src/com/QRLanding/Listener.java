package com.QRLanding;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Listener {
    boolean First = true;
    public void createAndShowLandingGUI(){
        LAThread laThread = new LAThread();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;

        JFrame frame = new JFrame("哔哩哔哩二维码登陆");
        frame.setBounds((width-90)/2,(height-260)/2,190,260);

        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        JLabel label = new JLabel(new ImageIcon("img\\qq.jpg"));


        JButton button = new JButton("刷新");
        button.setFont(new Font(null,1,20));
        button.setSize(200,40);

        JLabel label1 = new JLabel("message:");
        label1.setFont(new Font(null,1,10));
        label1.setSize(200,20);

        frame.add(label,BorderLayout.NORTH);
        frame.add(button,BorderLayout.CENTER);
        frame.add(label1,BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){

                Map property = new HashMap();
                property.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
                property.put("x-requested-with","XMLHttpRequest");
                try {
                    URL url = new URL("https://passport.bilibili.com/qrcode/getLoginUrl");
                    laThread.setUrl(url);
                } catch (MalformedURLException malformedURLException) {
                    malformedURLException.printStackTrace();
                }
                laThread.setProperty(property);
                laThread.setMethod("GET");
                Thread thread = new Thread(laThread);
                thread.start();
                First = false;
            }

        });

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if(First){

                }else{
                    Image image = Toolkit.getDefaultToolkit().createImage("img\\qrcode.jpg");//createImage()达到实时更新
                    label.setIcon(new ImageIcon(image));

                }


                HtmlOperation htmlOperation = new HtmlOperation();
                Map property = new HashMap();
                URL url = null;
                try {
                    url = new URL("https://passport.bilibili.com/qrcode/getLoginInfo");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                String method = "POST";
                property.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
                property.put("x-requested-with","XMLHttpRequest");
                property.put("oauthKey",laThread.getOauthKey());
                property.put("gourl","https://www.bilibili.com/");
                String htmlResource = htmlOperation.Look(url,method,property);
                String regex = "message\":\"[^\"]+";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(htmlResource);
                property.clear();
                if(matcher.find()){
                    //二维码状态获取
                    label1.setText("message:" + matcher.group().toString().replaceAll("message\":\"",""));
                }else{
                    //登陆认证成功
                    try {
                        url = new URL(htmlOperation.getUrl("https://space.bilibili.com/207849959"));
                        System.out.println(url);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    method = "GET";
                    property.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
                    htmlResource = htmlOperation.Look(url,method,property);
                    System.out.println(htmlResource);
                    timer.cancel();
                    frame.dispose();
                }
            }
        };
        timer.schedule(timerTask,0,1000);
    }
}
