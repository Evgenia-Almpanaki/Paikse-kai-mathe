package Entity;

import java.util.ArrayList;

public class MenuOptions {

	private String[] option;
	private int length;
	private int[] x;
	private int[] y;
	private int[] width;
	private int[] height;
	
	public MenuOptions(String[] options){
		option = options;
		length = option.length;
		x = new int[option.length];
		y = new int[option.length];
		width = new int[option.length];
		height = new int[option.length];
	}
	
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
	
	public int length(){
		return length;
	}
	
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
