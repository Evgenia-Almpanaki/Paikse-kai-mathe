package GameState;

import java.awt.Graphics2D;

/*
 * Αυτή η κλάση υποδηλώνει μια <<φάση>> ή μια κατάσταση του παιχνιδιού. Κατάσταση, για παράδειγμα,
 * μπορεί να είναι η εισαγωγή ονόματος του παίχτη ή ένα παιχνίδι μαθήματος ή το τέλος του 
 * παιχνιδιού (game over). Είναι abstract γιατί υπάρχουν πολλές φάσεις με κοινές ιδιότητες.
 */

public abstract class GameState {

	//fields
	protected GameStateManager gsm;
	protected boolean hasBeenInitiated = false;
	
	////methods
	public boolean hasBeenInitiated(){
		return hasBeenInitiated;
	}
	
	public void setHasBeenInitiated(boolean b){
		hasBeenInitiated = b;
	}
	
	public abstract void init();
	public abstract void update();
	public abstract void render(Graphics2D g);
	
	//listeners
	public abstract void keyPressed(int keyCode);
	public abstract void keyReleased(int keyCode);
	public abstract void mouseClicked(int mouseType,int x,int y);
	public abstract void mouseDragged();
	public abstract void mouseMoved(int x,int y);
	
}
