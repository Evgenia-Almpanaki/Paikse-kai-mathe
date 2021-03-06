package Entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

/* ���� � ����� ���������� ��� ������ ��� ����������. ���� ��� ���������� ���� ������� ������������,
 * ������� ��� � ��������� ���������� ���� �������, ��� ��� ��������� ��� ��������. �� ������ ������ 
 * �� �������� �� ������� ��� ��� ��� ������������� ���� ������ �� ����������� ���� ���� �����, ��� ������
 *����������� ��� �������� ���� �� mouseEvents.
 */

public class GameButton {

	//class fields
	private BufferedImage buttonImage;
	private int width;
	private int height;
	private int x;
	private int y;
	
	//constructor
	public GameButton(String imagePath){
		
		try{

			buttonImage = ImageIO.read(getClass().getResourceAsStream(imagePath));
			width = buttonImage.getWidth();
			height = buttonImage.getHeight();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//getters and setters
	public int getWidth(){return width;}
	public int getHeight(){return height;}
	public int getX(){return x;}
	public int getY(){return y;}
	
	public void setX(int x){this.x = x;}
	public void setY(int y){this.y = y;}
	
	//checks if this button has been clicked through a mouseEvent
	public boolean isClicked(int x, int y){
		
		boolean result = false;
		if(x >= this.x && x<=this.x + this.width && y>= this.y && y<= this.y + this.height)
			result = true;
		return result;
		
	}
	
	public void update(){}
	
	//draws the button to the screen
	public void render(Graphics2D g){
		g.drawImage(buttonImage, x, y,null);
	}
	
}
