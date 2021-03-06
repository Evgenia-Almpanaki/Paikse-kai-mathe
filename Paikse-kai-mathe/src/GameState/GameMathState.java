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
import Entity.MathQuestionManager;
import Entity.Question_Math;
import Main.GamePanel;

public class GameMathState extends GameState {

	private Background bg;
	private BufferedImage image;

	private MathQuestionManager questionManager;
	private GameStateManager gsm;

	private Player player;
	private MessageBox messageBox;
	private GameButton checkButton, ignoreButton;
	private Question_Math currentQuestion;

	private boolean displayMessage;

	private GameTextField textfield;
	private String input;

	private Font inputFont, scoreFont,messageBoxFont;
	private Color inputColor, scoreColor,messageBoxColor;

	private boolean hasGameBeenPaused;

	public GameMathState(GameStateManager gsm){

		this.gsm = gsm;
		questionManager=new MathQuestionManager();

		//init messageBox
		messageBox = new MessageBox();
		messageBox.setXandY(GamePanel.WIDTH / 2 - messageBox.getWidth() / 2,	GamePanel.HEIGHT / 2 - messageBox.getHeight() / 2);
		displayMessage = false;

		//fonts & colors
		inputFont=new Font(Font.MONOSPACED, Font.ITALIC , (int) (20 * GamePanel.WIDTH/(double)GamePanel.HEIGHT));
		scoreFont=new Font("Courier New", Font.BOLD , (int) (20 * GamePanel.WIDTH/(double)GamePanel.HEIGHT));
		messageBoxFont=new Font("Courier New", Font.PLAIN , (int) (20 * messageBox.getWidth()/(double)messageBox.getHeight()));

		inputColor=Color.red.brighter();
		scoreColor=Color.blue.brighter();
		messageBoxColor=Color.black;

		//init background
		bg=new Background("/Backgrounds/mathBackground.jpg",1);
		bg.setVector(0, 0);

		//init image
		try {
			image=ImageIO.read(getClass().getResourceAsStream("/Backgrounds/math.jpg"));
		} catch (IOException e) {

		}

		//init textfield

		textfield=new GameTextField("/Textures/textfield.png");
		textfield.setX((int) (GamePanel.WIDTH/2-textfield.getWidth()/2));
		textfield.setY((int)(GamePanel.HEIGHT * (3/4.0)));

		// buttons
		ignoreButton = new GameButton("/Textures/buttonNext.png");
		ignoreButton.setX(GamePanel.WIDTH - ignoreButton.getWidth() - 10);
		ignoreButton.setY(GamePanel.HEIGHT - ignoreButton.getHeight() - 10);

		checkButton = new GameButton("/Textures/buttonCheck.png");
		checkButton.setX(GamePanel.WIDTH - checkButton.getWidth() - 10);
		checkButton.setY(ignoreButton.getY() - checkButton.getHeight() - 10);

		hasGameBeenPaused = false;
	}

	@Override
	public void init() {

		if(!hasGameBeenPaused){
			questionManager.init();
			//������� �� ��� ����-��������, ���������� �� ��������� ������
			switch(gsm.getDifficulty()){
			case 0: questionManager.loadQuestions("0");
			break;
			case 1: questionManager.loadQuestions("1");
			break;
			case 2: questionManager.loadQuestions("2");
			break;
			}

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

		//����������� �� background ��� �� textfield
		bg.render(g);
		textfield.render(g);

        Color color =g.getColor();
        Font font=g.getFont();
        g.setFont(new Font("SansSerif",Font.PLAIN,18));
        g.setColor(Color.black);
        
        g.drawString("������� ESC ��� �� ����� ��������", (int)(GamePanel.WIDTH/6.0), (int) (GamePanel.HEIGHT*(9.5/10)));
       
        g.setColor(color);
        g.setFont(font);

		//����������� �� ������� ��� �� �������-�������
		g.drawImage(image, GamePanel.WIDTH/4, GamePanel.HEIGHT/4,GamePanel.WIDTH/2, GamePanel.HEIGHT/2,null);

		g.setColor(scoreColor);
		g.setFont(scoreFont);

		//������������ �� ����
		g.drawString("������: "+player.getTempScore(), GamePanel.WIDTH/10, GamePanel.HEIGHT/8);

		//�� ���������� �� ��������� ��� ��� ����� ����� �� messageBox, 
		//����������� �� GAME_OVER_STATE
		if(currentQuestion==null && !displayMessage) 
			gsm.setState(GameStateManager.GAME_OVER_STATE);

		if (displayMessage){

			g.setColor(messageBoxColor);
			g.setFont(messageBoxFont);

			//����������� �� MessageBox & �������� � ��������
			messageBox.render(g);

			textfield.setInput("");
			textfield.setxInput(GamePanel.WIDTH/2 - textfield.getWidth()/2 + 17);
			textfield.setyInput(GamePanel.HEIGHT/2 - textfield.getHeight()/2 + g.getFontMetrics().getHeight() + 10);
			textfield.render(g);

		}
		else {

			//������ ����������� � �������,�� textfield ��� �� �������
			checkButton.render(g);
			ignoreButton.render(g);

			g.setColor(inputColor);
			g.setFont(inputFont);

			textfield.setxInput(textfield.getX()+19);
			textfield.setyInput(textfield.getY() + (int) (textfield.getHeight()/2.5) + 10);
			textfield.render(g);

			if(currentQuestion!=null)
				questionManager.drawString(g, currentQuestion.getQuestion(),(int)(GamePanel.WIDTH*(290/1000.0)),(int)(GamePanel.HEIGHT*(330/1000.0)),(int)(GamePanel.WIDTH*(430/1000.0)));

		}
	}

	@Override
	public void mouseClicked(int mouseType, int x, int y) {
		//�� �������� �� ������ ������� & ��� ����� ����� �� messageBox
		if(checkButton.isClicked(x, y) && !displayMessage){	

			displayMessage = true; //������� ����� �� MessageBox
			input=textfield.getText();//��������� �� ������� ��� �������

			//�� ��� ���� �������� ��������
			if(input.trim().equals("")){
				messageBox.setMessage("��������!");//����������� �� ������ "��������"
			}
			//�� ���� �������� � ����� ��������
			else if(currentQuestion.checkAnswer(input.trim())){
				messageBox.setMessage("�����!");	//����������� �� ������ "�����"
				player.setTempScore(player.getTempScore()+1);//��������� �� ���� ���� ���
				currentQuestion.setAnswered(true);//� ������� ��������� �� ����������
				currentQuestion=questionManager.getNextQuestion(currentQuestion);//��������� ���� ������� �������
			}
			else{
				//������ ����������� �� ������ "�����"
				messageBox.setMessage("�����! �����: "+currentQuestion.getAnswer());//����������� �� ������ "�����" ��� ����������� ��� � ����� ��������
				currentQuestion.setAnswered(true);//� ������� ��������� �� ����������
				currentQuestion=questionManager.getNextQuestion(currentQuestion);//��������� ���� ������� �������
			}
		}
		//�� ���� ������� �� ������� �������� ��� �� messageBox ��� ����� �����
		//���������� � ������� �������
		else if(ignoreButton.isClicked(x, y) && !displayMessage){ 
			textfield.setInput("");
			currentQuestion=questionManager.getNextQuestion(currentQuestion);
		} 
		//�� ������� �� ������� '�������' ��� messageB�x, ���� ������.
		else if(displayMessage && messageBox.isOkButtonClicked(x, y)){ 
			displayMessage= false; 
		} 

	}

	@Override
	public void keyPressed(int keyCode) {
		//�� ������� �� ESC, ���� ������� ���������� ��� ����� ������
		if(keyCode == KeyEvent.VK_ESCAPE){
			PauseMenuState.setPreviousState(GameStateManager.GAME_MATH_STATE);
			gsm.setState(GameStateManager.PAUSE_MENU_STATE);
			hasGameBeenPaused = true;
			return;
		}
		//������ �������������� �� keyPressed ��� textfield
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
