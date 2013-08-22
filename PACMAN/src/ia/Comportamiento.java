package ia;

import tablero.Tablero;
import fantasmas.Fantasma;

public interface Comportamiento {
	public void sigMovimiento(Tablero tablero, Fantasma fantasma);
}
