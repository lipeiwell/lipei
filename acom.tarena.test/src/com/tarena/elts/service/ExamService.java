/**
 * 
 */
package com.tarena.elts.service;

import java.util.List;

import com.tarena.elts.entity.User;
import com.tarena.elts.service.IdOrPwdException;
import com.tarena.elts.entity.QuestionInfo;
import com.tarena.elts.entity.ExamInfo;

public interface ExamService {
	User login(int id,String pwd)
	throws IdOrPwdException;
	ExamInfo startExam();
	QuestionInfo getQuestion(int index);
	void saveUserAnswers(int idx, List<Integer> userAnswers);
	int getScore();
	int examOver();

}
