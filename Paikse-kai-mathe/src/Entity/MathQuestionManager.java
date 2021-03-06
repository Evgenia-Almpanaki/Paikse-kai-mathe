package Entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

import Main.GamePanel;

public class MathQuestionManager {

	private static final String questionsFile="questions_Math.txt";

	private ArrayList<Question_Math> questions;			//�� ������ ��� ���������
	private ArrayList<Question_Math> askedQuestions;	//�� ������ ��� ��������� ��� ����� ��������
	private boolean oncePlayed;							//�� ����� ������� �� ��������� ��� ���� ���������������� �� ���������� ���������

	//colors & fonts
	private Font questionFont;
	private Color questionColor;

	public MathQuestionManager(){

		//font & color
		questionFont = new Font("Courier New", Font.ITALIC, (int) (15 * GamePanel.WIDTH/(double)GamePanel.HEIGHT));
		questionColor=Color.black;

		init();
	}

	public Font getQuestionFont() {
		return questionFont;
	}

	public Color getQuestionColor(){
		return questionColor;
	}
	
	public void init() {
		//��������� �������������
		questions = new ArrayList<Question_Math>();
		askedQuestions= new ArrayList<Question_Math>();
		oncePlayed=false;

	}

	public MathQuestionManager loadQuestions(String difficulty){
		//"�����������" �� ��������� ��� �� ������

		try{
			FileReader fr=new FileReader("Data/"+difficulty+"/"+questionsFile);
			BufferedReader in = new BufferedReader(fr);

			String question="";
			String s,ans;

			while(in.ready()){
				question="";
				while(!(s=in.readLine()).trim().equals("")){
					question += ("\n" + s);
				}

				ans=in.readLine();

				in.readLine();

				this.questions.add(new Question_Math(question.trim(), ans.trim()));

			}
			in.close();
			fr.close();

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return this;
	}

	public Question_Math getNextQuestion(Question_Math q){

		if(!oncePlayed){ //�� ��� ����� ������� ��� ���� ���� �� ��������� 
			int index, numberOfAskedQuestions=0;

			//������������� ����� ��������� ����� ��������
			for(Question_Math ques: questions){
				if(ques.isAsked())
					numberOfAskedQuestions++;
			}

			//�� � ������� ��� ��������� ��� ����� �������� ����� ���� �� ��� ������ ���� ��� ���������
			//� ��������� oncePlayed ������� ��� ���� true
			if(numberOfAskedQuestions == questions.size())
				oncePlayed=true;

			else{
				//���������� ��� ������� ���� ���� ��� ��� ��������������
				do{
					Random generator = new Random(System.currentTimeMillis());
					index=generator.nextInt(questions.size());
				}while(questions.get(index).isAsked());

				//� ������� ��������� �� ���������� & ����������� ���� ����������� ���������
				questions.get(index).setAsked(true);
				askedQuestions.add(questions.get(index));

				return questions.get(index);
			}
		}
		//�� ����� �������� ��� ���� ���� �� ���������
		if(oncePlayed){
			//��������� ��� ��� ������� ��� ���� ������� �� ������
			int i;
			for(i=0; i<askedQuestions.size();i++){
				Question_Math ques= askedQuestions.get(i);
				if(ques.getQuestion().equals(q.getQuestion())){
					break;
				}

			}
			//��������� � ������� ���� 1 ��� �� ������� ��� ������� �������
			i++;

			//�� � ������� ���� ��������� �� ����, ���� �������� ��� ��� ����
			if(i==askedQuestions.size()) 
				i=0;
			//�� ��� ��� �������-������ ����� �� ����� ������� �� ���������� �������, ������������ � �������.
			for(int j=i; j<askedQuestions.size();j++){
				Question_Math ques= askedQuestions.get(j);
				if(!ques.isAnswered()){
					return ques;
				}
			}

			//�� ��� ��� ���� ����� ��� �������-������ ������� �� ���������� �������, ������������ � ������� ����.
			for(int j=0; j<i;j++){
				Question_Math ques= askedQuestions.get(j);
				if(!ques.isAnswered()){
					return ques;
				}
			}
		}
		//����� �� ����� ��������� ���� �� ��������� , ������������ � ���� null
		return null;
	}

	//������������ � index-� ������� 
	public Question_Math getQuestion(int index){
		if(index<questions.size())
			return questions.get(index);
		return null;
	}

	//����������� � �������-�������
	public void drawString(Graphics2D g, String s, int x, int y, int width)
	{		
		/*x,y � ���� ��� ������������
		 *width �� ������� ������ ������ ��� ������ �� ������� �� �������-�������
		 **/

		g.setColor(questionColor);
		g.setFont(questionFont);
		FontMetrics fm = g.getFontMetrics();

		//���� �������
		int lineHeight = fm.getHeight();

		//�������� ����� ��� �� ������ ��� �������� � ����� ����
		int curX = x;
		int curY = y;

		//��������� � ������� �� ������ �� ���� �� ��� ����� �� ����
		String[] words = s.split(" ");

		for (String word : words)//��� ���� ����
		{

			// ������ �����
			int wordWidth = fm.stringWidth(word + " ");

			/* �� �� ������� ���� �� ��� ������� ���� ��������� 
			 * �� ������� ������ ������ � � ���� ������ �� "\n",
			 * ���������� ���� ������� �����.
			 * */
			if (curX + wordWidth >= x + width || word.startsWith("\n"))
			{
				curY += lineHeight; //�� y ��������� ���� ��� �����
				curX = x;			//�� x ������������ ��� ������ x-������
			}
			//�������� � ����
			g.drawString(word, curX, curY);

			// ����� ���������� ��� ��� ������� ����
			curX += wordWidth;

		}
	}

}
