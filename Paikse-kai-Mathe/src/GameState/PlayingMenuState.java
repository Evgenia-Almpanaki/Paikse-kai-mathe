package GameState;

import java.awt.Graphics2D;

import Background.Background;
import Entity.Player;
import Entity.SubjectOptions;

public class PlayingMenuState extends GameState {

	private Background bg;
	
	private String title;
	private SubjectOptions options;
	
	public PlayingMenuState(GameStateManager gsm){
		this.gsm = gsm;
		
		bg = new Background("/Backgrounds/menubg.gif", 1);
		bg.setVector(0, 1);
		
		title = "Διάλεξε Παιχνίδι!";
	}
	
	public void init() {
		
		options = new SubjectOptions(gsm.getDifficulty());
		bg.setPosition(0, 0);
	}

	public void update() {
		bg.update();
	}

	public void render(Graphics2D g) {
		bg.render(g);
		g.drawString(title, 100, 100);
		if(options == null)
			options = new SubjectOptions(gsm.getDifficulty());
		options.render(g);
	}

	public void keyPressed(int keyCode) {}

	public void keyReleased(int keyCode) {}

	public void mouseClicked(int mouseType, int x, int y) {
		int option = options.subjectClicked(x, y);
		if(option == 0){
			gsm.setState(GameStateManager.GAME_HISTORY_STATE);
		}
		else if(option == 1){
			gsm.setState(GameStateManager.GAME_GRAMMAR_STATE);
		}
		else if(option == 3){
			gsm.setState(GameStateManager.GAME_GEOGRAPHY_STATE);
		}
		else if(option == 2){
			gsm.setState(GameStateManager.GAME_MATH_STATE);
		}
	}

	public void mouseDragged() {}

	public void mouseMoved(int x, int y) {}

}
