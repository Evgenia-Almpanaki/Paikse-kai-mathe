package Entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JTextField;

public class GrammarQuestionManager {
	
	private boolean firstTime = true;
	
	private ArrayList<String> questionList = new ArrayList<String>();
	private ArrayList<String> answerList = new ArrayList<String>();
	
	private ArrayList<String> rightAnswer = new ArrayList<String>();
	
	private int currentQuestionIndex = 0;
	
	private Color generalColor = Color.yellow;
	private Font generalFont = new Font("Century Gothic", Font.PLAIN, 23); // 23 kanoniko
	
	private ArrayList<Color> JTextFieldColor = new ArrayList<Color>();
	private int colorCounter = 0;
	//private Color JTextFieldColor = Color.black;
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
	private String answerFile;
	
	private String input = "";
	private String originalWord = "";
	
	private boolean check = false;
	
	private int score = 0;

	
	
	public GrammarQuestionManager(String subject, int difficulty){
		
		generateManager(subject, difficulty);
	}
	
	
	private void generateManager(String subject, int difficulty) {
		try{
			InputStreamReader input = new InputStreamReader(getClass().getResourceAsStream("/"+difficulty +"/" +subject +".data"));
			BufferedReader IN = new BufferedReader(input);
			
			while(IN.ready()){
				
				String question = IN.readLine();
				String tempAnswer = IN.readLine();
				
				
				if(IN.ready() && IN.readLine().equals("==")){
					questionList.add(question);
					answerList.add(tempAnswer);
					
					
				}
				
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
		
		for(int i=0; i<25; i++){ //25 tuxaio! allaghhh
			this.JTextFieldColor.add(Color.black);
			//this.JTextFieldColor.get(i) = Color.black;
		}
		
	}
	
	public void getNextQuestion(){
		
		boolean size = false;
		
		// && questionList.size() != 0 
		if(currentQuestionIndex == questionList.size()-1){
			System.out.println("2");
			currentQuestionIndex = 0;
			size = true;
			
		}
		else if(questionList.size() == 1){
			System.out.println("3");
			this.textFile = "ygtfyjgjhg *12345* hgj *opaaaaaa*";
		}
		else{
			System.out.println("4");
			currentQuestionIndex++;
		
		}
		
		
		
		if(check == true && size == true){
			questionList.remove(questionList.size()-1);
			this.answerList.remove(questionList.size()-1);
			
			for(int i=0; i<JTextFieldColor.size(); i++){
				this.JTextFieldColor.set(i, Color.black);
			}
			
			check = false;
		}
		else if(check == true && size == false){
			//for(int i=currentQuestionIndex-1; i<questionList.size()-1; i++){
				//questionList.set(i, questionList.get(i+1));
			//}
			
			questionList.remove(currentQuestionIndex-1);
			this.answerList.remove(currentQuestionIndex-1);
			
			//questionList.remove(textFile);
			//answerList.remove(answerList.get(currentQuestionIndex));
			for(int i=0; i<JTextFieldColor.size(); i++){
				this.JTextFieldColor.set(i, Color.black);
			}
			
			check = false;
			currentQuestionIndex--; // h = 0
			
			System.out.println("1");
		}
		
		// 1 allagh
		if(questionList.size() == 0){
			//System.out.println("3");
			
			System.exit(0);
		}
		//teleutaia allagh
		textFile = questionList.get(currentQuestionIndex);
		
		//this.answerFile = answerList.get(currentQuestionIndex);
		//String[] answers = this.getAnswerList();
		//answerList.get(currentQuestionIndex);
		//this.rightAnswer = this.answerList.get(currentQuestionIndex);
	}
	

	
	public void render(Graphics2D g){
		
		g.setColor(generalColor);
		
		g.setFont(generalFont);
		
		
		//textFile = questionList.get(currentQuestionIndex);
		this.setTextFile(originalWord, input);
		
		
		this.drawString(g, textFile, 250, 230, 850);
		
		
		
		
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
    			//System.out.println("ksana"); 
    			this.curX.add(curX);
           	 	this.curY.add(curY-25);
           	 	this.wordList.add(word);
    			
    			String wordWithOutStar = word.substring(1);
    			wordWithOutStar = word.replace(word.substring(word.length()-1), "");
    			
    			g.setColor(JTextFieldColor.get(colorCounter));
    			this.colorCounter++;
     	        g.setFont(JTextFieldFont);
     	        
     	       
     	       g.drawImage(TextFieldImage, curX, curY-25, 190, fm.getHeight(), null);
     	       g.drawString(wordWithOutStar, curX+8, curY);
     	       
    	        
     	       this.width.add(fm.stringWidth(word));
     	       this.height = fm.getHeight();
     	       
     	       //this.wordList.add(word);
     	       //this.setWordList(wordList);
     	       wordWidth = 190;
     	       
    		}
    		else{
    			g.setFont(generalFont);
    			g.setColor(generalColor);
        		g.drawString(word, curX, curY);
        		
    		}
    		
    		// Metakinhsou deksia gia thn epomenh leksh
    		curX += wordWidth;
    		
    		//input = "";
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
		//String[] answer = this.answerFile.split(" ");
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
		//    2 allagh
		if(firstTime && questionList.size() != 0){
			textFile = questionList.get(currentQuestionIndex);
		}
		else if(check){
			this.checkQuestion();
			
		}
		else{           // firstTime == false
			
			textFile = textFile.replace(originalWord, input);
			
			//System.out.println(textFile);
		}
		
	}


	public void checkQuestion() {
		boolean found = false;
		int counter = 0;
		int tempScore = 0;
		String[] answers = this.getAnswerList();
		
		//String answers[] =  this.answerList.get(currentQuestionIndex).split(" ");
		//la8os oi answers sugrinei  swstes apanthseis apo to prwto -auto pou exw valei
		
		String s = textFile;
		
		String[] words = s.split(" ");
    	
    	//System.out.println("in");
    	
    	for (String word : words){
    		if(word.startsWith("*")){
    			for(int i=0; i<answers.length; i++){
    				
    				if(answers[i].equals(word)){ //current answer
    					//System.out.println("in2222222");
    					this.JTextFieldColor.add(counter, JTextFieldColorRightAnswer);
    					found = true;
    					tempScore++;
        				counter++;
        				System.out.println("found");
        				System.out.println(answers[i] + word);
    				}
    				System.out.println(answers[i] + word);
    			}
    			if(found == false){
    				//System.out.println("in3333");
					this.JTextFieldColor.add(counter, JTextFieldColorWrongAnswer);; // oti einai to teleutaio! pernoun ena xrwma
					counter++;
					System.out.println("not found");
					System.out.println(word);
				}
    			
    			found = false;
    		}
    	}
		score = tempScore;
		
	}
	public void setCheck(boolean ch){
		check = ch;
	}
	
	public String getScore(){
		
		String tempScore;
		
		tempScore = "Σκόρ: " + score;
		
		return tempScore;
	}
}
