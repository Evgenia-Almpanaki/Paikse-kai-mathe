package Entity;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class QuestionManager_Geography implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Question_Geography> questions;
	
	public QuestionManager_Geography(){
		
		questions = new ArrayList<Question_Geography>();
	}
	
	public void showQuestionsOnScreen(JFrame j){
		
		String s="Questions                                          Point\n";
		
		for(Question_Geography q: questions){
			s+=q.getQuestion()+"   ("+q.getPoint().x+","+q.getPoint().y+")\n";
		}
		
		JOptionPane.showMessageDialog(j, s, "List of Questions", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void addQuestion(String q,Point p){
		questions.add(new Question_Geography(q,p));
	}

	public ArrayList<Question_Geography> getQuestions() {
		return questions;
	}
	

	public Question_Geography getNextQuestion(Question_Geography q){
		boolean done=false;
		for(Question_Geography ques: questions){
			if(done==true)
				return ques;
			if(ques.getQuestion().equals(q.getQuestion())){
				done =true;
			}
		}
		return null;
		
	}
	

}
