package interfaz;

import gestarchivos.Gestor_Archivos;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

abstract public class VentanaDatosArchivo extends JFrame implements ActionListener {
	private static final long serialVersionUID = -4428422631519039288L;
	protected String usuario;
	protected Gestor_Archivos gestor;
	protected JLabel lbRuta;
	protected JTextField txtRuta;
	protected JButton btExaminar;
	protected JLabel lbTitulo;
	protected JTextField txtTitulo;
	protected JLabel lbAutor;
	protected JTextField txtAutor;
	protected JLabel lbAlbum;
	protected JTextField txtAlbum;
	protected JLabel lbGenero;
	protected JTextField txtGenero;
	protected JLabel lbAnyo;
	protected JTextField txtAnyo;
	protected JLabel lbDuracion;
	protected JSpinner txtHoras;
	protected JSpinner txtMinutos;
	protected JSpinner txtSegundos;
	protected JButton btConfirmar;
	protected JButton btCancelar;

	public VentanaDatosArchivo(String title, String textBtConfirmar, Gestor_Archivos gestor) {
		super(title);

		GridBagConstraints c = new GridBagConstraints();
		JPanel panel = new JPanel(new GridBagLayout());

		this.gestor = gestor;
		this.lbRuta = new JLabel("Ruta");
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		panel.add(this.lbRuta, c);
		this.txtRuta = new JTextField(20);
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 5;
		panel.add(this.txtRuta, c);
		this.btExaminar = new JButton("Examinar");
		this.btExaminar.addActionListener(this);
		c.gridx = 6;
		c.gridy = 0;
		c.gridwidth = 1;
		panel.add(this.btExaminar, c);
		this.lbTitulo = new JLabel("Titulo");
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(25, 0, 0, 0);
		panel.add(this.lbTitulo, c);
		this.txtTitulo = new JTextField(20);
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 5;
		panel.add(this.txtTitulo, c);
		this.lbAutor = new JLabel("Autor");
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		panel.add(this.lbAutor, c);
		this.txtAutor = new JTextField(20);
		c.gridx = 1;
		c.gridy = 2;
		c.gridwidth = 5;
		panel.add(this.txtAutor, c);
		this.lbAlbum = new JLabel("Album");
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		panel.add(this.lbAlbum, c);
		this.txtAlbum = new JTextField(20);
		c.gridx = 1;
		c.gridy = 3;
		c.gridwidth = 5;
		panel.add(this.txtAlbum, c);
		this.lbGenero = new JLabel("Genero");
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		panel.add(this.lbGenero, c);
		this.txtGenero = new JTextField(20);
		c.gridx = 1;
		c.gridy = 4;
		c.gridwidth = 5;
		panel.add(this.txtGenero, c);
		this.lbAnyo = new JLabel("Año");
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 1;
		panel.add(this.lbAnyo, c);
		this.txtAnyo = new JTextField(5);
		c.gridx = 1;
		c.gridy = 5;
		c.gridwidth = 5;
		c.anchor = GridBagConstraints.WEST;
		panel.add(this.txtAnyo, c);
		this.lbDuracion = new JLabel("Duración");
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.CENTER;
		panel.add(this.lbDuracion, c);
		SpinnerNumberModel model = new SpinnerNumberModel(0,0,23,1);
		this.txtHoras = new JSpinner(model);
		c.gridx = 1;
		c.gridy = 6;
		c.insets = new Insets(25,15,0,0);
		panel.add(this.txtHoras, c);
		c.gridx = 2;
		c.gridy = 6;
		panel.add(new JLabel(":"), c);
		model = new SpinnerNumberModel(0,0,59,1);
		this.txtMinutos = new JSpinner(model);
		c.gridx = 3;
		c.gridy = 6;
		panel.add(this.txtMinutos, c);
		c.gridx = 4;
		c.gridy = 6;
		panel.add(new JLabel(":"), c);
		model = new SpinnerNumberModel(0,0,59,1);
		this.txtSegundos = new JSpinner(model);
		c.gridx = 5;
		c.gridy = 6;
		panel.add(this.txtSegundos, c);
		this.btConfirmar = new JButton(textBtConfirmar);
		this.btConfirmar.addActionListener(this);
		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 3;
		panel.add(this.btConfirmar, c);
		this.btCancelar = new JButton("Cancelar");
		this.btCancelar.addActionListener(this);
		c.gridx = 3;
		c.gridy = 7;
		panel.add(this.btCancelar, c);
		this.setContentPane(panel);
		this.setSize(525,450);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	protected File examinar() {
		JFileChooser chooser = new JFileChooser();
		chooser.setMultiSelectionEnabled(false);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setFileFilter(new MP3FileFilter());
		int opcion = chooser.showOpenDialog(this);

		switch (opcion) {
			case JFileChooser.APPROVE_OPTION:
				this.txtRuta.setText(chooser.getSelectedFile().getAbsolutePath());
				return chooser.getSelectedFile();
			default:
				return null;
		}
	}

	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton)e.getSource();

		if (source == this.btExaminar)
			examinar();
		else if (source == this.btConfirmar)
			confirmar();
		else if (source == this.btCancelar)
			this.dispose();
	}

	abstract protected void confirmar();

	protected HashMap<String, String> crearHashMap() {
		HashMap<String, String> datos = new HashMap<String, String>();

		datos.put("ruta", this.txtRuta.getText());
		datos.put("titulo", this.txtTitulo.getText());
		datos.put("autor", this.txtAutor.getText());
		datos.put("album", this.txtAlbum.getText());
		datos.put("genero", this.txtGenero.getText());
		datos.put("anyo", this.txtAnyo.getText());

		int horas = (Integer)this.txtHoras.getValue();
		int minutos = (Integer)this.txtMinutos.getValue();
		int segundos = (Integer)this.txtSegundos.getValue();
		String duracion = Integer.toString((horas*60+minutos)*60+segundos);
		datos.put("duracion", duracion);

		return datos;
	}
}
