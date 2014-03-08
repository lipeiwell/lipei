package com.tarena.elts.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.tarena.elts.entity.User;
import com.tarena.test.JFrameDemo;

public class ExamLoginFrame extends JFrame{

	private static final long serialVersionUID = -1796834130339697339L;
	private ClientContext clientContext;
	public ExamLoginFrame(){
		init();
	}
	public void setClientContext(ClientContext context){
		this.clientContext=context;
	}
	private void init() {
		setSize(1000,500);
		setTitle("达内在线考试");
		setContentPane(createContentpane());
		setDefaultCloseOperation(ExamLoginFrame.EXIT_ON_CLOSE);
	}
	private JPanel createContentpane() {
		JPanel p=new JPanel(new BorderLayout());
		p.add(BorderLayout.SOUTH,new Label("版权所有，盗版必杀！达内科技2011",Label.RIGHT));
		p.add(BorderLayout.NORTH,createUpPane());
		p.add(BorderLayout.CENTER,createCenterPane());
		p.setBorder(new EmptyBorder(8,8,8,8));
		return p;
	}
	private JPanel createCenterPane() {
		JPanel p=new JPanel(new BorderLayout());
		p.add(BorderLayout.CENTER,creatMenubar());
		info=new JLabel("欢迎：XXXXX  同学！",JLabel.CENTER);
		p.add(BorderLayout.NORTH,info);
		return p;
	}
	
	private JPanel creatMenubar() {
		JPanel p=new JPanel(new FlowLayout());
		JButton start=creatImgbtn("exam.png","开始");
		JButton result=creatImgbtn("result.png","成绩");
		JButton message=creatImgbtn("message.png","规则");
		JButton exit=creatImgbtn("exit.png","结束");
		p.add(start);
		p.add(result);
		p.add(message);
		p.add(exit);
		
		start.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent e) {
		        clientContext.start();
		      }
		    });
		    result.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent e) {
		        clientContext.result();
		      }
		    });
		
		return p;
	}
	private JButton creatImgbtn(String img,String txt){
		ImageIcon ico = new ImageIcon(getClass().getResource(img));
		    JButton button = new JButton(txt, ico);
		    button.setVerticalTextPosition(JButton.BOTTOM);
		    button.setHorizontalTextPosition(JButton.CENTER);
		    return button;
		
	}
/*	private JPanel creatcenterImage() {
		JPanel p=new JPanel(new FlowLayout());
		
		JButton label1=new JButton();
		//label1.add(new Label("开始",Label.RIGHT));
		JButton label2=new JButton();
		JButton label3=new JButton();
		JButton label4=new JButton();
		URL url = ExamLoginFrame.class.getResource("exam.png");
		URL url2 = ExamLoginFrame.class.getResource("result.png");
		URL url3 = ExamLoginFrame.class.getResource("message.png");
		URL url4 = ExamLoginFrame.class.getResource("exit.png");
	    ImageIcon ico = new ImageIcon(url);
	    ImageIcon ico2 = new ImageIcon(url2);
	    ImageIcon ico3 = new ImageIcon(url3);
	    ImageIcon ico4 = new ImageIcon(url4);
	    label1.setIcon(ico);
	    label2.setIcon(ico2);
	    label3.setIcon(ico3);
	    label4.setIcon(ico4);
		p.add(label1);
		p.add(label2);
		p.add(label3);
		p.add(label4);
		JButton[] button=new JButton[4];
		URL[] url={ExamLoginFrame.class.getResource("exam.png"),ExamLoginFrame.class.getResource("result.png"),
				ExamLoginFrame.class.getResource("message.png"),ExamLoginFrame.class.getResource("exit.png")};
		ImageIcon[] ico=new ImageIcon[4];
		for(int i=0;i<4;i++){
			button[i]=new JButton();
			ico[i]=new ImageIcon(url[i]);
			button[i].setIcon(ico[i]);
			p.add(button[i]);
			
		}
		return p;
	}*/
	private JPanel createUpPane() {
		JPanel p=new JPanel(new BorderLayout());
		p.add(BorderLayout.CENTER,creatImage());
		return p;
	}
	private JPanel creatImage() {
		JPanel p=new JPanel(new FlowLayout());
		JLabel label1=new JLabel();
		JLabel label2=new JLabel();
		URL url = ExamLoginFrame.class.getResource("tarena.png");
		URL url2 = ExamLoginFrame.class.getResource("exam_title.png");
	    ImageIcon ico = new ImageIcon(url);
	    ImageIcon ico2 = new ImageIcon(url2);
	    label1.setIcon(ico);
	    label2.setIcon(ico2);
		p.add(label1);
		p.add(label2);
		return p;
	}
	private JLabel info;
	public void updateView(User user) {
		String str="欢迎:  "+user+"  同学参加达内基础测试！";
		info.setText(str);
	}
	
	
}
