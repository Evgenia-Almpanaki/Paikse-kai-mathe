package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.List;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import Background.Background;
import Entity.GameButton;
import Entity.MessageBox;
import Entity.Player;
import Entity.QuestionManager;
import Main.Game;
import Main.GamePanel;

public class GameHistoryState extends GameState {
	
	private Background bg;
	private QuestionManager manager;
	private String score;
	private GameButton buttonCheck;
	private GameButton buttonSkip;
	private GameButton buttonExit;
	private Player player;
	private MessageBox messageBox;
	
	private boolean hasGameBeenPaused;
	private boolean displayMessage;
	
	private Color scoreColor;
	
	private Font font;
	
	public GameHistoryState(GameStateManager gsm){
		this.gsm = gsm;
		
		scoreColor = Color.YELLOW;
		
		buttonSkip = new GameButton("/Textures/buttonNext.png");
		buttonSkip.setX(GamePanel.WIDTH - buttonSkip.getWidth() - 10);
		buttonSkip.setY(GamePanel.HEIGHT - buttonSkip.getHeight() - 10);
		
		buttonCheck = new GameButton("/Textures/buttonCheck.png");
		buttonCheck.setX(GamePanel.WIDTH - buttonCheck.getWidth() - 10);
		buttonCheck.setY(buttonSkip.getY() - buttonCheck.getHeight() - 10);
		
		buttonExit = new GameButton("/Textures/exitButton.png");
		buttonExit.setX(10);
		buttonExit.setY(GamePanel.HEIGHT - buttonExit.getHeight() - 10);
		
		bg = new Background("/Backgrounds/history_bg.gif", 1);
		bg.setVector(0, 0);
		
		messageBox = new MessageBox();
		messageBox.setXandY(GamePanel.WIDTH/2 - messageBox.getWidth()/2, GamePanel.HEIGHT/2 - messageBox.getHeight()/2);
		displayMessage = false;
		
		hasGameBeenPaused = false;
		
	}

	public void init() {
		if(!hasGameBeenPaused){
			manager = new QuestionManager("history", gsm.getDifficulty());
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
		g.setColor(scoreColor);
		g.drawString(score, 500, 100);
		if(displayMessage)
			messageBox.render(g);
		else{
			manager.render(g);
				
			if(manager.size() != 0){
				buttonCheck.render(g);
				buttonSkip.render(g);
				buttonExit.render(g);
			}
		}
			
	}
	
	private void saveScoreToFile(){
		
		try{
			//read score file to update it
			InputStreamReader input = new InputStreamReader(getClass().getResourceAsStream("/scores.data"));
			BufferedReader IN = new BufferedReader(input);
		
			int freeScoreSlots = Integer.parseInt(IN.readLine());
			
			if(freeScoreSlots == 0){
				
				try{
					freeScoreSlots++;
					
					File file = new File("Save/scores.data");
					FileOutputStream output = new FileOutputStream(file);
					PrintStream OUT = new PrintStream(output);
					
					OUT.println(String.valueOf(freeScoreSlots));
					OUT.println(player.getName() + "," +player.getTotalScore());
					
					OUT.close();
					output.close();
					
				}catch(Exception ex){
					ex.printStackTrace();
				}
				
			}
			else if(freeScoreSlots < 5){
				freeScoreSlots++;
				
				ArrayList<String> list= new ArrayList<String>();
				for(int i=0;i<freeScoreSlots-1;i++){
					list.add(IN.readLine());
				}
				list.add(player.getName() + "," + player.getTotalScore());
				
				Comparator<String> comparator = new Comparator<String>() {
				    public int compare(String c1, String c2) {
				        String[] temp1 = c1.split(",");
				        String[] temp2 = c2.split(",");
				        return -(Integer.parseInt(temp1[1]) - Integer.parseInt(temp2[1]));
				    }
				};
				
				Collections.sort(list, comparator);
				
				try{
					
					File file = new File("Save/scores.data");
					FileOutputStream output = new FileOutputStream(file);
					PrintStream OUT = new PrintStream(output);
					
					OUT.println(String.valueOf(freeScoreSlots));
					for(int i=0;i<list.size();i++){
						OUT.println(list.get(i));
					}
					
					OUT.close();
					output.close();
					
				}catch(Exception ex){
					ex.printStackTrace();
				}
				
			}
			
		
		}catch(Exception ex){
			ex.printStackTrace();
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
		if(displayMessage){
			if(messageBox.isOkButtonClicked(x, y))
				displayMessage = false;
		}
		else{
			manager.mouseClicked(x, y);
		
			if(buttonCheck.isClicked(x, y)){
			
				displayMessage = true;
				messageBox.setMessage("Λάθος");
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
			else if(buttonExit.isClicked(x, y)){
				player.setTotalScore(player.getTempScore() + player.getTotalScore());
				player.setTempScore(0);
				saveScoreToFile();
				System.exit(0);//allagh
			}
		}
		
		if(manager.size()==0 && manager.skippedListSize()==0){
			if(displayMessage)
				return;
			saveScoreToFile();
			gsm.setState(GameStateManager.GAME_OVER_STATE);
		}
	}

	public void mouseDragged() {}

	public void mouseMoved(int x, int y) {}

}
