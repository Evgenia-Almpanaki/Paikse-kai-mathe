package Background;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Main.Game;
import Main.GamePanel;

/* This class creates a background for the game with the given directory.
 * The background is able to auto-move in a direction we give, as doubles dx and dy.
 * If dx and dy are 0, the background doesn't move.
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
	
	public void setPosition(double x, double y){
		
		this.x = x*moveScale;
		this.y = y*moveScale;
		fixPosition();
		
	}
	
	public void setVector(double dx, double dy){
		
		this.dx = dx;
		this.dy = dy;
		
	}

	private void fixPosition() {
		while(x <= -width) x+=width;
		while(x >= width) x -= width;
		while(y <= -height) y+=height;
		while(y >= height) y -= height;
	}
	
	public void update(){
		
		x += dx;
		y += dy;
		fixPosition();
		
	}
	
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
