package com.QRLanding;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
public class CreateAndShowGUI {
    public CreateAndShowGUI(URL url,String filename){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height;
        int width = screenSize.width;

        JFrame frame = new JFrame("哔哩哔哩二维码登陆");
        frame.setBounds((width-200)/2,(height-250)/2,200,250);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        JLabel label = new JLabel(new ImageIcon(filename));

        JButton button = new JButton("刷新");
        button.setFont(new Font(null,1,20));

        frame.add(label,BorderLayout.NORTH);
        frame.add(button);
        frame.setVisible(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LookAndListen lookAndListen = new LookAndListen();
                //获取html源文件
                String htmlResource = lookAndListen.Look(url);
                //获取二维码图片地址
                String QrcodeUrl = lookAndListen.getQrcodeUrl(htmlResource);
                //保存二维码图片
                String filename = null;
                try {
                    filename = lookAndListen.createQrcodePicture(QrcodeUrl);
                } catch (IOException io) {
                    System.out.println(io.getMessage());
                }
                label.setIcon(new ImageIcon("img\\qrcode.jpg"));
            }
        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
