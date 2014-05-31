package Entity;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


public class QuestionManager_Geography implements Serializable{

	private static final long serialVersionUID = 1L;
	private static int numberOfAskedQuestions=0;
	private static final int maxNumberOfAskedQuestions=3;//9
	private static final String questionsFile="questions_Geography.txt";

	private ArrayList<Question_Geography> questionsGreece;
	private ArrayList<Question_Geography> questionsEurope;
	private ArrayList<Question_Geography> askedQuestions;
	private boolean oncePlayed;

	public QuestionManager_Geography(){

		init();
		loadQuestions();
	}

	public void init() {

		questionsGreece = new ArrayList<Question_Geography>();
		questionsEurope = new ArrayList<Question_Geography>();
		askedQuestions = new ArrayList<Question_Geography>();
		oncePlayed=false;
		numberOfAskedQuestions=0;

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

	public QuestionManager_Geography loadQuestions(){
		init();
		try{
			FileReader fr=new FileReader("Data/"+questionsFile);
			BufferedReader in = new BufferedReader(fr);
			String s;
			int x,y;

			do{
				s=in.readLine();
			}while(!s.trim().equals("Questions - Greece"));

			while(!(s=in.readLine()).trim().equals("Questions - Europe")){

				x=Integer.parseInt(in.readLine());
				y=Integer.parseInt(in.readLine());
				this.addQuestionGreece(s, new Point(x,y));
			}

			while(!s.trim().equals("Questions - Europe")){
				s=in.readLine();
			}

			while(in.ready()){
				s=in.readLine();
				x=Integer.parseInt(in.readLine());
				y=Integer.parseInt(in.readLine());
				this.addQuestionEurope(s, new Point(x,y));

			}

			in.close();
			fr.close();

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return this;
	}

	public Question_Geography getNextQuestionGreece(Question_Geography q){
		if(!oncePlayed){
			if(numberOfAskedQuestions>=maxNumberOfAskedQuestions){
				oncePlayed=true;
			}
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

				this.askedQuestions.add(getQuestionsGreece().get(number));

				return getQuestionsGreece().get(number);
			}
		}
		if(oncePlayed){
			int i;
			for(i=0; i<askedQuestions.size();i++){
				Question_Geography ques= askedQuestions.get(i);
				if(ques.getQuestion().equals(q.getQuestion())){
					break;
				}

			}
			i++;
			if(i==askedQuestions.size()) i=0;
			for(int j=i; j<askedQuestions.size();j++){
				Question_Geography ques= askedQuestions.get(j);
				if(!ques.isAnswered()){
					return ques;
				}
			}
		}
		return null;
	}

	public Question_Geography getNextQuestionEurope(Question_Geography q){
		if(!oncePlayed){
			if(numberOfAskedQuestions>=maxNumberOfAskedQuestions){
				oncePlayed=true;
			}
			else{

				for(Question_Geography ques: questionsEurope){
					if(ques.getQuestion().equals(q.getQuestion())){
						ques.setAsked(true);
						numberOfAskedQuestions++;
					}
				}

				int number;

				do{
					Random generator = new Random(System.currentTimeMillis());
					number=generator.nextInt(getQuestionsEurope().size());
				}while(this.getQuestionsEurope().get(number).isAsked());

				this.askedQuestions.add(getQuestionsEurope().get(number));

				return getQuestionsEurope().get(number);
			}
		}
		if(oncePlayed){
			int i;
			for(i=0; i<askedQuestions.size();i++){
				Question_Geography ques= askedQuestions.get(i);
				if(ques.getQuestion().equals(q.getQuestion())){
					break;
				}

			}
			i++;
			if(i==askedQuestions.size()) i=0;
			for(int j=i; j<askedQuestions.size();j++){
				Question_Geography ques= askedQuestions.get(j);
				if(!ques.isAnswered()){
					return ques;
				}
			}
		}
		return null;
	}




}
