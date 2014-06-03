package Entity;
import java.awt.Point;
import java.io.Serializable;


public class Question_Geography implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String question;	//η ερώτηση σε μορφή String
	private Point pos; 			//το σημείο που αντιστοιχεί στην απάντηση
	private boolean selected;	//αν έχει επιλεγεί η απάντηση
	private boolean asked;		//αν έχει ερωτηθεί η ερώτηση
	private boolean answered;	//αν έχει απαντηθεί η ερώτηση
	
	public Question_Geography(String q,Point p){
		question=q;
		pos=p;
		selected=false;
		asked=false;
		answered=false;
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
