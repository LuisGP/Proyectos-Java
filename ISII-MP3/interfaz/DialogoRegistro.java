package interfaz;

import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import gestusuarios.Gestor_Usuarios;

public class DialogoRegistro extends DialogoNombreContrasena {
	private static final long serialVersionUID = -5984509930470981330L;
	public DialogoRegistro(JDialog padre, Gestor_Usuarios gestor) {
		super(padre, "Registro de un nuevo usuario", gestor);

		GridBagConstraints c = new GridBagConstraints();
		JPanel panel = (JPanel)this.getContentPane();

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.insets = new Insets(25, 75, 0, 0);
		panel.add(this.btAceptar, c);
		c.gridx = 1;
		c.gridy = 2;
		c.insets = new Insets(25, 50, 0, 0);
		panel.add(this.btCancelar, c);
	}

	protected void aceptar() {
		String nombre = this.txtNombre.getText();
		String contraseña = new String(this.txtContraseña.getPassword());

		if (nombre.length() == 0 || contraseña.length() == 0) {
			JOptionPane.showMessageDialog(this, "Debe rellenar el nombre y la contraseña");
			return;
		}

		JOptionPane.showMessageDialog(this, nombre, "Nuevo nombre", JOptionPane.INFORMATION_MESSAGE);
		JOptionPane.showMessageDialog(this, contraseña, "Nuevo contraseña", JOptionPane.INFORMATION_MESSAGE);
		/*if (!gestor.newUser(nombre, contraseña)) {
			JOptionPane.showMessageDialog(this, "El nombre introducido ya existe en el sistema");
			return;
		}*/

		this.dispose();
	}
}
