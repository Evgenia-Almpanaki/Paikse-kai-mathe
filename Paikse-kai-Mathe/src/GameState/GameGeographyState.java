package GameState;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Entity.QuestionManager_Geography;
import Entity.Question_Geography;
import Main.GamePanel;


public class GameGeographyState extends GameState{


	private QuestionManager_Geography questionManager;
	private String imagePath, backgroundPath;
	private Geography game;
	private String questions;
	private int width = GamePanel.WIDTH;
	private int height = GamePanel.HEIGHT;

	public GameGeographyState(GameStateManager gsm){
		this.gsm = gsm;
		backgroundPath="/Backgrounds/geo.jpg";

		if(gsm.getDifficulty()==1){
			imagePath="/1/greece.jpg";
			questions="questions1.txt";
		}
		else {//if(gsm.getDifficulty()==2){
			imagePath="/1/greece.jpg";
			//imagePath="/2/europe.jpg";
			questions="questions.txt";//2
		}

		//questionManager=loadQuestions(GAME_FOLDER_1 + questions);
		questionManager= new QuestionManager_Geography();
		addQuestionsHardCoded();
		
		//saveChangesToFile("/1/" + questions);

		game=new Geography(backgroundPath, imagePath , questionManager,gsm);
	}

	public QuestionManager_Geography loadQuestions(String file){
		try{
			FileInputStream fileIn = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			questionManager = (QuestionManager_Geography)in.readObject();
			fileIn.close();
			in.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		if(questionManager == null){
			questionManager = new QuestionManager_Geography();
		}
		return questionManager;
	}

	void saveChangesToFile(String file){
		try{
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			if(questionManager != null)
				oos.writeObject(questionManager);
			oos.close();
			fos.close();
		}
		catch(Exception ex){
			ex.printStackTrace();

		}
	}

	private void addQuestionsHardCoded(){

		questionManager.addQuestion("Που βρίσκεται ο Θερμαϊκός κόλπος;",(new Point((int) (664*(width/1000.0)),(int) (420 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται ο ποταμός Εβρος;",(new Point((int) (823*(width/1000.0)),(int) (330 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται η λίμνη Κορώνεια;",(new Point((int) (680*(width/1000.0)),(int) (366 * (height/1000.0)))));
		/*questionManager.addQuestion("Που βρίσκεται η λίμνη Βόλβη;",(new Point((int) (693*(width/1000.0)),(int) (366 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται ο Βόλος, πρωτεύουσα του νομού Μαγνησίας;",(new Point((int) (668*(width/1000.0)),(int) (504 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται ο κόλπος Παγασητικός;",(new Point((int) (670*(width/1000.0)),(int) (520 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται ο Αμβρακικός κόλπος;",(new Point((int) (575*(width/1000.0)),(int) (538 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται το όρος Κίσσαβος;",(new Point((int) (657*(width/1000.0)),(int) (460 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται η λίμνη Βιστωνίδα;",(new Point((int) (771*(width/1000.0)),(int) (324 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται ο Πύργος, πρωτεύουσα του νομού Ηλείας;",(new Point((int) (595*(width/1000.0)),(int) (682 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται η οροσειρά της Ροδόπης;",(new Point((int) (761*(width/1000.0)),(int) (290 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται η Βέροια, πρωτεύουσα του νομού Ημαθίας;",(new Point((int) (635*(width/1000.0)),(int) (380 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται το Καρπενήσι, πρωτεύουσα του νομού Ευρυτανίας;",(new Point((int) (610*(width/1000.0)),(int) (550 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται το όρος Ταΰγετος;",(new Point((int) (632*(width/1000.0)),(int) (750 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται η λίμνη μικρή Πρέσπα;",(new Point((int) (587*(width/1000.0)),(int) (350 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται το όρος Ψηλορείτης;",(new Point((int) (760*(width/1000.0)),(int) (950 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται το νησί Λέσβος;",(new Point((int) (817*(width/1000.0)),(int) (507 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται ο ποταμός Αξιός;",(new Point((int) (655*(width/1000.0)),(int) (346 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται ο ποταμός Αχελώος;",(new Point((int) (590*(width/1000.0)),(int) (590 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται το νησί Νάξος;",(new Point((int) (790*(width/1000.0)),(int) (750 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται το νησί Αμοργός;",(new Point((int) (810*(width/1000.0)),(int) (770 * (height/1000.0)))));
		
	
		questionManager.addQuestion("Που βρίσκονται τα Πυρηναία όρη;",(new Point((int) (690*(width/1000.0)),(int) (755 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται η οροσειρά των ’λπεων;",(new Point((int) (750*(width/1000.0)),(int) (695 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται ο ποταμός Σηκουάνας;",(new Point((int) (710*(width/1000.0)),(int) (660 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται η Σικελία;",new Point((int) (778*(width/1000.0)),(int) (850 * (height/1000.0))));
		questionManager.addQuestion("Που βρίσκεται το ’μστερνταμ, πρωτεύουσα της Ολλανδίας;",(new Point((int) (722*(width/1000.0)),(int) (600 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται η Κοπεγχάγη, πρωτεύουσα της Δανίας;",(new Point((int) (760 * (width/1000.0)),(int) (540 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται ο ποταμός Τάμεσης;",(new Point((int) (695 * (width/1000.0)),(int) (600 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται ο ποταμός Ρήνος;",(new Point((int) (732*(width/1000.0)),(int) (610 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκονται τα Καυκάσια όρη;",(new Point((int) (958*(width/1000.0)),(int) (665 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται η Λισαβόνα, πρωτεύουσα της Πορτογαλίας;",(new Point((int) (625*(width/1000.0)),(int) (798 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται η Ιρλανδία;",(new Point((int) (665*(width/1000.0)),(int) (550 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται η Ισλανδία;",(new Point((int) (655*(width/1000.0)),(int) (340 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται η νήσος Κορσική;",(new Point((int) (745*(width/1000.0)),(int) (775 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται η Κύπρος;",(new Point((int) (910*(width/1000.0)),(int) (848 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκονται οι Βαρσοβία, πρωτεύουσα της Πολωνίας;",(new Point((int) (805*(width/1000.0)),(int) (605 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται η Βιέννη, πρωτεύουσα της Αυστρίας;",(new Point((int) (805*(width/1000.0)),(int) (605 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται η Πράγα, πρωτεύουσα της Τσεχίας;",(new Point((int) (773*(width/1000.0)),(int) (631 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται η νήσος Σαρδηνία;",(new Point((int) (745*(width/1000.0)),(int) (805 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται η Αδριατική θάλασσα;",(new Point((int) (775*(width/1000.0)),(int) (735 * (height/1000.0)))));
		questionManager.addQuestion("Που βρίσκεται η Βαλτική θάλασσα;",(new Point((int) (787*(width/1000.0)),(int) (520 * (height/1000.0)))));
	*/	}



	@Override
	public void init() {
		game.init();

	}

	@Override
	public void update() {
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
