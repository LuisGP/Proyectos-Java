package ia;

import java.util.ArrayList;

import tablero.Casilla;
import tablero.Tablero;
import fantasmas.Fantasma;

public class Salida implements Comportamiento {
	Fantasma f;
	Tablero t;
	boolean exito;
	int lastMenor = -1;
	ArrayList v = new ArrayList();
	ArrayList pesos = new ArrayList();

	public void sigMovimiento(Tablero tablero, Fantasma fantasma) {
		Casilla actual = tablero.miCasilla(fantasma);
		Casilla[] vecinos = new Casilla[4];
		int menor = -1;
		int minimo = Integer.MAX_VALUE;
		int aux;
		f = fantasma;
		t = tablero;
		
		for(int i = 0; i < 4; i++){
			vecinos[i] = actual.getVecino(i);
			//if(vecinos[i].isAccesible() || vecinos[i].esPuerta()){
				aux = heuristica(vecinos[i]);
				if(aux < minimo){
					minimo = aux;
					menor = i;
				}
			//}
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
	
	/* Heuristica
	 * Tabla cargada con las distancias en el tablero ;)
	 * 
	 * Elegir la menor
	 */
	private int heuristica(Casilla actual){
		int fila = t.PixelYToPos(actual.getY());
		int col = t.PixelXToPos(actual.getX());
		int valor = 99;
		
		if(!v.contains(actual)){
			if(actual.esPuerta() || actual.isAccesible()){
				valor = Math.abs(fila - 11)+Math.abs(col - 14);
				v.add(actual);
				pesos.add(new Integer(valor));
			}
		}else{
			valor = ((Integer)pesos.get(v.indexOf(actual))).intValue();
		}
		
		//System.out.println("Objetivo: "+t.getCasilla(11, 14));
		
		if(valor == 0){
			f.fuera();
		}
		return valor;
	}
	

	public String toString(){
		return "Salida "+lastMenor+" y "+exito+": "+f;
	}

}
