package Entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import Main.GamePanel;

public class HistoryQuestionManager {

	private ArrayList<Question_History> mainList;
	private ArrayList<Question_History> randomQuestionList;
	private ArrayList<Question_History> skippedQuestionsList;
	
	private int currentQuestionIndex=0;
	private int currentAnswer = -1;
	
	private Font questionFont;
	private Color questionColor;
	private Color choosenAnswer;
	
	public HistoryQuestionManager(String subject, int difficulty){
		mainList = new ArrayList<Question_History>();
		randomQuestionList = new ArrayList<Question_History>();
		skippedQuestionsList = new ArrayList<Question_History>();
		
		float w = GamePanel.WIDTH/(float)30;
		float h = GamePanel.HEIGHT/(float)30;
		float ar = Math.abs(w-h);
		questionFont = new Font("Arial", Font.PLAIN, (int)ar);
		questionColor = Color.GREEN.darker();
		choosenAnswer = Color.BLACK;
		
		generateManager(subject, difficulty);
		
	}
	
	public Question_History getQuestionAtPosition(int position){return randomQuestionList.get(position);}
	public int getCurrentQuestionCorrectIndex(){return randomQuestionList.get(currentQuestionIndex).getCorrectPositionIndex();}
	public int getCurrentQuestionIndex(){return currentQuestionIndex;}
	public int getCurrentAnswer(){return currentAnswer;}
	public int guestionListLength(){return randomQuestionList.size();}
	public int size(){return randomQuestionList.size();}
	public int skippedListSize(){return skippedQuestionsList.size();}
	
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
				
				if(IN.ready() && IN.readLine().trim().equals("===")){
					Question_History newQuestion = new Question_History(tempLine, tempLineAnswers, tempCorrectIndex);
					mainList.add(newQuestion);
				}
				
			}
			
			generateRandomList();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	private void generateRandomList(){
		int k = 20;
		int random = (int)(Math.random()*100 % k);
		
		for(int i=0;i<7;i++){
			randomQuestionList.add(mainList.remove(random));
			k--;
			random = (int)(Math.random()*100 % k);
		}
		
	}
	
	public void getNextQuestion(){
		
		if(randomQuestionList.size() > 1){
			skippedQuestionsList.add(randomQuestionList.remove(currentQuestionIndex));
			currentAnswer = -1;
		}
		else{
			skippedQuestionsList.add(randomQuestionList.remove(currentQuestionIndex));
			randomQuestionList = skippedQuestionsList;
			currentQuestionIndex = 0;
			currentAnswer = -1;
		}
	}
	
	public void removeQuestion(Question_History q){
		randomQuestionList.remove(q);
	}
	
	public void render(Graphics2D g){
		if(!(randomQuestionList.size() > currentQuestionIndex)){
			return;
		}
		Question_History currentQuestion = randomQuestionList.get(currentQuestionIndex);
	
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
		
		if(currentQuestionIndex >= randomQuestionList.size())
			return;
		int answer = randomQuestionList.get(currentQuestionIndex).whichAnswerIsClicked(x, y);
		
		if(answer != -1)
			currentAnswer = answer;
	}
	
}
