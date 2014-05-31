package GameState;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import Entity.Player;

public class GameStateManager {

	//states stuff
	private ArrayList<GameState> gameStates;
	private int currentState;
	
	private int difficulty;
	private Player player;
	
	//state values
	public static final int MENU_STATE = 0;
	public static final int SCORE_DISPLAY_STATE = 1;
	public static final int PAUSE_MENU_STATE = 2;
	public static final int INPUT_STATE = 3;
	public static final int DIFFICULTY_STATE = 4;
	public static final int PLAYING_MENU_STATE = 5;
	public static final int GAME_HISTORY_STATE = 6;
	public static final int GAME_OVER_STATE = 7;
	public static final int GAME_GRAMMAR_STATE = 8;
	public static final int GAME_GEOGRAPHY_STATE = 9;
	public static final int GAME_MATH_STATE = 10;
	
	/*prosorino*/ private Thread thread;
	
	//constructor
	public GameStateManager(){
		
		gameStates = new ArrayList<GameState>();
		currentState = MENU_STATE;
		difficulty = -1;
		
		generateStates();
		
	}
	
	/*prosorino*/ public void setThread(Thread thread){this.thread = thread;}
				  public Thread getThread(){return thread;}
	
	private void generateStates(){
		gameStates.add(new MenuState(this));
		gameStates.add(new ScoreDisplayState("Save/scores.data",this));
		gameStates.add(new PauseMenuState(this));
		gameStates.add(new InputState(this));
		gameStates.add(new DifficultyState(this));
		gameStates.add(new PlayingMenuState(this));
		gameStates.add(new GameHistoryState(this));
		gameStates.add(new GameOverState(this));
		gameStates.add(new GameGrammarState(this));
		gameStates.add(new GameGeographyState(this));
		gameStates.add(new GameMathState(this));
	}
	
	public void saveScoreToFile(){
		
		try{
			
			//import scores file
			File file = new File("Save/scores.data");
			FileInputStream inputStream = new FileInputStream(file);
			InputStreamReader input = new InputStreamReader(inputStream);
			BufferedReader IN = new BufferedReader(input);
			
			int numerOfScores = Integer.parseInt(IN.readLine());
			
			if(numerOfScores == 0){
				String output = player.getName() + "," + player.getTotalScore();
				numerOfScores++;
				
				PrintWriter writer = new PrintWriter(file);
				writer.println(numerOfScores);
				writer.println(output);
				writer.close();
				return;
			}
			else if(numerOfScores<5){
				
				ArrayList<String[]> list = new ArrayList<String[]>();
				ArrayList<String[]> tempList = new ArrayList<String[]>();
				
				for(int i=0;i<numerOfScores;i++){
					list.add(IN.readLine().split(","));
				}
				
				for(int i=0;i<list.size();i++){
					String[] tempScore = list.get(i);
					
					if(Integer.parseInt(tempScore[1]) <= player.getTotalScore()){
						tempList.addAll(list.subList(i, list.size()));
						if(i>0){
							ArrayList<String[]> temp = new ArrayList<String[]>();
							temp.addAll(list.subList(0, i));
							
							list.clear();
							list.addAll(temp);
							list.add(new String[]{player.getName(),String.valueOf(player.getTotalScore())});
							numerOfScores++;
							list.addAll(tempList);
							
							PrintWriter writer = new PrintWriter(file);
							writer.println(numerOfScores);
							for(int j=0;j<list.size();j++){
								writer.println(list.get(j)[0] + "," + list.get(j)[1]);
							}
							writer.close();
							return;
						}
						else{
							list.clear();
							list.add(new String[]{player.getName(),String.valueOf(player.getTotalScore())});
							numerOfScores++;
							list.addAll(tempList);
							
							PrintWriter writer = new PrintWriter(file);
							writer.println(numerOfScores);
							for(int j=0;j<list.size();j++){
								writer.println(list.get(j)[0] + "," + list.get(j)[1]);
							}
							writer.close();
							return;
						}
					}
				}
				
				list.add(new String[]{player.getName(),String.valueOf(player.getTotalScore())});
				numerOfScores++;
				
				PrintWriter writer = new PrintWriter(file);
				writer.println(numerOfScores);
				for(int i=0;i<list.size();i++){
					writer.println(list.get(i)[0] + "," + list.get(i)[1]);
				}
				writer.close();
				
			}
			else{
				
				ArrayList<String[]> list = new ArrayList<String[]>();
				ArrayList<String[]> tempList = new ArrayList<String[]>();
				
				for(int i=0;i<numerOfScores;i++){
					list.add(IN.readLine().split(","));
				}
				
				for(int i=0;i<list.size();i++){
					
					String[] tempScore = list.get(i);
					if(Integer.parseInt(tempScore[1]) <= player.getTotalScore()){
						tempList.addAll(list.subList(i, list.size()));
						if(i>0){
							ArrayList<String[]> temp = new ArrayList<String[]>();
							temp.addAll(list.subList(0, i));
							
							list.clear();
							list.addAll(temp);
							list.add(new String[]{player.getName(),String.valueOf(player.getTotalScore())});
							list.addAll(tempList);
							
							PrintWriter writer = new PrintWriter(file);
							writer.println(numerOfScores);
							for(int j=0;j<numerOfScores;j++){
								writer.println(list.get(j)[0] + "," + list.get(j)[1]);
							}
							writer.close();
							return;
						}
						else{
							list.clear();
							list.add(new String[]{player.getName(),String.valueOf(player.getTotalScore())});
							list.addAll(tempList);
							
							PrintWriter writer = new PrintWriter(file);
							writer.println(numerOfScores);
							for(int j=0;j<numerOfScores;j++){
								writer.println(list.get(j)[0] + "," + list.get(j)[1]);
							}
							writer.close();
							return;
						}
					}
				}
				
			}
			
		
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}
	
	public int getDifficulty(){return difficulty;}
	
	public Player getPlayer(){return player;}
	
	public void setState(int state){
		currentState = state;
		gameStates.get(currentState).init();
	}
	
	public void setPlayer(Player aPlayer){
		player = aPlayer;
	}
	
	public void setDifficulty(int value){
		difficulty = value;
	}
	
	public void update(){
		gameStates.get(currentState).update();
	}
	
	public void render(Graphics2D g){
		gameStates.get(currentState).render(g);
	}
	
	public void keyPressed(int keyCode){
		gameStates.get(currentState).keyPressed(keyCode);
	}
	
	public void keyReleased(int keyCode){
		gameStates.get(currentState).keyReleased(keyCode);
	}
	
	public void mouseClicked(int mouseCode, int x, int y){
		gameStates.get(currentState).mouseClicked(mouseCode,x,y);
	}
	
	public void mouseMoved(int x,int y){
		gameStates.get(currentState).mouseMoved(x, y);
	}
	
}
