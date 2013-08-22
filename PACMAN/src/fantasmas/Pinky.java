package fantasmas;

import ia.*;

/* Rosa */
public class Pinky extends Fantasma {

	public Pinky(int x, int y) {
		super(x, y);
		this.sprite = Texturas.pinkyArriba;
		super.velocidad = 1.4;

		sprites[0] = Texturas.pinkyIzquierda;
		sprites[1] = Texturas.pinkyDerecha;
		sprites[2] = Texturas.pinkyArriba;
		sprites[3] = Texturas.pinkyAbajo;
	}
	
	public Pinky(int level) {
		this(0,0);
		this.level = level;
		this.velocidad = Math.min(1.8,velocidad+0.1*(level-1));
		this.vel2 = velocidad;
		
		switch(level){
		case 1:
			this.ia = new Aleatorio();
			//this.ia = new Quieto();
			break;
		default:
			this.ia = new Aleatorio();
			break;
		}
	}
	
	public String toString(){
		return "Pinky: "+super.toString();
	}
}
