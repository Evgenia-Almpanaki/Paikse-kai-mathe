package Entity;

/* ���� � ����� ���������� ��� ������ ��� ����������. � Player ������ �� ���������� �� ����� ��� ��� �� ����.
 * �������� ��� ����, �� �������� ��� �� ���������. �� ��������� ������� ��������� �� ���� ��� ���� �������� 
 * ��� ������ � �������. �� �������� ���� ����� �� �������� ���� ��� ����������.
 */

public class Player {

	//fields
	private String name;
	private int totalScore;
	private int tempScore;
	
	//constructor
	public Player(String aName){
		name = aName;
		totalScore =0;
		tempScore =0;
	}

	//getters and setters
	public int getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}

	public int getTempScore() {
		return tempScore;
	}

	public void setTempScore(int tempScore) {
		this.tempScore = tempScore;
	}

	public String getName() {
		return name;
	}
	
}
