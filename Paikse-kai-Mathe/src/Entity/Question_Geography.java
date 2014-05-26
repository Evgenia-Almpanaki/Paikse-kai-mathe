package Entity;
import java.awt.Point;
import java.io.Serializable;


public class Question_Geography implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String question;
	private Point pos; 
	private boolean selected=false;
	
	public Question_Geography(String q,Point p){
		question=q;
		pos=p;
	}
	
	public String getQuestion(){
		return question;
	}
	
	public Point getPoint(){
		return pos;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
