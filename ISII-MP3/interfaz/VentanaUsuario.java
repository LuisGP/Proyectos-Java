package interfaz;
import gestusuarios.Gestor_Usuarios;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class VentanaUsuario extends JFrame implements ActionListener {
	private static final long serialVersionUID = -441771194343070524L;
	private static final String opciones[] = {"Modificar mis datos",
							                  "Gestionar mis archivos MP3",
							                  "Eliminarme"};
	private Gestor_Usuarios gestor;
	private String nombre;
	private ButtonGroup bgOpciones;
	private JButton btAceptar;
	private JButton btSalir;

	public VentanaUsuario(Gestor_Usuarios gestor) {
		super("Gestión del usuario");
		this.gestor = gestor;
		this.nombre = null;

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		this.bgOpciones = new ButtonGroup();
		c.gridx = 0;
		c.gridheight = 1;
		c.gridwidth = 2;
		for (int i = 0; i < opciones.length; i++) {
			JRadioButton rbOpcion = new JRadioButton(opciones[i]);
			c.gridy = i;
			panel.add(rbOpcion, c);
			this.bgOpciones.add(rbOpcion);
		}
		this.btAceptar = new JButton("Aceptar");
		this.btAceptar.addActionListener(this);
		c.gridx = 0;
		c.gridy++;
		panel.add(this.btAceptar, c);
		this.btSalir = new JButton("Salir");
		this.btSalir.addActionListener(this);
		c.gridx = 1;
		panel.add(this.btSalir, c);
		this.setContentPane(panel);
		this.setSize(300,300);
	}

	public void cambiarNombre(String nombre) {
		this.nombre = nombre;
	}

	public void actionPerformed(ActionEvent arg0) {
		Enumeration<AbstractButton> elements = this.bgOpciones.getElements();
		String chosenOption = null;

		while (elements.hasMoreElements()) {
			JRadioButton element = (JRadioButton)elements.nextElement();

			if (element.isSelected()) {
				chosenOption = element.getText();
				break;
			}
		}
		if (chosenOption == null) {
			JOptionPane.showMessageDialog(this, "Debe seleccionar una opción");
			return;
		}
		if (chosenOption.equals(opciones[0]))
			modificaUsuario();
		else if (chosenOption.equals(opciones[1])) {
			this.dispose();
			new VentanaArchivos(this.nombre);
		} else if (chosenOption.equals(opciones[2]))
			eliminarUsuario();
	}

	private void modificaUsuario() {
		new DialogoModificarUsuario(this, this.gestor, this.nombre);
	}

	private void eliminarUsuario() {
		new DialogoEliminarUsuario(this, this.gestor, this.nombre);
	}
}
