package interfaz;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import gestarchivos.Gestor_Archivos;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class VentanaArchivos extends JFrame implements ActionListener {
	private static final long serialVersionUID = 3442018418773816033L;
	private Gestor_Archivos gestor;
	private JButton btAgregarArchivoExistente;
	private JButton btAgregarArchivoInexistente;
	private JButton btBuscarArchivo;
	private JButton btModificarArchivo;
	private JButton btBorrarArchivo;
	private JButton btReproducirArchivo;
	private JButton btCrearListaReproduccion;
	private JList listResultado;
	private JComboBox cbRelacion;
	private JComboBox cbRestriccion;
	private JComboBox cbCampo;
	private JTextField txtValor;
	private JList listCriterios;

	public VentanaArchivos(String nombre) {
		super("Gestionar mis archivos MP3");
		//this.gestor = new Gestor_Archivos(nombre,null);

		JPanel panelArchivos = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		this.btAgregarArchivoExistente = new JButton("Añadir existentes");
		this.btAgregarArchivoExistente.addActionListener(this);
		c.gridx = 0;
		c.gridy = 0;
		panelArchivos.add(this.btAgregarArchivoExistente,c);
		this.btAgregarArchivoInexistente = new JButton("Añadir");
		this.btAgregarArchivoInexistente.addActionListener(this);
		c.gridx = 1;
		c.gridy = 0;
		panelArchivos.add(this.btAgregarArchivoInexistente,c);
		this.btBuscarArchivo = new JButton("Iniciar busqueda");
		this.btBuscarArchivo.addActionListener(this);
		c.gridx = 2;
		c.gridy = 0;
		panelArchivos.add(this.btBuscarArchivo,c);
		this.listResultado = new JList();
		this.listResultado.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		c.gridx = 0;
		c.gridy = 1;
		c.gridheight = 4;
		c.gridwidth = 3;
		panelArchivos.add(new JScrollPane(this.listResultado),c);
		this.btModificarArchivo = new JButton("Modificar");
		this.btModificarArchivo.addActionListener(this);
		c.gridx = 0;
		c.gridy = 5;
		c.gridheight = 1;
		c.gridwidth = 1;
		panelArchivos.add(this.btModificarArchivo,c);
		this.btBorrarArchivo = new JButton("Borrar");
		this.btBorrarArchivo.addActionListener(this);
		c.gridx = 1;
		c.gridy = 5;
		panelArchivos.add(this.btBorrarArchivo,c);
		this.btReproducirArchivo = new JButton("Reproducir");
		this.btReproducirArchivo.addActionListener(this);
		c.gridx = 2;
		c.gridy = 5;
		panelArchivos.add(this.btReproducirArchivo,c);
		this.btCrearListaReproduccion = new JButton("Crear lista de reproducción");
		this.btCrearListaReproduccion.addActionListener(this);
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 3;
		panelArchivos.add(this.btCrearListaReproduccion,c);

		JPanel panelCriterios = new JPanel(new GridBagLayout());

		this.setContentPane(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,panelArchivos,panelCriterios));
		this.setSize(600,800);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton)e.getSource();

		if (source == this.btAgregarArchivoExistente)
			this.agregarArchivosExistentes();
		else if (source == this.btAgregarArchivoInexistente)
			this.agregarArchivoInexistente();
		else if (source == this.btBuscarArchivo)
			this.buscar();
		else {
			if (this.listResultado.isSelectionEmpty()) {
				JOptionPane.showMessageDialog(this, "Debe seleccionar uno o más archivos");
				return;
			}
			if (source == this.btModificarArchivo)
				this.modificarArchivo();
			else if (source == this.btBorrarArchivo)
				this.borrarArchivo();
			else if (source == this.btReproducirArchivo)
				this.reproducirArchivo();
			else if (source == this.btCrearListaReproduccion)
				this.crearListaReproduccion();
		}
	}

	private void agregarArchivosExistentes() {
		new DialogoAgregarArchivos(this, this.gestor);
	}

	private void agregarArchivoInexistente() {
		new VentanaAgregarArchivo(this.gestor);
	}

	private void buscar() {
		JOptionPane.showMessageDialog(this, "Buscando", "Archivos", JOptionPane.INFORMATION_MESSAGE);
		/*this.gestor.buscar();
		this.listResultado.setListData(this.gestor.getLista().toArray());*/
	}

	private void modificarArchivo() {
		int[] seleccion = this.listResultado.getSelectedIndices();

		new VentanaModificarArchivo(this.gestor, seleccion);
	}

	private void borrarArchivo() {
		int[] seleccion = this.listResultado.getSelectedIndices();
		ArrayList<Integer> ids = new ArrayList<Integer>();

		for (int i = 0; i < seleccion.length; i++) {
			ids.add(seleccion[i]);
			JOptionPane.showMessageDialog(this, "Archivo " + i, "Borrando", JOptionPane.INFORMATION_MESSAGE);
		}
		//this.gestor.eliminarArchivos(ids);
	}

	private void reproducirArchivo() {
		int[] seleccion = this.listResultado.getSelectedIndices();

		for (int i = 0; i < seleccion.length; i++) {
			JOptionPane.showMessageDialog(this, "Archivo " + i, "Reproduciendo", JOptionPane.INFORMATION_MESSAGE);
			//this.gestor.reproducirArchivo(seleccion[i]);
		}
	}

	private void crearListaReproduccion() {
		int[] seleccion = this.listResultado.getSelectedIndices();
		ArrayList<Integer> ids = new ArrayList<Integer>();

		for (int i = 0; i < seleccion.length; i++) {
			ids.add(seleccion[i]);
			JOptionPane.showMessageDialog(this, "Archivo " + i, "Crear lista de reproducción", JOptionPane.INFORMATION_MESSAGE);
		}
		//this.gestor.crearListaRepr(ids, format, destino)
	}
}
