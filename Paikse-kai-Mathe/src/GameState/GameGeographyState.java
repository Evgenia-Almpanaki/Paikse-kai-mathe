package GameState;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Background.Background;
import Entity.GameButton;
import Entity.MessageBox;
import Entity.Player;
import Entity.QuestionManager_Geography;
import Entity.Question_Geography;
import Main.GamePanel;


public class GameGeographyState extends GameState{

	private QuestionManager_Geography questionManager;
	private String imagePath, backgroundPath;
	private int difficulty=0;
	private Background background;
	private BufferedImage image;
	private int xImage, yImage;
	private Player player;
	private MessageBox messageBox;
	private GameButton checkButton, ignoreButton;
	private Question_Geography currentQuestion;
	private boolean displayMessage;
	private GameStateManager gsm;
	private int width = GamePanel.WIDTH;
	private int height = GamePanel.HEIGHT;

	public GameGeographyState(GameStateManager gsm){
		this.gsm = gsm;
		backgroundPath="/Backgrounds/geo.jpg";
		imagePath="/1/greece.jpg";
		questionManager=new QuestionManager_Geography();
		questionManager.loadQuestions();

		// buttons
		ignoreButton = new GameButton("/Textures/buttonNext.png");
		ignoreButton.setX(GamePanel.WIDTH - ignoreButton.getWidth() - 10);
		ignoreButton.setY(GamePanel.HEIGHT - ignoreButton.getHeight() - 10);

		checkButton = new GameButton("/Textures/buttonCheck.png");
		checkButton.setX(GamePanel.WIDTH - checkButton.getWidth() - 10);
		checkButton.setY(ignoreButton.getY() - checkButton.getHeight() - 10);

		//x, y of image
		xImage=GamePanel.WIDTH/2;
		yImage=GamePanel.HEIGHT/4;

		//init background
		background = new Background(backgroundPath, 1);
		background.setVector(0, 0);

		//init image
		try {
			image=ImageIO.read(getClass().getResourceAsStream(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		//init messageBox
		messageBox = new MessageBox();
		messageBox.setXandY(GamePanel.WIDTH / 2 - messageBox.getWidth() / 2,	GamePanel.HEIGHT / 2 - messageBox.getHeight() / 2);
		displayMessage = false;


	}

	@Override
	public void update() {
		difficulty=gsm.getDifficulty();
		if(difficulty==1){
			imagePath="/1/greece.jpg";
		}
		else if(difficulty==2){
			imagePath="/2/europe.jpg";
		}
		try {
			image=ImageIO.read(getClass().getResourceAsStream(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public void mouseClicked(int mouseType, int x, int y) {

		if(checkButton.isClicked(x, y) && !displayMessage){
			currentQuestion.setAnswered(true);

			displayMessage = true; 
			if(currentQuestion.isSelected() ){
				currentQuestion.setSelected(false);
				messageBox.setMessage("Σωστό!");	
				player.setTempScore(player.getTempScore()+1);
			}
			else{
				messageBox.setMessage("Λάθος!");
				if(difficulty==1){
					for(Question_Geography q:questionManager.getQuestionsGreece()){
						q.setSelected(false);
					}
				}
				else{
					for(Question_Geography q:questionManager.getQuestionsEurope()){
						q.setSelected(false);
					}
				}
				currentQuestion.setSelected(true);
			}
			if(difficulty==1)
				currentQuestion=questionManager.getNextQuestionGreece(currentQuestion);
			else if(difficulty==2)
				currentQuestion=questionManager.getNextQuestionEurope(currentQuestion);
		}
		else if(ignoreButton.isClicked(x, y) && !displayMessage){ 
			if(difficulty==1){
				currentQuestion=questionManager.getNextQuestionGreece(currentQuestion);
				for(Question_Geography q:questionManager.getQuestionsGreece()){
					q.setSelected(false);
				}
			}else if(difficulty==2){
				currentQuestion=questionManager.getNextQuestionEurope(currentQuestion);
				for(Question_Geography q:questionManager.getQuestionsEurope()){
					q.setSelected(false);
				}
			}
		} 
		else if(displayMessage && messageBox.isOkButtonClicked(x, y)){ 
			displayMessage= false; 
		} 
		else{
			String selectedQuestion = null;


			if(difficulty==1){

				for(Question_Geography q:questionManager.getQuestionsGreece()){

					int minx=(int)(q.getPoint().x *(width/1000.0));
					int miny=(int)(q.getPoint().y *(height/1000.0));
					int maxx=10+(int)(q.getPoint().x *(width/1000.0));
					int maxy=10+(int)(q.getPoint().y *(height/1000.0));

					if(x>minx && y>miny && x<maxx && y<maxy){
						selectedQuestion=q.getQuestion();
					}
				}
				for(Question_Geography q:questionManager.getQuestionsGreece()){
					if(q.getQuestion().equals(selectedQuestion)) 
						q.setSelected(true); 
					else 
						q.setSelected(false);
				}
			}
			else if(difficulty==2){

				for(Question_Geography q:questionManager.getQuestionsEurope()){

					int minx=(int)(q.getPoint().x *(width/1000.0));
					int miny=(int)(q.getPoint().y *(height/1000.0));
					int maxx=10+(int)(q.getPoint().x *(width/1000.0));
					int maxy=10+(int)(q.getPoint().y *(height/1000.0));

					if(x>minx && y>miny && x<maxx && y<maxy){
						selectedQuestion=q.getQuestion();
					}
				}
				for(Question_Geography q:questionManager.getQuestionsEurope()){
					if(q.getQuestion().equals(selectedQuestion)) 
						q.setSelected(true); 
					else 
						q.setSelected(false);
				}
			}


		}

	}
	public void render(Graphics2D g) {

		boolean done = false;

		background.render(g);
		g.drawImage(image, xImage, yImage,GamePanel.WIDTH/2,3 * (GamePanel.HEIGHT/4),null);

		if(currentQuestion==null && !displayMessage) {
			g.drawString("Δεν υπάρχουν άλλες ερωτήσεις",GamePanel.WIDTH/3, GamePanel.HEIGHT/8 );
			done=true;
		}

		if(done){
			done=false;
			displayMessage=false;
			gsm.setState(GameStateManager.GAME_OVER_STATE);

		}
		g.setColor(Color.MAGENTA);
		g.drawString("Βαθμοί: "+player.getTempScore(), GamePanel.WIDTH/10, GamePanel.HEIGHT/8);


		if (displayMessage)
			messageBox.render(g);
		else {
			renderQuestions(g);
			checkButton.render(g);
			ignoreButton.render(g);

		}
	}

	public void renderQuestions(Graphics g) {
		g.setColor(Color.red);
		if(currentQuestion!=null){
			// draw question
			g.drawString(currentQuestion.getQuestion(), GamePanel.WIDTH/3, GamePanel.HEIGHT/8);

			// draw answers
			if(difficulty==1){
				for(Question_Geography q:questionManager.getQuestionsGreece()){ 
					if(q.isSelected())
						g.setColor(Color.GREEN); 
					else 
						g.setColor(Color.red);
					g.fillOval((int)(q.getPoint().x*(width/1000.0)),(int) (q.getPoint().y*(height/1000.0)), 10, 10);
				}
			}
			else if(difficulty==2){
				for(Question_Geography q:questionManager.getQuestionsEurope()){ 
					if(q.isSelected())
						g.setColor(Color.GREEN); 
					else 
						g.setColor(Color.red);
					g.fillOval((int)(q.getPoint().x*(width/1000.0)),(int) (q.getPoint().y*(height/1000.0)), 10, 10);
				}
			}
		}
	}
	public void init() {
		questionManager.init();
		questionManager.loadQuestions();
		difficulty=gsm.getDifficulty();
		player = gsm.getPlayer();

		if(difficulty==1){
			if(questionManager.getQuestionsGreece().size()>0)
				currentQuestion = questionManager.getNextQuestionGreece(new Question_Geography("", new Point(0,0)));
		}
		else if(difficulty==2){
			if(questionManager.getQuestionsEurope().size()>0)
				currentQuestion = questionManager.getNextQuestionEurope(new Question_Geography("", new Point(0,0)));
		}

	}


	public void setImagePath(String img){
		imagePath=img;
	}

	public void keyPressed(int keyCode) {

	}

	public void keyReleased(int keyCode) {

	}

	public void mouseDragged() {

	}

	public void mouseMoved(int x, int y) {

	}
}
