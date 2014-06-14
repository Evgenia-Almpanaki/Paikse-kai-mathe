package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import Main.GamePanel;

public class LoadState extends GameState {

	private static int loadBarWidth  = 737;
	private static int numResources = 5;
	private  static int loadAdd = loadBarWidth / numResources;
	private  static int loadStatus =0;
	
	private static int counter = 1;
	private static int time = 7;
	
	private String msg = "Παρακαλώ Περιμένετε";
	
	public LoadState(GameStateManager gsm){
		this.gsm = gsm;
	}
	
	@Override
	public void init() {}

	@Override
	public void update() {
		if(gsm.getCurrentState() == GameStateManager.LOAD_SCREEN){
			time--;
			if(time<=0){
				load();
				time = 7;
			}
		}

	}

	@Override
	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		g.setColor(Color.RED);
		g.drawRect(GamePanel.WIDTH/2 - loadBarWidth/2, GamePanel.HEIGHT/2 - 50, loadBarWidth, 100);
		g.setColor(Color.BLUE);
		g.fillRect(GamePanel.WIDTH/2 - loadBarWidth/2 -1, GamePanel.HEIGHT/2 - 50, loadStatus, 101);
		
		g.setColor(Color.RED);
		g.setFont(new Font("Arial",Font.PLAIN, 40));
		g.drawString(msg, GamePanel.WIDTH/2 - g.getFontMetrics().stringWidth(msg)/2, GamePanel.HEIGHT/3);
	}
	
	private void load(){
		switch(counter){
		case 1:
			gsm.generateStates(1);
			counter++;
			LoadState.loadMore();
			return;
		case 2:
			gsm.generateStates(2);
			counter++;
			LoadState.loadMore();
			return;
		case 3:
			gsm.generateStates(3);
			counter++;
			LoadState.loadMore();
			return;
		case 4:
			gsm.generateStates(4);
			counter++;
			LoadState.loadMore();
			return;
		case 5:
			gsm.generateStates(5);
			counter++;
			LoadState.loadMore();
			return;
		case 6:
			counter++;
			LoadState.loadMore();
			gsm.setState(GameStateManager.MENU_STATE);
			return;
		}
		
	}
	
	public static void loadMore(){
		loadStatus += loadAdd;
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
