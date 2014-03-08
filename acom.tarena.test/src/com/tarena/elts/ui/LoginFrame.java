package com.tarena.elts.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class LoginFrame extends JFrame{
	private static final long serailVersionUID= -7993721800179709451L;
	private ClientContext clientContext;
	public void setClientContext(ClientContext context){
		this.clientContext=context;
	}
	public LoginFrame(){
		init();
	}
	public void init(){
		setSize(300,180);
		setTitle("系统登录");
		setContentPane(createContentPane());
		setVisible(true);
		setDefaultCloseOperation(LoginFrame.EXIT_ON_CLOSE);
	}
	private JPanel createContentPane(){
		JPanel p=new JPanel(new BorderLayout());
		p.add(BorderLayout.NORTH,new JLabel("登录系统",JLabel.CENTER));
		p.add(BorderLayout.CENTER,createCenterPane());
		p.add(BorderLayout.SOUTH,createBtnPane());
		p.setBorder(new EmptyBorder(8,8,8,8));
		return p;
		
	}
	public JPanel createCenterPane(){
		JPanel p=new JPanel(new BorderLayout());
		p.add(BorderLayout.NORTH,createIdPwdPane());
		p.setBorder(new EmptyBorder(6,6,6,6));
		return p;
	}
	public JPanel createBtnPane(){
		JPanel p=new JPanel(new FlowLayout());
		JButton login=new JButton("登录");
		JButton cancel=new JButton("取消");
		p.add(login);
		p.add(cancel);
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//System.out.println("Longin Click");
				clientContext.login();
			}
		});
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				clientContext.exit(LoginFrame.this);
			}});
		return p;
	}
	private JPanel createIdPwdPane(){
		JPanel p=new JPanel(new GridLayout(2,1,0,8));
		p.add(createIdPane());
		p.add(createPwdPane());
		return p;
	}
	private JPanel createIdPane(){
		JPanel p=new JPanel(new BorderLayout());
		JLabel l=new JLabel("编号");
		// 务必将idField 引用到界面控件对象
		idField=new JTextField();
		p.add(BorderLayout.WEST,l);
		p.add(BorderLayout.CENTER,idField);
		return p;
	}
	private JPanel createPwdPane(){
		JPanel p=new JPanel(new BorderLayout());
		JLabel l=new JLabel("密码");
	    pwdField = new JPasswordField();
	    pwdField.enableInputMethods(true);
	    p.add(BorderLayout.WEST, l);
	    p.add(BorderLayout.CENTER, pwdField);
	    return p;
	}
	  //务必将idField 引用到界面控件对象
	  private JTextField idField;
	  public int getUserId() {
	    String str = idField.getText();
	    return Integer.parseInt(str); 
	  }
	//务必将pwdField 引用到界面控件对象
	  private JPasswordField pwdField;
	  public String getPwd() {
	    char[] pwd = pwdField.getPassword();
	    return new String(pwd); 
	  }
	
}
