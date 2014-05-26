package GameState;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import Background.Background;
import Entity.GameButton;
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
	private MessageBox messageBox;
	private boolean displayMessage;
	
    
    
    //103
    private String title = "Συμπλήρωσε τα κενά με την κατάλληλη λέξη: ";
    
    
    private String textFile = "Σε μισή ώρα θα αρχίσει η παράσταση στη μεγάλη σκηνή. *Παρακολουθείτε* ένα εκπληκτικό μεγαλειώδες θέαμα."
    		+ " *Βλέπετε* ακροβάτες που *περπατούν* σε τεντωμένο σκοινή και *εκτελούν* επικίνδυνα ακροβατικά. Δέκα λευκά άλογα "
    		+ "*καλπάζουν* όλο χάρη γύρω γύρω στην πίστα και *στέκονται* στα δύο μπροστινά τους πόδια. Θηριοδαμαστές *δαμάζουν* άγρια "
    		+ "ζώα, όπως λοντάρια και τίγρεις. *Απολαμβάνετε* επιδείξεις ταχυδακτυλουργών και παντομίμες αστείων κλόουν. *Εμφανίζονται*"
    		+ " στη σκηνή φακίριδες που *καταπίνουν* σπαθιά και *κάθονται* πάνω σε καρφιά.";
    
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
	
	
	
	private ArrayList<JTextField> field = new ArrayList<JTextField>();
	
    
    public GameGrammarState(GameStateManager gsm) {
    	
    	this.gsm = gsm;
        //ImageIcon image = new ImageIcon(this.getClass().getResource(s));
        
        bg = new Background("/Backgrounds/board.jpg", 1);
		bg.setVector(0, 0); // kinhsh
		
		
		buttonSkip = new GameButton("/Textures/buttonNext.png");
		buttonSkip.setX(GamePanel.WIDTH - buttonSkip.getWidth() - 10);
		buttonSkip.setY(GamePanel.HEIGHT - buttonSkip.getHeight() - 10);
		
		buttonCheck = new GameButton("/Textures/buttonCheck.png");
		buttonCheck.setX(GamePanel.WIDTH - buttonCheck.getWidth() - 10);
		buttonCheck.setY(buttonSkip.getY() - buttonCheck.getHeight() - 10);
		
		buttonExit = new GameButton("/Textures/exitButton.png");
		buttonExit.setX(10);
		buttonExit.setY(GamePanel.HEIGHT - buttonExit.getHeight() - 10);  
        
        
		
        
    }

    public void render(Graphics2D g) {

        bg.render(g); 
        buttonCheck.render(g);
        buttonSkip.render(g);
        
        
        g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString(title, 55, 60);
		
		g.setColor(generalColor);
		
		g.setFont(generalFont);
		
		this.drawString(g, textFile, 250, 230, 850);
        
		
        
    }
    
    public void drawString(Graphics g, String s, int x, int y, int width)
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
    			String wordWithOutStar = word.substring(1);
    			wordWithOutStar = word.replace(word.substring(word.length()-1), "");
    			//wordWithOutStar = wordWithOutStar.substring(1);
    			JTextField field2 = new JTextField(wordWithOutStar);
    			field2.setForeground(JTextFieldColor);
    			field2.setFont(JTextFieldFont);
    	        field2.setBounds(new Rectangle(curX, curY-25, fm.stringWidth(word), fm.getHeight()));
    	       field2.paint(g);
    	        
    	        
    	        field.add(field2);
    	        
    	        
    		}
    		else{
    			
        		g.drawString(word, curX, curY);
        		
    		}
    		
    		// Metakinhsou deksia gia thn epomenh leksh
    		curX += wordWidth;
    	}
    }
    

	@Override
	public void mouseClicked(int mouseType, int x, int y) {
		for(int i=0; i<field.size();i++){
		String answer= field.get(i).getText();
		if(answer.contentEquals(rightAnswer[i])){
			field.get(i).setForeground(JTextFieldColorRightAnswer);
		}
		else{
			field.get(i).setForeground(JTextFieldColorWrongAnswer);
		}
		
		}
		
	}


	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyPressed(int keyCode) {
		// TODO Auto-generated method stub
		
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