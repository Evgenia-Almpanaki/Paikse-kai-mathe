package GameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import Background.Background;
import Entity.GameButton;
import Main.GamePanel;

public class ScoreDisplayState extends GameState {
	
	private Background bg;
	
	private String filePath;
	
	private String title;
	private GameButton backButton;
	
	private Color titleColor;
	private Color scoresColor;
	
	private String [][] scores;
	
	private String noScoresMessage;
	private boolean areThereNoScores;
	
	private static int previousState;
	
	public ScoreDisplayState(String scoreFilePath, GameStateManager gsm){
		this.gsm = gsm;
		backButton = new GameButton("/Textures/backButton.png");
		backButton.setX(GamePanel.WIDTH - backButton.getWidth() - 15);
		backButton.setY(GamePanel.HEIGHT - backButton.getHeight() - 15);
		
		bg = new Background("/Backgrounds/menubg.gif", 1);
		bg.setVector(0, 0);
		
		titleColor = Color.BLACK;
		scoresColor = Color.ORANGE.darker();
		
		title = "Οι Μεγαλύτερες Βαθμολογίες";
		
		filePath = scoreFilePath;
	}
	
	public static void setPreviousState(int state){
		previousState = state;
	}
	
	private void importScores() {
		
		try{
			File file = new File(filePath);
			FileInputStream inputStream = new FileInputStream(file);
			InputStreamReader input = new InputStreamReader(inputStream);
			BufferedReader IN = new BufferedReader(input);
			
			int numberOfScores = Integer.parseInt(IN.readLine());
			if(numberOfScores == 0){
				areThereNoScores = true;
				return;
			}
			areThereNoScores = false;
			
			scores = new String[numberOfScores][2];
			
			for(int i=0;i<numberOfScores;i++){
				
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
		noScoresMessage = "Δεν υπάρχουν Βαθμολογίες";
	}

	public void update() {
		bg.update();
	}

	public void render(Graphics2D g) {
		bg.render(g);
		g.setColor(titleColor);
		g.drawString(title, 100, 100);
		g.setColor(scoresColor);
		
		if(areThereNoScores){
			g.drawString(noScoresMessage, 100, 200);
		}
		else
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
