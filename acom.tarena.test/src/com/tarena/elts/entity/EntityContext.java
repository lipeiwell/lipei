package com.tarena.elts.entity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.*;

import com.tarena.elts.util.Config;

public class EntityContext implements Serializable {
	private static final long serialVersionUID = -9043674279239537777L;
	private HashMap<Integer, User> users = new HashMap<Integer, User>();
	private Config config;

	public EntityContext(Config config) {
		this.config = config;
		loadUsers(config.getString("UserFile"));
		loadQuestions(config.getString("QuestionFile"));
	}

	public User getUser(int id) {
		return users.get(id);
	}

	private void loadUsers(String filename) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(filename), "GBK"));
			String str;
			while ((str = in.readLine()) != null) {
				if (str.startsWith("#")) {
					continue;
				}
				if (str.trim().equals("")) {
					continue;
				}
				User u = parseUser(str);
				users.put(u.getId(), u);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private User parseUser(String str) {
		String[] data = str.split(":");
		User u = new User();
		u.setId(Integer.parseInt(data[0]));
		u.setName(data[1]);
		u.setPassword(data[2]);
		u.setEmail(data[3]);
		u.setPhone(data[4]);

		return u;

	}

	private Map<Integer, List<Question>> questions = new HashMap<Integer, List<Question>>();
	
	public List<Question> findQuestion(int level){
		return new ArrayList<Question>(questions.get(level));
	}
	
	private void loadQuestions(String file) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new BufferedInputStream(new FileInputStream(file)), "gbk"));
			String str;
			while ((str = in.readLine()) != null) {
				str = str.trim();
				if (str.startsWith("#") || str.equals("")) {
					continue;
				}
				Question q = parseQuestion(str, in);
				addQuestion(q);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private void addQuestion(Question q) {
		if(questions.containsKey(q.getLevel())){
			questions.get(q.getLevel()).add(q);
		}else{
			List<Question> list=new ArrayList<Question>();
			list.add(q);
			questions.put(q.getLevel(), list);
		}
		/*List<Question> list = questions.get(q.getLevel());
		if (list == null) {
			list = new ArrayList<Question>();
			questions.put(q.getLevel(), list);
		}
		list.add(q);*/
		/*System.out.println("q:ccccccccccccccccc"+q);
		System.out.println("list:ccccccccccccccc"+list);
		System.out.println("question:ccccccccccccccc"+questions);*/
	}
	private Question parseQuestion(String str, BufferedReader in)
			throws IOException {
		String[] data = str.split("[@,][a-z]+=");
		// str: @answer=2/3,score=5,level=5
		// 以上字符串 切为: 如下结果
		// data:{"","2/3","5","5"}
		Question q = new Question();
		q.setAnswer(parseAnswer(data[1]));
		q.setScore(Integer.parseInt(data[2]));
		q.setLevel(Integer.parseInt(data[3]));
		q.setTitle(in.readLine());// 读取标题
		List<String> options = new ArrayList<String>();
		options.add(in.readLine());// 连续读取4个选项
		options.add(in.readLine());
		options.add(in.readLine());
		options.add(in.readLine());
		q.setOptions(options);
		return q;
	}

	private List<Integer> parseAnswer(String answer) {
		List<Integer> list = new ArrayList<Integer>();
		String[] data = answer.split("/");
		for (String s : data) {
			list.add(Integer.parseInt(s));
		}
		return list;
	}

	public static void main(String[] args) {
		EntityContext ctx = new EntityContext(new Config("client.properties"));
		// ctx.loadUsers("user.txt");
		System.out.println(ctx.users);
		System.out.println(ctx.questions);
	}
	public int getTimeLimit(){
		return config.getInt("TimeLimit");
	}
	public String getTitle(){
		return config.getString("PaperTitle");
	}
}
