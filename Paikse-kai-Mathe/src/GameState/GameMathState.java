package GameState;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Background.Background;
import Main.GamePanel;

public class GameMathState extends GameState {

	private Background bg;
	private BufferedImage image;


	public GameMathState(GameStateManager gsm){
		this.gsm = gsm;

		//init background
		bg=new Background("/math.jpg",1);
		bg.setVector(0, 0);

		//init image
		try {
			image=ImageIO.read(getClass().getResourceAsStream("/math_textfield.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
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
	public void render(Graphics2D g) {

		bg.render(g);
		g.drawImage(image, GamePanel.WIDTH/4, GamePanel.HEIGHT/4,GamePanel.WIDTH/2, GamePanel.HEIGHT/3,null);
		

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
	public void mouseClicked(int mouseType, int x, int y) {
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
