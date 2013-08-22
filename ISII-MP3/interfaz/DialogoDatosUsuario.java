package interfaz;

import gestusuarios.Gestor_Usuarios;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

abstract public class DialogoDatosUsuario extends JDialog implements ActionListener {
	protected JLabel lbContrase�a;
	protected JPasswordField txtContrase�a;
	protected JButton btAceptar;
	protected JButton btCancelar;
	protected String nombre;
	protected Gestor_Usuarios gestor;

	protected DialogoDatosUsuario(JFrame padre, String title, Gestor_Usuarios gestor, String nombre) {
		super(padre, title, true);

		this.nombre = nombre;
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		this.lbContrase�a = new JLabel("Contrase�a");
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 0, 33);
		panel.add(this.lbContrase�a, c);
		this.txtContrase�a = new JPasswordField(20);
		this.txtContrase�a.setEchoChar('*');
		c.gridx = 1;
		c.gridy = 0;
		c.insets = new Insets(0, 0, 0, 0);
		c.gridwidth = 2;
		panel.add(this.txtContrase�a, c);
		this.btAceptar = new JButton("Aceptar");
		this.btAceptar.addActionListener(this);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.insets = new Insets(25, 0, 0, 0);
		panel.add(this.btAceptar, c);
		this.btCancelar = new JButton("Cancelar");
		this.btCancelar.addActionListener(this);
		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(25, 25, 0, 0);
		panel.add(this.btCancelar, c);

		this.setContentPane(panel);
		this.setSize(400,200);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton)e.getSource();

		if (source == this.btAceptar)
			aceptar();
		else if (source == this.btCancelar)
			this.dispose();
	}

	abstract protected void aceptar();
}
