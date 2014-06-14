package Entity;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;


public class GeographyQuestionManager{

	private static int numberOfAskedQuestions;				//αριθμός ερωτήσεων που έχουν ερωτηθεί
	private static final int maxNumberOfAskedQuestions=9;	//μέγιστος αριθμός ερωτήσεων 
	private static final String questionsFile="questions_Geography.txt";//αρχείο ερωτήσεων

	private ArrayList<Question_Geography> questionsGreece;	//ερωτήσεις Ελλάδας //Ε' Δημοτικού
	private ArrayList<Question_Geography> questionsEurope;	//ερωτήσεις Ευρώπης //Στ' Δημοτικού
	private ArrayList<Question_Geography> askedQuestions;	//ερωτηθείσες ερωτήσεις
	private boolean oncePlayed;

	public GeographyQuestionManager(){

		init();
		loadQuestions();
	}

	public void init() {

		//συνάρτηση αρχικοποίησης
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

	public GeographyQuestionManager loadQuestions(){
		//"φόρτωση" ερωτήσεων
		try{
			FileReader fr=new FileReader("Data/"+questionsFile);
			BufferedReader in = new BufferedReader(fr);

			String s;
			int x,y;

			do{
				s=in.readLine();
			}while(!s.trim().equals("Questions - Greece"));

			//προστίθενται οι ερωτήσεις για την Ελλάδα

			while(!(s=in.readLine()).trim().equals("Questions - Europe")){

				x=Integer.parseInt(in.readLine());
				y=Integer.parseInt(in.readLine());
				this.addQuestionGreece(s, new Point(x,y));
			}

			while(!s.trim().equals("Questions - Europe")){
				s=in.readLine();
			}

			//προστίθενται οι ερωτήσεις για την Ευρώπη
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
		if(!oncePlayed){//αν δεν έχουν περάσει μια φορά όλες οι ερωτήσεις 

			/*αν ο αριθμός των ερωτήσεων που έχουν ερωτηθεί είναι 
			 *ίσος με τον αριθμό των μέγιστων δυνατών ερωτήσεων
			 *η μεταβλητή oncePlayed παίρνει την τιμή true
			 */
			if(numberOfAskedQuestions>=maxNumberOfAskedQuestions){
				oncePlayed=true;
			}
			else{
				//η ερώτηση-όρισμα δηλώνεται ως ερωτηθείσα & 
				//προστιθεται στις ερωτηθείσες ερωτήσεις

				for(Question_Geography ques: questionsGreece){
					if(ques.getQuestion().equals(q.getQuestion())){
						ques.setAsked(true);
						numberOfAskedQuestions++;
					}
				}

				//επιλέγεται μια τυχαία ερώτηση από τις μη ερωτηθείσες και επιστρέφεται
				int number;
				do{
					Random generator = new Random(System.currentTimeMillis());
					number=generator.nextInt(getQuestionsGreece().size());
				}while(this.getQuestionsGreece().get(number).isAsked());

				this.askedQuestions.add(getQuestionsGreece().get(number));

				return getQuestionsGreece().get(number);
			}
		}
		//αν έχει ερωτηθεί το μέγιστο όριο ερωτήσεων
		if(oncePlayed){
			//αναζήτηση για την ερώτηση που έχει περάσει ως όρισμα
			int i;
			for(i=0; i<askedQuestions.size();i++){
				Question_Geography ques= askedQuestions.get(i);
				if(ques.getQuestion().equals(q.getQuestion())){
					break;
				}

			}
			//αυξάνεται ο δείκτης κατά 1 για να δείχνει την επόμενη ερώτηση
			i++;

			//αν ο δείκτης έχει ξεπεράσει το όριο, τότε ξεκινάει από την αρχή
			if(i==askedQuestions.size()) i=0;

			//αν από την ερώτηση-όρισμα μέχρι το τέλος υπάρχει μη απαντημένη ερώτηση, επιστρέφεται η ερώτηση.
			for(int j=i; j<askedQuestions.size();j++){
				Question_Geography ques= askedQuestions.get(j);
				if(!ques.isAnswered()){
					return ques;
				}
			}

			//αν από την αρχή μέχρι την ερώτηση-όρισμα υπάρχει μη απαντημένη ερώτηση, επιστρέφεται η ερώτηση αυτή.
			for(int j=0; j<i;j++){
				Question_Geography ques= askedQuestions.get(j);
				if(!ques.isAnswered()){
					return ques;
				}
			}
		}
		//τέλος αν έχουν απαντηθεί όλες οι ερωτήσεις , επιστρέφεται η τιμή null
		return null;
	}

	//αντίστοιχα με τον ίδιο τρόπο επιλέγεται και η ερώτηση για την Ευρώπη
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
