package pacman;

import fantasmas.Fantasma;

public class Punto {
	private int puntos = 10;
	private int mult = 1;
	
	public Punto(boolean bonus){
		if(bonus){
			mult = 5;
		}
	}
	
	public int puntuacion(){
		return mult*puntos;
	}
	
	public void inspirarMiedo(Fantasma[] f){
		for(int i = 0; i < f.length; i++){
			f[i].infundirMiedo();
		}
	}
}
