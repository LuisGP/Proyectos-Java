package ia;

import pacman.Pacman;
import tablero.Casilla;
import tablero.Tablero;
import fantasmas.Fantasma;

public class Precognisciente implements Comportamiento {
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
	
	Casilla pc;
	Casilla pcUlt = null;


	public void sigMovimiento(Tablero tablero, Fantasma fantasma) {
		actual = tablero.miCasilla(fantasma);
		int menor = -1;
		int minimo = Integer.MAX_VALUE;
		int aux;
		f = fantasma;
		t = tablero;
		p = t.pacman;
		pc = t.miCasilla(p);
		
		if(this.pc != this.pcUlt)
			t.refreshDistancia();

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
		
		ultima = actual;
		pcUlt = pc;

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

	private int heuristica(Casilla actual){
		return actual.distPacman;
	}

	public String toString(){
		return "Precognisciente "+lastMenor+" y "+exito+": "+f;
	}

}
