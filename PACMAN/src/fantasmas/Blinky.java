package fantasmas;

import ia.*;

/* Rojo */
public class Blinky extends Fantasma {
	public Blinky(int x, int y) {
		super(x, y);
		this.sprite = Texturas.blinkyDerecha;
		super.velocidad = 1.6;

		sprites[0] = Texturas.blinkyIzquierda;
		sprites[1] = Texturas.blinkyDerecha;
		sprites[2] = Texturas.blinkyArriba;
		sprites[3] = Texturas.blinkyAbajo;
	}

	public Blinky(int level) {
		this(0,0);
		this.level = level;
		this.velocidad = Math.min(2,velocidad+0.1*(level-1));
		this.vel2 = velocidad;
		
		switch(level){
		case 1:
			//this.ia = new Aleatorio();
			this.ia = new VisionDirecta();
			//this.ia = new Precognisciente();
			//this.ia = new Rastro();
			break;
		case 2:
			//this.ia = new Manhattan();
			this.ia = new VisionDirecta();
			//this.ia = new Aleatorio();
			break;
		default:
			this.ia = new Aleatorio();
			break;
		}
	}
	
	public String toString(){
		return "Blinky: "+super.toString();
	}
}
