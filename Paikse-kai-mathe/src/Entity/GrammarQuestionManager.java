package Entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class GrammarQuestionManager {
	
	private boolean firstTime = true;
	
	private ArrayList<String> questionList = new ArrayList<String>();
	private ArrayList<String> answerList = new ArrayList<String>();
	
	private int currentQuestionIndex = 0;
	
	private Color generalColor = Color.yellow;
	private Font generalFont = new Font("Century Gothic", Font.PLAIN, 23); // 23 kanoniko
	
	private ArrayList<Color> JTextFieldColor = new ArrayList<Color>();
	private int colorCounter = 0;
	
	private Font JTextFieldFont = new Font("Century Gothic", Font.BOLD, 23);
	
	private Color JTextFieldColorRightAnswer = Color.green;
	private Color JTextFieldColorWrongAnswer = Color.red;
	
	private BufferedImage TextFieldImage;
	
	private int height;

	private  ArrayList<Integer> width = new ArrayList<Integer>();
	
	private ArrayList<Integer> curX = new ArrayList<Integer>();
	private ArrayList<Integer> curY = new ArrayList<Integer>();
	
	private ArrayList<String> wordList = new ArrayList<String>();
	
	private String textFile;
	
	private String input = "";
	private String originalWord = "";
	
	private boolean check = false;
	
	private int score = 0;

	private boolean isGameOver = false;
	
	
	public GrammarQuestionManager(String subject, int difficulty){
		
		generateManager(subject, difficulty);
	}
	
	
	private void generateManager(String subject, int difficulty) {
		
		int c = 0;
		
		boolean num[] = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
		
		Random randomGenerator = new Random();
	    
		for (int i = 0; i < 7; i++){
	      int randomInt = randomGenerator.nextInt(20);
	      num[randomInt] = true;
	      
	    }
	    
	  
		
		try{
			InputStreamReader input = new InputStreamReader(getClass().getResourceAsStream("/"+difficulty +"/" +subject +".data"));
			BufferedReader IN = new BufferedReader(input);
			
			while(IN.ready()){
				
				
				String question = IN.readLine();
				String tempAnswer = IN.readLine();
				
				//Βάζει τυχαίες ερωτήσεις μέσα στην λίστα
				if(IN.ready() && IN.readLine().equals("==") && num[c] == true){
					questionList.add(question);
					answerList.add(tempAnswer);
					
					
				}
				c++;
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		try {
			
			TextFieldImage = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/GrammarTextField2.png"));
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
		for(int i=0; i<25; i++){ 
			this.JTextFieldColor.add(Color.black);
			
		}
		
	}
	
	public void getNextQuestion(){
		
		boolean size = false;
		
		//Έλέγχει αν βρισκόμαστε στην τελευταία ερώτηση της λίστας
		if(currentQuestionIndex == questionList.size()-1 ){
			currentQuestionIndex = 0;
			size = true;	
		}
		else{
			currentQuestionIndex++;
		}
		
		
		
		if(check == true && size == true){
			questionList.remove(questionList.size()-1);
			answerList.remove(answerList.size()-1);
			
			for(int i=0; i<JTextFieldColor.size(); i++){
				this.JTextFieldColor.set(i, Color.black);
			}
			
			check = false;
		}
		else if(check == true && size == false){
			
			questionList.remove(currentQuestionIndex-1);
			answerList.remove(currentQuestionIndex-1);
			
			for(int i=0; i<JTextFieldColor.size(); i++){
				this.JTextFieldColor.set(i, Color.black);
			}
			
			check = false;
			currentQuestionIndex--; // h = 0
			
			
		}
		
		// Έλέγχει αν τελείωσαν οι ερωτήσεις
		if(questionList.size() == 0){
			
			isGameOver = true;
		}
		else{
			textFile = questionList.get(currentQuestionIndex);
		
		}
		
	}
	
	public boolean isGameOver(){return isGameOver;}
	

	
	public void render(Graphics2D g){
		
		g.setColor(generalColor);
		
		g.setFont(generalFont);
		
		
		this.setTextFile(originalWord, input);
		
		
		this.drawString(g, textFile, GamePanel.WIDTH*150/GamePanel.HEIGHT, GamePanel.WIDTH*150/GamePanel.HEIGHT, GamePanel.WIDTH*450/GamePanel.HEIGHT); //250, 230, 850
		
	}
	
	public void drawString(Graphics2D g, String s, int x, int y, int width)
    {		
		this.setTextFile(originalWord, input);
		this.colorCounter = 0;
		
		
		
		// FontMetrics gives us information about the width,
    	// height, etc. of the current Graphics object's Font.
    	FontMetrics fm = g.getFontMetrics();
    	
    	int lineHeight = fm.getHeight();

    	int curX = x;
    	int curY = y;
    	 
    	 
    	String[] words = s.split(" ");
    	
    	

    	for (String word : words)
    	{

    		
    		
    		// Vres to platos ths le3hs. 
    		int wordWidth = fm.stringWidth(word + " ");
    		

    		// An to keimeno mazi me thn epomenh le3h ksepernane to platos 
    		// metakinhsou sthn epomenh seira.
    		if (curX + wordWidth >= x + width)
    		{
    			curY += lineHeight;
    			curX = x;
    		}
    		
    		//An h le3h ksekinaei me * topothetise ta TextField 
    		//alliws vale thn leksh
    		if(word.startsWith("*")){
    			 
    			this.curX.add(curX);
           	 	this.curY.add(curY-25);
           	 	this.wordList.add(word);
    			
    			String wordWithOutStar = word.substring(1);
    			wordWithOutStar = word.replace(word.substring(word.length()-1), "");
    			
    			g.setColor(JTextFieldColor.get(colorCounter));
    			this.colorCounter++;
     	        g.setFont(JTextFieldFont);
     	        
     	       if(g.getFontMetrics().stringWidth(wordWithOutStar) > 150){
	       			
	       			String tempInput = "";
	       			
	       			int counter =1;
	       			tempInput = wordWithOutStar;
	       			while(true){
	       				if(g.getFontMetrics().stringWidth(tempInput) > 180){
	       					tempInput = wordWithOutStar.substring(counter);
	       					counter++;
	       				}
	       				else{
	       					break;
	       				}
	       					
	       			}
	       			g.drawImage(TextFieldImage, curX, curY-25, 190, fm.getHeight(), null);
	           		g.drawString(tempInput, curX+8, curY);
     	       }
     	       else{
     	    	   g.drawImage(TextFieldImage, curX, curY-25, 190, fm.getHeight(), null);
     	    	   g.drawString(wordWithOutStar, curX+8, curY);
     	       }

     	       
     	       
     	       
    	        
     	       this.width.add(fm.stringWidth(word));
     	       this.height = fm.getHeight();
     	       
     	       wordWidth = 190;
     	       
    		}
    		else{
    			g.setFont(generalFont);
    			g.setColor(generalColor);
        		g.drawString(word, curX, curY);
        		
    		}
    		
    		// Metakinhsou deksia gia thn epomenh leksh
    		curX += wordWidth;
    		
    		
    	}
		
    	
    }
	
	public int getHeight() {
		return height;
	}

	public ArrayList<Integer> getWidth() {
		return width;
	}

	public ArrayList<Integer> getCurX() {
		return curX;
	}

	public ArrayList<Integer> getCurY() {
		return curY;
	}

	
	public String getCurrentQuestion() {
		
		return questionList.get(currentQuestionIndex);
	}
	
	public String [] getAnswerList(){
		
		String[] answer = this.answerList.get(currentQuestionIndex).split(" ");
		
		return answer;
	}
	
	public ArrayList<String> getWordList() {
		return wordList;
	}
	
	public void setOriginalWord(String word){
		
		this.originalWord = word;
		
	}

	public void setinput(String word){
	
		String tempInput;
		tempInput = word;
		
		tempInput = "*" +tempInput +"*";
		this.input = tempInput;
	
	}
	
	public void setFirstTime(boolean s){
		
		this.firstTime = s;
		
	}
	
	private void setTextFile(String originalWord, String input){
		
		if(firstTime && questionList.size() != 0){
			textFile = questionList.get(currentQuestionIndex);
		}
		else if(check){
			this.checkQuestion();
			
		}
		else if (originalWord != ""){   // alliws vazei asterakia kai ginontai ola eikones        // firstTime == false    // allagh den thn exoun ta paidiaaaaaaaaaaaaaa
			
			textFile = textFile.replace(originalWord, input);
			
			
		}
		else {
			
		}
		
	}


	public void checkQuestion() {
		boolean found = false;
		int counter = 0;
		int tempScore = 0;
		String[] answers = this.getAnswerList();
		
		
		String s = textFile;
		
		String[] words = s.split(" ");
    	
    	
    	for (String word : words){
    		if(word.startsWith("*")){
    			// Έλέγχει αν οι απαντήσεις του χρήστη είναι οι σωστές απαντήσεις
    			for(int i=0; i<answers.length; i++){
    				
    				if(answers[i].equals(word)){ //current answer
    					
    					this.JTextFieldColor.add(counter, JTextFieldColorRightAnswer);
    					found = true;
    					tempScore++;
        				counter++;
        				
    				}
    				
    			}
    			if(found == false){
    				
					this.JTextFieldColor.add(counter, JTextFieldColorWrongAnswer);; 
					counter++;
					
				}
    			
    			found = false;
    		}
    	}
		score = tempScore;
		
	}
	public void setCheck(boolean ch){
		check = ch;
	}
	
	public int getScore(){
		
		int tempScore;
		
		tempScore =  score;
		
		return tempScore;
	}
}
