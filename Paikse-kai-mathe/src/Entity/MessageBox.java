package Entity;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Main.GamePanel;

/* ���� � ����� ���������� ��� �������� ��������� ��� ����������, ��� ��� ��������� �������� ���������. ���� ��� 
 * ���������� ���� ������� ������������, ������� ��� � ��������� ���������� ���� �������, ��� ��� ��������� ��� 
 * ��������� �����. ������������ ��� ��� GameButton ��� �� �������� ��� ���������.
 */

public class MessageBox {
	
	//stuff to draw
	private BufferedImage image;
	private GameButton okButton;
	private String message;
	
	//dimension of images
	private int width;
	private int height;
	private int x;
	private int y;
	
	//constructor
	public MessageBox(){
		
		try{
			
			float aspectRatio = GamePanel.WIDTH/(float)GamePanel.HEIGHT;
			image = ImageIO.read(getClass().getResourceAsStream("/Textures/messagebox.png"));
			width = Math.abs((int)(image.getWidth() * aspectRatio - image.getWidth()));
			height = Math.abs((int)(image.getHeight() * aspectRatio - image.getHeight()));
			
			message = "";
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	//getters and setters
	public int getWidth(){return width;}
	public int getHeight(){return height;}
	
	public void setMessage(String s){message = s;}
	public void setXandY(int x, int y){
		this.x = x;
		this.y = y;
		
		okButton = new GameButton("/Textures/okButton.png");
		okButton.setX(x + width/2 - okButton.getWidth()/2);
		okButton.setY(y + height * 4/5 - okButton.getHeight());
	}
	
	//draw messageBox to screen
	public void render(Graphics2D g){
		FontMetrics fm = g.getFontMetrics();
		
		g.drawImage(image, x, y, width, height, null);
		g.drawString(message, x + width/2 - fm.stringWidth(message)/2 , y + height * 2/5 - fm.getHeight()/2);
		okButton.render(g);
		
	}
	
	//checks if button OK is clicked to dispose the messageBox from rendering
	public boolean isOkButtonClicked(int x, int y){
		if(okButton.isClicked(x, y))
			return true;
		else 
			return false;
	}
	
}
