package GameState;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import Entity.QuestionManager_Geography;
import Entity.Question_Geography;
import Main.GamePanel;


public class GameGeographyState extends GameState{

	private static final String questionsFile="questions_Geography.txt";

	private QuestionManager_Geography questionManager;
	private String imagePath, backgroundPath;
	private int difficulty=0;
	private GameGeographySubState game;
	private String questions;
	private int width = GamePanel.WIDTH;
	private int height = GamePanel.HEIGHT;

	public GameGeographyState(GameStateManager gsm){
		this.gsm = gsm;
		backgroundPath="/Backgrounds/geo.jpg";
		imagePath="/1/greece.jpg";

		questionManager=loadQuestions("Data/"+questionsFile);
		save();

		game=new GameGeographySubState(backgroundPath, imagePath , questionManager,gsm);
	}

	private void save() {
		try {
			FileWriter fr=new FileWriter("questions_Geography1.txt");
			PrintWriter pr=new PrintWriter(fr);
			
			pr.println("Questions - Greece");
			for(Question_Geography q: questionManager.getQuestionsGreece()){
				pr.println(q.getQuestion());
				pr.println(   (   (q.getPoint().x ) *1000)/width          ) ;
				pr.println(   (   (q.getPoint().y ) *1000)/height         );
			}
			
			pr.println("Questions - Europe");
			for(Question_Geography q: questionManager.getQuestionsEurope()){
				pr.println(q.getQuestion());
				pr.println(   (   (q.getPoint().x ) *1000)/width          ) ;
				pr.println(   (   (q.getPoint().y ) *1000)/height         );
			
			}
			
			pr.close();
			fr.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public QuestionManager_Geography loadQuestions(String file){
		questionManager= new QuestionManager_Geography();
		try{
			FileReader fr=new FileReader(file);
			BufferedReader in = new BufferedReader(fr);
			String s;
			int x,y;

			do{
				s=in.readLine();
			}while(!s.trim().equals("Questions - Greece"));

			while(!(s=in.readLine()).trim().equals("Questions - Europe")){

				x=Integer.parseInt(in.readLine());
				y=Integer.parseInt(in.readLine());
				questionManager.addQuestionGreece(s, new Point(x,y));
			}

			while(!s.trim().equals("Questions - Europe")){
				s=in.readLine();
			}

			while(in.ready()){
				s=in.readLine();
				x=Integer.parseInt(in.readLine());
				y=Integer.parseInt(in.readLine());
				questionManager.addQuestionEurope(s, new Point(x,y));

			}

			in.close();
			fr.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return questionManager;
	}

	@Override
	public void init() {
		game.init();

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
		game.setImagePath(imagePath);
		game.update();

	}

	@Override
	public void render(Graphics2D g) {
		game.render(g);

	}

	@Override
	public void keyPressed(int keyCode) {
		game.keyPressed(keyCode);
	}

	@Override
	public void keyReleased(int keyCode) {
		game.keyReleased(keyCode);
	}

	@Override
	public void mouseClicked(int mouseType, int x, int y) {
		game.mouseClicked(mouseType, x, y);
	}

	@Override
	public void mouseDragged() {
		game.mouseDragged();
	}

	@Override
	public void mouseMoved(int x, int y) {
		game.mouseMoved(x, y);
	}
}
