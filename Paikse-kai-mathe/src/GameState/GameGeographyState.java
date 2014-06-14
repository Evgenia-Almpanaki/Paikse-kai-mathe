package GameState;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Background.Background;
import Entity.GameButton;
import Entity.MessageBox;
import Entity.Player;
import Entity.GeographyQuestionManager;
import Entity.Question_Geography;
import Main.GamePanel;


public class GameGeographyState extends GameState{

	private GeographyQuestionManager questionManager;
	private GameStateManager gsm;

	private String imagePath, backgroundPath; 

	private int difficulty=0;

	private Background background;
	private BufferedImage image;	//χάρτης
	private int xImage, yImage;		//x,y χάρτη

	private Player player;
	private MessageBox messageBox;
	private GameButton checkButton, ignoreButton;
	private Question_Geography currentQuestion;//τρέχουσα ερώτηση
	private boolean displayMessage;//αν είναι ορατό το messageBox

	private int width = GamePanel.WIDTH;
	private int height = GamePanel.HEIGHT;

	private boolean hasGameBeenPaused;//αν το παιχνίδι είναι σε παύση

	private Font font,messageBoxFont;
	private Color color,messageBoxColor;

	public GameGeographyState(GameStateManager gsm){
		this.gsm = gsm;

		backgroundPath="/Backgrounds/geo.jpg";
		imagePath="/1/greece.jpg";

		questionManager=new GeographyQuestionManager();

		//init messageBox
		messageBox = new MessageBox();
		messageBox.setXandY(GamePanel.WIDTH / 2 - messageBox.getWidth() / 2,	GamePanel.HEIGHT / 2 - messageBox.getHeight() / 2);
		displayMessage = false;

		//fonts & colors -τα fonts είναι συναρτήσει ύψους-πλάτους παραθύρου
		font=new Font("Courier New", Font.BOLD,(int) ((width/(double)height) * 20));
		color=Color.red.brighter();
		messageBoxColor=Color.black;
		messageBoxFont=new Font("Courier New", Font.PLAIN , (int) (20 * messageBox.getWidth()/(double)messageBox.getHeight()));

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

		}

		hasGameBeenPaused = false;
	}

	@Override
	public void update() {

		difficulty=gsm.getDifficulty();

		if(difficulty==1){ //αν είμαστε στην Ε' Δημοτικού, 
			imagePath="/1/greece.jpg";//εμφανίζεται ο χάρτης της Ελλάδος
		}
		else if(difficulty==2){//αν είμαστε στην Στ' Δημοτικού, 
			imagePath="/2/europe.jpg";//εμφανίζεται ο χάρτης της Ευρώπης
		}

		try {
			image=ImageIO.read(getClass().getResourceAsStream(imagePath));
		} catch (IOException e) {

		}

	}
	public void mouseClicked(int mouseType, int x, int y) {
		//αν επιλεγεί το κουμπί ελέγχου & δεν είναι ορατό το messageBox
		if(checkButton.isClicked(x, y) && !displayMessage){
			//γίνεται ορατό το MessageBox
			displayMessage = true; 

			//αναζήτηση για το αν έχει επιλεγεί απάντηση
			boolean atLeastOneQuestionSelected = false;
			if(difficulty==1){
				for(Question_Geography q: questionManager.getQuestionsGreece()){
					if(q.isSelected())
						atLeastOneQuestionSelected = true;
				}
			}
			else{
				for(Question_Geography q: questionManager.getQuestionsEurope()){
					if(q.isSelected())
						atLeastOneQuestionSelected = true;
				}
			}
			//αν έχει επιλεγεί η σωστή απάντηση
			if(currentQuestion.isSelected() && atLeastOneQuestionSelected){

				currentQuestion.setSelected(false);//ξε-επιλέγεται η απάντηση
				messageBox.setMessage("Σωστό!");	//το MessageBox βγάζει το μήνυμα "Σωστό"
				player.setTempScore(player.getTempScore()+1);//αυξάνεται το σκορ
				currentQuestion.setAnswered(true);//η ερώτηση δηλώνεται ως απαντημένη

			}
			//αν δεν έχει επιλεγεί απάντηση
			else if(!atLeastOneQuestionSelected){
				//εμφανίζεται το μήνυμα "Διάλεξε"
				messageBox.setMessage("Διάλεξε!");
			}
			else{
				//αλλιώς εμφανίζεται το μήνυμα 'Λάθος'
				messageBox.setMessage("Λάθος!");

				//σβήνει όποια απάντηση είναι επιλεγμένη
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
				//επιλέγεται η σωστή απάντηση
				currentQuestion.setSelected(true);
				//η ερώτηση δηλώνεται ως απαντημένη 
				currentQuestion.setAnswered(true);
			}
			//αν επιλεγεί απάντηση 
			//προχωράμε στην επόμενη ερώτηση
			if(atLeastOneQuestionSelected){
				if(difficulty==1)
					currentQuestion=questionManager.getNextQuestionGreece(currentQuestion);
				else if(difficulty==2)
					currentQuestion=questionManager.getNextQuestionEurope(currentQuestion);
			}
		}
		//αν έχει πατηθεί το πλήκτρο αγνόησης και το messageBox δεν είναι ορατό
		//επιλέγεται η επόμενη ερώτηση
		//και σβήνει η επιλεγμένη ερώτηση
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
		//αν πατηθεί το πλήκτρο 'εντάξει' στο messageBοx, τότε σβήνει
		else if(displayMessage && messageBox.isOkButtonClicked(x, y)){ 
			displayMessage= false; 
		} 
		else{
			//αλλιώς γίνεται έλεγχος για το αν έχει επιλεγεί κάποια απάντηση
			String selectedQuestion = null;

			if(difficulty==1){

				for(Question_Geography q:questionManager.getQuestionsGreece()){//για κάθε ερώτηση

					//επιλέγονται τα όρια για την κάθε απάντηση κουκκίδα
					int minx=(int)(q.getPoint().x *(width/1000.0));
					int miny=(int)(q.getPoint().y *(height/1000.0));
					int maxx=10+(int)(q.getPoint().x *(width/1000.0));
					int maxy=10+(int)(q.getPoint().y *(height/1000.0));

					//αν η επιλογή έχει γίνει μέσα στα όρια, 
					//η ερώτηση δηλώνεται ως απαντημένη
					if(x>minx && y>miny && x<maxx && y<maxy){
						selectedQuestion=q.getQuestion();
					}
				}
				/*η επιλεγμένη ερώτηση δηλώνεται ως επιλεγμένη , 
				 * ενώ ακυρώνονται όλες οι άλλες, έτσι ώστε μόνο 
				 * μια ερώτηση να είναι επιλεγμένη κάθε φορά
				 */
				for(Question_Geography q:questionManager.getQuestionsGreece()){
					if(q.getQuestion().equals(selectedQuestion)) 
						q.setSelected(true); 
					else 
						q.setSelected(false);
				}
			}
			//αντίστοιχα με τις ερωτήσεις της Ελλάδας, γίνονται και της Ευρώπης για την Στ' Δημοτικού
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

		//σχεδιάζεται το background και ο χάρτης
		background.render(g);
		g.drawImage(image, xImage, yImage,GamePanel.WIDTH/2,3 * (GamePanel.HEIGHT/4),null);

        Color color =g.getColor();
        Font font=g.getFont();
        g.setFont(new Font("SansSerif",Font.PLAIN,18));
        g.setColor(Color.black);
       
        g.drawString("Πατήστε ESC για το μενού επιλογών", (int)(GamePanel.WIDTH/10.0), (int) (GamePanel.HEIGHT*(9.5/10)));
       
        g.setColor(color);
        g.setFont(font);

		//αν τελειώσουν οι ερωτήσεις και δεν ειναι ορατο το messageBox, 
		//εμφανίζεται το GAME_OVER_STATE
		if(currentQuestion==null && !displayMessage)
			gsm.setState(GameStateManager.GAME_OVER_STATE);

		g.setColor(color);
		g.setFont(font);

		//καταγράφεται το σκορ
		g.drawString("Βαθμοί: "+player.getTempScore(), GamePanel.WIDTH/10, GamePanel.HEIGHT/8);

		g.setColor(messageBoxColor);
		g.setFont(messageBoxFont);

		if (displayMessage)
			//εμφανίζεται το MessageBox
			messageBox.render(g);
		else {
			//αλλιώς εμφανίζεται η ερώτηση και τα κουμπιά
			renderQuestion(g);
			checkButton.render(g);
			ignoreButton.render(g);

		}
	}

	public void renderQuestion(Graphics g) {

		g.setColor(color);
		g.setFont(font);

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
		//συνάρτηση αρχικοποίησης

		if(!hasGameBeenPaused){
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
		else{
			hasGameBeenPaused = false;
		}



	}

	public void setImagePath(String img){
		imagePath=img;
	}

	public void keyPressed(int keyCode) {
		//αν πατηθεί το ESC, τότε γίνεται μετακίνηση στο μενού παύσης
		if(keyCode == KeyEvent.VK_ESCAPE){
			PauseMenuState.setPreviousState(GameStateManager.GAME_GEOGRAPHY_STATE);
			gsm.setState(GameStateManager.PAUSE_MENU_STATE);
			hasGameBeenPaused = true;
		}
	}

	public void keyReleased(int keyCode) {

	}

	public void mouseDragged() {

	}

	public void mouseMoved(int x, int y) {

	}
}
