package main;

public interface Sprite {
	public int getWidth();
	public int getHeight();
	public void pintar(int x,int y);
	public GameWindow getWindow();
}