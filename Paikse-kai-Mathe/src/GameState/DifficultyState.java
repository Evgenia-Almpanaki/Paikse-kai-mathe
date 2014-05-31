package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import Background.Background;
import Entity.MenuOptions;
import Main.GamePanel;

/*
 * Η κλάση αυτή είναι μια κατάσταση του παιχνιδιού, η κατάσταση στην οποία ο παίχτης 
 * καλείται να επιλέξει την δυσκολία του παιχνιδιού, δηλαδή την τάξη του δημοτικού που επιθημεί.
 * 
 */

public class DifficultyState extends GameState {
	
	//background
	private Background bg;
	
	//menu and title String options
	private int currentMenuChoise;
	private MenuOptions options;
	private String title;
	
	//menu and title fonts and colors
	private Color titleColor;
	private Color menuOptionsColor;
	private Font titleFont;
	private Font menuOptionsFont;
	
	//difficulty values
	public static final int TETARTH_DHMOTIKOU = 0;
	public static final int PEMPTH_DHMOTIKOU = 1;
	public static final int EKTH_DHMOTIKOU = 2;

	//constructor
	public DifficultyState(GameStateManager gsm){
		this.gsm = gsm;
		
		bg = new Background("/Backgrounds/menubg.gif", 1);
		bg.setVector(0,0);
		
		options = new MenuOptions(new String[]{"Τετάρτη Δημοτικού","Πέμπτη Δημοτικού","Έκτη Δημοτικού"});
		currentMenuChoise = 0;
		title = "Επέλεξε Την Τάξη σου";
		
		titleColor = Color.RED.darker();
		menuOptionsColor = Color.YELLOW.darker();
		titleFont = new Font("Century Gothic",Font.PLAIN,68);
		menuOptionsFont = new Font("Arial",Font.PLAIN,42);
	}
	
	public void init() {}

	//updates the game
	public void update() {
		bg.update();
	}

	//draws stuff for this state
	public void render(Graphics2D g) {
		
		//draw background
		bg.render(g);
		
		//draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString(title, GamePanel.WIDTH/2 - g.getFontMetrics().stringWidth(title)/2, 100);
		
		//draw menu options
		g.setFont(menuOptionsFont);
		for(int i=0;i<options.length();i++){
			if(currentMenuChoise == i){
				g.setColor(Color.BLACK);
			}
			else{
				g.setColor(menuOptionsColor);
			}
			FontMetrics fm = g.getFontMetrics();
			
			g.drawString(options.getOption(i), GamePanel.WIDTH/2 - fm.stringWidth(options.getOption(i))/2, GamePanel.HEIGHT/3 + i* GamePanel.HEIGHT/6 + fm.getMaxAscent() + fm.getLeading());
			options.setHeight(fm.getHeight(), i);
			options.setWidth(fm.stringWidth(options.getOption(i)), i);
			options.setX(GamePanel.WIDTH/2 - options.getWidth(i)/2, i);
			options.setY(GamePanel.HEIGHT/3 + i* GamePanel.HEIGHT/6, i);
		}
	}
	
	//sets the new state that has been selected
	private void select(){
		
		if(currentMenuChoise == 0)
			gsm.setDifficulty(TETARTH_DHMOTIKOU);
		else if(currentMenuChoise == 1)
			gsm.setDifficulty(PEMPTH_DHMOTIKOU);
		else if(currentMenuChoise == 2)
			gsm.setDifficulty(EKTH_DHMOTIKOU);
		
		gsm.getThread().suspend();
		gsm.setState(GameStateManager.PLAYING_MENU_STATE);
		gsm.getThread().resume();
		
	}

	//keyboard input
	public void keyPressed(int keyCode) {
		
		if(keyCode == KeyEvent.VK_ENTER){
			select();
		}
		else if(keyCode == KeyEvent.VK_UP){
			if(currentMenuChoise>0)
				currentMenuChoise--;
		}
		else if(keyCode == KeyEvent.VK_DOWN){
			if(currentMenuChoise<options.length() -1)
				currentMenuChoise++;
		}
		
	}

	public void keyReleased(int keyCode) {}

	//mouse input
	public void mouseClicked(int mouseType, int x, int y) {
		if(mouseType == MouseEvent.BUTTON1){
			int choise = options.checkIfOptionIsClicked(x, y);
			if(choise != -1){
				currentMenuChoise = choise;
				select();
			}
		}
	}

	public void mouseDragged() {}

	//mouse input
	public void mouseMoved(int x, int y) {
		int choise = options.checkIfOptionIsClicked(x, y);
		if(choise != -1){
			currentMenuChoise = choise;
		}
	}

}
