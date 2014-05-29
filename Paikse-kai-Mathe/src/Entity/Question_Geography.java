package Entity;
import java.awt.Point;
import java.io.Serializable;


public class Question_Geography implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String question;
	private Point pos; 
	private boolean selected;
	private boolean asked;
	private boolean answered;
	
	public Question_Geography(String q,Point p){
		question=q;
		pos=p;
		selected=false;
		asked=false;
	}
	
	public boolean isAnswered() {
		return answered;
	}

	public void setAnswered(boolean answered) {
		this.answered = answered;
	}

	public boolean isAsked() {
		return asked;
	}

	public void setAsked(boolean asked) {
		this.asked = asked;
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
