package com.tarena.test;

import java.util.List;

import com.tarena.elts.ui.ClientContext;
import com.tarena.elts.ui.ExamFrame;
import com.tarena.elts.ui.ExamLoginFrame;
import com.tarena.elts.ui.LoginFrame;
import com.tarena.elts.entity.ExamInfo;
import com.tarena.elts.entity.QuestionInfo;
import com.tarena.elts.entity.User;
import com.tarena.elts.service.ExamService;
import com.tarena.elts.service.IdOrPwdException;
import com.tarena.elts.ui.WelcomeWindow;

public class MVCDemo {
	
	public static void main(String[] args) {
		//初始化软件零件
		LoginFrame loginFrame=new LoginFrame();
		ExamFrame examFrame=new ExamFrame();
		ExamLoginFrame menuFrame=new ExamLoginFrame();
		WelcomeWindow welcomeWindow=new WelcomeWindow();
		ClientContext clientContext=new ClientContext();
		ExamService service=new ExamServiceDemo();
		//组装组件
		loginFrame.setClientContext(clientContext);
	    clientContext.setExamService(service);
	    clientContext.setLoginFrame(loginFrame);
	    clientContext.setExamFrame(examFrame);
	    clientContext.setExamLoginFrame(menuFrame);
	    clientContext.setWelcomeWindow(welcomeWindow);
	    //启动软件界面
	    clientContext.show();
	}
	  static class ExamServiceDemo implements ExamService{
		    public User login(int id, String pwd) 
		      throws IdOrPwdException {
		      if(id==1000 && pwd.equals("1234")){
		        return new User("Robin",1000,"1234");
		      }
		      throw new IdOrPwdException("用户名/密码错误!");
		    }

			@Override
			public QuestionInfo getQuestion(int i) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public ExamInfo startExam() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int examOver() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getScore() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public void saveUserAnswers(int idx, List<Integer> userAnswers) {
				// TODO Auto-generated method stub
				
			}
		  }
}
