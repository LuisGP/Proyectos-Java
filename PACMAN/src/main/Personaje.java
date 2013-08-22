package main;

import tablero.Tablero;

public abstract class Personaje {
	protected double velocidad;
	protected double x;
	protected double y;
	protected int x_tab;
	protected int y_tab;
	protected Sprite sprite;
	
	public void pintar(){
		sprite.pintar((int)x, (int)y);
	}
	
	public void situar(int x, int y){
		x_tab = x;
		y_tab = y;
		this.x = 120+(20*x);
		this.y = (20*y);
	}
	
	abstract public boolean setHorizontalMovement(Tablero tablero, int i);
	abstract public boolean setVerticalMovement(Tablero tablero, int i);
	
	public boolean colisionPersonaje(Personaje personaje, Tablero tab){
		int x = personaje.getX();
		int y = personaje.getY();
		
		return Math.abs(x-this.x)<25 && Math.abs(y-this.y)<25;
	}

	public int getX() {
		return (int)x;
	}

	public int getY() {
		return (int)y;
	}
	
	public String toString(){
		return "X: "+x+", Y: "+y;
	}
}
