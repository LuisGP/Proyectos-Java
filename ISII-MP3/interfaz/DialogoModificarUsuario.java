package interfaz;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import gestusuarios.Gestor_Usuarios;

public class DialogoModificarUsuario extends DialogoDatosUsuario {
	private static final long serialVersionUID = 645286913504914151L;

	public DialogoModificarUsuario(JFrame padre, Gestor_Usuarios gestor, String nombre) {
		super(padre, "Modificar mis datos", gestor, nombre);
	}

	protected void aceptar() {
		String contraseña = new String(this.txtContraseña.getPassword());

		JOptionPane.showMessageDialog(this, contraseña, "Nueva contraseña", JOptionPane.INFORMATION_MESSAGE);
		this.dispose();
	}
}
