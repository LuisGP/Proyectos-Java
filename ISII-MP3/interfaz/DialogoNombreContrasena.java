package interfaz;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gestusuarios.Gestor_Usuarios;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

abstract public class DialogoNombreContrasena extends JDialog implements ActionListener {
	protected JLabel lbNombre;
	protected JTextField txtNombre;
	protected JLabel lbContraseña;
	protected JPasswordField txtContraseña;
	protected JButton btAceptar;
	protected JButton btCancelar;
	protected Gestor_Usuarios gestor;

	protected DialogoNombreContrasena(JDialog padre, String titulo, Gestor_Usuarios gestor) {
		super(padre, titulo, true);

		this.gestor = gestor;
		this.inicializarComponentes();
	}

	protected DialogoNombreContrasena(String titulo, Gestor_Usuarios gestor) {
		super();

		this.gestor = gestor;
		this.setTitle(titulo);
		this.inicializarComponentes();
	}

	private void inicializarComponentes() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		this.lbNombre = new JLabel("Nombre");
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.insets = new Insets(0, 0, 25, 50);
		panel.add(this.lbNombre, c);
		this.txtNombre = new JTextField(20);
		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 25, 0);
		c.gridwidth = 3;
		panel.add(this.txtNombre, c);
		this.lbContraseña = new JLabel("Contraseña");
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(0, 0, 0, 33);
		c.gridwidth = 1;
		panel.add(this.lbContraseña, c);
		this.txtContraseña = new JPasswordField(20);
		this.txtContraseña.setEchoChar('*');
		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(0, 0, 0, 0);
		c.gridwidth = 3;
		panel.add(this.txtContraseña, c);
		this.btAceptar = new JButton("Aceptar");
		this.btAceptar.addActionListener(this);
		this.btCancelar = new JButton("Cancelar");
		this.btCancelar.addActionListener(this);
		this.setContentPane(panel);
		this.setSize(400,200);

	}

	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton)e.getSource();

		if (source == this.btAceptar)
			aceptar();
		else if (source == this.btCancelar)
			cancelar();
	}

	protected void cancelar() {
		this.dispose();
	}

	protected abstract void aceptar();
}
