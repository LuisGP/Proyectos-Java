package ia;

import fantasmas.Fantasma;
import pacman.Pacman;

import tablero.Casilla;
import tablero.Tablero;

public class Manhattan implements Comportamiento{
	Fantasma f;
	Tablero t;
	Pacman p;
	boolean exito;
	//boolean repetir = false;
	int lastMenor = -1;
	int antLastMenor = -2;
	
	Casilla actual;
	boolean recicla = true;
	boolean nuevaVisitada = false;
	Casilla ultima = null;
	Casilla[] vecinosUltimos = new Casilla[4];
	Casilla penultima = null;
	Casilla[] vecinos = new Casilla[4];


	public void sigMovimiento(Tablero tablero, Fantasma fantasma) {
		Casilla actual = tablero.miCasilla(fantasma);
		int menor = -1;
		int minimo = Integer.MAX_VALUE;
		int aux;
		f = fantasma;
		t = tablero;
		p = t.pacman;
		
		/*recicla = true;
		
		if(nuevaVisitada && act == ultima){
			System.out.println("Recicla! "+actual);
			System.out.println("Ultima: "+ultima);
			actual = ultima;
			vecinos = vecinosUltimos;
			nuevaVisitada = false;
		}else if(actual != act){
			actual = act;
			recicla = false;
			System.out.println("Nueva! "+actual);
		}*/

		for(int i = 0; i < 4; i++){
			//if(!recicla)
				vecinos[i] = actual.getVecino(i);
			if(vecinos[i] != null){
				aux = heuristica(vecinos[i]);
				if(aux < minimo){
					minimo = aux;
					menor = i;
				}
			}
		}

		//System.out.println("H: "+minimo+"; Direccion: "+menor);
		if(antLastMenor == menor && menor != lastMenor){	
			lastMenor = menor;
			vecinos[menor] = null;
			menor = nuevoMenor(vecinos);
			antLastMenor = lastMenor;
			
			//repetir = true;
			/* Atasca al fantasma 
			if(visitada(vecinos[menor])){
				vecinos[menor] = null;
				menor = getPenultima(vecinos);
			}*/
		}

		switch(menor){
		case Casilla.IZQ:
			exito = fantasma.setHorizontalMovement(tablero, -1);
			break;
		case Casilla.DER:
			exito = fantasma.setHorizontalMovement(tablero, 1);
			break;
		case Casilla.ARR:
			exito = fantasma.setVerticalMovement(tablero, -1);
			break;
		case Casilla.ABA:
			exito = fantasma.setVerticalMovement(tablero, 1);
			break;
		default:
			break;
		}

		if(!exito){ // No esta alineado, alineemonos
			switch(lastMenor){
			case Casilla.IZQ:
				fantasma.setHorizontalMovement(tablero, -1);
				break;
			case Casilla.DER:
				fantasma.setHorizontalMovement(tablero, 1);
				break;
			case Casilla.ARR:
				fantasma.setVerticalMovement(tablero, -1);
				break;
			case Casilla.ABA:
				fantasma.setVerticalMovement(tablero, 1);
				break;
			default:
				break;
			}
			/*int indice = v.indexOf(vecinos[menor]);
				pesos.set(indice, 99);*/
		}else{
			antLastMenor = lastMenor;
			lastMenor = menor;
			Casilla nueva = vecinos[menor];
			/* Inutil, de momento */
			if(!visitada(nueva)){
				this.añadirCasilla(nueva);
				this.nuevaVisitada = true;
			}
		}
	}

	private int nuevoMenor(Casilla[] vecinos) {
		int aux, minimo, menor = -1;
		minimo = Integer.MAX_VALUE;
		for(int i = 0; i < 4; i++){
			if(vecinos[i] != null){
				aux = heuristica(vecinos[i]);
				if(aux < minimo){
					minimo = aux;
					menor = i;
				}
			}
		}
		return menor;
	}
	
	private void añadirCasilla(Casilla ult){
		penultima = ultima;
		vecinosUltimos = vecinos;
		ultima = ult;
	}
	
	private boolean visitada(Casilla cas){
		if(cas == ultima && cas == penultima)
			return true;
		return false;
	}

	/* Heuristica
	 * Tabla cargada con las distancias en el tablero ;)
	 * 
	 * Elegir la menor
	 */
	private int heuristica(Casilla actual){
		int fila = t.PixelYToPos(actual.getY());
		int col = t.PixelXToPos(actual.getX());
		int valor = 99;
		Casilla pc = t.miCasilla(p);
		int pfil = t.PixelYToPos(pc.getY());
		int pcol = t.PixelXToPos(pc.getX());

		if(actual.esPuerta() || actual.isAccesible()){
			valor = Math.abs(fila - pfil)+Math.abs(col - pcol);
		}

		return valor;
	}

	public String toString(){
		return "Manhattan "+lastMenor+" y "+exito+": "+f;
	}

}
