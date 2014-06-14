package Entity;


/* Η κλάση αυτή αναπαριστά ένα μενού επιλογών για το παιχνίδι, όπου θα οδηγούν σε διάφορες άλλες 
 * φάσεις του παιχνιδιού. Οι επιλογές δίνονται ως ένας πίνακας από string. Επίσης, αποθηκεύονται 
 * το μέγεθος και οι συντεταγμένες των string αυτών, όπως ακριβώς θα ζωγαφιστούν στην οθόνη.
 */

public class MenuOptions {

	//fields
	private String[] option;
	private int[] x;
	private int[] y;
	private int[] width;
	private int[] height;
	
	//number of options in the array
	private int length;
	
	//constructor
	public MenuOptions(String[] options){
		option = options;
		length = option.length;
		x = new int[option.length];
		y = new int[option.length];
		width = new int[option.length];
		height = new int[option.length];
	}
	
	//getters and setters
	public void setWidth(int width, int optionPosition){
		this.width[optionPosition] = width;
	}
	
	public void setHeight(int height, int optionPosition){
		this.height[optionPosition] = height;
	}
	
	public void setX(int x, int optionPosition){
		this.x[optionPosition] = x;
	}
	
	public void setY(int y, int optionPosition){
		this.y[optionPosition] = y;
	}

	public int getX(int optionPosition) {
		return x[optionPosition];
	}

	public int getY(int optionPosition) {
		return y[optionPosition];
	}

	public int getWidth(int optionPosition) {
		return width[optionPosition];
	}

	public int getHeight(int optionPosition) {
		return height[optionPosition];
	}
	
	public String getOption(int optionPosition){
		return option[optionPosition];
	}
	
	//returns the number of options from this menu
	public int length(){
		return length;
	}
	
	//checks if and which one of the options is clicked
	public int checkIfOptionIsClicked(int x,int y){
		
		int choise = -1;
		
		for(int i=0;i<length;i++){
			
			if(x >= this.x[i] && x<= this.x[i]+width[i] && y>= this.y[i] && y<= this.y[i]+height[i]){
				return i;
			}
			
		}
		
		return choise;
		
	}
	
}
