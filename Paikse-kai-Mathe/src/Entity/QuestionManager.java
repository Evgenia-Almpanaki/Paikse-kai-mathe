package Entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;

import javax.jws.Oneway;
import javax.swing.ImageIcon;

import Main.GamePanel;

public class QuestionManager {

	private ArrayList<Question> questionList;
	
	private int currentQuestionIndex=0;
	private int currentAnswer = -1;
	
	private Font questionFont;
	private Color questionColor;
	private Color choosenAnswer;
	
	public QuestionManager(String subject, int difficulty){
		questionList = new ArrayList<Question>();
		questionFont = new Font("Arial", Font.BOLD, 30);
		questionColor = Color.ORANGE;
		choosenAnswer = Color.BLACK;
		
		generateManager(subject, difficulty);
		
	}
	
	public Question getQuestionAtPosition(int position){return questionList.get(position);}
	public int getCurrentQuestionCorrectIndex(){return questionList.get(currentQuestionIndex).getCorrectPositionIndex();}
	public int getCurrentQuestionIndex(){return currentQuestionIndex;}
	public int getCurrentAnswer(){return currentAnswer;}
	public int guestionListLength(){return questionList.size();}
	public int size(){return questionList.size();}

	
	public void resetCurrentQuestionIndex(){currentQuestionIndex = 0;}
	public void resetCurrentAnswer(){currentAnswer = -1;}
	
	private void generateManager(String subject, int difficulty){
		
		try{
			//File questionData = new File("Data/"+difficulty +"/" +subject +".data");
			//FileInputStream inputStream = new FileInputStream(questionData);
			InputStreamReader input = new InputStreamReader(getClass().getResourceAsStream("/"+difficulty +"/" +subject +".data"));
			BufferedReader IN = new BufferedReader(input);
			
			while(IN.ready()){
				
				String tempLine = IN.readLine();
				String[] tempLineAnswers = {IN.readLine(), IN.readLine(), IN.readLine(), IN.readLine()};
				int tempCorrectIndex = Integer.parseInt(IN.readLine());
				
				if(IN.ready() && IN.readLine().equals("===")){
					Question newQuestion = new Question(tempLine, tempLineAnswers, tempCorrectIndex);
					questionList.add(newQuestion);
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void getNextQuestion(){
		if(questionList.size() > currentQuestionIndex){
			currentQuestionIndex++;
			currentAnswer = -1;
		}
	}
	
	public void removeQuestion(Question q){
		questionList.remove(q);
	}
	
	public void render(Graphics2D g){
		if(!(questionList.size() > currentQuestionIndex)){
			return;
		}
		Question currentQuestion = questionList.get(currentQuestionIndex);
	
		FontMetrics fm = g.getFontMetrics();
		g.setFont(questionFont);
		g.setColor(questionColor);
		
		//draw question and calculate question's positioning stuff
		g.drawString(currentQuestion.getQuestion(), GamePanel.WIDTH/2 - fm.stringWidth(currentQuestion.getQuestion())/2, GamePanel.HEIGHT/3 - fm.getHeight()/2 + fm.getMaxAscent() + fm.getLeading());
		currentQuestion.setHeight(fm.getHeight(), 0);
		currentQuestion.setWidth(fm.stringWidth(currentQuestion.getQuestion()), 0);
		currentQuestion.setX(GamePanel.WIDTH/2 - currentQuestion.getWidth(0)/2, 0);
		currentQuestion.setY(GamePanel.HEIGHT/3 - currentQuestion.getHeight(0)/2 , 0);
		
		//draw answers and calculate positioning stuff
		for(int i=0;i<4;i++){
			
			if(i+1 == currentAnswer){
				g.setColor(choosenAnswer);
			}
			else{
				g.setColor(questionColor);
			}
			
			g.drawString(currentQuestion.getAnswerInPosition(i) , GamePanel.WIDTH/2 - fm.stringWidth(currentQuestion.getAnswerInPosition(i))/2 , currentQuestion.getHeight(0)+currentQuestion.getY(0) + i*70 + fm.getMaxAscent() + fm.getLeading());
			currentQuestion.setHeight(fm.getHeight(), i+1);
			currentQuestion.setWidth(fm.stringWidth(currentQuestion.getAnswerInPosition(i)), i+1);
			currentQuestion.setX(GamePanel.WIDTH/2 - currentQuestion.getWidth(i+1)/2, i+1);
			currentQuestion.setY(currentQuestion.getHeight(0)+currentQuestion.getY(0) + i*70, i+1);
			
		}
		
	}
	
	public void mouseClicked(int x, int y){
		
		if(currentQuestionIndex >= questionList.size())
			return;
		int answer = questionList.get(currentQuestionIndex).whichAnswerIsClicked(x, y);
		
		if(answer != -1)
			currentAnswer = answer;
	}
	
}
