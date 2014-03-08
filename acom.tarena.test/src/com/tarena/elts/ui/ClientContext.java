package com.tarena.elts.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.io.Serializable;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.tarena.elts.entity.ExamInfo;
import com.tarena.elts.entity.QuestionInfo;
import com.tarena.elts.entity.User;
import com.tarena.elts.service.ExamService;
import com.tarena.elts.service.IdOrPwdException;

public class ClientContext implements Serializable {
	private LoginFrame loginFrame;
	private ExamLoginFrame menuFrame;
	private ExamFrame examFrame;
	private WelcomeWindow welcomeWindow;
	private ExamService examService;

	public void setExamService(ExamService examService) {
		this.examService = examService;
	}

	public void setExamFrame(ExamFrame examFrame) {
		this.examFrame = examFrame;
	}

	// IOC
	public void setLoginFrame(LoginFrame loginFrame) {
		this.loginFrame = loginFrame;
	}

	public void setExamLoginFrame(ExamLoginFrame menuFrame) {
		this.menuFrame = menuFrame;
	}

	public void setWelcomeWindow(WelcomeWindow welcomeWindow) {
		this.welcomeWindow = welcomeWindow;
	}

	public void show() {
		loginFrame.setVisible(true);
	}

	public void login() {
		try {
			int id = loginFrame.getUserId();
			String pwd = loginFrame.getPwd();
			User user = examService.login(id, pwd);
			// ����ɹ�
			loginFrame.setVisible(false);
			menuFrame.updateView(user);
			center(menuFrame);
			menuFrame.setVisible(true);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(loginFrame, "ID����������");
		} catch (IdOrPwdException e) {
			e.printStackTrace();
			// ��½ʧ��
			JOptionPane.showMessageDialog(loginFrame, e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(menuFrame, "ϵͳ���ˣ�"+e.getMessage());
		}
	}
	public void exit(JFrame from){
		int val=JOptionPane.showConfirmDialog(from, "�˳���");
		if(val==JOptionPane.YES_OPTION){
			System.exit(0);
		}
	}

	public void start() {
		try{
		examInfo=examService.startExam();
		QuestionInfo questionInfo=examService.getQuestion(0);
		currentQuestion = questionInfo;
		examFrame.updateView(examInfo, questionInfo);
		menuFrame.setVisible(false);
		center(examFrame);
	    examFrame.setVisible(true);
	    startTimer();
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(menuFrame, e.getMessage());
		}
	}
	/** ��ǰ���ڴ����Ŀ, ��ʼ����ʱ��, ��ʼ��Ϊ��һ��
	   * ÿ�η�ҳ, ����Ϊ�µĵ�ǰ��Ŀ */
	  private QuestionInfo currentQuestion;
	  /** ��ǰ���Ե�״̬��Ϣ, �ڿ�ʼ����ʱ���ʼ��Ϊ ������Ϣ 
	   * �ع���ʼ��ʼstart()����!
	   */
	  private ExamInfo examInfo;
	  public void next() {
	    try {
	      int idx = currentQuestion.getQuestionIndex();
	      List<Integer> userAnswers = 
	          examFrame.getUserAnswers();
	      examService.saveUserAnswers(idx, userAnswers);
	      currentQuestion = examService.getQuestion(idx+1);
	      examFrame.updateView(examInfo, currentQuestion);
	    } catch (Exception e) {
	      e.printStackTrace();
	      JOptionPane.showMessageDialog(
	          examFrame, e.getMessage());
	    }
	  }
	  public void prev() {
	    try {
	      int idx = currentQuestion.getQuestionIndex();
	      List<Integer> userAnswers = 
	          examFrame.getUserAnswers();
	      examService.saveUserAnswers(idx, userAnswers);
	      currentQuestion = examService.getQuestion(idx-1);
	      examFrame.updateView(examInfo, currentQuestion);
	    } catch (Exception e) {
	      e.printStackTrace();
	      JOptionPane.showMessageDialog(
	          examFrame, e.getMessage());
	    }
	  }

	  public void send() {
	    int val = JOptionPane.showConfirmDialog(examFrame, "����?");
	    if (val != JOptionPane.YES_OPTION) {
	      return;
	    }
	    gameOver();
	  }

	  public void result() {
	    try {
	      int score = examService.getScore();
	      JOptionPane.showMessageDialog(
	          menuFrame, "���Է���:"+score);
	      
	    } catch (Exception e) {
	      e.printStackTrace();
	      JOptionPane.showMessageDialog(
	          menuFrame, e.getMessage());
	    }
	  }
	  
	  private void center(Window win){
	    Toolkit toolkit = Toolkit.getDefaultToolkit();
	    Dimension screen = toolkit.getScreenSize();
	    int x = (screen.width - win.getWidth()) / 2;
	    int y = (screen.height - win.getHeight())/2;
	    win.setLocation(x, y);
	  }
	  private Timer timer;
	  private void startTimer(){
	    timer = new Timer();
	    int timeout = examInfo.getTimeLimit()*60*1000;
	    final long end = System.currentTimeMillis() + timeout;
	    timer.schedule(new TimerTask(){
	      public void run() {
	        //show ����Ҫ��ʾ��ʣ��ʱ��
	        long show = end - System.currentTimeMillis();
	        examFrame.updateTime(show);
	      }
	    }, 0, 1000);
	    timer.schedule(new TimerTask(){
	      public void run() {
	        //timer.cancel();
	        gameOver();//��ǰ����!
	      }
	    }, timeout);
	  }
	  
	  public void gameOver() {
	    
	    try {
	      if(timer!=null){
	        timer.cancel();
	      }
	      int idx = currentQuestion.getQuestionIndex();
	      List<Integer> userAnswers = 
	          examFrame.getUserAnswers();
	      examService.saveUserAnswers(idx, userAnswers);
	      
	      int score = examService.examOver();
	      JOptionPane.showMessageDialog(
	          examFrame, "��ķ���:"+score);
	      examFrame.setVisible(false);
	      center(menuFrame);
	      menuFrame.setVisible(true);
	      
	    } catch (Exception e) {
	      e.printStackTrace();
	      JOptionPane.showMessageDialog(
	          examFrame, e.getMessage());
	    }

	  }
}
