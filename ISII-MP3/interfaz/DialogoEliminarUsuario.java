package interfaz;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gestusuarios.Gestor_Usuarios;

public class DialogoEliminarUsuario extends DialogoDatosUsuario {
	private static final long serialVersionUID = 1336093594974656124L;

	public DialogoEliminarUsuario(JFrame padre, Gestor_Usuarios gestor, String nombre) {
		super(padre, "Eliminarme", gestor, nombre);
	}

	protected void aceptar() {
		String contrase�a = new String(this.txtContrase�a.getPassword());

		JOptionPane.showMessageDialog(this, contrase�a, "Antigua contrase�a", JOptionPane.INFORMATION_MESSAGE);
		//gestor.deleteUser(this.nombre, contrase�a);
	}
}
