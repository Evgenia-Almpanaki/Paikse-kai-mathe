package Entity;

public class Question_Math {
	
	private String question;	//η ερώτηση σε μορφή String
	private String answer; 		//η απάντηση σε μορφή String
	private boolean answered;	//αν έχει απαντηθεί
	private boolean asked;		//αν έχει ερωτηθεί
	
	public Question_Math(String q,String ans){
		question=q;
		answer=ans;
		answered=false;
		asked=false;
	}
	
	public boolean isAsked() {
		return asked;
	}

	public void setAsked(boolean asked) {
		this.asked = asked;
	}

	public boolean isAnswered() {
		return answered;
	}

	public void setAnswered(boolean answered) {
		this.answered = answered;
	}
	
	public String getQuestion(){
		return question;
	}
	
	public String getAnswer(){
		return answer;
	}
	
	//συνάρτηση που επιστρέφει αν το όρισμα ισούται με την απάντηση
	public boolean checkAnswer(String s){
		if(answer.trim().equals(s.trim()))
			return true;
		return false;
	}
	

}
