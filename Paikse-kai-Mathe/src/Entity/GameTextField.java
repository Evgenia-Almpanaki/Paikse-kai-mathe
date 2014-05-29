package Entity;

import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Main.GamePanel;

/* Αυτή η κλάση αναπαριστά ένα textfield του παιχνιδιού. Κατα την δημιουργία ενός τέτοιου αντικειμένου,
 * δίνεται και η διεύθηνση προορισμού μιας εικόνας, για την υλοποίηση του textfield. Το textfield μπορεί 
 * να κρατήσει το μεγεθός του και τις συντεταγμένες στις οποίες θα ζωγραφιστεί πάνω στην οθόνη, για λόγους
 *αναγνώρισης του.
 */

public class GameTextField {

	//image drawing fields
	private BufferedImage image;
	private int x;
	private int y;
	private int width;
	private int height;
	
	//text input to the field
	private String input;
	private String tempInput;
	
	
	//constructor
	public GameTextField(String path){
		
		try{
			
			image = ImageIO.read(getClass().getResourceAsStream(path));
			width = image.getWidth();
			height = image.getHeight();
			input = "";
			tempInput ="";
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	//getters and setters
	public int getX(){return x;}
	public int getY(){return y;}
	public int getWidth(){return width;}
	public int getHeight(){return height;}
	public String getText(){return input;}
	
	public void setX(int x){this.x = x;}
	public void setY(int y){this.y = y;}
	
	
	public void update(){}
	
	//draw textfield to screen
	public void render(Graphics2D g){
		g.drawImage(image, x, y, null);
		
		if(g.getFontMetrics().stringWidth(input) > width){
			int counter =1;
			tempInput = input;
			while(true){
				if(g.getFontMetrics().stringWidth(tempInput) > width - 30){
					tempInput = input.substring(counter);
					counter++;
				}
				else
					break;
			}
			
			g.drawString(tempInput, GamePanel.WIDTH/2 - width/2 + 17, GamePanel.HEIGHT/2 - height/2 + g.getFontMetrics().getHeight() + 10);
		}
		else
			g.drawString(input, GamePanel.WIDTH/2 - width/2 + 17, GamePanel.HEIGHT/2 - height/2 + g.getFontMetrics().getHeight() + 10);
		
		/*if(input.length() > 17){
			g.drawString(input.substring(input.length()-17, input.length()), GamePanel.WIDTH/2 - width/2 + 17, 
				GamePanel.HEIGHT/2 - height/2 + g.getFontMetrics().getHeight() + 10);
		}
		else{
			g.drawString(input, GamePanel.WIDTH/2 - width/2 + 17, GamePanel.HEIGHT/2 - height/2 + g.getFontMetrics().getHeight() + 10);
		}*/
	}
	
	
	//event for typing input
	public void keyPressed(int keyCode){
		
		if(keyCode >= 65 && keyCode <= 90){
			boolean capsLockIsON = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
			if(capsLockIsON)
				input += KeyEvent.getKeyText(keyCode);
			else{
				String temp = KeyEvent.getKeyText(keyCode);
				temp = temp.toLowerCase();
				input += temp;
			}
		}
		else if(keyCode == KeyEvent.VK_BACK_SPACE){
			if(input.length()>0){
				input = input.substring(0, input.length()-1);
				input.trim();
			}
		}
		
	}
	
}
