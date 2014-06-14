package GameState;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Background.Background;
import Entity.GameButton;
import Entity.GrammarQuestionManager;
import Entity.Player;
import Main.GamePanel;

// Οδηγίες του παινιδιού Γλώσσα
// Ο χρήστης κάνει κλικ με το ποντίκι σε μια απο τις λέξεις που βρίσκεται σε  TextField την οποία επιθυμεί να
// αλλάξει. Στη συνέχεια γράφει την σωστή λέξη και κάνει ένα κλιπ έξω απο τα TextField ώστε να καταχωριθεί  
// η καινούργια λέξη. Τέλος αφού διορθώσει όλες τις λέξεις πατάει το κουμπί Έλεγχος και του εμφανίζονται 
// με πράσινο χρώμα οι λέξεις τις οποίες διόρθωσε σωστά και με κόκκινο οι λανθασμένες. Επίσης ο χρήστης έχει την 
// επιλογή να αφήσει μια έρωτηση να την απαντήσει στο τέλος πατόντας το κουμπί Επόμενο.

public class GameGrammarState extends GameState{
	
	private Background bg;
	
	private int score;
	private GameButton buttonCheck;
	private GameButton buttonSkip;
	
	private Player player;
	
    private GrammarQuestionManager manager;
    
    //103
    private String title = "Συμπλήρωσε τα κενά με την κατάλληλη λέξη και κάνε κλικ στο κενό για να καταχωρηθεί: ";
    
    private String textFile; 

    private Color titleColor = Color.yellow;
	private Font titleFont = new Font("Century Gothic", Font.BOLD, 30); 
	
	private Color JTextFieldColor = Color.black;
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
	
	private boolean tonos = false;
	private boolean capsLockIsON = false;

	private boolean hasGameBeenPaused;
    
    public GameGrammarState(GameStateManager gsm) {
    	
    	this.gsm = gsm;
        
        
        bg = new Background("/Backgrounds/board.jpg", 1);
		bg.setVector(0, 0); // kinhsh
		
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
		
		
		hasGameBeenPaused = false;
		
    }
    
	@Override
	public void init() {
		if(!hasGameBeenPaused){
			manager = new GrammarQuestionManager("grammar", gsm.getDifficulty());
			player = gsm.getPlayer();
			score = player.getTempScore();
			textFile =  manager.getCurrentQuestion();
		}
		else{
			hasGameBeenPaused = false;
		}
		
		
	}

	@Override
	public void update() {
		if(manager.isGameOver()){
			gsm.setState(GameStateManager.GAME_OVER_STATE);
		}
	}

    public void render(Graphics2D g) {

        bg.render(g); 
        buttonCheck.render(g);
        buttonSkip.render(g);
        
        Color color =g.getColor();
        Font font=g.getFont();
        g.setFont(new Font("SansSerif",Font.PLAIN,18));
        g.setColor(Color.black);
       
        g.drawString("Πατήστε ESC για το μενού επιλογών", (int)(GamePanel.WIDTH/10.0), (int) (GamePanel.HEIGHT*(9.5/10)));
        
        g.setColor(color);
        g.setFont(font);
        
        if(manager == null){
        	manager = new GrammarQuestionManager("grammar", gsm.getDifficulty());
        }
        
        String tempScore = "Σκόρ: " + score;
        
        g.drawString(tempScore, 1200, 100); 
        
        g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString(title, 55, 60);
		
		
		manager.render(g);
		
		this.drawString(g, textFile, GamePanel.WIDTH*150/GamePanel.HEIGHT, GamePanel.WIDTH*150/GamePanel.HEIGHT, GamePanel.WIDTH*450/GamePanel.HEIGHT); //250, 230, 850
		
        
    }
    
    
    public void drawString(Graphics2D g, String s, int x, int y, int width)
    {
		
    	
    	// Αν έχει ο χρήστης έχει κάνει κλικ πάνω σε ένα TextField βάζει την καινούργια λέξη που πληκτρολογεί ο χρήστης στο TextField
    	if(input != null && this.isPressed ){
    		
    		g.setColor(JTextFieldColor);
 	        g.setFont(JTextFieldFont);
 	        
 	    	   
 	        
    		if(input.length() == 0){
        		g.drawString(input, this.curX.get(thesh), this.curY.get(thesh)+25);
        		
        	}
    		else if(g.getFontMetrics().stringWidth(input) > 150){
    			
    			String tempInput = "";
    			
    			int counter =1;
    			tempInput = input;
    			while(true){
    				if(g.getFontMetrics().stringWidth(tempInput) > 180){
    					tempInput = input.substring(counter);
    					counter++;
    				}
    				else{
    					break;
    				}
    					
    			}
    			g.drawImage(TextFieldImage, this.curX.get(thesh), this.curY.get(thesh), 190, this.height, null);
        		g.drawString(tempInput, this.curX.get(thesh)+8, this.curY.get(thesh)+25);
    		}
    		else {
        		g.drawImage(TextFieldImage, this.curX.get(thesh), this.curY.get(thesh), 190, this.height, null);
        		g.drawString(input, this.curX.get(thesh)+8, this.curY.get(thesh)+25);
        		
        		
        	}
    		
    	}
    	
    }
    
    public boolean textFieldImageIsClicked(int x, int y){
    	
    	this.curX = manager.getCurX();
    	this.curY = manager.getCurY();
    	this.width = manager.getWidth();
    	this.height = manager.getHeight();
    	this.wordList = manager.getWordList();
    	
    	boolean isPressed =false;
    	
    	//Ελέγχει αν η περιοχή που έκανε κλικ το ποντίκι ο χρήστης ανήκει στα TextField
    	for(int i=0; i<width.size(); i++){
			
			if(x >= this.curX.get(i) && x<=this.curX.get(i) + 190  && y>= this.curY.get(i) && y<= this.curY.get(i) + this.height){
				
				thesh = i;
				isPressed = true;
				this.originalWord = wordList.get(thesh);
				
			}
		}
    	
    	return isPressed;
    }
    

	@Override
	public void mouseClicked(int mouseType, int x, int y) {
		
		//Ελέγχει αν το κλικ με το ποντίκι έγινε σε ένα απο τα Textfield 
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
			score += manager.getScore();
			player.setTempScore(score);
			
		}
		else if(buttonSkip.isClicked(x, y)){
			manager.getNextQuestion();
			
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
			
			
			// Μετατρέπει τα αγγικά γράμματα που παίρνει απο το πληκτρολόγιο στα αντίστοιχα ελληνικά
			
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