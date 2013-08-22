package tablero;

import fantasmas.Fantasma;
import fantasmas.Texturas;
import pacman.Punto;
import main.Personaje;
import main.Sprite;

public class Casilla {
	private Sprite sprite;
	private boolean accesible;
	private boolean puerta = false;
	private Punto punto;
	
	public int distPacman = 0;
	public long pacmanPaso = 0;
	
	private int x;
	private int y;
	
	private Casilla[] vecinos = new Casilla[4];
	//	 0, izq; 1, der; 2, arriba; 3, abajo
	static public final int IZQ = 0;
	static public final int DER = 1;
	static public final int ARR = 2;
	static public final int ABA = 3;
	
	private Casilla[] teleport = new Casilla[4];
	
	public Casilla(int tipo){
		this(tipo,false,false);
	}
	
	public Casilla(int tipo, boolean bonus, boolean vacio){
		switch(tipo){
		case TiposCasilla.muro:
			this.accesible = false;
			this.puerta = true;
			this.sprite = null;
			this.punto = null;
			break;
		case TiposCasilla.pasillo:
			this.accesible = true;
			if(!vacio){
				this.punto = new Punto(bonus);
				if(bonus)
					this.sprite = Texturas.puntoBonus;
				else
					this.sprite = Texturas.punto;
			}
			break;
		case TiposCasilla.paredHorizontal:
			this.accesible = false;
			this.sprite = Texturas.horizontal;
			this.punto = null;
			break;
		case TiposCasilla.paredVertical:
			this.accesible = false;
			this.sprite = Texturas.vertical;
			this.punto = null;
			break;
		case TiposCasilla.paredEsquina1:
			this.accesible = false;
			this.sprite = Texturas.esquina1;
			this.punto = null;
			break;
		case TiposCasilla.paredEsquina2:
			this.accesible = false;
			this.sprite = Texturas.esquina2;
			this.punto = null;
			break;
		case TiposCasilla.paredEsquina3:
			this.accesible = false;
			this.sprite = Texturas.esquina3;
			this.punto = null;
			break;
		case TiposCasilla.paredEsquina4:
			this.accesible = false;
			this.sprite = Texturas.esquina4;
			this.punto = null;
			break;
		default:
			break;
		}
	}

	public boolean isAccesible() {
		return accesible;
	}
	
	public boolean esPuerta(){
		return puerta;
	}
	
	public void setVecino(Casilla cas, int sentido){
		vecinos[sentido] = cas;
	}
	
	public Casilla getVecino(int sentido){
		return vecinos[sentido];
	}
	
	public void setTeleport(Casilla cas, int sentido){
		teleport[sentido] = cas;
	}
	
	public Casilla atraviesa(int sentido){
		return teleport[sentido];
	}

	public int visita(Fantasma[] f) {
		int p = 0;
		if(punto != null){
			p = punto.puntuacion();
			punto = new Punto(true);
			sprite = null;
			if(p == punto.puntuacion()){
				punto.inspirarMiedo(f);
			}
			punto = null;
		}
		
		this.pacmanPaso = System.currentTimeMillis();
		
		return p;
	}
	
	public void pintar(int x,int y){
		this.x = x;
		this.y = y;
		if(sprite != null)
			this.sprite.pintar(x, y);
	}
	
	public void pintar(){
		if(sprite != null)
			this.sprite.pintar(x, y);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public boolean alineadoVertical(Personaje pj){
		if(Math.abs(pj.getX()-this.x) == 10){
			return true;
		}
		return false;
	}
	
	public boolean alineadoHorizontal(Personaje pj){
		if(Math.abs(pj.getY()-this.y) == 10){
			return true;
		}
		return false;
	}

	public String toString(){
		return this.x+" "+this.y;
	}
	
	public boolean esBola(){
		return punto != null;
	}

	public void setDistancia() {
		int minima = 99;
		
		if(this.accesible){		
			for(int i = 0; i < 4; i++)
				if(vecinos[i] != null && vecinos[i].distPacman < minima)
					minima = vecinos[i].distPacman;

		}
		this.distPacman = Math.min(minima + 1, 99);
	}
}
