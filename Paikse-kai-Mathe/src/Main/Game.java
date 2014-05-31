package Main;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.Locale;

import javax.swing.JFrame;

public class Game {

	public static void main(String[] args) {
		
		GraphicsDevice vc;
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		vc = env.getDefaultScreenDevice();
		
		JFrame gameFrame = new JFrame();
		gameFrame.setUndecorated(true);
		gameFrame.setContentPane(new GamePanel());
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.pack();
		gameFrame.setVisible(true);
		gameFrame.setResizable(false);
		
		vc.setFullScreenWindow(gameFrame);
	}

}
