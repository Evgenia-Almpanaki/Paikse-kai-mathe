package GameState;

import java.awt.FontMetrics;
import java.awt.Graphics2D;

import Background.Background;
import Entity.GameButton;
import Entity.Player;
import Main.GamePanel;

public class GameOverState extends GameState{

	private Background bg;
	private String title;
	private String tempScore;
	private String totalScore;
	private Player player;
	private GameButton okButton;
	
	public GameOverState(GameStateManager gsm){
		this.gsm = gsm;
		bg = new Background("/Backgrounds/menubg.gif", 1);
		bg.setVector(0, 0);
		
		okButton = new GameButton("/Textures/okButton.png");
		okButton.setX(GamePanel.WIDTH - okButton.getWidth() - 10);
		okButton.setY(GamePanel.HEIGHT - okButton.getHeight() - 10);
	}

	public void init() {
		player = gsm.getPlayer();
		tempScore = "Το σκόρ σου έιναι: " + player.getTempScore();
		player.setTotalScore(player.getTotalScore() + player.getTempScore());
		totalScore = "Το συνολικό σου σκόρ είναι: " + player.getTotalScore();
		player.setTempScore(0);
		
		title = "Τέλος παιχνιδιού";
	}

	public void update() {}

	public void render(Graphics2D g) {
		bg.render(g);
		okButton.render(g);
		
		FontMetrics fm = g.getFontMetrics();
		g.drawString(title, GamePanel.WIDTH/2 - fm.stringWidth(title)/2, 100);
		g.drawString(tempScore, GamePanel.WIDTH/2 - fm.stringWidth(tempScore)/2, GamePanel.HEIGHT/2 - fm.getHeight()/2);
		g.drawString(totalScore, GamePanel.WIDTH/2 - fm.stringWidth(totalScore)/2, GamePanel.HEIGHT/2 + fm.getHeight()/2);
	}

	public void keyPressed(int keyCode) {}

	public void keyReleased(int keyCode) {}

	public void mouseClicked(int mouseType, int x, int y) {
		if(okButton.isClicked(x, y)){
			gsm.setState(GameStateManager.PLAYING_MENU_STATE);
		}
	}

	public void mouseDragged() {}

	public void mouseMoved(int x, int y) {}
	
	
	
}
