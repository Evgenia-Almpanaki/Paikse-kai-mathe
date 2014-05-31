package Entity;

public class Question_Math {
	
	private String question;
	private String answer; 
	private boolean answered;
	
	public Question_Math(String q,String ans){
		question=q;
		answer=ans;
		answered=false;
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
