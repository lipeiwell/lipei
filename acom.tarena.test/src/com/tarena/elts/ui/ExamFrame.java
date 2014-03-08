package com.tarena.elts.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.tarena.elts.entity.ExamInfo;
import com.tarena.elts.entity.QuestionInfo;

public class ExamFrame extends JFrame {

	private Option[] options = new Option[4];

	public ExamFrame() {
		init();
	}

	private void init() {
		setTitle("达内科技在线考试");
		setSize(600, 380);
		setContentPane(createContentPane());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
		      public void windowClosing(WindowEvent e) {
		         clientContext.send();
		      }
		    });
	}

	private JPanel createContentPane() {
		JPanel pane = new JPanel(new BorderLayout());
		pane.setBorder(new EmptyBorder(6, 6, 6, 6));
		ImageIcon icon = new ImageIcon(getClass().getResource("exam_title.png"));

		pane.add(BorderLayout.NORTH, new JLabel(icon));

		pane.add(BorderLayout.CENTER, createCenterPane());

		pane.add(BorderLayout.SOUTH, createToolsPane());

		return pane;
	}

	private JPanel createCenterPane() {
		JPanel pane = new JPanel(new BorderLayout());

		JLabel examInfo = new JLabel("姓名:XXX 考试:XXX 考试时间:XXX", JLabel.CENTER);
		info=examInfo;
		pane.add(BorderLayout.NORTH, examInfo);

		pane.add(BorderLayout.CENTER, createQuestionPane());
		pane.add(BorderLayout.SOUTH, createOptionsPane());
		return pane;
	}

	private JPanel createOptionsPane() {
		JPanel pane = new JPanel();
		Option a = new Option(0, "A");
		Option b = new Option(1, "B");
		Option c = new Option(2, "C");
		Option d = new Option(3, "D");
		options[0] = a;
		options[1] = b;
		options[2] = c;
		options[3] = d;
		pane.add(a);
		pane.add(b);
		pane.add(c);
		pane.add(d);
		return pane;
	}

	private JScrollPane createQuestionPane() {
		JScrollPane pane = new JScrollPane();
		pane.setBorder(new TitledBorder("题目"));
		JTextArea questionArea = new JTextArea();
		question = questionArea;
		questionArea.setText("问题\nA.\nB.");
		questionArea.setLineWrap(true);
		questionArea.setEditable(false);
		pane.getViewport().add(questionArea);
		return pane;
	}

	private JPanel createToolsPane() {
		JPanel pane = new JPanel(new BorderLayout());
		pane.setBorder(new EmptyBorder(0, 10, 0, 10));
		questionCount = new JLabel("题目:20 的 1题");
		JLabel time = new JLabel("剩余时间:222秒");
		pane.add(BorderLayout.WEST, questionCount);
		pane.add(BorderLayout.EAST, time);
		pane.add(BorderLayout.CENTER, createBtnPane());
		return pane;
	}

	private JPanel createBtnPane() {
		JPanel pane = new JPanel(new FlowLayout());
		JButton prev = new JButton("上一题");
		JButton next = new JButton("下一题");
		JButton send = new JButton("交卷");

		pane.add(prev);
		pane.add(next);
		pane.add(send);
		
		prev.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent e) {
		        clientContext.prev();
		      }
		    });
		    next.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent e) {
		        clientContext.next();
		      }
		    });
		    send.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent e) {
		        clientContext.send();
		      }
		    });

		return pane;
	}

	class Option extends JCheckBox {
		private static final long serialVersionUID = 5569918347028393191L;
		int val;

		public Option(int val, String txt) {
			super(txt);
			this.val = val;
		}
	}

	public void setClientContext(ClientContext clientContext) {
		    this.clientContext = clientContext;
		  }
	private JLabel info;
	private JLabel questionCount;
	private JTextArea question;
	private ClientContext clientContext;

	public void updateView(ExamInfo examInfo, QuestionInfo questionInfo) {
		info.setText(examInfo.toString());
		questionCount.setText("题目" + examInfo.getQuestionCount() + "的"
				+ (questionInfo.getQuestionIndex() + 1) + "题");
		question.setText(questionInfo.toString());
		updateOptions(questionInfo.getUserAnswers());
	}

	private void updateOptions(List<Integer> userAnswers) {
		for(Option o:options){
			o.setSelected(false);
			if(userAnswers.contains(o.val)){
				o.setSelected(true);
			}
		}
	}
	public List<Integer> getUserAnswers(){
		List<Integer>  ans=new ArrayList<Integer>(); 
		for(Option o:options){
			if(o.isSelected()){
				ans.add(o.val);
			}
		}
		return ans;
		
	}
	  private JLabel timer;
	  public void updateTime(long show) {
	    long h = show/1000/60/60;
	    long m = show/1000/60%60;
	    long s = show/1000%60;
	    if(m<=5){
	      timer.setForeground(Color.red);
	    }
	    timer.setText(h+":"+m+":"+s);
	  }
	

}