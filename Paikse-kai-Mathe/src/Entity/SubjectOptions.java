package Entity;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import GameState.DifficultyState;
import Main.Game;
import Main.GamePanel;

public class SubjectOptions {

	private BufferedImage[] images;
	private int[] widths;
	private int[] heights;
	private int[] x;
	private int[] y;
	
	public SubjectOptions(int difficulty){
		 
		try{
			
			if(difficulty == DifficultyState.TETARTH_DHMOTIKOU){
				images = new BufferedImage[3];
				widths = new int[3];
				heights = new int[3];
				x = new int[3];
				y = new int[3];
				
				float ar = GamePanel.WIDTH/(float)GamePanel.HEIGHT;
				for(int i=0;i<images.length;i++){
					images[i] = ImageIO.read(getClass().getResourceAsStream("/SubjectMenu/subject" + (i+1) +".gif"));
					widths[i] = Math.abs((int)(images[i].getWidth()*ar - images[i].getWidth()));
					heights[i] = Math.abs((int)(images[i].getHeight()*ar - images[i].getHeight()));
					
					/*widths[i] = (int)(images[i].getWidth() * (images[i].getWidth() / (double)GamePanel.WIDTH));
					heights[i] = (int)(images[i].getHeight() * (images[i].getHeight() / (double)GamePanel.HEIGHT));*/
				}
				
				x[0] = GamePanel.WIDTH/2 - widths[0] - 10;
				x[1] = GamePanel.WIDTH/2 + 10;
				x[2] = GamePanel.WIDTH/2 - widths[2]/2;
				
				y[0] = GamePanel.HEIGHT/2 - heights[0];
				y[1] = GamePanel.HEIGHT/2 - heights[1];
				y[2] = GamePanel.HEIGHT/2 + 10;
				
			}
			else if(difficulty == DifficultyState.PEMPTH_DHMOTIKOU || difficulty == DifficultyState.EKTH_DHMOTIKOU){
				images = new BufferedImage[4];
				widths = new int[4];
				heights = new int[4];
				x = new int[4];
				y = new int[4];
				
				float ar = GamePanel.WIDTH/(float)GamePanel.HEIGHT;
				for(int i=0;i<images.length;i++){
					images[i] = ImageIO.read(getClass().getResourceAsStream("/SubjectMenu/subject" + (i+1) +".gif"));
					widths[i] = Math.abs((int)(images[i].getWidth()*ar - images[i].getWidth()));
					heights[i] = Math.abs((int)(images[i].getHeight()*ar - images[i].getHeight()));
				}
				
				x[0] = GamePanel.WIDTH/2 - widths[0] - 10;
				x[1] = GamePanel.WIDTH/2 + 10;
				x[2] = GamePanel.WIDTH/2 - widths[2] - 10;
				x[3] = GamePanel.WIDTH/2 + 10;
				
				y[0] = GamePanel.HEIGHT/2 - heights[0];
				y[1] = GamePanel.HEIGHT/2 - heights[1];
				y[2] = GamePanel.HEIGHT/2 + 10;
				y[3] = GamePanel.HEIGHT/2 + 10;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public int subjectClicked(int x,int y){
		int subject = -1;
		
		for(int i=0;i<images.length;i++){
			
			if(x>= this.x[i] && x<= this.x[i] + widths[i] && y>= this.y[i] && y<= this.y[i] + heights[i]){
				subject = i;
			}
			
		}
		
		return subject;
	}
	
	public void render(Graphics2D g){
		
		for(int i=0;i<images.length;i++){
			
			g.drawImage(images[i], x[i], y[i],widths[i],heights[i],null);
			
		}
		
	}
	
}
