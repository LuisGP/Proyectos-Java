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
		String contrase�a = new String(this.txtContrase�a.getPassword());

		if (nombre.length() == 0 || contrase�a.length() == 0) {
			JOptionPane.showMessageDialog(this, "Debe rellenar el nombre y la contrase�a");
			return;
		}

		JOptionPane.showMessageDialog(this, nombre, "Nuevo nombre", JOptionPane.INFORMATION_MESSAGE);
		JOptionPane.showMessageDialog(this, contrase�a, "Nuevo contrase�a", JOptionPane.INFORMATION_MESSAGE);
		/*if (!gestor.newUser(nombre, contrase�a)) {
			JOptionPane.showMessageDialog(this, "El nombre introducido ya existe en el sistema");
			return;
		}*/

		this.dispose();
	}
}
