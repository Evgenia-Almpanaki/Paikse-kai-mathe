package GameState;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Background.Background;
import Entity.Animation;
import Entity.GameButton;
import Entity.SubjectOptions;
import Main.GamePanel;

public class PlayingMenuState extends GameState {

	private Background bg;
	
	private String title;
	private SubjectOptions options;
	private GameButton buttonExit;
	
	private Animation animation;
	
	public PlayingMenuState(GameStateManager gsm){
		this.gsm = gsm;
		
		bg = new Background("/Backgrounds/games_menu_bg.gif", 1);
		bg.setVector(0, 0);
		
		buttonExit = new GameButton("/Textures/exitButton.png");
		buttonExit.setX(10);
		buttonExit.setY(GamePanel.HEIGHT - buttonExit.getHeight() - 10);
		
		title = "Διάλεξε Παιχνίδι!";
	}
	
	public void init() {
		
		options = new SubjectOptions(gsm.getDifficulty());
		bg.setPosition(0, 0);
		
		BufferedImage[] frames = new BufferedImage[10];
		for(int i=0;i<10;i++){
			try {
				String dir = "/" + (i+1) + ".gif";
				frames[i] = ImageIO.read(getClass().getResourceAsStream(dir));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		animation = new Animation();
		animation.setFrames(frames);
		animation.setDelay(300);
	}

	public void update() {
		bg.update();
		animation.update();
	}

	public void render(Graphics2D g) {
		bg.render(g);
		animation.render(g);
		g.drawString(title, 100, 100);
		//if(options == null)
			//options = new SubjectOptions(gsm.getDifficulty());
		options.render(g);
		buttonExit.render(g);
	}

	public void keyPressed(int keyCode) {}

	public void keyReleased(int keyCode) {}

	public void mouseClicked(int mouseType, int x, int y) {
		if(buttonExit.isClicked(x, y)){
			gsm.saveScoreToFile();
			System.exit(0);
		}
		int option = options.subjectClicked(x, y);
		gsm.getThread().suspend();//
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
		gsm.getThread().resume();//
	}

	public void mouseDragged() {}

	public void mouseMoved(int x, int y) {}

}
