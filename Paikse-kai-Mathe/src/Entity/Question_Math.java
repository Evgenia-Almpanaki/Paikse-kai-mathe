package Entity;

public class Question_Math {
	
	private String question;
	private String answer; 
	private boolean answered;
	private boolean asked;
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
	
	public boolean checkAnswer(String s){
		if(answer.trim().equals(s.trim()))
			return true;
		return false;
	}
	

}
