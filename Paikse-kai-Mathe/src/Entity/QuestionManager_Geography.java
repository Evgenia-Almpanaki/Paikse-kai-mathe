package Entity;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


public class QuestionManager_Geography implements Serializable{

	private static final long serialVersionUID = 1L;
	private static int numberOfAskedQuestions=0;

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
		numberOfAskedQuestions++;
		if(numberOfAskedQuestions>10)
			return null;
		else{

			for(Question_Geography ques: questionsGreece){
				if(ques.getQuestion().equals(q.getQuestion())){
					ques.setAsked(true);
					numberOfAskedQuestions++;
				}
			}

			int number;

			do{
				Random generator = new Random(System.currentTimeMillis());
				number=generator.nextInt(getQuestionsGreece().size());
			}while(this.getQuestionsGreece().get(number).isAsked());
			return getQuestionsGreece().get(number);
		}

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
