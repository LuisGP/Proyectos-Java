package tablero;

import java.awt.Color;

import main.Game;
import main.Personaje;
import pacman.Pacman;
import fantasmas.Fantasma;

public class Tablero {
	private Casilla[][] casillas;
	static public int alto = 31;
	static public int ancho = 28;
	
	public Fantasma[] fantasmas;
	public Pacman pacman;
	
	public int nbolas = 0;
	
	public Tablero(){
		casillas = new Casilla[alto][ancho];
		this.init();
	}

	public Casilla getCasilla(int fila, int columna) {
		if(!(fila < Tablero.alto && columna < Tablero.ancho))
			return null;
		if(fila <= 0 || columna <= 0)
			return null;
		
		return casillas[fila][columna];
	}
	
	public Casilla getCasillaPorPixel(int x, int y){
		int fila = this.PixelYToPos(y+20);
		int col = this.PixelXToPos(x+20);
		
		if(!(fila < Tablero.alto && col < Tablero.ancho))
			return null;
		if(fila <= 0 || col <= 0)
			return null;
		
		return casillas[PixelYToPos(y)][PixelXToPos(x)];
	}

	public void setCasilla(Casilla casilla, int fila, int columna) {
		this.casillas[fila][columna] = casilla;
	}
	
	// Metodo para pasar de 26x29 a 800x600
	public int posToPixelX(int x){
		return 120+(x*20);
	}
	
	public int posToPixelY(int y){
		return (y*20)-10;
	}
	
	// Viceversa
	public int PixelXToPos(int x){
		//return 120+(x*20);
		return (x-120)/20;
	}
	
	public int PixelYToPos(int y){
		//return (y*20)-10;
		return (y+10)/20;
	}
	
	public void init(){
		int j = 0;
		int i = 0;
		
		/* Fila 0*/
		casillas[j][i] = new Casilla(TiposCasilla.paredEsquina3);
		for(i = 1; i < 13; i++){
			casillas[j][i] = new Casilla(TiposCasilla.paredHorizontal);
		}
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina4);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina3);
		for(i = 15; i < 27; i++){
			casillas[j][i] = new Casilla(TiposCasilla.paredHorizontal);
		}
		casillas[j++][i] = new Casilla(TiposCasilla.paredEsquina4);
		
		i = 0;
		/* Fila 1 */
		casillas[j][i] = new Casilla(TiposCasilla.paredVertical);
		for(i = 1; i < 13; i++)
			casillas[j][i] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		for(i = 15; i < 27; i++)
			casillas[j][i] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j++][i] = new Casilla(TiposCasilla.paredVertical);
		
		i = 0;
		/* Fila 2 */
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina3);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina4);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina3);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina4);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina3);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina4);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina3);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina4);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j++][i] = new Casilla(TiposCasilla.paredVertical);

		
		i = 0;
		/* Fila 3 */
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,true,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,true,false);
		casillas[j++][i] = new Casilla(TiposCasilla.paredVertical);

		i = 0;
		/* Fila 4 */
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina1);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina1);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);
		
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina1);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina1);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina1);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j++][i] = new Casilla(TiposCasilla.paredVertical);
		
		i = 0;
		/* Fila 5 */
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		for(i = 1; i < 27; i++)
			casillas[j][i] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j++][i] = new Casilla(TiposCasilla.paredVertical);
		
		i = 0;
		/* Fila 6 */
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina3);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina4);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina3);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina4);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina3);
		for(i = 11; i < 17; i++)
			casillas[j][i] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina4);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina3);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina4);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina3);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina4);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j++][i] = new Casilla(TiposCasilla.paredVertical);
		
		i = 0;
		/* Fila 7 */
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina1);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);

		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina4);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina3);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina1);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina1);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j++][i] = new Casilla(TiposCasilla.paredVertical);
		
		i = 0;
		/* Fila 8 */
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		for(; i < 7; i++)
			casillas[j][i] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		for(; i < 13; i++)
			casillas[j][i] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		for(; i < 19; i++)
			casillas[j][i] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		for(; i < 27; i++)
			casillas[j][i] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j++][i] = new Casilla(TiposCasilla.paredVertical);
		
		
		i = 0;
		/* Fila 9 */
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina4);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina4);

		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina3);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina1);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina3);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j++][i] = new Casilla(TiposCasilla.paredEsquina1);
		
		
		i = 0;
		/* Fila 10 */
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina3);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina1);

		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina1);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina4);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j++][i] = new Casilla(TiposCasilla.muro);
		
		i = 0;
		/* Fila 11 */
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		for(; i < 19; i++)
			casillas[j][i] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j++][i] = new Casilla(TiposCasilla.muro);
		
		i = 0;
		/* Fila 12 */
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina3);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina4);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j++][i] = new Casilla(TiposCasilla.muro);
		
		i = 0;
		/* Fila 13 */
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina1);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina1);
		
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		//int aux = i;
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		//casillas[13][aux].setTeleport(casillas[11][aux],Casilla.ARR);
		//casillas[13][aux+1].setTeleport(casillas[11][aux+1],Casilla.ARR);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina1);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j++][i] = new Casilla(TiposCasilla.paredHorizontal);
		
		i = 0;
		/* Fila 14 */
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j++][i] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[14][27].setTeleport(casillas[14][0],Casilla.DER);
		casillas[14][0].setTeleport(casillas[14][26],Casilla.IZQ);
		
		i = 0;
		/* Fila 15 */
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina4);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina3);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina4);
		
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina3);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina4);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina3);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j++][i] = new Casilla(TiposCasilla.paredHorizontal);
		
		
		i = 0;
		/* Fila 16 */
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina1);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j++][i] = new Casilla(TiposCasilla.muro);
		
		i = 0;
		/* Fila 17 */
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		for(; i < 19; i++)
			casillas[j][i] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j++][i] = new Casilla(TiposCasilla.muro);
		
		i = 0;
		/* Fila 18 */
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina3);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina4);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j][i++] = new Casilla(TiposCasilla.muro);
		casillas[j++][i] = new Casilla(TiposCasilla.muro);
		
		i = 0;
		/* Fila 19 */
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina3);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina1);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina1);
		
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina4);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina3);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina1);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina1);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j++][i] = new Casilla(TiposCasilla.paredEsquina4);
		
		i = 0;
		/* Fila 20 */
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		for(i = 1; i < 13; i++)
			casillas[j][i] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		for(; i < 27; i++)
			casillas[j][i] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j++][i] = new Casilla(TiposCasilla.paredVertical);
		
		i = 0;
		/* Fila 21 */
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina3);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina4);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina3);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina4);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina3);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina4);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina3);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina4);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j++][i] = new Casilla(TiposCasilla.paredVertical);
		
		i = 0;
		/* Fila 22 */
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina4);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina1);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);
		
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina1);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina1);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina3);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina1);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j++][i] = new Casilla(TiposCasilla.paredVertical);
		
		i = 0;
		/* Fila 23 */
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,true,false);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		
		for(; i < 13; i++)
			casillas[j][i] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,true);
		for(; i < 22; i++)
			casillas[j][i] = new Casilla(TiposCasilla.pasillo,false,false);
		
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,true,false);
		casillas[j++][i] = new Casilla(TiposCasilla.paredVertical);
		
		i = 0;
		/* Fila 24 */
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina4);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina3);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina4);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina3);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina4);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina3);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina4);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina3);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);		
		casillas[j++][i] = new Casilla(TiposCasilla.paredEsquina1);
		
		i = 0;
		/* Fila 25 */
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina3);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina1);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina1);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina4);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina3);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina1);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina1);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);		
		casillas[j++][i] = new Casilla(TiposCasilla.paredEsquina4);
		
		i = 0;
		/* Fila 26 */
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		for(; i < 7; i++)
			casillas[j][i] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		for(; i < 13; i++)
			casillas[j][i] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		for(; i < 19; i++)
			casillas[j][i] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		for(; i < 27; i++)
			casillas[j][i] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j++][i] = new Casilla(TiposCasilla.paredVertical);
		
		i = 0;
		/* Fila 27 */
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina3);
		for(; i < 7; i++)
			casillas[j][i] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina1);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);
		for(; i < 11; i++)
			casillas[j][i] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina4);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina3);
		for(; i < 19; i++)
			casillas[j][i] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina1);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);
		for(; i < 25; i++)
			casillas[j][i] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina4);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j++][i] = new Casilla(TiposCasilla.paredVertical);
		
		i = 0;
		/* Fila 28 */
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina1);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);
		
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina1);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina1);
		casillas[j][i++] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j++][i] = new Casilla(TiposCasilla.paredVertical);
		
		i = 0;
		/* Fila 29 */
		casillas[j][i++] = new Casilla(TiposCasilla.paredVertical);
		for(i = 1; i < 27; i++)
			casillas[j][i] = new Casilla(TiposCasilla.pasillo,false,false);
		casillas[j++][i] = new Casilla(TiposCasilla.paredVertical);
		
		i = 0;
		/* Fila 30 */
		casillas[j][i++] = new Casilla(TiposCasilla.paredEsquina2);
		for(i = 1; i < 27; i++)
			casillas[j][i] = new Casilla(TiposCasilla.paredHorizontal);
		casillas[j][i] = new Casilla(TiposCasilla.paredEsquina1);
		
		
		/* Construir vecinos y contar bolas */
		// Los bordes no son necesarios y evitamos "null"
		for(int fila = 1; fila < Tablero.alto-1; fila++)
			for(int col = 1; col < Tablero.ancho-1; col++){
				casillas[fila][col].setVecino(casillas[fila-1][col], Casilla.ARR);
				casillas[fila][col].setVecino(casillas[fila+1][col], Casilla.ABA);
				casillas[fila][col].setVecino(casillas[fila][col-1], Casilla.IZQ);
				casillas[fila][col].setVecino(casillas[fila][col+1], Casilla.DER);
				if(casillas[fila][col].esBola())
					this.nbolas++;
			}
	}
	
	public void pintar(){
		int y, x;
		//Game.window.getDrawGraphics().setColor(Color.RED);
		for(int i = 0; i < ancho; i++)
			for(int j = 0; j < alto; j++){
				y = this.posToPixelY(j);
				x = this.posToPixelX(i);
				if(casillas[j][i] != null){
					casillas[j][i].pintar(x,y);
					//String str = new Integer(casillas[j][i].distPacman).toString();
					//Game.window.getDrawGraphics().drawString(str, x+5, y+5);
				}
			}
	}

	public boolean isAccesible(int x, int y){
		int fila = this.PixelYToPos(y+20);
		int col = this.PixelXToPos(x+20);
		
		if(!(fila < Tablero.alto && col < Tablero.ancho))
			return false;
		if(fila <= 0 || col <= 0)
			return false;
		
		return this.casillas[fila][col].isAccesible();
	}
	
	public boolean esPuerta(int x, int y){
		int fila = this.PixelYToPos(y+20);
		int col = this.PixelXToPos(x+20);
		
		if(!(fila < Tablero.alto && col < Tablero.ancho))
			return false;
		if(fila <= 0 || col <= 0)
			return false;
		
		return this.casillas[fila][col].esPuerta();
	}

	public int vecinosVerticales(int i, int j) {
		int vecinos = 0;
		
		if((i - 1 > 0 && j + 1 < Tablero.ancho && i < Tablero.alto) && casillas[i-1][j].isAccesible())
			vecinos++;
		if((i + 1 < Tablero.alto && j + 1 < Tablero.ancho) && casillas[i+1][j].isAccesible())
			vecinos++;			
		
		return vecinos;
	}
	
	public int vecinosHorizontales(int i, int j) {
		int vecinos = 0;
		
		if((j - 1 > 0 && i < Tablero.alto && j + 1 < Tablero.ancho) && casillas[i][j-1].isAccesible())
			vecinos+=1;
		if((j + 1 < Tablero.ancho && i < Tablero.alto) && casillas[i][j+1].isAccesible())
			vecinos+=2;			
		
		return vecinos;
	}
	
	public Casilla miCasilla(Personaje p){
		int fila = this.PixelYToPos(p.getY()+20);
		int col = this.PixelXToPos(p.getX()+20);
		
		return casillas[fila][col];
	}

	public boolean visionDirecta(Fantasma f, Pacman p) { 
		if(visionDirectaX(f,p) || visionDirectaY(f,p)){
			return true;
		}		
		return false;
	}

	private boolean visionDirectaY(Fantasma f, Pacman p) {
		int fy = PixelYToPos(f.getY());
		int py = PixelYToPos(p.getY());
		
		if(fy != py)
			return false;
		
		int fx = PixelXToPos(f.getX());
		int px = PixelXToPos(p.getX());
		int inc = 1;
		
		if(fx > px){
			inc = -1;
		}
		
		for(int i = fx; i != px; i+=inc){
			if(getCasilla(i, fy) == null || !getCasilla(i, fy).isAccesible())
				return false;
		}

		return true;
	}

	private boolean visionDirectaX(Fantasma f, Pacman p) {
		int fx = PixelXToPos(f.getX());
		int px = PixelXToPos(p.getX());
		
		if(fx != px)
			return false;
		
		int fy = PixelYToPos(f.getY());
		int py = PixelYToPos(p.getY());
		int inc = 1;
		
		if(fy > py){
			inc = -1;
		}
		
		for(int i = fy; i != py; i+=inc){
			if(getCasilla(fx, i) == null || !getCasilla(fx, i).isAccesible())
				return false;
		}

		return true;
	}
	
	public void refreshDistancia(){
		Casilla pc = miCasilla(pacman);
		int px = PixelXToPos(pc.getX());
		int py = PixelYToPos(pc.getY());
		
		/* init */
		for(int fila = 0; fila < Tablero.alto; fila++)
			for(int col = 0; col < Tablero.ancho; col++)
				casillas[fila][col].distPacman = 99;
		
		/* Set Pacman */
		pc.distPacman = 0;
		
		/* Cuadrante A */
		for(int col = px+1; col < ancho; col++){
			for(int fila = py; fila >= 0; fila--){
				casillas[fila][col].setDistancia();
			}
		}
		
		/* Cuadrante B */
		for(int col = px; col < ancho; col++){
			for(int fila = py+1; fila < alto; fila++){
				casillas[fila][col].setDistancia();
			}
		}
		
		/* Cuadrante C */
		for(int col = px; col >= 0; col--){
			for(int fila = py-1; fila >= 0; fila--){
				casillas[fila][col].setDistancia();
			}
		}
		
		/* Cuadrante D */
		for(int col = px-1; col >= 0; col--){
			for(int fila = py; fila < alto; fila++){
				casillas[fila][col].setDistancia();
			}
		}
		
		/* Recomponer todo */
		for(int fila = alto-1; fila >= 0; fila--)
			for(int col = ancho-1; col >= 0; col--)
				if(casillas[fila][col] != pc)
					casillas[fila][col].setDistancia();

		/*for(int fila = 0; fila < alto; fila++)
			for(int col = ancho-1; col >= 0; col--)
				if(casillas[fila][col] != pc)
					casillas[fila][col].setDistancia();

		for(int fila = alto-1; fila >= 0; fila--)
			for(int col = 0; col < ancho; col++)
				if(casillas[fila][col] != pc)
					casillas[fila][col].setDistancia();*/

		for(int fila = 0; fila < alto; fila++)
			for(int col = 0; col < ancho; col++)
				if(casillas[fila][col] != pc)
					casillas[fila][col].setDistancia();
		
		for(int fila = alto-1; fila >= 0; fila--)
			for(int col = ancho-1; col >= 0; col--)
				if(casillas[fila][col] != pc)
					casillas[fila][col].setDistancia();

		//System.out.println("OK!");
	}
}
