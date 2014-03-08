package com.tarena.elts.client;

import com.tarena.elts.entity.EntityContext;
import com.tarena.elts.service.ExamServiceImpl;
import com.tarena.elts.ui.ClientContext;
import com.tarena.elts.ui.ExamFrame;
import com.tarena.elts.ui.LoginFrame;
import com.tarena.elts.ui.ExamLoginFrame;
import com.tarena.elts.ui.WelcomeWindow;
import com.tarena.elts.util.Config;

public class Main {
	  public static void main(String[] args) {
	    //初始化软件组件(零件)
	    LoginFrame loginFrame = new LoginFrame();
	    //loginFrame.setDefaultCloseOperation(LoginFrame.EXIT_ON_CLOSE);
	    ExamFrame examFrame = new ExamFrame();
	    ExamLoginFrame menuFrame = new ExamLoginFrame();
	    WelcomeWindow welcomeWindow = new WelcomeWindow();
	    ClientContext clientContext = new ClientContext();
	    ExamServiceImpl service = new ExamServiceImpl();
	    Config config = new Config("client.properties");
	    EntityContext entityContext = new EntityContext(config);
	    
	    //组装组件
	    service.setEntityContext(entityContext);
	    loginFrame.setClientContext(clientContext);
	    menuFrame.setClientContext(clientContext);
	    examFrame.setClientContext(clientContext);
	    clientContext.setExamService(service);
	    clientContext.setLoginFrame(loginFrame);
	    clientContext.setExamFrame(examFrame);
	    clientContext.setExamLoginFrame(menuFrame);
	    clientContext.setWelcomeWindow(welcomeWindow);
	    //启动软件界面
	    clientContext.show();

	  }

	}
