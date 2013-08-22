package ia;

import pacman.Pacman;
import fantasmas.Fantasma;
import tablero.Casilla;
import tablero.Tablero;

public class Huida implements Comportamiento{
	Fantasma f;
	Tablero t;
	Pacman p;
	boolean exito;
	int lastMenor = -1;

	public void sigMovimiento(Tablero tablero, Fantasma fantasma) {
		Casilla actual = tablero.miCasilla(fantasma);
		Casilla[] vecinos = new Casilla[4];
		int menor = -1;
		int minimo = Integer.MAX_VALUE;
		int aux;
		f = fantasma;
		t = tablero;
		p = t.pacman;

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

		//System.out.println("H: "+minimo+"; Direccion: "+menor);

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
			lastMenor = menor;
		}
	}
	
	
	private int heuristica(Casilla actual){
		int fila = t.PixelYToPos(actual.getY());
		int col = t.PixelXToPos(actual.getX());
		int valor = 99;
		Casilla pc = t.miCasilla(p);
		int pfil = t.PixelYToPos(pc.getY());
		int pcol = t.PixelXToPos(pc.getX());

		if(actual.esPuerta() || actual.isAccesible()){
			valor = 99-(Math.abs(fila - pfil)+Math.abs(col - pcol));
		}

		return valor;
	}


	public String toString(){
		return "Manhattan "+lastMenor+" y "+exito+": "+f;
	}
}
