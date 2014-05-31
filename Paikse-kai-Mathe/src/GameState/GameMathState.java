package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Background.Background;
import Entity.GameButton;
import Entity.GameTextField;
import Entity.MessageBox;
import Entity.Player;
import Entity.QuestionManager_Math;
import Entity.Question_Math;
import Main.GamePanel;

public class GameMathState extends GameState {

	private Background bg;
	private BufferedImage image;
	private QuestionManager_Math questionManager;
	private Player player;
	private MessageBox messageBox;
	private GameButton checkButton, ignoreButton;
	private Question_Math currentQuestion;
	private boolean displayMessage;
	private GameStateManager gsm;
	private GameTextField textfield;
	private String input;
	private Font inputFont, questionFont;
	private boolean hasGameBeenPaused;

	public GameMathState(GameStateManager gsm){

		this.gsm = gsm;
		questionManager=new QuestionManager_Math();

		//fonts
		inputFont=new Font(Font.MONOSPACED, Font.ITALIC , 33);
		questionFont=questionManager.getQuestionFont();

		//init background
		bg=new Background("/Backgrounds/mathBackground.jpg",1);
		bg.setVector(0, 0);

		//init image
		try {
			image=ImageIO.read(getClass().getResourceAsStream("/math.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		//init textfield

		textfield=new GameTextField("/Textures/textfield.png");
		textfield.setX((int) (GamePanel.WIDTH/3.3));
		textfield.setY((int)(GamePanel.HEIGHT * (3/4.0)));

		// buttons
		ignoreButton = new GameButton("/Textures/buttonNext.png");
		ignoreButton.setX(GamePanel.WIDTH - ignoreButton.getWidth() - 10);
		ignoreButton.setY(GamePanel.HEIGHT - ignoreButton.getHeight() - 10);

		checkButton = new GameButton("/Textures/buttonCheck.png");
		checkButton.setX(GamePanel.WIDTH - checkButton.getWidth() - 10);
		checkButton.setY(ignoreButton.getY() - checkButton.getHeight() - 10);

		//init messageBox
		messageBox = new MessageBox();
		messageBox.setXandY(GamePanel.WIDTH / 2 - messageBox.getWidth() / 2,	GamePanel.HEIGHT / 2 - messageBox.getHeight() / 2);
		displayMessage = false;
		
		hasGameBeenPaused = false;
	}

	@Override
	public void init() {
		
		if(!hasGameBeenPaused){
			questionManager.init();
			questionManager.loadQuestions();

			currentQuestion=questionManager.getNextQuestion(new Question_Math("",""));
			player = gsm.getPlayer();
		}
		else{
			hasGameBeenPaused = false;
		}
		
		
	}

	@Override
	public void update() {

	}

	@Override
	public void render(Graphics2D g) {


		bg.render(g);
		textfield.render(g);

		g.drawImage(image, GamePanel.WIDTH/4, GamePanel.HEIGHT/4,GamePanel.WIDTH/2, GamePanel.HEIGHT/2,null);

		g.setColor(Color.BLUE);
		g.setFont(new Font("Arial", Font.BOLD, 33));

		g.drawString("Βαθμοί: "+player.getTempScore(), GamePanel.WIDTH/10, GamePanel.HEIGHT/8);

		if(currentQuestion==null && !displayMessage) 
			gsm.setState(GameStateManager.GAME_OVER_STATE);


		if (displayMessage){
			messageBox.render(g);
			g.drawString("  ", textfield.getX()+19, textfield.getY() + (int) (textfield.getHeight()/2.5) + 10);
		}
		else {
			checkButton.render(g);
			ignoreButton.render(g);

			g.setFont(inputFont);
			int numberOfLettersAllowed=(int) (textfield.getWidth() * 0.8 )/g.getFontMetrics().getWidths()[0];

			if(textfield.getText().length()<numberOfLettersAllowed)
				g.drawString(textfield.getText(), textfield.getX()+19, textfield.getY() + (int) (textfield.getHeight()/2.5) + 10);
			else
				g.drawString(textfield.getText().substring(textfield.getText().length()-numberOfLettersAllowed, textfield.getText().length()), textfield.getX()+19, textfield.getY() + textfield.getHeight()/2 + 10);
			g.setFont(questionFont);

			if(currentQuestion!=null)
				questionManager.drawString(g, currentQuestion.getQuestion(),(int)(GamePanel.WIDTH*(290/1000.0)),(int)(GamePanel.HEIGHT*(330/1000.0)),(int)(GamePanel.WIDTH*(430/1000.0)));

		}
	}

	@Override
	public void mouseClicked(int mouseType, int x, int y) {
		if(checkButton.isClicked(x, y) && !displayMessage){	

			input=textfield.getText();
			currentQuestion.setAnswered(true);
			
			displayMessage = true;
			if(input.trim().equals("")){
				messageBox.setMessage("Απάντησε!");
			}
			else if(currentQuestion.checkAnswer(input.trim())){
				messageBox.setMessage("Σωστό!");	
				player.setTempScore(player.getTempScore()+1);
			}
			else{
				messageBox.setMessage("Λάθος!");
			}
			currentQuestion=questionManager.getNextQuestion(currentQuestion);
		}
		else if(ignoreButton.isClicked(x, y) && !displayMessage){ 
			currentQuestion=questionManager.getNextQuestion(currentQuestion);
		} 
		else if(displayMessage && messageBox.isOkButtonClicked(x, y)){ 
			displayMessage= false; 
		} 

	}

	@Override
	public void keyPressed(int keyCode) {
		if(keyCode == KeyEvent.VK_ESCAPE){
			PauseMenuState.setPreviousState(GameStateManager.GAME_MATH_STATE);
			gsm.setState(GameStateManager.PAUSE_MENU_STATE);
			hasGameBeenPaused = true;
			return;
		}
		
		textfield.keyPressed(keyCode);

	}

	@Override
	public void keyReleased(int keyCode) {
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
