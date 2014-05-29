package GameState;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import Background.Background;
import Entity.MenuOptions;
import Main.GamePanel;

public class PauseMenuState extends GameState {
	
	private Background bg;
	private static int previousState;
	
	private Color menuOptionColor;
	private Font menuOptionFont;
	
	private MenuOptions options;
	private int currentMenuChoise;
	
	public PauseMenuState(GameStateManager gsm){
		this.gsm = gsm;
		
		bg = new Background("/Backgrounds/pause_bg.gif", 0);
		bg.setVector(0, 0);
		
		menuOptionColor = Color.GREEN.darker().darker();
		menuOptionFont = new Font("Arial", Font.BOLD, 40);
		
		options = new MenuOptions(new String[]{"Επιστροφή","Αλλαγή Παιχνιδιού","Βαθμολογίες","Έξοδος"});
		currentMenuChoise = 0;
	}
	
	public static void setPreviousState(int state){previousState = state;}

	public void init() {}

	public void update() {
		bg.update();
	}

	public void render(Graphics2D g) {
		bg.render(g);
		
		//draw menu options
		g.setFont(menuOptionFont);
		FontMetrics fm;
		for(int i = 0; i<options.length();i++){
			fm = g.getFontMetrics();
			options.setHeight(fm.getHeight(), i);
			options.setWidth(fm.stringWidth(options.getOption(i)), i);
			options.setX(GamePanel.WIDTH/2 - options.getWidth(i)/2, i);
			options.setY(GamePanel.HEIGHT /3 +  i*GamePanel.HEIGHT/6, i);
			
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(options.getX(i)-5,options.getY(i)-5, options.getWidth(i)+10, options.getHeight(i)+10);
			g.setColor(Color.BLACK);
			g.drawRect(options.getX(i)-5,options.getY(i)-5, options.getWidth(i)+10, options.getHeight(i)+10);
			
			if(i == currentMenuChoise){
				g.setColor(Color.BLACK);
			}
			else{
				g.setColor(menuOptionColor);
			}
			//fm = g.getFontMetrics();
					
			g.drawString(options.getOption(i), GamePanel.WIDTH/2 - fm.stringWidth(options.getOption(i))/2, GamePanel.HEIGHT /3 +  i*GamePanel.HEIGHT/6 + fm.getMaxAscent() + fm.getLeading());
			/*options.setHeight(fm.getHeight(), i);
			options.setWidth(fm.stringWidth(options.getOption(i)), i);
			options.setX(GamePanel.WIDTH/2 - options.getWidth(i)/2, i);
			options.setY(GamePanel.HEIGHT /3 +  i*GamePanel.HEIGHT/6, i);*/
		}
	}

	public void keyPressed(int keyCode) {
		if(keyCode == KeyEvent.VK_ESCAPE){
			gsm.setState(previousState);
		}
		else if(keyCode == KeyEvent.VK_ENTER){
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
		
		if(currentMenuChoise == 0){
			gsm.setState(previousState);
		}
		else if(currentMenuChoise == 1){
			gsm.setState(GameStateManager.PLAYING_MENU_STATE);
		}
		else if(currentMenuChoise == 2){
			ScoreDisplayState.setPreviousState(GameStateManager.PAUSE_MENU_STATE);
			gsm.setState(GameStateManager.SCORE_DISPLAY_STATE);
		}
		else if(currentMenuChoise == 3){
			System.exit(0);
		}
	}

	public void keyReleased(int keyCode) {}

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

	public void mouseMoved(int x, int y) {
		int choise = options.checkIfOptionIsClicked(x, y);
		if(choise != -1)
			currentMenuChoise = choise;
	}

}
