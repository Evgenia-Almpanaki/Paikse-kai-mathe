package Entity;

import java.io.Serializable;

import Main.GamePanel;

public class Question{

	private String question;
	private String[] answers;
	private int x[];
	private int y[];
	private int widths[];
	private int heights[];
	
	private int correctAnswer;
	private boolean answeredCorrect;
	
	public Question(String aQuestion, String[] allAnswers, int correct){
		
		question = aQuestion;
		answers = allAnswers;
		correctAnswer = correct;
		answeredCorrect = false;
		x= new int[5];
		y= new int[5];
		widths= new int[5];
		heights= new int[5];
		
	}
	
	public String getQuestion(){return question;}
	public String getAnswerInPosition(int position){return answers[position];}
	public String getCorrectAnswer(){return answers[correctAnswer];}
	public int getCorrectPositionIndex(){return correctAnswer;}
	public boolean isAnsweredCorrect(){return answeredCorrect;}
	public int getX(int position){return x[position];}
	public int getY(int position){return y[position];}
	public int getWidth(int position){return widths[position];}
	public int getHeight(int position){return heights[position];}
	
	public void setX(int x, int position){this.x[position] = x;}
	public void setY(int y, int position){this.y[position] = y;}
	public void setWidth(int value, int position){this.widths[position] = value;}
	public void setHeight(int value, int position){this.heights[position] = value;}
	public void setAnsweredResult(boolean answer){answeredCorrect = answer;}
	
	public int whichAnswerIsClicked(int x, int y){
		int result = -1;
		
		for(int i=1;i<=4;i++){
			
			if(x>= this.x[i] && x<= this.x[i] + widths[i] && y>= this.y[i] && y<= this.y[i] + heights[i]){
				return i;
			}
			else if((x< this.x[i] && x>this.x[i]+widths[i] && y<this.y[1] && y> this.y[4])){
				return -1;
			}
			
		}
		
		return result;
		
	}
	
}
