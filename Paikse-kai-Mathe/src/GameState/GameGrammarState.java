package GameState;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import Background.Background;
import Entity.GameButton;
import Entity.GameTextField;
import Entity.GrammarQuestionManager;
import Entity.MessageBox;
import Entity.Player;
import Entity.QuestionManager;
import Main.GamePanel;

public class GameGrammarState extends GameState{
	
	private Background bg;
	
	private String score;
	private GameButton buttonCheck;
	private GameButton buttonSkip;
	private GameButton buttonExit;
	private Player player;
	//private MessageBox messageBox;
	//private boolean displayMessage;
	
    private GrammarQuestionManager manager;
    
    //103
    private String title = "Συμπλήρωσε τα κενά με την κατάλληλη λέξη: ";
    
    private String textFile; 
    
    private String[] rightAnswer = {"Παρακολουθείτε", "Βλέπετε", "περπατούν", "εκτελούν", "καλπάζουν", "στέκονται", "δαμάζουν",
    		"Απολαμβάνετε", "Εμφανίζονται", "καταπίνουν", "κάθονται"};
    
    private Color generalColor = Color.yellow;
	private Font generalFont = new Font("Century Gothic", Font.PLAIN, 23); // 23 kanoniko
	
	private Color titleColor = Color.yellow;
	private Font titleFont = new Font("Century Gothic", Font.BOLD, 34);
	
	private Color JTextFieldColor = Color.black;
	private Color JTextFieldColorRightAnswer = Color.green;
	private Color JTextFieldColorWrongAnswer = Color.red;
	private Font JTextFieldFont = new Font("Century Gothic", Font.BOLD, 23);
	
	
	
	private BufferedImage TextFieldImage;
	
	private String input = "";
	private String originalWord = "";
	
	private  ArrayList<String> wordList = new ArrayList<String>();

	private int height;

	private  ArrayList<Integer> width = new ArrayList<Integer>();
	
	private ArrayList<Integer> curX = new ArrayList<Integer>();
	private ArrayList<Integer> curY = new ArrayList<Integer>();
	
	private boolean isPressed = false;
	
	private int thesh;
	
	private boolean hasGameBeenPaused;
	
	boolean tonos = false;
	boolean capsLockIsON = false;
    
    public GameGrammarState(GameStateManager gsm) {
    	
    	//inputList.add("");
    	
    	this.gsm = gsm;
        //ImageIcon image = new ImageIcon(this.getClass().getResource(s));
        
        bg = new Background("/Backgrounds/board.jpg", 1);
		bg.setVector(0, 0); // kinhsh
		
		hasGameBeenPaused = false;
		
		try {
			
			TextFieldImage = ImageIO.read(getClass().getResourceAsStream("/Backgrounds/GrammarTextField2.png"));
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
		buttonSkip = new GameButton("/Textures/buttonNext.png");
		buttonSkip.setX(GamePanel.WIDTH - buttonSkip.getWidth() - 10);
		buttonSkip.setY(GamePanel.HEIGHT - buttonSkip.getHeight() - 10);
		
		buttonCheck = new GameButton("/Textures/buttonCheck.png");
		buttonCheck.setX(GamePanel.WIDTH - buttonCheck.getWidth() - 10);
		buttonCheck.setY(buttonSkip.getY() - buttonCheck.getHeight() - 10);
		
		buttonExit = new GameButton("/Textures/exitButton.png");
		buttonExit.setX(10);
		buttonExit.setY(GamePanel.HEIGHT - buttonExit.getHeight() - 10);  
        
        //TextFieldImage = new GameButton("/Backgrounds/GrammarTextField2.png");
		
		
    }
    
	@Override
	public void init() {
		if(!hasGameBeenPaused){
			manager = new GrammarQuestionManager("grammar", gsm.getDifficulty());
			player = gsm.getPlayer();
			score = "Σκόρ: " + player.getTempScore();
			textFile =  manager.getCurrentQuestion();
			rightAnswer = manager.getAnswerList();
		}
		else
			hasGameBeenPaused = false;
	}

	@Override
	public void update() {}

    public void render(Graphics2D g) {

        bg.render(g); 
        buttonCheck.render(g);
        buttonSkip.render(g);
        buttonExit.render(g);
        //System.out.println(manager);
        /*if(manager == null){
        	System.out.println("s");
        	manager = new GrammarQuestionManager("grammar", gsm.getDifficulty());
        }*/
        	
        score = manager.getScore();
        g.drawString(score, 1200, 60);
        
        g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString(title, 55, 60);
		
		
		manager.render(g);
		
		
		this.drawString(g, textFile, 250, 230, 850);
        
		
        
    }
    
    public void drawString(Graphics2D g, String s, int x, int y, int width)
    {
		
    	if(input != null && this.isPressed ){
    		
    		g.setColor(JTextFieldColor);
 	        g.setFont(JTextFieldFont);
 	        
 	    	   
 	        
    		if(input.length() == 0){
        		g.drawString(input, this.curX.get(thesh), this.curY.get(thesh)+25);
        		//System.out.println("1");
        	}
    		else {
        		g.drawImage(TextFieldImage, this.curX.get(thesh), this.curY.get(thesh), 190, this.height, null);
        		g.drawString(input, this.curX.get(thesh)+8, this.curY.get(thesh)+25);
        		
        		//manager.setinput(input);
        		//manager.setOriginalWord("*Παρακολουθείτε*");
        		
        		//System.out.println("3");
        	}
    	}
    	else if(input!= null &&this.isPressed){
    		/*for(int i=0; i<inputList.size(); i++){
    			//g.drawString(inputList.get(i), this.curX.get(i), this.curY.get(i)+25);
    		}*/
    	}
    	
    	
        
    	
    }
    
    public boolean textFieldImageIsClicked(int x, int y){
    	
    	this.curX = manager.getCurX();
    	this.curY = manager.getCurY();
    	this.width = manager.getWidth();
    	this.height = manager.getHeight();
    	this.wordList = manager.getWordList();
    	
    	boolean isPressed =false;
    	for(int i=0; i<curX.size(); i++){
			
			if(x >= this.curX.get(i) && x<=this.curX.get(i) + this.width.get(i)  && y>= this.curY.get(i) && y<= this.curY.get(i) + this.height){
				
				//System.out.println("Check");
				thesh = i;
				isPressed = true;
				this.originalWord = wordList.get(thesh);
				
			}
		}
    	
    	return isPressed;
    }
    

	@Override
	public void mouseClicked(int mouseType, int x, int y) {
		
		
		
		/*for(int i=0; i<inputList.size(); i++){
			//System.out.println(this.inputList.get(i));
			
		}*/
		
		
		//System.out.println("....");
		
		//Textfield pressed :)
		if(textFieldImageIsClicked(x, y)){
			
			this.isPressed = true;
			manager.setFirstTime(false);
		}
		else{
			this.isPressed = false;
			manager.setinput(input);
    		manager.setOriginalWord(this.originalWord);
    		input = "";
			
		}
		
		
		if(buttonCheck.isClicked(x, y)){
			manager.setCheck(true);
			manager.checkQuestion();
			//manager.removeQuestion(textFile);
			//manager.getNextQuestion();
			//this.isPressed = true;
			
			System.out.println("Check1");
		}
		else if(buttonSkip.isClicked(x, y)){
			manager.getNextQuestion();
			System.out.println("Check2");
		}
		else if(buttonExit.isClicked(x, y)){
			
			System.exit(0);
		}
		
		
	}




	@Override
	public void keyPressed(int keyCode) {
		
		if(keyCode == KeyEvent.VK_ESCAPE){
			PauseMenuState.setPreviousState(GameStateManager.GAME_GRAMMAR_STATE);
			gsm.setState(GameStateManager.PAUSE_MENU_STATE);
			hasGameBeenPaused = true;
			return;
		}
		
		if(this.isPressed){
			if(keyCode >= 65 && keyCode <= 90){
				capsLockIsON = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
				if(capsLockIsON)
					input += KeyEvent.getKeyText(keyCode);
				else{
					String temp = KeyEvent.getKeyText(keyCode);
					temp = temp.toLowerCase();
					input += temp;
				}
			}
			else if(keyCode == KeyEvent.VK_BACK_SPACE){
				if(input.length()>0){
					input = input.substring(0, input.length()-1);
					input.trim();
				}
			}
			else if(keyCode == 59){
				tonos = true;
			}
			
			
			
			//"\u03b1\u03b2\u03b3\u03b4\u03b5\u03b6\u03b7\u03b8\u03b9\u03ba\u03bb\u03bc\u03bd\u03be\u03bf\u03c0\u03c1\u03c3\u03c2\u03c4\u03c5\u03c6\u03c7\u03c8\u03c9";
			//    α     β     γ     δ     ε     ζ     η     θ     ι     κ     λ     μ     ν     ξ     ο     π     ρ     σ     ς     τ     υ     φ     χ     ψ     ω 
			//0x3b1 0x3b2 0x3b3 0x3b4 0x3b5 0x3b6 0x3b7 0x3b8 0x3b9 0x3ba 0x3bb 0x3bc 0x3bd 0x3be 0x3bf 0x3c0 0x3c1 0x3c3 0x3c2 0x3c4 0x3c5 0x3c6 0x3c7 0x3c8 0x3c9
			// 	a       b    g     d     e     z     h    u     i      k     l    m     n     j      o     p     r     s      w     t    y     f     x     c    v
			
			// έ υ ο ά ή ω
			
			
			if(input.endsWith("a") || input.endsWith("A")){
				if(capsLockIsON){
					if(tonos == true){
						input = input.replace(input.substring(input.length()-1), "");
						input += "\u0386";
						tonos = false;
					}
					else{
						input = input.replace(input.substring(input.length()-1), "");
						input += "\u0391";
					}
				}
				else{
					if(tonos == true){
						input = input.replace(input.substring(input.length()-1), "");
						input += "\u03AC";
						tonos = false;
					}
					else{
						input = input.replace(input.substring(input.length()-1), "");
						input += "\u03b1";
					}
				}
				
			}
			else if (input.endsWith("b") || input.endsWith("B")){
				if(capsLockIsON){
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u0392";
				}
					
				else{
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u03b2";
				}
				
			}
			else if (input.endsWith("c") || input.endsWith("C")){
				if(capsLockIsON){
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u03A8";
				}
				else{
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u03c8";
				}
			}
			else if (input.endsWith("d") || input.endsWith("D")){
				if(capsLockIsON){
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u0394";
				}
					
				else{
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u03b4";	
				}

			}
			else if (input.endsWith("e") || input.endsWith("E")){
				if(capsLockIsON){
					if(tonos == true){
						input = input.replace(input.substring(input.length()-1), "");
						input += "\u0388";
						tonos = false;
					}
					else{
						input = input.replace(input.substring(input.length()-1), "");
						input += "\u0395";
					}
				}
					
				else{
					if(tonos == true){
						input = input.replace(input.substring(input.length()-1), "");
						input += "\u03AD";
						tonos = false;
					}
					else{
						input = input.replace(input.substring(input.length()-1), "");
						input += "\u03b5";
					}
				}

			}
			else if (input.endsWith("f") || input.endsWith("F")){
				if(capsLockIsON){
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u03A6";	
				}
					
				else{
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u03c6";	
				}
				
			}
			else if (input.endsWith("g") || input.endsWith("G")){
				if(capsLockIsON){
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u0393";	
				}
					
				else{
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u03b3";	
				}
				
			}
			else if (input.endsWith("h") || input.endsWith("H")){
				if(capsLockIsON){
					if(tonos == true){
						input = input.replace(input.substring(input.length()-1), "");
						input += "\u0389";
						tonos = false;
					}
					else{
						input = input.replace(input.substring(input.length()-1), "");
						input += "\u0397";
					}
				}
					
				else{
					if(tonos == true){
						input = input.replace(input.substring(input.length()-1), "");
						input += "\u03AE";
						tonos = false;
					}
					else{
						input = input.replace(input.substring(input.length()-1), "");
						input += "\u03b7";
					}	
				}
				
			}
			else if (input.endsWith("i") || input.endsWith("I")){
				if(capsLockIsON){
					if(tonos == true){
						input = input.replace(input.substring(input.length()-1), "");
						input += "\u038A";
						tonos = false;
					}
					else{
						input = input.replace(input.substring(input.length()-1), "");
						input += "\u0399";
					}
				}
					
				else{
					if(tonos == true){
						input = input.replace(input.substring(input.length()-1), "");
						input += "\u03AF";
						tonos = false;
					}
					else{
						input = input.replace(input.substring(input.length()-1), "");
						input += "\u03b9";
					}	
				}
				
			}
			else if (input.endsWith("j") || input.endsWith("J")){
				if(capsLockIsON){
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u039E";	
				}
					
				else{
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u03be";	
				}
				
			}
			else if (input.endsWith("k") || input.endsWith("K")){
				if(capsLockIsON){
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u039A";	
				}
					
				else{
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u03ba";	
				}
				
			}
			else if (input.endsWith("m") || input.endsWith("M")){
				if(capsLockIsON){
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u039C";	
				}
					
				else{
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u03bc";	
				}
				
			}
			else if (input.endsWith("l") || input.endsWith("L")){
				if(capsLockIsON){
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u039B";	
				}
					
				else{
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u03bb";	
				}
				
			}
			else if (input.endsWith("n") || input.endsWith("N")){
				if(capsLockIsON){
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u039D";	
				}
					
				else{
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u03bd";	
				}
				
			}
			else if (input.endsWith("o") || input.endsWith("O")){
				if(capsLockIsON){
					if(tonos == true){
						input = input.replace(input.substring(input.length()-1), "");
						input += "\u038C";
						tonos = false;
					}
					else{
						input = input.replace(input.substring(input.length()-1), "");
						input += "\u039F";
					}
				}
					
				else{
					if(tonos == true){
						input = input.replace(input.substring(input.length()-1), "");
						input += "\u03CC";
						tonos = false;
					}
					else{
						input = input.replace(input.substring(input.length()-1), "");
						input += "\u03bf";
					}	
				}
				
			}
			else if (input.endsWith("p") || input.endsWith("P")){
				if(capsLockIsON){
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u03A0";
				}
					
				else{
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u03c0";
				}
				
			}
			else if (input.endsWith("q") || input.endsWith("Q")){
				if(capsLockIsON){
					input = input.replace(input.substring(input.length()-1), "");
					input += "";
				}
					
				else{
					input = input.replace(input.substring(input.length()-1), "");
					input += "";	
				}
				
			}
			else if (input.endsWith("r") || input.endsWith("R")){
				if(capsLockIsON){
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u03A1";	
				}
					
				else{
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u03c1";	
				}
				
			}
			else if (input.endsWith("s") || input.endsWith("S")){
				if(capsLockIsON){
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u03A3";	
				}
					
				else{
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u03c3";	
				}
				
			}
			else if (input.endsWith("t") || input.endsWith("T")){
				if(capsLockIsON){
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u03A4";
				}
					
				else{
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u03c4";	
				}
				
			}
			else if (input.endsWith("u") || input.endsWith("U")){
				if(capsLockIsON){
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u0398";
				}
					
				else{
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u03b8";	
				}
				
			}
			else if (input.endsWith("v") || input.endsWith("V")){
				if(capsLockIsON){
					if(tonos == true){
						input = input.replace(input.substring(input.length()-1), "");
						input += "\u038F";
						tonos = false;
					}
					else{
						input = input.replace(input.substring(input.length()-1), "");
						input += "\u03A9";
					}
				}
					
				else{
					if(tonos == true){
						input = input.replace(input.substring(input.length()-1), "");
						input += "\u03CE";
						tonos = false;
					}
					else{
						input = input.replace(input.substring(input.length()-1), "");
						input += "\u03c9";
					}	
				}
				
			}
			else if (input.endsWith("w") || input.endsWith("W")){
				if(capsLockIsON){
					input = input.replace(input.substring(input.length()-1), "");
					input += "";	
				}
					
				else{
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u03c2";	
				}
				
			}
			else if (input.endsWith("x") || input.endsWith("X")){
				if(capsLockIsON){
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u03A7";	
				}
					
				else{
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u03c7";	
				}
				
			}
			else if (input.endsWith("y") || input.endsWith("Y")){
				if(capsLockIsON){
					if(tonos == true){
						input = input.replace(input.substring(input.length()-1), "");
						input += "\u038E";
						tonos = false;
					}
					else{
						input = input.replace(input.substring(input.length()-1), "");
						input += "\u03A5";
					}	
				}
					
				else{
					if(tonos == true){
						input = input.replace(input.substring(input.length()-1), "");
						input += "\u03CD";
						tonos = false;
					}
					else{
						input = input.replace(input.substring(input.length()-1), "");
						input += "\u03c5";
					}	
				}
				
			}
			else if (input.endsWith("z") || input.endsWith("Z")){
				if(capsLockIsON){
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u0396";	
				}
					
				else{
					input = input.replace(input.substring(input.length()-1), "");
					input += "\u03b6";	
				}
				
			}
			
			
			
		}
		
	}

	@Override
	public void keyReleased(int keyCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(int x, int y) {
		// TODO Auto-generated method stub
		
	}
    
}