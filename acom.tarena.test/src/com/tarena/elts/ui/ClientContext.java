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
			// 登入成功
			loginFrame.setVisible(false);
			menuFrame.updateView(user);
			center(menuFrame);
			menuFrame.setVisible(true);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(loginFrame, "ID必须是数字");
		} catch (IdOrPwdException e) {
			e.printStackTrace();
			// 登陆失败
			JOptionPane.showMessageDialog(loginFrame, e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			JOptionPane.showMessageDialog(menuFrame, "系统错了！"+e.getMessage());
		}
	}
	public void exit(JFrame from){
		int val=JOptionPane.showConfirmDialog(from, "退出？");
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
	/** 当前正在答对题目, 开始考试时候, 初始化为第一题
	   * 每次翻页, 更新为新的当前题目 */
	  private QuestionInfo currentQuestion;
	  /** 当前考试的状态信息, 在开始考试时候初始化为 考试信息 
	   * 重构开始开始start()代码!
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
	    int val = JOptionPane.showConfirmDialog(examFrame, "交吗?");
	    if (val != JOptionPane.YES_OPTION) {
	      return;
	    }
	    gameOver();
	  }

	  public void result() {
	    try {
	      int score = examService.getScore();
	      JOptionPane.showMessageDialog(
	          menuFrame, "考试分数:"+score);
	      
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
	        //show 是需要显示的剩余时间
	        long show = end - System.currentTimeMillis();
	        examFrame.updateTime(show);
	      }
	    }, 0, 1000);
	    timer.schedule(new TimerTask(){
	      public void run() {
	        //timer.cancel();
	        gameOver();//提前交卷!
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
	          examFrame, "你的分数:"+score);
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
