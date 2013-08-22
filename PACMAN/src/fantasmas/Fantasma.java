package fantasmas;

import tablero.Casilla;
import tablero.Tablero;
import main.Personaje;
import main.Sprite;

import ia.*;

public abstract class Fantasma extends Personaje{
	public static final int MIEDO = 1;
	public static final int NORMAL = 2;
	public static final int COMIDO = 3;
	public static final int SALIENDO = 4;
	public static final int DENTRO = 5;
	public int estado;
	public int ant_estado;
	protected Comportamiento ia;
	protected Comportamiento comido = new ACasa();
	protected Comportamiento saliendo = new Salida();
	protected Comportamiento huida = new Huida();
	protected Comportamiento dentro = new Aleatorio();
	protected Sprite[] sprites = new Sprite[4];
	
	protected double vel2;
	
	public int level = 1;
	private long inicio_miedo;
	private long fin_miedo;
	private long tiempo = Long.MAX_VALUE;
	
	public void setEstado(int state){
		if(this.estado == COMIDO || this.estado == MIEDO)
			this.velocidad = vel2;
		estado = state;
	}
	
	public Fantasma(int level) {
		this.x = 0;
		this.y = 0;
		this.estado = Fantasma.NORMAL;
		this.level = level;
	}
	
	public Fantasma(int x,int y) {
		this.x = x;
		this.y = y;
		this.estado = Fantasma.NORMAL;
	}
	
	public void infundirMiedo(){
		if(estado == NORMAL || estado == MIEDO){
			inicio_miedo = System.currentTimeMillis();
			fin_miedo = inicio_miedo+(10000/level);
			ant_estado = estado;
			if(estado != MIEDO){
				//vel2 = velocidad;
				velocidad = velocidad * 0.6;
			}
			estado = Fantasma.MIEDO;
		}
	}
	
	public void tranquilizar(){
		if(estado == MIEDO){
			estado = NORMAL;
			velocidad = vel2;
		}
	}
	
	public long comprobarMiedo(){
		if(estado == MIEDO && fin_miedo <= System.currentTimeMillis())
			tranquilizar();
		return fin_miedo - System.currentTimeMillis();
	}
	
	/*public boolean setHorizontalMovement(Tablero tablero, int i) {
		int margen = 9 * (i/Math.abs(i));
		if(tablero.isAccesible(getX()+margen, getY()) || tablero.esPuerta(getX()+margen, getY())){
			this.x+=(i*velocidad);
			if(i < 0){
				if(estado == Fantasma.MIEDO)
					sprite = Texturas.miedoIzquierda;
				else if(estado == Fantasma.NORMAL)
					sprite = sprites[0];
			}else{
				if(estado == Fantasma.MIEDO)
					sprite = Texturas.miedoDerecha;
				else if(estado == Fantasma.NORMAL)
					sprite = sprites[1];
			}
		}else{
			int sentido = 1;
			Casilla next;
			if(i < 0)
				sentido = 0;
			next = tablero.miCasilla(this).atraviesa(sentido);
			if(next != null){
				this.x = next.getX();
			}
		}
		return true;
	}

	public boolean setVerticalMovement(Tablero tablero, int i) {
		int margen = 9 * (i/Math.abs(i));
		if(tablero.isAccesible(getX(), getY()+margen) || tablero.esPuerta(getX(), getY()+margen)){
			this.y+=(i*velocidad);
			if(i < 0){
				if(estado == Fantasma.MIEDO)
					sprite = Texturas.miedoArriba;
				else if(estado == Fantasma.NORMAL)
					sprite = sprites[2];
			}else{
				if(estado == Fantasma.MIEDO)
					sprite = Texturas.miedoAbajo;
				else if(estado == Fantasma.NORMAL)
					sprite = sprites[3];
			}
		}else{
			int sentido = 3;
			Casilla next;
			if(i < 0)
				sentido = 2;
			next = tablero.miCasilla(this).atraviesa(sentido);
			if(next != null){
				this.y = next.getY();
			}
		}
		return true;
	}*/
	
	public int vecinosVerticales(Tablero tab){
		//if((getX())%20 == 0)
		if(tab.miCasilla(this).alineadoVertical(this))
			return tab.vecinosVerticales(tab.PixelXToPos(getX()+20),tab.PixelYToPos(getY()+20));
		return 0;
	}
	
	public int vecinosHorizontales(Tablero tab){
		//if((getY())%20 == 0)
		if(tab.miCasilla(this).alineadoHorizontal(this))
			return tab.vecinosHorizontales(tab.PixelXToPos(getX()+20),tab.PixelYToPos(getY()+20));
		return 0;
	}

	public Comportamiento getIa() {
		switch(estado){
		case MIEDO:
			return ia;
		case NORMAL:
			return ia;
		case COMIDO:
			return comido;
		case SALIENDO:
			return saliendo;
		case DENTRO:
			return dentro;
		default:
			return ia;
		}
	}

	public void setIa(Comportamiento ia) {
		this.ia = ia;
	}
	
	// Añadir HUIDA
	public void siguienteMovimiento(Tablero tablero){
		switch(estado){
		case MIEDO:
			if(!(ia instanceof Aleatorio))
				huida.sigMovimiento(tablero, this);
			else
				ia.sigMovimiento(tablero, this);
			break;
		case NORMAL:
			ia.sigMovimiento(tablero, this);
			break;
		case COMIDO:
			comido.sigMovimiento(tablero, this);
			break;
		case SALIENDO:
			saliendo.sigMovimiento(tablero, this);
			break;
		case DENTRO:
			dentro.sigMovimiento(tablero, this);
			break;
		default:
			break;
		}
		
		/*if(estado == COMIDO)
			comido.sigMovimiento(tablero, this);
		else if(estado == SALIENDO)
			saliendo.sigMovimiento(tablero, this);
		else if(estado == MIEDO){
			if(!(ia instanceof Aleatorio))
				huida.sigMovimiento(tablero, this);
			else
				ia.sigMovimiento(tablero, this);
		}else
			ia.sigMovimiento(tablero, this);
		*/
	}

	public void salir(){
		saliendo = new Salida();
		this.estado = SALIENDO;
		this.tiempo = Long.MAX_VALUE;
	}
	
	public void fuera(){
		this.estado = NORMAL;
		this.situar(13, 10);	// CHAPUZA!!
	}
	
	public void comido() {
		this.estado = COMIDO;
		this.sprite = Texturas.comido;
		this.velocidad = vel2 * 4;
	}
	
	public void revivir(){
		estado = DENTRO;
		velocidad = vel2;
		this.tiempo = System.currentTimeMillis()+3000;
		situar(13, 14);	// CHAPUZA!!
	}

	public long getTiempo() {
		return tiempo;
	}

	public void setTiempo(long tiempo) {
		this.tiempo = tiempo;
	}
	
	public boolean setHorizontalMovement(Tablero tablero, int i) {
		boolean logro = false;
		Casilla actual = tablero.miCasilla(this);
		if(actual.alineadoHorizontal(this)){
			int auxx, auxy;
			auxx = (int)((x+20)+(i*10+i*velocidad));
			if(i > 0){
				auxx--;
			}
			auxy = (int)y+20;
			Casilla sig = tablero.getCasillaPorPixel(auxx, auxy);
			if(sig != null && sig.isAccesible()){
				this.x+=(i*velocidad);
				logro = true;
				if(i < 0){
					if(estado == Fantasma.MIEDO)
						sprite = Texturas.miedoIzquierda;
					else if(estado != Fantasma.COMIDO)
						sprite = sprites[0];
				}else{
					if(estado == Fantasma.MIEDO)
						sprite = Texturas.miedoDerecha;
					else if(estado != Fantasma.COMIDO)
						sprite = sprites[1];
				}
			}else{
				int sentido = 1;
				if(i < 0)
					sentido = 0;
				Casilla next = actual.getVecino(sentido);
				if(next == null)
					next = actual.atraviesa(sentido);
				else
					next = next.atraviesa(sentido);
				if(next != null){
					this.x = next.getX();
					logro = true;
				}else if(this.x % 10 != 0){
					//System.out.println("Wow: "+actual+"->"+sig);
					this.x = actual.getX()-10;
				}
			}
		}else{
			// Las Y'es
			if(Math.abs((actual.getY()-10) - this.y) <= velocidad*10){
				this.y = actual.getY()-10;
				logro = true;
			}
		}
		return logro;
	}
	
	public boolean setVerticalMovement(Tablero tablero, int i) {
		boolean logro = false;
		boolean puerta = false;
		Casilla actual = tablero.miCasilla(this);
		if(actual.alineadoVertical(this)){
			int auxx, auxy;
			auxx = (int)(x+20);
			auxy = (int)((y+20)+(i*10+i*velocidad));
			if(i < 0){
				auxy++;
			}else{
				auxy--;
			}
			Casilla sig = tablero.getCasillaPorPixel(auxx, auxy);
			if(this.estado == SALIENDO || this.estado == COMIDO)
				puerta = sig.esPuerta();

			if(sig != null && (sig.isAccesible() || puerta)){
				this.y+=(i*velocidad);
				logro = true;
			if(i < 0){
				if(estado == Fantasma.MIEDO)
					sprite = Texturas.miedoArriba;
				else if(estado != Fantasma.COMIDO)
					sprite = sprites[2];
			}else{
				if(estado == Fantasma.MIEDO)
					sprite = Texturas.miedoAbajo;
				else if(estado != Fantasma.COMIDO)
					sprite = sprites[3];
			}
			}else{
				int sentido = 3;
				if(i < 0)
					sentido = 2;
				Casilla next = actual.getVecino(sentido);
				if(next == null)
					next = actual.atraviesa(sentido);
				else
					next = next.atraviesa(sentido);
				if(next != null){
					this.y = next.getY();
					logro = true;
				}else if(this.y % 10 != 0){
					//System.out.println("Wow: "+actual+"->"+sig);
					this.y = actual.getY()-10;
				}
			}
		}else{
			// Las X'es
			if(Math.abs((actual.getX()-10) - this.x) <= velocidad*10){
				this.x = actual.getX()-10;
				logro = true;
			}
		}
		return logro;
	}
	
	public String toString(){
		String st = "";
		switch(estado){
		case MIEDO:
			st = " Estado MIEDO ";
			break;
		case NORMAL:
			st = " Estado NORMAL ";
			break;
		case COMIDO:
			st = " Estado COMIDO ";
			break;
		case SALIENDO:
			st = " Estado SALIENDO ";
			break;
		case DENTRO:
			st = " Estado DENTRO ";
			break;
		default:break;
		}
		return super.toString()+st;
	}

	public void alinear(Casilla actual) {
		// Las X'es
		this.x = actual.getX()-10;
		// Las Y'es
		this.y = actual.getY()-10;
	}
}
