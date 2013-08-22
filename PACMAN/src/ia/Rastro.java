package ia;

import pacman.Pacman;
import tablero.Casilla;
import tablero.Tablero;
import fantasmas.Fantasma;

public class Rastro implements Comportamiento {
	Fantasma f;
	Tablero t;
	Pacman p;
	boolean exito;
	//boolean repetir = false;
	int lastMenor = -1;
	int antLastMenor = -2;
	
	Casilla actual;
	Casilla ultima = null;
	Casilla[] vecinos = new Casilla[4];
	
	Aleatorio alea = new Aleatorio();


	public void sigMovimiento(Tablero tablero, Fantasma fantasma) {
		actual = tablero.miCasilla(fantasma);
		int menor = -1;
		long minimo = Long.MAX_VALUE;
		long aux;
		f = fantasma;
		t = tablero;
		p = t.pacman;

		if(actual.pacmanPaso != 0 && actual.pacmanPaso + 4000 > System.currentTimeMillis()){
			for(int i = 0; i < 4; i++){
				vecinos[i] = actual.getVecino(i);
				if(vecinos[i] != null){
					aux = heuristica(vecinos[i]);
					if(aux < minimo){
						minimo = aux;
						menor = i;
					}
				}
			}
		}else{
			alea.sigMovimiento(tablero, fantasma);
			return;
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
		}else{
			lastMenor = menor;
		}
		//System.out.println(this);
		//System.out.println("Menor: "+minimo);
	}

	private long heuristica(Casilla actual){
		long t = (int)(System.currentTimeMillis() - actual.pacmanPaso);
		return (t / 100);
	}

	public String toString(){
		return "Rastro "+lastMenor+" y "+exito+": "+f;
	}

}
