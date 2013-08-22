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
		String contrase�a = new String(this.txtContrase�a.getPassword());

		JOptionPane.showMessageDialog(this, contrase�a, "Nueva contrase�a", JOptionPane.INFORMATION_MESSAGE);
		this.dispose();
	}
}
