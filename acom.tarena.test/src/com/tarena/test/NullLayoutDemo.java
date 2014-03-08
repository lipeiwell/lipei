package com.tarena.test;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class NullLayoutDemo {
	public static void main(String[] args) {
		JFrame win=new JFrame("Layout布局演示窗口");
		JPanel panel=new JPanel();
		JButton ok=new JButton("确定");
		JButton cancel=new JButton("取消");
		win.setSize(514,399);
		panel.setLayout(null);//Layout布局，null布局
		ok.setLocation(340,299);
		ok.setSize(77,23);
		cancel.setLocation(422,299);
		cancel.setSize(77,23);
		panel.add(ok);
		panel.add(cancel);
		win.setContentPane(panel);
		win.setVisible(true);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

}
