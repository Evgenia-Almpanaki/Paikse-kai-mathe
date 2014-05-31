package Entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

public class QuestionManager_Math {

	private static final String questionsFile="questions_Math.txt";

	private ArrayList<Question_Math> questions;
	private ArrayList<Question_Math> askedQuestions;
	private boolean oncePlayed;

	private Font questionFont;

	public QuestionManager_Math(){

		//font
		questionFont = new Font("Century Gothic", Font.ITALIC, 23);
		
		init();
		loadQuestions();
	}

	public Font getQuestionFont() {
		return questionFont;
	}

	public void print(){
		for(Question_Math q:questions){
			System.out.println("Question :"+q.getQuestion()+"\nAnswer:"+q.getAnswer());
		}
	}

	public void init() {

		questions = new ArrayList<Question_Math>();
		askedQuestions= new ArrayList<Question_Math>();
		oncePlayed=false;

	}

	public QuestionManager_Math loadQuestions(){

		try{
			FileReader fr=new FileReader("Data/"+questionsFile);
			BufferedReader in = new BufferedReader(fr);
			String question="";
			String s,ans;

			while(in.ready()){
				question="";
				while(!(s=in.readLine()).trim().equals("")){
					question+=s;
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
		
		if(!oncePlayed){
			int index, numberOfAskedQuestions=0;

			for(Question_Math ques: questions){
				if(ques.isAsked())
					numberOfAskedQuestions++;
			}
			if(numberOfAskedQuestions == questions.size())
				oncePlayed=true;
			else{
				do{
					Random generator = new Random(System.currentTimeMillis());
					index=generator.nextInt(questions.size());
				}while(questions.get(index).isAsked());

				questions.get(index).setAsked(true);
				askedQuestions.add(questions.get(index));
				
				return questions.get(index);
			}
		}
		if(oncePlayed){
			int i;
			for(i=0; i<askedQuestions.size();i++){
				Question_Math ques= askedQuestions.get(i);
				if(ques.getQuestion().equals(q.getQuestion())){
					break;
				}

			}
			
			i++;
			if(i==askedQuestions.size()) 
				i=0;
			
			for(int j=i; j<askedQuestions.size();j++){
				Question_Math ques= askedQuestions.get(j);
				if(!ques.isAnswered()){
					return ques;
				}
			}
			
			for(int j=0; j<i;j++){
				Question_Math ques= askedQuestions.get(j);
				if(!ques.isAnswered()){
					return ques;
				}
			}
		}
		return null;
	}
	public Question_Math getQuestion(int index){
		if(index<questions.size())
			return questions.get(index);
		return null;
	}
	public void drawString(Graphics2D g, String s, int x, int y, int width)
	{		

		// FontMetrics gives us information about the width,
		// height, etc. of the current Graphics object's Font.
		FontMetrics fm = g.getFontMetrics();

		int lineHeight = fm.getHeight();

		int curX = x;
		int curY = y;

		String[] words = s.split(" ");

		for (String word : words)
		{
			// words' width
			int wordWidth = fm.stringWidth(word + " ");


			// An to keimeno mazi me thn epomenh le3h ksepernane to platos 
			// metakinhsou sthn epomenh seira.
			if (curX + wordWidth >= x + width)
			{
				curY += lineHeight;
				curX = x;
			}

			g.setColor(Color.black);
			g.setFont(questionFont);
			g.drawString(word, curX, curY);


			// Metakinhsou deksia gia thn epomenh leksh
			curX += wordWidth;

		}


	}

}
