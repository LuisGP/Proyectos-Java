package fantasmas;

import ia.*;

/* Cian */
public class Inky extends Fantasma {
	public Inky(int x, int y) {
		super(x, y);
		this.sprite = Texturas.inkyAbajo;
		super.velocidad = 1;

		sprites[0] = Texturas.inkyIzquierda;
		sprites[1] = Texturas.inkyDerecha;
		sprites[2] = Texturas.inkyArriba;
		sprites[3] = Texturas.inkyAbajo;
	}
	
	public Inky(int level) {
		this(0,0);
		this.level = level;
		this.velocidad = Math.min(1.5,velocidad+0.1*(level-1));
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
		return "Inky: "+super.toString();
	}
}
