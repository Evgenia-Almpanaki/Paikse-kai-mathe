package GameState;

import java.awt.Graphics2D;

/*
 * ���� � ����� ���������� ��� <<����>> � ��� ��������� ��� ����������. ���������, ��� ����������,
 * ������ �� ����� � �������� �������� ��� ������ � ��� �������� ��������� � �� ����� ��� 
 * ���������� (game over). ����� abstract ����� �������� ������ ������ �� ������ ���������.
 */

public abstract class GameState {

	//fields
	protected GameStateManager gsm;
	protected boolean hasBeenInitiated = false;
	
	////methods
	public boolean hasBeenInitiated(){
		return hasBeenInitiated;
	}
	
	public void setHasBeenInitiated(boolean b){
		hasBeenInitiated = b;
	}
	
	public abstract void init();
	public abstract void update();
	public abstract void render(Graphics2D g);
	
	//listeners
	public abstract void keyPressed(int keyCode);
	public abstract void keyReleased(int keyCode);
	public abstract void mouseClicked(int mouseType,int x,int y);
	public abstract void mouseDragged();
	public abstract void mouseMoved(int x,int y);
	
}
