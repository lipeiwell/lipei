package com.tarena.test;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JFrameDemo {
	public static void main(String[] args) {
		JFrame win =new JFrame("Image Demo");
		JPanel pane=new JPanel();
		JLabel label =new JLabel();
		URL url=JFrameDemo.class.getResource("img.png");
		ImageIcon ico=new ImageIcon(url);
		label.setIcon(ico);
		pane.add(label);
		win.setContentPane(pane);//swing窗口中必须添加pannel
		win.setSize(280,144);
		win.setVisible(true);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}
