package interfaz;

import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import gestarchivos.Gestor_Archivos;

public class VentanaAgregarArchivo extends VentanaDatosArchivo {
	private static final long serialVersionUID = 8688464139343962024L;

	public VentanaAgregarArchivo(Gestor_Archivos gestor) {
		super("Agregar archivo", "Agregar", gestor);
	}

	protected void confirmar() {
		Iterator<Entry<String, String>> it = crearHashMap().entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> entry = it.next();
			JOptionPane.showMessageDialog(this, entry.getValue(), entry.getKey(), JOptionPane.INFORMATION_MESSAGE);
		}
		//this.gestor.agregarArchivo(crearHashMap());
	}
}
