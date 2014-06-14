package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import Background.Background;
import Entity.GameButton;
import Entity.HistoryQuestionManager;
import Entity.MessageBox;
import Entity.Player;
import Main.GamePanel;

public class GameHistoryState extends GameState {

	private Background bg;
	private HistoryQuestionManager manager;
	private String score;
	private GameButton buttonCheck;
	private GameButton buttonSkip;
	private Player player;
	private MessageBox messageBox;

	private boolean hasGameBeenPaused;
	private boolean displayCheckMessage;

	private Color scoreColor;

	public GameHistoryState(GameStateManager gsm){
		this.gsm = gsm;

		scoreColor = Color.YELLOW;

		buttonSkip = new GameButton("/Textures/buttonNext.png");
		buttonSkip.setX(GamePanel.WIDTH - buttonSkip.getWidth() - 10);
		buttonSkip.setY(GamePanel.HEIGHT - buttonSkip.getHeight() - 10);

		buttonCheck = new GameButton("/Textures/buttonCheck.png");
		buttonCheck.setX(GamePanel.WIDTH - buttonCheck.getWidth() - 10);
		buttonCheck.setY(buttonSkip.getY() - buttonCheck.getHeight() - 10);

		bg = new Background("/Backgrounds/history_bg.gif", 1);
		bg.setVector(0, 0);

		messageBox = new MessageBox();
		messageBox.setXandY(GamePanel.WIDTH/2 - messageBox.getWidth()/2, GamePanel.HEIGHT/2 - messageBox.getHeight()/2);
		displayCheckMessage = false;

		hasGameBeenPaused = false;

	}

	public void init() {
		if(!hasGameBeenPaused){
			manager = new HistoryQuestionManager("history", gsm.getDifficulty());
			player = gsm.getPlayer();
			score = "Σκόρ: " + player.getTempScore();
		}
		else{
			hasGameBeenPaused = false;
		}
	}

	public void update() {}

	public void render(Graphics2D g) {
		bg.render(g);

        Font font=g.getFont();
        g.setFont(new Font("SansSerif",Font.PLAIN,18));
        g.setColor(Color.black);
       
        g.drawString("Πατήστε ESC για το μενού επιλογών", (int)(GamePanel.WIDTH/3.0), (int) (GamePanel.HEIGHT*(9.5/10)));
        
        g.setFont(font);

		g.setColor(scoreColor);
		g.drawString(score, 500, 100);
		if(displayCheckMessage)
			messageBox.render(g);
		else{
			manager.render(g);

			if(manager.size() != 0){
				buttonCheck.render(g);
				buttonSkip.render(g);
			}
		}

	}

	public void keyPressed(int keyCode) {
		if(keyCode == KeyEvent.VK_ESCAPE){
			PauseMenuState.setPreviousState(GameStateManager.GAME_HISTORY_STATE);
			gsm.setState(GameStateManager.PAUSE_MENU_STATE);
			hasGameBeenPaused = true;
		}
	}

	public void keyReleased(int keyCode) {}

	public void mouseClicked(int mouseType, int x, int y) {
		if(displayCheckMessage){
			if(messageBox.isOkButtonClicked(x, y))
				displayCheckMessage = false;
		}
		else{
			manager.mouseClicked(x, y);

			if(buttonCheck.isClicked(x, y)){

				displayCheckMessage = true;
				messageBox.setMessage("Λάθος");
				if(manager.getCurrentAnswer() == -1){
					displayCheckMessage = true;
					messageBox.setMessage("Επέλεξε κάτι");
					return;
				}
				if(manager.getCurrentQuestionCorrectIndex() == manager.getCurrentAnswer()){
					player.setTempScore(player.getTempScore()+1);
					score = "Σκόρ: " + player.getTempScore();
					messageBox.setMessage("Σωστό");
				}

				manager.resetCurrentAnswer();
				manager.removeQuestion(manager.getQuestionAtPosition(manager.getCurrentQuestionIndex()));
				/*if(manager.size()>0 && manager.getCurrentQuestionIndex() > manager.size()-1){
					manager.resetCurrentQuestionIndex();
				}*/
			}
			else if(buttonSkip.isClicked(x, y)){
				manager.getNextQuestion();
			}
		}

		if(manager.size()==0 && manager.skippedListSize()==0){
			if(displayCheckMessage)
				return;
			gsm.setState(GameStateManager.GAME_OVER_STATE);
		}
	}

	public void mouseDragged() {}

	public void mouseMoved(int x, int y) {}

}
