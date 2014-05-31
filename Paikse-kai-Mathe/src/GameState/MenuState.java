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

public class MenuState extends GameState {
	
	//menu background
	private Background background;
	
	//menu stuff
	private int currentMenuChoise;
	private MenuOptions options;
	
	//colors and fonts
	private Color titleColor;
	private Color menuOptionsColor;
	private Font titleFont;
	private Font menuOptionsFont;
	
	public MenuState(GameStateManager gsm){
		this.gsm = gsm;
		background = new Background("/Backgrounds/menubg.gif", 1);
		background.setVector(1, 0 );
		
		currentMenuChoise =0;
		titleColor = Color.BLACK;
		menuOptionsColor = Color.RED;
		
		titleFont = new Font("Century Gothic",Font.BOLD,68);
		menuOptionsFont = new Font("Arial",Font.PLAIN,42);
		
		options = new MenuOptions(new String[]{"Έναρξη","Βαθμολογίες","Έξοδος"});
	}

	public void init() {}

	public void update() {
		background.update();
	}

	public void render(Graphics2D g) {
		
		//draw background
		background.render(g);
		
		//draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("Παίξε - Μάθε", GamePanel.WIDTH/2 - g.getFontMetrics().stringWidth("Παίξε - Μάθε")/2, 120);
		
		//draw menu options
		g.setFont(menuOptionsFont);
		FontMetrics fm;
		for(int i = 0; i<options.length();i++){
			
			if(i == currentMenuChoise){
				g.setColor(Color.BLACK);
			}
			else{
				g.setColor(menuOptionsColor);
			}
			
			fm = g.getFontMetrics();
			g.drawString(options.getOption(i), GamePanel.WIDTH/2 - fm.stringWidth(options.getOption(i))/2, GamePanel.HEIGHT /3 +  i*GamePanel.HEIGHT/6 + fm.getMaxAscent() + fm.getLeading());
			options.setHeight(fm.getHeight(), i);
			options.setWidth(fm.stringWidth(options.getOption(i)), i);
			options.setX(GamePanel.WIDTH/2 - options.getWidth(i)/2, i);
			options.setY(GamePanel.HEIGHT /3 +  i*GamePanel.HEIGHT/6, i);
			
		}
		
	}

	public void keyPressed(int keyCode) {
		
		if(keyCode == KeyEvent.VK_ENTER){
			select();
		}
		else if(keyCode == KeyEvent.VK_UP){
			if(currentMenuChoise >0)
				currentMenuChoise--;
		}
		else if(keyCode == KeyEvent.VK_DOWN){
			if(currentMenuChoise < options.length() -1)
				currentMenuChoise++;
		}
		
	}
	
	private void select(){
		gsm.getThread().suspend();
		
		if(currentMenuChoise == 0){
			gsm.setState(GameStateManager.INPUT_STATE);
			gsm.getThread().resume();
		}
		else if(currentMenuChoise == 1){
			ScoreDisplayState.setPreviousState(GameStateManager.MENU_STATE);
			gsm.setState(GameStateManager.SCORE_DISPLAY_STATE);
			gsm.getThread().resume();
		}
		else if(currentMenuChoise == 2){
			System.exit(0);
		}
	}
	
	public void keyReleased(int keyCode){}

	public void mouseClicked(int mouseType,int x,int y) {
		if(mouseType == MouseEvent.BUTTON1){
			int choise = options.checkIfOptionIsClicked(x, y);
			if(choise != -1){
				currentMenuChoise = choise;
				select();
			}
		}
	}

	public void mouseDragged() {}
	
	public void mouseMoved(int x,int y){
		int choise = options.checkIfOptionIsClicked(x, y);
		if(choise != -1)
			currentMenuChoise = choise;
	}

}
