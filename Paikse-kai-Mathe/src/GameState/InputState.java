package GameState;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import Background.Background;
import Entity.GameButton;
import Entity.GameTextField;
import Entity.Player;
import Main.GamePanel;

public class InputState extends GameState {
	
	private Background bg;
	private String message;
	private GameTextField inputField;
	private Player player;
	private GameButton okButton;
	
	public InputState(GameStateManager gsm){
		this.gsm = gsm;
		
		
		okButton = new GameButton("/Textures/okButton.png");
		okButton.setX(GamePanel.WIDTH - okButton.getWidth() - 15);
		okButton.setY(GamePanel.HEIGHT - okButton.getHeight() -15);
		
		inputField = new GameTextField("/Textures/textfield.png");
		inputField.setX(GamePanel.WIDTH/2 - inputField.getWidth()/2);
		inputField.setY(GamePanel.HEIGHT/2 - inputField.getHeight()/2);
		
		bg = new Background("/Backgrounds/menubg.gif", 1);
		bg.setVector(0, 0);
		
		message = "Γράψε το όνομά σου";
	}
	
	public void init() {}

	public void update(){
		bg.update();
	}

	public void render(Graphics2D g) {
		bg.render(g);
		g.setColor(Color.ORANGE.darker());
		g.drawString(message, GamePanel.WIDTH/2 - g.getFontMetrics().stringWidth(message)/2, 100);
		
		inputField.setxInput(GamePanel.WIDTH/2 - inputField.getWidth()/2 + 17);
		inputField.setyInput(GamePanel.HEIGHT/2 - inputField.getHeight()/2 + g.getFontMetrics().getHeight() + 10);
		inputField.render(g);
		
		okButton.render(g);
	}

	public void keyPressed(int keyCode) {
		inputField.keyPressed(keyCode);
	}

	public void keyReleased(int keyCode) {}

	public void mouseClicked(int mouseType, int x, int y) {
		if(mouseType == MouseEvent.BUTTON1 && okButton.isClicked(x, y)){
			if(!inputField.getText().equals("")){
				player = new Player(inputField.getText());
				gsm.setPlayer(player);
				gsm.setState(GameStateManager.DIFFICULTY_STATE);
			}
		}
	}

	public void mouseDragged() {}
	
	public void mouseMoved(int x,int y){}

}
