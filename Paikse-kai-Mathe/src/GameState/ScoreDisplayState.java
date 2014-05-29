package GameState;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import Background.Background;
import Entity.GameButton;
import Main.Game;
import Main.GamePanel;

public class ScoreDisplayState extends GameState {
	
	private Background bg;
	
	private String title;
	private GameButton backButton;
	
	private String [][] scores;
	
	private static int previousState;
	
	public ScoreDisplayState(String scoreFilePath, GameStateManager gsm){
		this.gsm = gsm;
		backButton = new GameButton("/Textures/backButton.png");
		backButton.setX(GamePanel.WIDTH - backButton.getWidth() - 15);
		backButton.setY(GamePanel.HEIGHT - backButton.getHeight() - 15);
		
		bg = new Background("/Backgrounds/menubg.gif", 1);
		bg.setVector(0, 0);
		
		title = "Οι Μεγαλύτερες Βαθμολογίες";
		importScores();
	}
	
	public static void setPreviousState(int state){
		previousState = state;
	}
	
	private void importScores() {
		
		try{
			
			InputStreamReader input = new InputStreamReader(getClass().getResourceAsStream("/scores.data"));
			BufferedReader IN = new BufferedReader(input);
			
			int n = Integer.parseInt(IN.readLine());
			scores = new String[n][2];
			for(int i=0;i<n;i++){
				String[] temp = IN.readLine().split(",");
				scores[i][0] = temp[0];
				scores[i][1] = temp[1];
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public void init() {
		importScores();
	}

	public void update() {
		bg.update();
	}

	public void render(Graphics2D g) {
		bg.render(g);
		g.drawString(title, 100, 100);
		for(int i=0;i<scores.length;i++){
			g.drawString((i+1) + ": " + scores[i][0] + ", " + scores[i][1], 100, 200 + i*g.getFontMetrics().getHeight());
		}
		backButton.render(g);
	}

	public void keyPressed(int keyCode) {}

	public void keyReleased(int keyCode) {}

	public void mouseClicked(int mouseType, int x, int y) {
		if(mouseType == MouseEvent.BUTTON1 && backButton.isClicked(x, y))
			gsm.setState(previousState);
	}

	public void mouseDragged() {}

	public void mouseMoved(int x, int y) {}

}
