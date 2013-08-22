package pacman;

import tablero.Casilla;
import tablero.Tablero;
import fantasmas.Texturas;
import main.Personaje;

public class Pacman extends Personaje{
	public int puntos = 0;
	public int vidas = 4;
	private int mov = 0;
	private int boca = 14;//20; Duracion Animacion
	
	private int direccion = 1;
	
	public Pacman(int x,int y) {
		super.x = x;
		super.y = y;
		super.sprite = Texturas.espaldas;
		super.velocidad = 1.4;
	}

	/*public boolean setHorizontalMovement(Tablero tablero, int i) {
		int margen = 8 * (i/Math.abs(i));
		boolean logro = false;
		Casilla actual = tablero.miCasilla(this);
		if(actual.alineadoHorizontal(this)){
			if(tablero.isAccesible(getX()+margen, getY())){
				this.x+=(i*velocidad);
				mov = mov + direccion;
				//mov%=boca;

				if((mov == 13)||(mov == 0)){
					direccion = -direccion;
				}

				if(i < 0){
					//if(getX()%2==0)
					/*if(mov < boca/2)
					sprite = Texturas.abreIz;
				else
					sprite = Texturas.cierraIz;*/
/*					sprite = Texturas.animacion[mov];
				}else{
					if(mov < boca/2)
						sprite = Texturas.abreDe;
					else
						sprite = Texturas.cierraDe;
				}
				logro = true;
			}else{
				int sentido = 1;
				Casilla next;
				if(i < 0)
					sentido = 0;
				next = tablero.miCasilla(this).atraviesa(sentido);
				if(next != null){
					this.x = next.getX();
					logro = true;
				}
			}
			int p = tablero.miCasilla(this).visita(tablero.fantasmas);
			if(p != 0){
				puntos += p;
				tablero.nbolas--;
			}
		}
		return logro;
	}*/

	/*public boolean setVerticalMovement(Tablero tablero, int i) {
		int margen = 8 * (i/Math.abs(i));
		boolean logro = false;
		Casilla actual = tablero.miCasilla(this);
		if(actual.alineadoVertical(this)){
			if(tablero.isAccesible(getX(), getY()+margen)){
				this.y+=(i*velocidad);
				mov++;
				mov%=boca;
				if(i < 0){
					//if(getY()%2==0)
					if(mov < boca/2)
						sprite = Texturas.abreAr;
					else
						sprite = Texturas.cierraAr;
				}else{
					if(mov < boca/2)
						sprite = Texturas.abreAb;
					else
						sprite = Texturas.cierraAb;
				}
				logro = true;
			}else{
				int sentido = 3;
				Casilla next;
				if(i < 0)
					sentido = 2;
				next = tablero.miCasilla(this).atraviesa(sentido);
				if(next != null){
					this.y = next.getY();
					logro = true;
				}
			}
			int p = tablero.miCasilla(this).visita(tablero.fantasmas);
			if(p != 0){
				puntos += p;
				tablero.nbolas--;
			}
		}
		return logro;
	}	*/
	
	
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
				mov = mov + direccion;
				logro = true;

				//mov%=boca;
				if((mov >= 13)||(mov <= 0)){
					direccion = -direccion;
				}
				mov %= 14;
				if(mov < 0)
					mov = 0;
				if(i < 0){
					sprite = Texturas.animacion[mov];
				}else{
					if(mov < boca/2)
						sprite = Texturas.abreDe;
					else
						sprite = Texturas.cierraDe;
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
			int p = tablero.miCasilla(this).visita(tablero.fantasmas);
			if(p != 0){
				puntos += p;
				tablero.nbolas--;
			}
		}else{
			// Las Y'es
			if(Math.abs((actual.getY()-10) - this.y) <= velocidad*4){
				this.y = actual.getY()-10;
				logro = true;
			}
		}
		return logro;
	}
	
	public boolean setVerticalMovement(Tablero tablero, int i) {
		boolean logro = false;
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
			if(sig != null && sig.isAccesible()){
				this.y+=(i*velocidad);
				mov++;
				logro = true;
				mov%=boca;
				if(i < 0){
					//if(getY()%2==0)
					if(mov < boca/2)
						sprite = Texturas.abreAr;
					else
						sprite = Texturas.cierraAr;
				}else{
					if(mov < boca/2)
						sprite = Texturas.abreAb;
					else
						sprite = Texturas.cierraAb;
				}
				logro = true;
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
			int p = tablero.miCasilla(this).visita(tablero.fantasmas);
			if(p != 0){
				puntos += p;
				tablero.nbolas--;
			}
		}else{
			// Las X'es
			if(Math.abs((actual.getX()-10) - this.x) <= velocidad*4){
				this.x = actual.getX()-10;
				logro = true;
			}
		}
		return logro;
	}
	
	public void situar(int x, int y){
		super.situar(x,y);
		super.sprite = Texturas.espaldas;
	}
}
