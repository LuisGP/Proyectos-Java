package ia;

import pacman.Pacman;
import tablero.Casilla;
import tablero.Tablero;
import fantasmas.Fantasma;

public class VisionDirecta implements Comportamiento {
	Fantasma f;
	Tablero t;
	Pacman p;
	Casilla pc;
	Comportamiento alea = new Aleatorio();
	Comportamiento manhattan = new Manhattan();
	Comportamiento ultimo;
	boolean exito;
	int lastMenor = -1;
	long visto = 0;

	public void sigMovimiento(Tablero tablero, Fantasma fantasma) {
		f = fantasma;
		t = tablero;
		p = t.pacman;
		
		if(visionDirecta(t,f,p)){
			if(pc == null){
				fantasma.alinear(t.miCasilla(f));
			}
			pc = t.miCasilla(p);
			manhattan.sigMovimiento(t, f);
			System.out.println(manhattan);
			ultimo = manhattan;
			visto = System.currentTimeMillis()+2000;
			System.out.println("TE VEO!");
		}else{
			if(visto > System.currentTimeMillis()){
				manhattan.sigMovimiento(t, f);
				ultimo = manhattan;
			}else{
				alea.sigMovimiento(t, f);
				ultimo = alea;
				pc = null;
			}
		}
	}

	private boolean visionDirecta(Tablero t, Fantasma f, Pacman p) {
		return t.visionDirecta(f,p);
	}

	public String toString(){
		return "Directa "+ultimo;
	}

}
