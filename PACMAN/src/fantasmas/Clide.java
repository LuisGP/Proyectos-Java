package fantasmas;

import ia.*;

/* Naranja */
public class Clide extends Fantasma {
	public Clide(int x, int y) {
		super(x, y);
		this.sprite = Texturas.clideIzquierda;
		super.velocidad = 1.2;

		sprites[0] = Texturas.clideIzquierda;
		sprites[1] = Texturas.clideDerecha;
		sprites[2] = Texturas.clideArriba;
		sprites[3] = Texturas.clideAbajo;
	}
	
	public Clide(int level) {
		this(0,0);
		this.level = level;
		this.velocidad = Math.min(1.7,velocidad+0.1*(level-1));
		this.vel2 = velocidad;
		
		switch(level){
		case 1:
			//this.ia = new Aleatorio();
			//this.ia = new Manhattan();
			//this.ia = new Quieto();
			this.ia = new Precognisciente();
			break;
		default:
			//this.ia = new Aleatorio();
			this.ia = new Precognisciente();
			break;
		}
	}
	
	public String toString(){
		return "Clide: "+super.toString();
	}
}
