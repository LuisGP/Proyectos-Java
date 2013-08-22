package iu;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import crypto.Certificados;

public class Interfaz implements ActionListener, DestinoFichero, KeyStoreInfo{
	
	// Singleton
	private static Interfaz singleton = null;
	
	private static int pestActual = 0;
	private JButton botonFichero = null;
	
	private JFrame jFrame = null;
	private JFrame selFichero = null;
	private MiJFileChooser jFileChooser = null;
	private JLabel jlFichero = new JLabel(System.getProperty("user.home")+"\\.keystore");
	
	private File fichero = null;
	
	private JPanel jGestCertPane = null;
	private JPanel jFirmarPane = null;
	private JPanel jCheckFirmaPane = null;
	private JPanel jCheckMDPane = null;
	private JPanel jCifrarPane = null;
	private JTabbedPane jTabbedPane = null;
	
	private char[] storepass = null;
	
	// Gestor Cert.
	private JButton btFile = null;
	private JButton btImportar = null;
	private JButton btExportar = null;
	private JButton btListado = null;
	private JButton btCrear = null;
	
	// Firmar
	private JButton btFile2Sig = null;
	private JButton btSigFile = null;
	private JButton btPkFile = null;
	private JButton btFirmar = null;
	private String[] listaCertificados = new String[0];
	private JComboBox cbCertificadosFirma;// = new JComboBox();
	private JPasswordField pfFirma = new JPasswordField(20);
	private JTextField tfFile2Sig = new JTextField(20);
	private JTextField tfPkFile = new JTextField(20);
	private JTextField tfSigFile = new JTextField(20);
	private JPanel jpListaAlias;
	
	// CheckFirma
	private JButton btCFile2Sig = null;
	private JButton btCSigFile = null;
	private JButton btCPkFile = null;
	private JButton btCFirma = null;
	private JPasswordField pfCFirma = new JPasswordField(20);
	private JTextField tfCFile2Sig = new JTextField(20);
	private JTextField tfCPkFile = new JTextField(20);
	private JTextField tfCSigFile = new JTextField(20);
	private JTextField tfCSigAlg = new JTextField(20);
	private JTextField tfCKeyAlg = new JTextField(20);
	private JPanel jpCListaAlias;
	private JCheckBox jcbC = new JCheckBox();
	private Component[] compFichero;

	// MD5
	private JButton btMD5File = null;
	private JButton btCheckMD5 = null;
	private JTextField tfMD5File = new JTextField(20);
	private JTextArea taMD5 = new JTextArea();
	
	// Cifrar/Descifrar
	private JButton btClearFile = null;
	private JButton btCifFile = null;
	private JButton btCifrar = null;
	private JButton btDescifrar = null;
	private JTextField tfClearFile = new JTextField(20);
	private JTextField tfCifFile = new JTextField(20);
	private JPasswordField pfCifrar = new JPasswordField(20);
	
	protected Interfaz(){
		getJFrame();
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static Interfaz getInterfaz(){
		if(singleton == null){
			singleton = new Interfaz();
		}
		return singleton;
	}
	
	public static void setPestActual(int i){
		pestActual = i;
	}
	
	public void setFichero(File f){
		if(botonFichero == btFile){
			fichero = f;
			//getGestCertPane().add(new JLabel(fichero.getName()), BorderLayout.CENTER);
			String path = fichero.getAbsolutePath();
			if(!f.exists() && !path.contains(".keystore")){
				path += ".keystore";
				fichero = new File(path);
			}
			// No está autentificado
			this.storepass = null;
			jlFichero.setText(path);
			jFrame.repaint();
		}
		// Firmar
		if(botonFichero == btFile2Sig){
			String path = f.getAbsolutePath();
			if(f.exists()){
				tfFile2Sig.setText(path);
			}else{
				JOptionPane.showMessageDialog(null, "El fichero no existe", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
		if(botonFichero == btSigFile){
			String path = f.getAbsolutePath();
			tfSigFile.setText(path);
		}
		if(botonFichero == btPkFile){
			String path = f.getAbsolutePath();
			tfPkFile.setText(path);
		}
		// CheckFirma
		if(botonFichero == btCFile2Sig){
			String path = f.getAbsolutePath();
			if(f.exists()){
				tfCFile2Sig.setText(path);
			}else{
				JOptionPane.showMessageDialog(null, "El fichero no existe", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
		if(botonFichero == btCSigFile){
			String path = f.getAbsolutePath();
			tfCSigFile.setText(path);
		}
		if(botonFichero == btCPkFile){
			String path = f.getAbsolutePath();
			tfCPkFile.setText(path);
		}
		// MD5
		if(botonFichero == btMD5File){
			String path = f.getAbsolutePath();
			tfMD5File.setText(path);
		}
		// Cifrar/Descifrar
		if(botonFichero == btClearFile){
			String path = f.getAbsolutePath();
			tfClearFile.setText(path);
		}
		if(botonFichero == btCifFile){
			String path = f.getAbsolutePath();
			tfCifFile.setText(path);
		}
	}
	
	public File getFichero(){
		return fichero;
	}
	
	private void selectorFicheros(){
		botonFichero = btFile;
		selFichero = new JFrame("Seleccione o cree el KeyStore");
		jFileChooser = new MiJFileChooser(selFichero, this, MiJFileChooser.KEYSTORE);
		//FiltroKS filtro = new FiltroKS();
		//jFileChooser.addChoosableFileFilter(filtro);
		selFichero.getContentPane().add(jFileChooser);
		selFichero.setSize(selFichero.getPreferredSize());
		selFichero.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		//jFileChooser.addActionListener(this);
		selFichero.setVisible(true);		
	}
	
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setSize(new Dimension(500, 400));
			jFrame.setTitle("Practica SI");
			jFrame.setContentPane(getJTabbedPane());
		}
		return jFrame;
	}

	private JPanel getGestCertPane() {
		pestActual = 0;
		if (jGestCertPane == null) {
			jGestCertPane = new JPanel();
			//jGestCertPane.setLayout(new BorderLayout());
			GridBagLayout gbl = new GridBagLayout();
			GridBagConstraints gbc = new GridBagConstraints();
			jGestCertPane.setLayout(gbl);
			
			gbc.weightx = 1;
			gbc.weighty = 1;
			
			JLabel jl = new JLabel("Gestion de Certificados");
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbl.setConstraints(jl, gbc);			
			jGestCertPane.add(jl);
			
			btCrear = new JButton("Crear Certificado Autofirmado");
			btCrear.addActionListener(this);
			btCrear.setToolTipText("Permite la creación de certificados autofirmados en el almacén de claves actual");
			gbc.gridx = 0;
			gbc.gridy = 1; 
			gbl.setConstraints(btCrear, gbc);
			jGestCertPane.add(btCrear);
			
			JPanel panel0 = new JPanel();
			GridBagLayout gbl0 = new GridBagLayout();
			panel0.setLayout(gbl0);
			btImportar = new JButton("Importar Certificado");
			btImportar.addActionListener(this);
			btImportar.setToolTipText("Utilidad para la importación de certificados en el almacén de claves actual");
			JPanel panela = new JPanel();
			gbc.gridx = 0;
			gbc.gridy = 0; 
			panela.add(btImportar);
			gbl0.setConstraints(panela, gbc);
			panel0.add(panela);
			btExportar = new JButton("Exportar Certificado");
			btExportar.addActionListener(this);
			btExportar.setToolTipText("Guarda un certificado contenido en el almacén de claves con otro formato");
			JPanel panelb = new JPanel();
			gbc.gridx = 1;
			gbc.gridy = 0;
			panelb.add(btExportar);
			gbl0.setConstraints(panelb, gbc);
			panel0.add(panelb);
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbl.setConstraints(panel0, gbc);
			jGestCertPane.add(panel0);
			
			JPanel panel1 = new JPanel();
			GridBagLayout gbl1 = new GridBagLayout();
			panel1.setLayout(gbl1);
			btListado = new JButton("Listar Certificados");
			btListado.addActionListener(this);
			btListado.setToolTipText("Muestra una lista con los certificados contenidos en el almacén de claves actual");
			JPanel panelc = new JPanel();
			gbc.gridx = 0;
			gbc.gridy = 0; 
			panelc.add(btListado);
			gbl1.setConstraints(panelc, gbc);
			panel1.add(panelc);
			btFile = new JButton("Seleccione KeyStore");
			btFile.addActionListener(this);
			btFile.setToolTipText("Permite elegir un fichero de almacén de claves");
			JPanel paneld = new JPanel();
			gbc.gridx = 1;
			gbc.gridy= 0;
			paneld.add(btFile);
			gbl1.setConstraints(paneld, gbc);
			panel1.add(paneld);
			gbc.gridx = 0;
			gbc.gridy = 3;
			gbl.setConstraints(panel1, gbc);
			jGestCertPane.add(panel1);
			
			JPanel panel2 = new JPanel();
			GridBagLayout gbl2 = new GridBagLayout();
			panel2.setLayout(gbl2);
			JLabel jlFile = new JLabel("KeyStore: ");
			jlFile.setToolTipText("Almacén de claves actual");
			gbc.gridx = 0;
			gbc.gridy= 0;
			gbl2.setConstraints(jlFile, gbc);
			panel2.add(jlFile);			
			gbc.gridx = 1;
			gbc.gridy= 0;
			gbl2.setConstraints(jlFichero, gbc);
			panel2.add(jlFichero);		
			jlFichero.setToolTipText("Almacén de claves actual");
			gbc.gridx = 0;
			gbc.gridy = 4;
			gbl.setConstraints(panel2, gbc);
			jGestCertPane.add(panel2);
			
		}
		return jGestCertPane;
	}
	
	private JPanel getFirmarPane() {
		if (jFirmarPane == null) {
			jFirmarPane = new JPanel();
			//jFirmarPane.setLayout(new BorderLayout());
			//jFirmarPane.add(new JLabel("Firmar"), BorderLayout.NORTH);
			
			GridBagLayout gbl = new GridBagLayout();
			GridBagConstraints gbc = new GridBagConstraints();
			jFirmarPane.setLayout(gbl);
			
			gbc.fill = GridBagConstraints.BOTH;
			gbc.weightx = 1;
			gbc.weighty = 1;
			
			JPanel jp = new JPanel();
			JLabel jl = new JLabel("Firmar documento");
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbl.setConstraints(jp, gbc);
			jp.add(jl);
			jFirmarPane.add(jp);
			
			jpListaAlias = new JPanel();
			jl = new JLabel("Alias: ");
			jl.setToolTipText("Seleccion el certificado con el que firmar");
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbl.setConstraints(jpListaAlias, gbc);
			jpListaAlias.add(jl);
			cbCertificadosFirma = new JComboBox(listaCertificados);			
			jpListaAlias.add(cbCertificadosFirma);
			jFirmarPane.add(jpListaAlias);			
			
			jp = new JPanel();
			jl = new JLabel("Contraseña: ");
			jl.setToolTipText("Introduzca la contraseña del certificado seleccionado");
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbl.setConstraints(jp, gbc);
			jp.add(jl);
			jp.add(this.pfFirma);
			jFirmarPane.add(jp);
			
			jp = new JPanel();
			jl = new JLabel("Fichero a firmar: ");
			jl.setToolTipText("Fichero que desea firmar");
			gbc.gridx = 0;
			gbc.gridy = 3;
			gbl.setConstraints(jp, gbc);
			jp.add(jl);
			jp.add(tfFile2Sig);
			btFile2Sig = new JButton("Explorar");
			btFile2Sig.addActionListener(this);
			jp.add(btFile2Sig);
			jFirmarPane.add(jp);
			
			jp = new JPanel();
			jl = new JLabel("Fichero de la firma: ");
			jl.setToolTipText("Fichero donde se guardará la firma");
			gbc.gridx = 0;
			gbc.gridy = 4;
			gbl.setConstraints(jp, gbc);
			jp.add(jl);
			jp.add(tfSigFile);
			btSigFile = new JButton("Explorar");
			btSigFile.addActionListener(this);
			jp.add(btSigFile);
			jFirmarPane.add(jp);
			
			jp = new JPanel();
			jl = new JLabel("Fichero de clave publica: ");
			jl.setToolTipText("Fichero donde guardará su clave publica, en caso de querer compartirla");
			gbc.gridx = 0;
			gbc.gridy = 5;
			gbl.setConstraints(jp, gbc);
			jp.add(jl);
			jp.add(tfPkFile);
			btPkFile = new JButton("Explorar");
			btPkFile.addActionListener(this);
			jp.add(btPkFile);
			jFirmarPane.add(jp);
			
			jp = new JPanel();
			btFirmar = new JButton("Firmar");
			btFirmar.setToolTipText("Guarda en el fichero de la firmar, la firma digital correspondiente para el fichero de entrada");
			gbc.gridx = 0;
			gbc.gridy = 6;
			gbl.setConstraints(jp, gbc);
			jp.add(btFirmar);
			btFirmar.addActionListener(this);
			jFirmarPane.add(jp);
			
		}else{
			GridBagLayout gbl = (GridBagLayout)jFirmarPane.getLayout();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			/*jFirmarPane.remove(cbCertificadosFirma);
			cbCertificadosFirma = new JComboBox(listaCertificados);
			gbc.gridx = 1;
			gbc.gridy = 1;
			gbl.setConstraints(cbCertificadosFirma, gbc);
			jFirmarPane.add(cbCertificadosFirma);*/
			jFirmarPane.remove(jpListaAlias);
			jpListaAlias = new JPanel();
			JLabel jl = new JLabel("Alias: ");
			jl.setToolTipText("Seleccion el certificado con el que firmar");
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbl.setConstraints(jpListaAlias, gbc);
			jpListaAlias.add(jl);
			cbCertificadosFirma = new JComboBox(listaCertificados);
			//gbc.gridx = 1;
			//gbc.gridy = 1;
			//gbl.setConstraints(cbCertificadosFirma, gbc);			
			jpListaAlias.add(cbCertificadosFirma);

			jFirmarPane.add(jpListaAlias);
		}
		return jFirmarPane;
	}

	private JPanel getCheckFirmaPane() {
		pestActual = 2;

		compFichero = new Component[3];
		compFichero[0] = tfCPkFile;
		compFichero[1] = tfCSigAlg;
		compFichero[2] = tfCKeyAlg;
		
		for(int i = 0; i < compFichero.length; i++)
			compFichero[i].setEnabled(false);
		
		if (jCheckFirmaPane == null) {
			jCheckFirmaPane = new JPanel();
			//jCheckFirmaPane.setLayout(new BorderLayout());
			//jCheckFirmaPane.add(new JLabel("Firmar"), BorderLayout.NORTH);
			
			GridBagLayout gbl = new GridBagLayout();
			GridBagConstraints gbc = new GridBagConstraints();
			jCheckFirmaPane.setLayout(gbl);
			
			gbc.fill = GridBagConstraints.BOTH;
			gbc.weightx = 1;
			gbc.weighty = 1;
			
			JPanel jp = new JPanel();
			JLabel jl = new JLabel("Comprobar la firma de un documento");
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbl.setConstraints(jp, gbc);
			jp.add(jl);
			jCheckFirmaPane.add(jp);
			
			jpCListaAlias = new JPanel();
			jl = new JLabel("Alias: ");
			jl.setToolTipText("Seleccion el certificado con el que cotejar");
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbl.setConstraints(jpCListaAlias, gbc);
			jcbC.setToolTipText("Utilizar Certificado");
			jcbC.setSelected(true);
			jcbC.addActionListener(this);
			jpCListaAlias.add(jcbC);
			jpCListaAlias.add(jl);
			cbCertificadosFirma = new JComboBox(listaCertificados);			
			jpCListaAlias.add(cbCertificadosFirma);
			jCheckFirmaPane.add(jpCListaAlias);			
			
			jp = new JPanel();
			jl = new JLabel("Contraseña: ");
			jl.setToolTipText("Introduzca la contraseña del certificado seleccionado");
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbl.setConstraints(jp, gbc);
			jp.add(jl);
			jp.add(this.pfCFirma);
			jCheckFirmaPane.add(jp);
			
			jp = new JPanel();
			jl = new JLabel("Fichero firmado: ");
			jl.setToolTipText("Fichero del que desea comprobar su firma");
			gbc.gridx = 0;
			gbc.gridy = 3;
			gbl.setConstraints(jp, gbc);
			jp.add(jl);
			jp.add(tfCFile2Sig);
			btCFile2Sig = new JButton("Explorar");
			btCFile2Sig.addActionListener(this);
			jp.add(btCFile2Sig);
			jCheckFirmaPane.add(jp);
			
			jp = new JPanel();
			jl = new JLabel("Fichero de la firma: ");
			jl.setToolTipText("Fichero donde se encuentra la firma");
			gbc.gridx = 0;
			gbc.gridy = 4;
			gbl.setConstraints(jp, gbc);
			jp.add(jl);
			jp.add(tfCSigFile);
			btCSigFile = new JButton("Explorar");
			btCSigFile.addActionListener(this);
			jp.add(btCSigFile);
			jCheckFirmaPane.add(jp);
			
			jp = new JPanel();
			jl = new JLabel("Fichero de clave publica: ");
			jl.setToolTipText("Fichero donde tiene la clave publica con la que firmó el fichero, si no tiene el certificado");
			gbc.gridx = 0;
			gbc.gridy = 5;
			gbl.setConstraints(jp, gbc);
			jp.add(jl);
			jp.add(tfCPkFile);
			btCPkFile = new JButton("Explorar");
			btCPkFile.addActionListener(this);
			jp.add(btCPkFile);
			jCheckFirmaPane.add(jp);
			
			jp = new JPanel();
			jl = new JLabel("Algoritmo de la firma: ");
			jl.setToolTipText("Necesario sólo en caso de la comprobación por fichero de clave pública");
			gbc.gridx = 0;
			gbc.gridy = 6;
			gbl.setConstraints(jp, gbc);
			jp.add(jl);
			jp.add(tfCSigAlg);
			jCheckFirmaPane.add(jp);
			
			jp = new JPanel();
			jl = new JLabel("Algoritmo de la clave: ");
			jl.setToolTipText("Necesario sólo en caso de la comprobación por fichero de clave pública");
			gbc.gridx = 0;
			gbc.gridy = 7;
			gbl.setConstraints(jp, gbc);
			jp.add(jl);
			jp.add(tfCKeyAlg);
			jCheckFirmaPane.add(jp);
			
			jp = new JPanel();
			btCFirma = new JButton("Comprobar Firma");
			btCFirma.setToolTipText("Comprueba la firma del fichero dado");
			gbc.gridx = 0;
			gbc.gridy = 8;
			gbl.setConstraints(jp, gbc);
			jp.add(btCFirma);
			btCFirma.addActionListener(this);
			jCheckFirmaPane.add(jp);
			
		}else{
			GridBagLayout gbl = (GridBagLayout)jCheckFirmaPane.getLayout();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			/*jCheckFirmaPane.remove(cbCertificadosFirma);
			cbCertificadosFirma = new JComboBox(listaCertificados);
			gbc.gridx = 1;
			gbc.gridy = 1;
			gbl.setConstraints(cbCertificadosFirma, gbc);
			jCheckFirmaPane.add(cbCertificadosFirma);*/
			jCheckFirmaPane.remove(jpCListaAlias);
			jpCListaAlias = new JPanel();
			JLabel jl = new JLabel("Alias: ");
			jl.setToolTipText("Seleccion el certificado con el que cotejar");
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbl.setConstraints(jpCListaAlias, gbc);
			jcbC.setSelected(true);
			jcbC.setToolTipText("Utilizar Certificado");
			jcbC.addActionListener(this);
			jpCListaAlias.add(jcbC);
			jpCListaAlias.add(jl);
			cbCertificadosFirma = new JComboBox(listaCertificados);
			//gbc.gridx = 1;
			//gbc.gridy = 1;
			//gbl.setConstraints(cbCertificadosFirma, gbc);			
			jpCListaAlias.add(cbCertificadosFirma);

			jCheckFirmaPane.add(jpCListaAlias);
		}
		
		return jCheckFirmaPane;
	}
	
	private JPanel getCheckMDPane() {
		pestActual = 3;
		if (jCheckMDPane == null) {
			jCheckMDPane = new JPanel();
			
			GridBagLayout gbl = new GridBagLayout();
			GridBagConstraints gbc = new GridBagConstraints();
			jCheckMDPane.setLayout(gbl);
			
			gbc.fill = GridBagConstraints.BOTH;
			gbc.weightx = 1;
			gbc.weighty = 1;
			
			JPanel jp = new JPanel();
			JLabel jl = new JLabel("Comprobar un resumen MD5");
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbl.setConstraints(jp, gbc);
			jp.add(jl);
			jCheckMDPane.add(jp);
			
			jp = new JPanel();
			jl = new JLabel("Fichero: ");
			jl.setToolTipText("Fichero a comprobar");
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbl.setConstraints(jp, gbc);
			jp.add(jl);
			jp.add(tfMD5File);
			btMD5File = new JButton("Explorar");
			btMD5File.addActionListener(this);
			jp.add(btMD5File);
			jCheckMDPane.add(jp);
			
			taMD5.setSize(400, 150);
			
			jp = new JPanel();
			jl = new JLabel("Resumen MD5: ");
			jl.setToolTipText("Peque aquí el resumen MD5");
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbl.setConstraints(jp, gbc);
			jp.add(jl);
			jCheckMDPane.add(jp);
			
			JScrollPane jsp = new JScrollPane();
			gbc.gridx = 0;
			gbc.gridy = 3;
			gbc.weighty = 10;
			gbl.setConstraints(jsp, gbc);
			jsp.setViewportView(taMD5);
			//jsp.add(taMD5);
			jCheckMDPane.add(jsp);			
			gbc.weighty = 1;
			
			jp = new JPanel();
			gbc.gridx = 0;
			gbc.gridy = 4;
			gbl.setConstraints(jp, gbc);
			btCheckMD5 = new JButton("Comprobar Resumen");
			btCheckMD5.addActionListener(this);
			jp.add(btCheckMD5);
			jCheckMDPane.add(jp);
		}
		return jCheckMDPane;
	}
	
	private JPanel getCifrarPane() {
		pestActual = 4;
		if (jCifrarPane == null) {
			jCifrarPane = new JPanel();
			
			GridBagLayout gbl = new GridBagLayout();
			GridBagConstraints gbc = new GridBagConstraints();
			jCifrarPane.setLayout(gbl);
			
			gbc.fill = GridBagConstraints.BOTH;
			gbc.weightx = 1;
			gbc.weighty = 1;
			
			JPanel jp = new JPanel();
			JLabel jl = new JLabel("Cifrar o Descifrar fichero");
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbl.setConstraints(jp, gbc);
			jp.add(jl);
			jCifrarPane.add(jp);
			
			jp = new JPanel();
			jl = new JLabel("Fichero en claro: ");
			jl.setToolTipText("Fichero a cifrar, o fichero donde descifrar el fichero cifrado");
			gbc.gridx = 0;
			gbc.gridy = 1;
			gbl.setConstraints(jp, gbc);
			jp.add(jl);
			jp.add(tfClearFile);
			btClearFile = new JButton("Explorar");
			btClearFile.addActionListener(this);
			jp.add(btClearFile);
			jCifrarPane.add(jp);
			
			taMD5.setSize(400, 150);
			
			jp = new JPanel();
			jl = new JLabel("Fichero cifrado: ");
			jl.setToolTipText("Fichero a descifrar, o donde cifrar el fichero en claro");
			gbc.gridx = 0;
			gbc.gridy = 2;
			gbl.setConstraints(jp, gbc);
			jp.add(jl);
			jp.add(tfCifFile);
			btCifFile = new JButton("Explorar");
			btCifFile.addActionListener(this);
			jp.add(btCifFile);
			jCifrarPane.add(jp);
			
			jp = new JPanel();
			jl = new JLabel("Contraseña: ");
			jl.setToolTipText("Contraseña con la que cifrar el fichero, o con la que descifrarlo");
			gbc.gridx = 0;
			gbc.gridy = 3;
			gbl.setConstraints(jp, gbc);
			jp.add(jl);
			jp.add(pfCifrar);
			jCifrarPane.add(jp);
			
			jp = new JPanel();
			gbc.gridx = 0;
			gbc.gridy = 4;
			gbl.setConstraints(jp, gbc);
			btCifrar = new JButton("Cifrar");
			btCifrar.addActionListener(this);
			jp.add(btCifrar);
			btDescifrar = new JButton("Descifrar");
			btDescifrar.addActionListener(this);
			jp.add(btDescifrar);
			jCifrarPane.add(jp);
		}
		return jCifrarPane;
	}
	
	private JTabbedPane getJTabbedPane() {
		if (jTabbedPane == null) {
			jTabbedPane = new JTabbedPane();
			jTabbedPane.addTab("Gestión de Certificados", getGestCertPane());
			jTabbedPane.addTab("Firmar Fichero",getFirmarPane());
			jTabbedPane.addTab("Comprobar firma de un fichero", getCheckFirmaPane());
			jTabbedPane.addTab("Comprobar resumen de un fichero", getCheckMDPane());
			jTabbedPane.addTab("Cifrar/Descifrar un fichero", getCifrarPane());
			jTabbedPane.setToolTipTextAt(0, "Creación, importación, exportación y listado de certificados. " +
					"Permite elegir Keystore.");
			jTabbedPane.setToolTipTextAt(1, "Firmar digitalmente un fichero con un certificado.");
			jTabbedPane.setToolTipTextAt(2, "Verificar la corrección de la firma en un fichero para autenticar a su autor.");
			jTabbedPane.setToolTipTextAt(3, "Comprobar un resumen de un fichero para confirmar su integridad.");
			jTabbedPane.setToolTipTextAt(4, "Cifrar o descifrar un fichero para ocultar o recuperar su información.");
			
			// Actualizamos los paneles
			jTabbedPane.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					 int i = jTabbedPane.getSelectedIndex();
					 Interfaz.setPestActual(i);
					 switch(i){
					 	case 0: getGestCertPane();
					 		break;
					 	case 1:
					 		new KeyStoreOpen(Interfaz.getInterfaz());
				 			break;
					 	case 2: //getCheckFirmaPane();
					 		new KeyStoreOpen(Interfaz.getInterfaz());
				 			break;
					 	case 3: getCheckMDPane();
				 			break;
					 	case 4: getCifrarPane();
				 			break;
					 }
					 //JOptionPane.showMessageDialog(null, i, "Mensaje", JOptionPane.WARNING_MESSAGE);
				 }
				 });
		}
		return jTabbedPane;
	}

	static public void main(String[] args){
		//new Interfaz();
		Interfaz.getInterfaz();
	}

	public void actionPerformed(ActionEvent e) {
		// Botón?
		try{
			// GestCert
			if(((JButton)e.getSource()).equals(btFile)){
				selectorFicheros();
			}
			if(((JButton)e.getSource()).equals(btCrear)){
				new CrearCertificado(this.fichero);
			}
			if(((JButton)e.getSource()).equals(btExportar)){
				new Exportar(this.fichero);
			}
			if(((JButton)e.getSource()).equals(btImportar)){
				new Importar(this.fichero);
			}
			if(((JButton)e.getSource()).equals(btListado)){
				new Listado(this.fichero);
			}
			// Firmar
			if(((JButton)e.getSource()).equals(btFile2Sig)){
				botonFichero = btFile2Sig;
				JFrame fc = new JFrame("Seleccione el fichero a firmar");
				jFileChooser = new MiJFileChooser(fc, this, MiJFileChooser.ALL);
				fc.getContentPane().add(jFileChooser);
				fc.setSize(fc.getPreferredSize());
				fc.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				fc.setVisible(true);
			}
			if(((JButton)e.getSource()).equals(btSigFile)){
				botonFichero = btSigFile;
				JFrame fc = new JFrame("Seleccione el fichero donde guardará la firma");
				jFileChooser = new MiJFileChooser(fc, this, MiJFileChooser.ALL);
				fc.getContentPane().add(jFileChooser);
				fc.setSize(fc.getPreferredSize());
				fc.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				fc.setVisible(true);
			}
			if(((JButton)e.getSource()).equals(btPkFile)){
				botonFichero = btPkFile;
				JFrame fc = new JFrame("Seleccione el fichero donde guardará su clave publica");
				jFileChooser = new MiJFileChooser(fc, this, MiJFileChooser.ALL);
				fc.getContentPane().add(jFileChooser);
				fc.setSize(fc.getPreferredSize());
				fc.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				fc.setVisible(true);
			}
			if(((JButton)e.getSource()).equals(btFirmar)){
				if(tfFile2Sig.getText() == null || tfFile2Sig.getText().equalsIgnoreCase("")){
					JOptionPane.showMessageDialog(null, "Debe especificar un archivo a firmar", "Error firmando el archivo", JOptionPane.WARNING_MESSAGE);
				}else{
					Certificados cert = new Certificados();
					String alias = (String)cbCertificadosFirma.getSelectedItem();
					String salida = cert.firmar(alias, pfFirma.getPassword(), jlFichero.getText(),
							storepass, new File(tfFile2Sig.getText()), tfSigFile.getText(),
							tfPkFile.getText());
					if(salida != null){
						JOptionPane.showMessageDialog(null, salida, "Error firmando el archivo", JOptionPane.ERROR_MESSAGE);
					}else{
						JOptionPane.showMessageDialog(null, "Fichero firmado correctamente", "Firmado", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
			// CheckFirma
			if(((JButton)e.getSource()).equals(btCFile2Sig)){
				botonFichero = btCFile2Sig;
				JFrame fc = new JFrame("Seleccione el fichero firmado");
				jFileChooser = new MiJFileChooser(fc, this, MiJFileChooser.ALL);
				fc.getContentPane().add(jFileChooser);
				fc.setSize(fc.getPreferredSize());
				fc.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				fc.setVisible(true);
			}
			if(((JButton)e.getSource()).equals(btCSigFile)){
				botonFichero = btCSigFile;
				JFrame fc = new JFrame("Seleccione el fichero donde guardó la firma");
				jFileChooser = new MiJFileChooser(fc, this, MiJFileChooser.ALL);
				fc.getContentPane().add(jFileChooser);
				fc.setSize(fc.getPreferredSize());
				fc.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				fc.setVisible(true);
			}
			if(((JButton)e.getSource()).equals(btCPkFile)){
				botonFichero = btCPkFile;
				JFrame fc = new JFrame("Seleccione el fichero donde está la clave publica");
				jFileChooser = new MiJFileChooser(fc, this, MiJFileChooser.ALL);
				fc.getContentPane().add(jFileChooser);
				fc.setSize(fc.getPreferredSize());
				fc.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				fc.setVisible(true);
			}
			if(((JButton)e.getSource()).equals(btCFirma)){
				if(tfCFile2Sig.getText() == null || tfCFile2Sig.getText().equalsIgnoreCase("")){
					JOptionPane.showMessageDialog(null, "Debe especificar el archivo a comprobar", "Error cotejando el archivo", JOptionPane.WARNING_MESSAGE);
				}else{
					Certificados cert = new Certificados();
					String alias = (String)cbCertificadosFirma.getSelectedItem();
					String sigalg = tfCSigAlg.getText();
					String keyalg = tfCKeyAlg.getText();
					String rutaClave = tfCPkFile.getText();
					
					if(rutaClave.equalsIgnoreCase(""))
						rutaClave = null;
					if(sigalg.equalsIgnoreCase(""))
						sigalg = null;
					if(keyalg.equalsIgnoreCase(""))
						keyalg = null;
					
					if(jcbC.isSelected()){
						rutaClave = sigalg = keyalg = null;
					}else{
						alias = null;
					}
						
					String salida = cert.checkFirma(alias, pfCFirma.getPassword(), jlFichero.getText(),
							storepass, new File(tfCFile2Sig.getText()), tfCSigFile.getText(),
							rutaClave,sigalg,keyalg);
					if(salida != null){
						JOptionPane.showMessageDialog(null, salida, "Error cotejando el archivo", JOptionPane.ERROR_MESSAGE);
					}else{
						JOptionPane.showMessageDialog(null, "La firma coincide", "Cotejado", JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
			// MD5
			if(((JButton)e.getSource()).equals(btMD5File)){
				botonFichero = btMD5File;
				JFrame fc = new JFrame("Seleccione el fichero a comprobar");
				jFileChooser = new MiJFileChooser(fc, this, MiJFileChooser.ALL);
				fc.getContentPane().add(jFileChooser);
				fc.setSize(fc.getPreferredSize());
				fc.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				fc.setVisible(true);
			}
			if(((JButton)e.getSource()).equals(btCheckMD5)){
				String ruta = tfMD5File.getText();
				String MD5 = taMD5.getText();
				if(ruta != null && !ruta.equalsIgnoreCase("")){
					Certificados cert = new Certificados();
					String msg = cert.checkMD(MD5, new File(ruta));
					if(msg == null){
						JOptionPane.showMessageDialog(null, "El resumen MD5 es correcto", "Comprobación satisfactoria", JOptionPane.INFORMATION_MESSAGE);
					}else{
						JOptionPane.showMessageDialog(null, msg, "Comprobacion MD5", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(null, "Debe seleccionar el fichero a comprobar", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
			// Cifrar - Descifrar
			if(((JButton)e.getSource()).equals(btClearFile)){
				botonFichero = btClearFile;
				JFrame fc = new JFrame("Seleccione el fichero en claro");
				jFileChooser = new MiJFileChooser(fc, this, MiJFileChooser.ALL);
				fc.getContentPane().add(jFileChooser);
				fc.setSize(fc.getPreferredSize());
				fc.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				fc.setVisible(true);
			}
			if(((JButton)e.getSource()).equals(btCifFile)){
				botonFichero = btCifFile;
				JFrame fc = new JFrame("Seleccione el fichero cifrado");
				jFileChooser = new MiJFileChooser(fc, this, MiJFileChooser.ALL);
				fc.getContentPane().add(jFileChooser);
				fc.setSize(fc.getPreferredSize());
				fc.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				fc.setVisible(true);
			}
			if(((JButton)e.getSource()).equals(btCifrar)){
				String rutaClaro = tfClearFile.getText();
				String rutaCif = tfCifFile.getText();
				if(rutaClaro != null && !rutaClaro.equalsIgnoreCase("")){
					if(rutaCif != null && !rutaCif.equalsIgnoreCase("")){
						Certificados cert = new Certificados();
						String msg = cert.cifrar(rutaClaro, rutaCif, pfCifrar.getPassword());
						if(msg == null){
							JOptionPane.showMessageDialog(null, "Fichero cifrado correctamente", "Cifrado completado", JOptionPane.INFORMATION_MESSAGE);
						}else{
							JOptionPane.showMessageDialog(null, msg, "Cifrado", JOptionPane.ERROR_MESSAGE);
						}
					}else{
						JOptionPane.showMessageDialog(null, "Debe seleccionar el fichero donde cifrar", "ERROR", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(null, "Debe seleccionar el fichero para cifrar", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
			if(((JButton)e.getSource()).equals(btDescifrar)){
				String rutaClaro = tfClearFile.getText();
				String rutaCif = tfCifFile.getText();
				if(rutaClaro != null && !rutaClaro.equalsIgnoreCase("")){
					if(rutaCif != null && !rutaCif.equalsIgnoreCase("")){
						Certificados cert = new Certificados();
						String msg = cert.descifrar(rutaClaro, rutaCif, pfCifrar.getPassword());
						if(msg == null){
							JOptionPane.showMessageDialog(null, "Fichero descifrado correctamente", "Descifrado completado", JOptionPane.INFORMATION_MESSAGE);
						}else{
							JOptionPane.showMessageDialog(null, msg, "Descifrado", JOptionPane.ERROR_MESSAGE);
						}
					}else{
						JOptionPane.showMessageDialog(null, "Debe seleccionar el fichero para descifrar", "ERROR", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(null, "Debe seleccionar el fichero donde descifrar", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		}catch(Exception ex){
			//System.out.println(e.getSource());
		}
		// JCheckBox
		try{
			if(((JCheckBox)e.getSource()).equals(jcbC)){
				boolean b = !jcbC.isSelected();
				for(int i = 0; i < compFichero.length; i++)
					compFichero[i].setEnabled(b);
			}
		}catch(Exception ex){
		}
	}

	public String getKeystorePath() {
		// TODO Auto-generated method stub
		return jlFichero.getText();
	}

	public void mostrar() {
		switch(pestActual){
		case 0:
			// Nada que hacer
			break;
		case 1:
			this.getFirmarPane();
			break;
		case 2:
			this.getCheckFirmaPane();
			break;
		case 3:
			break;
		case 4:
			break;
		}
	}

	public void setCertificados(String[] certificados) {
		this.listaCertificados = certificados;
	}

	public void setStorePass(char[] sp) {
		this.storepass = sp;
	}
}
