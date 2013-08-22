package ia;

import java.util.Random;

import fantasmas.Fantasma;
import tablero.Tablero;

public class Aleatorio implements Comportamiento{

	Random random = new Random();
	int lastR = -1;
	int margen = 9;
	boolean exito;
	Fantasma f;
	
	public void sigMovimiento(Tablero tablero, Fantasma fantasma) {
		int r;

		f = fantasma;

		if(lastR == -1){
			exito = fantasma.setHorizontalMovement(tablero, -1);
			lastR = 1;
		}

		if(!exito){
			double random = this.random.nextDouble() * 4;
			r = (int)Math.floor(random);
			lastR = r;
		}else{

			int auxr;
			double random = this.random.nextDouble() * 4;
			auxr = (int)Math.floor(random);
			int vecH = fantasma.vecinosHorizontales(tablero);
			int vecV = fantasma.vecinosVerticales(tablero);

			r = lastR;

			switch(lastR){
			case 0:
				if((vecV != 0) && (auxr % 2 == 0)){					
					if(vecV == 1){
						r = 3;
					}else if(vecV == 2){
						r = 2;
					}else{ // vecV == 3
						if(auxr == 1){
							r = 3;
						}else
							r = 2;
					}
				}
				break;
			case 1:
				if((vecV != 0) && (auxr % 2 == 0)){					
					if(vecV == 1){
						r = 3;
					}else if(vecV == 2){
						r = 2;
					}else{ // vecV == 3
						if(auxr == 1){
							r = 3;
						}else
							r = 2;
					}
				}				
				break;
			case 2:
				if((vecH != 0) && (auxr % 2 == 0)){					
					if(vecH == 1){
						r = 1;
					}else if(vecH == 2){
						r = 0;
					}else{ // vecH == 3
						if(auxr == 1){
							r = 1;
						}else
							r = 0;
					}
				}
				break;
			case 3:
				if((vecH != 0) && (auxr % 2 == 0)){					
					if(vecH == 1){
						r = 1;
					}else if(vecH == 2){
						r = 0;
					}else{ // vecH == 3
						if(auxr == 1){
							r = 1;
						}else
							r = 0;
					}
				}
				break;
			default:
				break;
			}
		}

		switch(r){
		case 0:
			exito = fantasma.setHorizontalMovement(tablero, 1);
			break;
		case 1:
			exito = fantasma.setHorizontalMovement(tablero, -1);
			break;
		case 2:
			exito = fantasma.setVerticalMovement(tablero, 1);
			break;
		case 3:
			exito = fantasma.setVerticalMovement(tablero, -1);
			break;
		default: break;
		}

	}
	
	public String toString(){
		return "Aleatorio "+lastR+" y "+exito+": "+f;
	}
}
