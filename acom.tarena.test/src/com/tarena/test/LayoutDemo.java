package com.tarena.test;

import javax.swing.*;
import java.awt.*;


public class LayoutDemo {
	public static void main(String[] args) {
		JFrame win=new JFrame();
		JPanel content=new JPanel(new BorderLayout());
		JPanel butpanel=new JPanel(new BorderLayout());
		JPanel right=new JPanel(new FlowLayout());
		JPanel left=new JPanel(new FlowLayout());
		JButton ok=new JButton("ok");
		JButton cancel=new JButton("cancel");
		JButton help=new JButton("help");
		right.add(ok);
		right.add(cancel);
		left.add(help);
		butpanel.add(BorderLayout.EAST,right);
		butpanel.add(BorderLayout.WEST,left);
		content.add(BorderLayout.SOUTH,butpanel);
		win.setContentPane(content);
		win.setSize(500,400);
		win.setVisible(true);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}

}
