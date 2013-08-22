package interfaz;
import gestusuarios.Gestor_Usuarios;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DialogoLogin extends DialogoNombreContrasena {
	private static final long serialVersionUID = -2392464090052180003L;
	private JButton btRegistrar;
	private VentanaUsuario ventanaUsuario;
	private DialogoRegistro dialogoRegistro;

	public DialogoLogin(Gestor_Usuarios gestor) {
		super("Validación de nombre y contraseña", gestor);
		this.ventanaUsuario = new VentanaUsuario(gestor);
		this.ventanaUsuario.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				DialogoLogin.this.setVisible(true);
			}
		});

		this.dialogoRegistro = new DialogoRegistro(this,gestor);
		this.dialogoRegistro.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				DialogoLogin.this.setVisible(true);
			}
		});

		GridBagConstraints c = new GridBagConstraints();
		JPanel panel = (JPanel)this.getContentPane();

		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.insets = new Insets(25, 25, 0, 0);
		panel.add(this.btAceptar, c);
		c.gridx = 1;
		c.gridy = 2;
		panel.add(this.btCancelar, c);
		this.btRegistrar = new JButton("Registrar");
		this.btRegistrar.addActionListener(this);
		c.gridx = 2;
		c.gridy = 2;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.insets = new Insets(25, 25, 0, 0);
		panel.add(this.btRegistrar, c);
		this.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				DialogoLogin.this.dispose();
				System.exit(0);
			}
		});
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.btRegistrar)
			nuevoUsuario();
		else
			super.actionPerformed(e);
	}

	private void nuevoUsuario() {
		this.dialogoRegistro.setVisible(true);
	}

	protected void aceptar() {
		String nombre;
		String contraseña;

		nombre = this.txtNombre.getText();
		contraseña = new String(this.txtContraseña.getPassword());

		if (nombre.length() == 0 || contraseña.length() == 0) {
			JOptionPane.showMessageDialog(this, "Debe rellenar el nombre y la contraseña");
			return;
		}

		JOptionPane.showMessageDialog(this, nombre, "Nombre", JOptionPane.INFORMATION_MESSAGE);
		JOptionPane.showMessageDialog(this, contraseña, "Contraseña", JOptionPane.INFORMATION_MESSAGE);
		/*if (!gestor.login(nombre, contraseña)) {
			JOptionPane.showMessageDialog(this, "El nombre y/o la contraseña no son válidos");
			return;
		}*/

		this.setVisible(false);
		this.ventanaUsuario.cambiarNombre(nombre);
		this.ventanaUsuario.setVisible(true);
	}

	protected void cancelar() {
		super.cancelar();
		System.exit(0);
	}
}
