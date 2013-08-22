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
		String contraseña = new String(this.txtContraseña.getPassword());

		JOptionPane.showMessageDialog(this, contraseña, "Antigua contraseña", JOptionPane.INFORMATION_MESSAGE);
		//gestor.deleteUser(this.nombre, contraseña);
	}
}
