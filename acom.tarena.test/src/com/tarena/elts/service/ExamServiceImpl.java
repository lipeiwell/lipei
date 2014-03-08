package com.tarena.elts.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.tarena.elts.entity.EntityContext;
import com.tarena.elts.entity.ExamInfo;
import com.tarena.elts.entity.Question;
import com.tarena.elts.entity.QuestionInfo;
import com.tarena.elts.entity.User;

public class ExamServiceImpl implements ExamService,Serializable{
	private static final long serialVersionUID = -89697097419788051L;
	private EntityContext entityContext;
	private boolean finish;
	private boolean started;
	public void setEntityContext(EntityContext entityContext){
		this.entityContext=entityContext;
		
	}
	public User login(int id, String pwd) throws IdOrPwdException {
		User user=entityContext.getUser(id);
		if(user==null){
			throw new RuntimeException("���޴��ˣ�");
		}
		if(user.getPassword().equals(pwd)){
			loginUser=user;
			return user;
		}
		throw new RuntimeException("�������");
	}
	private List<QuestionInfo> paper=new ArrayList<QuestionInfo>();

	private User loginUser;
	public QuestionInfo getQuestion(int index) {
		return paper.get(index);
	}
	public ExamInfo startExam() {
		if(finish){
			throw new RuntimeException("�����Ѿ�������");
		}
		if(started){
			throw new RuntimeException("�����Ѿ���ʼ��");
		}
		createPaper();
		ExamInfo  info=new ExamInfo();
		info.setQuestionCount(paper.size());
		info.setTimeLimit(entityContext.getTimeLimit());
		info.setTitle(entityContext.getTitle());
		info.setUser(loginUser);
		started=true;
		return info;
	}
	private void createPaper() {
		Random r=new Random();
		int idx=0;
		for(int i=Question.LEVEL1;i<=Question.LEVEL10;i++){
			List<Question> list=entityContext.findQuestion(i);
			Question q1=list.remove(r.nextInt(list.size()));
			Question q2=list.remove(r.nextInt(list.size()));
			paper.add(new QuestionInfo(idx++,q1));
			paper.add(new QuestionInfo(idx++,q2));
		}
	}
	public void saveUserAnswers(int idx, List<Integer> userAnswers) {
	    //�����û��𰸵������еĿ�����
	    paper.get(idx).setUserAnswers(userAnswers);
	  }
	  private int score = 0;
	  public int examOver() {
	    //�з�
	    for (QuestionInfo qInfo : paper) {
	      Question q = qInfo.getQuestion();
	      if(qInfo.getUserAnswers().equals(q.getAnswer())){
	        score+=q.getScore();
	      }
	    }
	    finish = true;
	    return score;
	  }
	  
	  public int getScore() {
	    if(!finish)
	      throw new RuntimeException("��û�п���ѽ!");
	    return score;
	  }
}
