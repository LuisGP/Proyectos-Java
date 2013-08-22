package interfaz;

import gestarchivos.Gestor_Archivos;

import java.util.ArrayList;

public class VentanaModificarArchivo extends VentanaDatosArchivo {
	private static final long serialVersionUID = -4672929454400456016L;
	private int[] seleccion;

	public VentanaModificarArchivo(Gestor_Archivos gestor, int[] seleccion) {
		super("Modificar mi archivo MP3", "Modificar", gestor);
		this.seleccion = seleccion;

		this.setVisible(true);
	}

	protected void confirmar() {
		ArrayList<Integer>ids = new ArrayList<Integer>();

		for (int i = 0; i < this.seleccion.length; i++)
			ids.add(this.seleccion[i]);
		this.gestor.modificarArchivos(ids, crearHashMap());
	}
}
