package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Main.GamePanel;

public class GameButton {

	private BufferedImage buttonImage;
	private int width;
	private int height;
	private int x;
	private int y;
	
	public GameButton(String imagePath){
		
		try{
			
			float ar = GamePanel.WIDTH/(float)GamePanel.HEIGHT;
			
			buttonImage = ImageIO.read(getClass().getResourceAsStream(imagePath));
			width = buttonImage.getWidth();
			height = buttonImage.getHeight();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public int getWidth(){return width;}
	public int getHeight(){return height;}
	public int getX(){return x;}
	public int getY(){return y;}
	
	public void setX(int x){this.x = x;}
	public void setY(int y){this.y = y;}
	
	public boolean isClicked(int x, int y){
		
		boolean result = false;
		if(x >= this.x && x<=this.x + this.width && y>= this.y && y<= this.y + this.height)
			result = true;
		return result;
		
	}
	
	public void update(){}
	
	public void render(Graphics2D g){
		g.drawImage(buttonImage, x, y,null);
	}
	
}
