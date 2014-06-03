package Entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

import Main.GamePanel;

public class QuestionManager_Math {

	private static final String questionsFile="questions_Math.txt";

	private ArrayList<Question_Math> questions;			//το σύνολο των ερωτήσεων
	private ArrayList<Question_Math> askedQuestions;	//το σύνολο των ερωτήσεων που έχουν ερωτηθεί
	private boolean oncePlayed;							//αν έχουν περάσει οι ερωτήσεις και τώρα επαναλαμβάνονται οι αγνοημένες ερωτήσεις

	//colors & fonts
	private Font questionFont;
	private Color questionColor;

	public QuestionManager_Math(){

		//font & color
		questionFont = new Font("Courier New", Font.ITALIC, (int) (15 * GamePanel.WIDTH/(double)GamePanel.HEIGHT));
		questionColor=Color.black;

		init();
	}

	public Font getQuestionFont() {
		return questionFont;
	}

	public void init() {
		//συνάρτηση αρχικοποίησης
		questions = new ArrayList<Question_Math>();
		askedQuestions= new ArrayList<Question_Math>();
		oncePlayed=false;

	}

	public QuestionManager_Math loadQuestions(String difficulty){
		//"φορτώνονται" οι ερωτήσεις από το αρχείο

		try{
			FileReader fr=new FileReader("Data/"+difficulty+"/"+questionsFile);
			BufferedReader in = new BufferedReader(fr);

			String question="";
			String s,ans;

			while(in.ready()){
				question="";
				while(!(s=in.readLine()).trim().equals("")){
					question += ("\n" + s);
				}

				ans=in.readLine();

				in.readLine();

				this.questions.add(new Question_Math(question.trim(), ans.trim()));

			}
			in.close();
			fr.close();

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return this;
	}

	public Question_Math getNextQuestion(Question_Math q){

		if(!oncePlayed){ //αν δεν έχουν περάσει μια φορά όλες οι ερωτήσεις 
			int index, numberOfAskedQuestions=0;

			//υπολογίζονται πόσες ερωτήσεις έχουν ερωτηθεί
			for(Question_Math ques: questions){
				if(ques.isAsked())
					numberOfAskedQuestions++;
			}

			//αν ο αριθμός των ερωτήσεων που έχουν ερωτηθεί είναι ίσος με τον αριθμό όλων των ερωτήσεων
			//η μεταβλητή oncePlayed παίρνει την τιμή true
			if(numberOfAskedQuestions == questions.size())
				oncePlayed=true;

			else{
				//επιλέγεται μια ερώτηση στην τύχη από τις εναπομείναντες
				do{
					Random generator = new Random(System.currentTimeMillis());
					index=generator.nextInt(questions.size());
				}while(questions.get(index).isAsked());

				//η ερώτηση δηλώνεται ως ερωτηθείσα & προστιθεται στις ερωτηθείσες ερωτήσεις
				questions.get(index).setAsked(true);
				askedQuestions.add(questions.get(index));

				return questions.get(index);
			}
		}
		//αν έχουν ερωτηθεί μια φορά όλες οι ερωτήσεις
		if(oncePlayed){
			//αναζήτηση για την ερώτηση που έχει περάσει ως όρισμα
			int i;
			for(i=0; i<askedQuestions.size();i++){
				Question_Math ques= askedQuestions.get(i);
				if(ques.getQuestion().equals(q.getQuestion())){
					break;
				}

			}
			//αυξάνεται ο δείκτης κατά 1 για να δείχνει την επόμενη ερώτηση
			i++;

			//αν ο δείκτης έχει ξεπεράσει το όριο, τότε ξεκινάει από την αρχή
			if(i==askedQuestions.size()) 
				i=0;
			//αν από την ερώτηση-όρισμα μέχρι το τέλος υπάρχει μη απαντημένη ερώτηση, επιστρέφεται η ερώτηση.
			for(int j=i; j<askedQuestions.size();j++){
				Question_Math ques= askedQuestions.get(j);
				if(!ques.isAnswered()){
					return ques;
				}
			}

			//αν από την αρχή μέχρι την ερώτηση-όρισμα υπάρχει μη απαντημένη ερώτηση, επιστρέφεται η ερώτηση αυτή.
			for(int j=0; j<i;j++){
				Question_Math ques= askedQuestions.get(j);
				if(!ques.isAnswered()){
					return ques;
				}
			}
		}
		//τέλος αν έχουν απαντηθεί όλες οι ερωτήσεις , επιστρέφεται η τιμή null
		return null;
	}

	//επιστρέφεται η index-η ερώτηση 
	public Question_Math getQuestion(int index){
		if(index<questions.size())
			return questions.get(index);
		return null;
	}

	//σχεδιάζεται η ερώτηση-κείμενο
	public void drawString(Graphics2D g, String s, int x, int y, int width)
	{		
		/*x,y η θέση του αντικειμένου
		 *width το μέγιστο δυνατό πλάτος που μπορεί να γραφτεί το κείμενο-ερώτηση
		 **/

		g.setColor(questionColor);
		g.setFont(questionFont);
		FontMetrics fm = g.getFontMetrics();

		//ύψος γραμμής
		int lineHeight = fm.getHeight();

		//δίνονται τιμές για το σημείο που γράφεται η πρώτη λέξη
		int curX = x;
		int curY = y;

		//χωρίζεται η ερώτηση σε λέξεις με βάση το που είναι τα κενά
		String[] words = s.split(" ");

		for (String word : words)//για κάθε λέξη
		{

			// πλάτος λέξης
			int wordWidth = fm.stringWidth(word + " ");

			/* Αν το κείμενο μαζί με την επόμενη λέξη ξεπερνάει 
			 * το μέγιστο δυνατό πλάτος ή η λέξη ξεκινά με "\n",
			 * μετακίνηση στην επόμενη σειρά.
			 * */
			if (curX + wordWidth >= x + width || word.startsWith("\n"))
			{
				curY += lineHeight; //το y αυξάνεται κατά μια σειρά
				curX = x;			//το x μετακινείται στο αρχικό x-πλάτος
			}
			//γράφεται η λέξη
			g.drawString(word, curX, curY);

			// Δεξιά μετακίνηση για την επόμενη λέξη
			curX += wordWidth;

		}
	}

}
