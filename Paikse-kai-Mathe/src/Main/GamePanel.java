package Main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.*;

import GameState.GameStateManager;
import GameState.MenuState;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener, MouseListener , MouseMotionListener{

	//game panel dimensions
	public static int WIDTH;
	public static int HEIGHT;
	
	//game thread
	private Thread thread;
	private boolean running;
	private int FPS = 60;
	private long targetTime = 1000/FPS;
	
	//drawing staff
	private BufferedImage image;
	private Graphics2D g;
	
	//game state manager
	private GameStateManager gameStateManager;
	
	//class constructor
	public GamePanel(){
		
		super();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		WIDTH = (int) screenSize.getWidth();
		HEIGHT = (int) screenSize.getHeight();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
		
	}
	
	public void addNotify(){
		super.addNotify();
		if(thread == null){
			thread = new Thread(this);
			addKeyListener(this);
			addMouseListener(this);
			addMouseMotionListener(this);
			thread.start();
		}
	}
	
	@SuppressWarnings("static-access")
	public void run() {
		
		init();
		
		long startTime;
		long urdTime; //this is the time required for update,render and draw
		long waitTime;
		
		//game loop
		while(running){
			
			startTime = System.nanoTime();
			
			update();
			render();
			draw();
			
			urdTime = (System.nanoTime() - startTime)/ 1000000;
			waitTime = targetTime - urdTime;
			
			if(waitTime < 0) waitTime = 5;
			
			try{
				thread.sleep(waitTime);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		
	}
	
	//initiate staff
	private void init(){
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D)image.getGraphics();
		
		running = true;
		
		gameStateManager = new GameStateManager();
		gameStateManager.setState(GameStateManager.MENU_STATE);
		
	}
	
	//update - render - draw
	private void update(){
		gameStateManager.update();
	}
	
	private void render(){
		gameStateManager.render(g);
	}
	
	private void draw(){
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0,WIDTH,HEIGHT, null);
		g2.dispose();
	}
	
	//All the listeners
	public void mouseDragged(MouseEvent e) {}

	public void mouseMoved(MouseEvent e) {
		if(gameStateManager != null)
			gameStateManager.mouseMoved(e.getX(), e.getY());
	}

	public void mouseClicked(MouseEvent e) {
		gameStateManager.mouseClicked(e.getButton(),e.getX() ,e.getY());
	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void keyPressed(KeyEvent e) {
		gameStateManager.keyPressed(e.getKeyCode());
	}

	public void keyReleased(KeyEvent e) {
		gameStateManager.keyReleased(e.getKeyCode());
	}

	public void keyTyped(KeyEvent e) {}

}
