package Entity;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class QuestionManager_Geography implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private ArrayList<Question_Geography> questionsGreece;
	private ArrayList<Question_Geography> questionsEurope;
	
	public QuestionManager_Geography(){

		questionsGreece = new ArrayList<Question_Geography>();
		questionsEurope = new ArrayList<Question_Geography>();
	}
		
	public void addQuestionGreece(String q,Point p){
		questionsGreece.add(new Question_Geography(q,p));
	}
	public void addQuestionEurope(String q,Point p){
		questionsEurope.add(new Question_Geography(q,p));
	}

	public ArrayList<Question_Geography> getQuestionsGreece() {
		return questionsGreece;
	}

	public ArrayList<Question_Geography> getQuestionsEurope() {
		return questionsEurope;
	}
	

	public Question_Geography getNextQuestionGreece(Question_Geography q){
		boolean done=false;
		for(Question_Geography ques: questionsGreece){
			if(done==true)
				return ques;
			if(ques.getQuestion().equals(q.getQuestion())){
				done =true;
			}
		}
		return null;
		
	}
	
	public Question_Geography getNextQuestionEurope(Question_Geography q){
		boolean done=false;
		for(Question_Geography ques: questionsEurope){
			if(done==true)
				return ques;
			if(ques.getQuestion().equals(q.getQuestion())){
				done =true;
			}
		}
		return null;
		
	}
	

}
