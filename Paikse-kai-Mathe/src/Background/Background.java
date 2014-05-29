package Background;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

import javax.imageio.ImageIO;

import Main.Game;
import Main.GamePanel;

/* Αυτή η κλάση δημιουργεί ένα background από μια εικόνα, μιάς δεδομένης διεύθηνσης προορισμού.
 * Το background μπορεί να κινείται προς μια κατεύθηνση που ορίζουμε, με την βοήθεια της 
 * μεθόδου setVector και των μεταβλητών dx και dy. Αν dx=0 και dy=0, τότε το background μένει ακίνητο.
 */

public class Background {

	//background properties
	private BufferedImage image;
	private int width;
	private int height;
	
	//background position and new position after movement
	private double x;
	private double y;
	private double dx;
	private double dy;
	
	private double moveScale;
	
	//constructor
	public Background(String directory, double moveScale){
		
		try{
			
			image = ImageIO.read(getClass().getResourceAsStream(directory));
			width = GamePanel.WIDTH;//image.getWidth();
			height = GamePanel.HEIGHT;//image.getHeight();
			
			this.moveScale = moveScale;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	//sets the position of the image on screen
	public void setPosition(double x, double y){
		
		this.x = x*moveScale;
		this.y = y*moveScale;
		fixPosition();
		
	}
	
	//sets the direction that the image moves
	public void setVector(double dx, double dy){
		
		this.dx = dx;
		this.dy = dy;
		
	}

	//fixes the position of the image (to avoid some rendering errors)
	private void fixPosition() {
		while(x <= -width) x+=width;
		while(x >= width) x -= width;
		while(y <= -height) y+=height;
		while(y >= height) y -= height;
	}
	
	//update new position
	public void update(){
		
		x += dx;
		y += dy;
		fixPosition();
		
	}
	
	//draw image to the panel
	public void render(Graphics2D g){
	
		g.drawImage(image,(int)x,(int)y,width,height,null);
		if(x < 0){
			g.drawImage(image, (int)x + GamePanel.WIDTH,(int)y,width,height,null);
		}
		if(x>0){
			g.drawImage(image, (int)x - GamePanel.WIDTH, (int)y,width,height, null);
		}
		
	}
	
}
