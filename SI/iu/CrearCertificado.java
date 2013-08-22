package iu;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import crypto.Certificados;

public class CrearCertificado extends JFrame implements ActionListener{
	private File ksFile = null;
	
	private JLabel CN = new JLabel("Nombre completo: ");
	private JLabel OU = new JLabel("Unidad de organizaci�n: ");
	private JLabel O = new JLabel("Organizaci�n: ");
	private JLabel L = new JLabel("Localidad: ");
	private JLabel S = new JLabel("Estado o provincia: ");
	private JLabel C = new JLabel("Pais (C�digo de 2 letras): ");
	
	private JLabel alias = new JLabel("Alias: ");
	private JLabel keyalg = new JLabel("Algoritmo para la Clave: ");
	private JLabel sigalg = new JLabel("Algoritmo de firmado: ");
	private JLabel keysize = new JLabel("Tama�o de la Clave: ");
	//private JLabel dname = new JLabel("Nombre completo: ");
	private JLabel validity = new JLabel("Validez (d�as): ");
	private JLabel keypass = new JLabel("Contrase�a certificado: ");
	private JLabel keystore = new JLabel("Keystore: ");
	private JLabel storepass = new JLabel("Contrase�a keystore: ");
	//private JLabel storetype = new JLabel("Tipo de Keystore: ");
	
	private JTextField tfCN = new JTextField(20);
	private JTextField tfOU = new JTextField(20);
	private JTextField tfO = new JTextField(20);
	private JTextField tfL = new JTextField(20);
	private JTextField tfS = new JTextField(20);
	private JTextField tfC = new JTextField(20);
	
	private JTextField tfAlias = new JTextField(20);
	private JTextField tfKeyalg = new JTextField(20);
	private JTextField tfSigalg = new JTextField(20);
	private JTextField tfKeysize = new JTextField(20);
	private JTextField tfValidity = new JTextField(20);
	private JPasswordField tfKeypass = new JPasswordField(20);
	private JLabel tfKeystore = new JLabel();
	private JPasswordField tfStorepass = new JPasswordField(20);
	//private JTextField tfStoretype = new JTextField(20);
	
	private JButton jbOk = new JButton("Crear");
	private JButton jbCancel = new JButton("Cancelar");
	
	public CrearCertificado(File keystore){
		super("Crear Certificado Autofirmado");
		
		if(keystore != null){
			ksFile = keystore;
		}
		
		this.setSize(500,500);
		
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		Container c = this.getContentPane();
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 1;
		gbc.weightx = 1;
		
		// Datos del certificado
		JPanel jp1 = new JPanel();
		c.setLayout(gbl);
		gbc.gridx = 0;
		gbc.gridy = 0;
		jp1.add(CN);
		CN.setToolTipText("Nombre Completo del Titular del certificado");
		gbl.setConstraints(jp1, gbc);
		c.add(jp1);
		
		JPanel jp1b = new JPanel(); 
		gbc.gridx = 1;
		gbc.gridy = 0;
		jp1b.add(tfCN);
		gbl.setConstraints(jp1b, gbc);
		c.add(jp1b);
		
		JPanel jp2 = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 1;
		jp2.add(OU);
		OU.setToolTipText("Nombre de la Unidad Organizativa del Titular del certificado");
		gbl.setConstraints(jp2, gbc);
		c.add(jp2);
		
		JPanel jp2b = new JPanel();
		gbc.gridx = 1;
		gbc.gridy = 1;
		jp2b.add(tfOU);
		gbl.setConstraints(jp2b, gbc);
		c.add(jp2b);
		
		JPanel jp3 = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 2;
		jp3.add(O);
		O.setToolTipText("Nombre de la Organizaci�n del Titular del certificado");
		gbl.setConstraints(jp3, gbc);
		c.add(jp3);
		
		JPanel jp3b = new JPanel();
		gbc.gridx = 1;
		gbc.gridy = 2;
		jp3b.add(tfO);
		gbl.setConstraints(jp3b, gbc);
		c.add(jp3b);
		
		JPanel jp4 = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 3;
		jp4.add(L);
		L.setToolTipText("Localidad del Titular del certificado");
		gbl.setConstraints(jp4, gbc);
		c.add(jp4);
		
		JPanel jp4b = new JPanel();
		gbc.gridx = 1;
		gbc.gridy = 3;
		jp4b.add(tfL);
		gbl.setConstraints(jp4b, gbc);
		c.add(jp4b);
		
		JPanel jp5 = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 4;
		jp5.add(S);
		S.setToolTipText("Estado o provincia del Titular del certificado");
		gbl.setConstraints(jp5, gbc);
		c.add(jp5);
		
		JPanel jp5b = new JPanel();
		gbc.gridx = 1;
		gbc.gridy = 4;
		jp5b.add(tfS);
		gbl.setConstraints(jp5b, gbc);
		c.add(jp5b);
		
		JPanel jp6 = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 5;
		jp6.add(C);
		C.setToolTipText("Pa�s (2 letras) del Titular del certificado");
		gbl.setConstraints(jp6, gbc);
		c.add(jp6);
		
		JPanel jp6b = new JPanel();
		gbc.gridx = 1;
		gbc.gridy = 5;
		jp6b.add(tfC);
		gbl.setConstraints(jp6b, gbc);
		c.add(jp6b);
		
		// Datos del keystore
		JPanel jp7 = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 6;
		jp7.add(alias);
		alias.setToolTipText("Alias para el certificado (Obligatorio)");
		gbl.setConstraints(jp7, gbc);
		c.add(jp7);
		
		JPanel jp7b = new JPanel();
		gbc.gridx = 1;
		gbc.gridy = 6;
		jp7b.add(tfAlias);
		gbl.setConstraints(jp7b, gbc);
		c.add(jp7b);
		
		JPanel jp8 = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 7;
		jp8.add(keyalg);
		keyalg.setToolTipText("Algoritmo para el cifrado de las claves (Obligatorio)");
		gbl.setConstraints(jp8, gbc);
		c.add(jp8);
		
		JPanel jp8b = new JPanel();
		gbc.gridx = 1;
		gbc.gridy = 7;
		jp8b.add(tfKeyalg);
		gbl.setConstraints(jp8b, gbc);
		c.add(jp8b);
		
		JPanel jp9 = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 8;
		jp9.add(sigalg);
		sigalg.setToolTipText("Algoritmo para el firmado (Obligatorio)");
		gbl.setConstraints(jp9, gbc);
		c.add(jp9);
		
		JPanel jp9b = new JPanel();
		gbc.gridx = 1;
		gbc.gridy = 8;
		jp9b.add(tfSigalg);
		gbl.setConstraints(jp9b, gbc);
		c.add(jp9b);
		
		JPanel jp10 = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 9;
		jp10.add(keysize);
		keysize.setToolTipText("Longitud de las claves");
		gbl.setConstraints(jp10, gbc);
		c.add(jp10);
		
		JPanel jp10b = new JPanel();
		gbc.gridx = 1;
		gbc.gridy = 9;
		jp10b.add(tfKeysize);
		gbl.setConstraints(jp10b, gbc);
		c.add(jp10b);
		
		JPanel jp11 = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 10;
		jp11.add(validity);
		validity.setToolTipText("Tiempo de vida para el certificado");
		gbl.setConstraints(jp11, gbc);
		c.add(jp11);
		
		JPanel jp11b = new JPanel();
		gbc.gridx = 1;
		gbc.gridy = 10;
		jp11b.add(tfValidity);
		gbl.setConstraints(jp11b, gbc);
		c.add(jp11b);
		
		JPanel jp12 = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 11;
		jp12.add(keypass);
		keypass.setToolTipText("Contrase�a para el certificado (Obligatorio)");
		gbl.setConstraints(jp12, gbc);
		c.add(jp12);
		
		JPanel jp12b = new JPanel();
		gbc.gridx = 1;
		gbc.gridy = 11;
		jp12b.add(tfKeypass);
		gbl.setConstraints(jp12b, gbc);
		c.add(jp12b);
		
		JPanel jp13 = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 12;
		jp13.add(this.keystore);
		this.keystore.setToolTipText("Ruta al almac�n de claves. Para modificarlo h�galo en la ventana principal" +
				"de la aplicaci�n, en la pesta�a \"Gesti�n de Certificados\". Si no existe, se crear�.");
		gbl.setConstraints(jp13, gbc);
		c.add(jp13);
		
		JPanel jp13b = new JPanel();
		gbc.gridx = 1;
		gbc.gridy = 12;
		if(keystore != null)
			tfKeystore.setText(keystore.getAbsolutePath());
		else
			tfKeystore.setText(System.getProperty("user.home")+"\\.keystore");
		jp13b.add(tfKeystore);
		gbl.setConstraints(jp13b, gbc);
		c.add(jp13b);
		
		JPanel jp14 = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 13;
		jp14.add(storepass);
		storepass.setToolTipText("Contrase�a para el almac�n de claves (Obligatorio)");
		gbl.setConstraints(jp14, gbc);
		c.add(jp14);
		
		JPanel jp14b = new JPanel();
		gbc.gridx = 1;
		gbc.gridy = 13;
		jp14b.add(tfStorepass);
		gbl.setConstraints(jp14b, gbc);
		c.add(jp14b);
		
		// Botones OK y Cancel
		JPanel jp16 = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 14;
		jbOk.addActionListener(this);
		jp16.add(jbOk);
		gbl.setConstraints(jp16, gbc);
		c.add(jp16);
		
		JPanel jp17 = new JPanel();
		gbc.gridx = 1;
		gbc.gridy = 14;
		jbCancel.addActionListener(this);
		jp17.add(jbCancel);
		gbl.setConstraints(jp17, gbc);
		c.add(jp17);
		
		this.setSize(this.getPreferredSize());
		
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		try{
			if(((JButton)e.getSource()).equals(jbOk)){
				Certificados cert = new Certificados();
				String alias, keyalg, sigalg, keypass, keystore, storepass, storetype, cn, ou, o, l ,s, c;
				Integer keysize = null;
				Integer validity = null;
				boolean correcto = true;
				
				// Keystore
				if(tfAlias.getText().compareTo("") == 0){
					alias = null;
					correcto = false;
				}else{
					alias = tfAlias.getText();
				}
				
				if(tfKeyalg.getText().compareTo("") == 0){
					keyalg = null;
					correcto = false;
				}else{
					keyalg = tfKeyalg.getText();
				}
				
				if(tfSigalg.getText().compareTo("") == 0){
					sigalg = null;
					correcto = false;
				}else{
					sigalg = tfSigalg.getText();
				}
				
				if(tfKeysize.getText().compareTo("") == 0){
					keysize = null;
				}else{
					try{
						keysize = Integer.parseInt(tfKeysize.getText());
					}catch(Exception en){
						correcto = false;
						tfKeysize.setText("Debe escribir un numero entero");
					}
				}
				
				if(tfValidity.getText().compareTo("") == 0){
					validity = null;
				}else{
					try{
						validity = Integer.parseInt(tfValidity.getText());
						if(validity < 0){
							tfValidity.setText("0");
							validity = 0;
						}
					}catch(Exception en){
						correcto = false;
						tfValidity.setText("Debe escribir el numero de dias");
					}
				}
				
				String aux = new String(tfKeypass.getPassword());
				if(aux.compareTo("") == 0){
					keypass = null;
					correcto = false;
				}else{
					keypass = aux;
				}
				
				if(ksFile == null){
					keystore = null;
				}else{
					keystore = "\""+ksFile.getAbsolutePath()+"\"";
				}
				
				aux = new String(tfStorepass.getPassword());
				if(aux.compareTo("") == 0){
					storepass = null;
					correcto = false;
				}else{
					storepass = aux;
				}

				// Certificado
				cn = tfCN.getText();
				ou = tfOU.getText();
				o = tfO.getText();				
				l = tfL.getText();
				s = tfS.getText();
				c = new String();
				if(tfC.getText().length() == 2)
					c = tfC.getText();
				else{
					tfC.setText("En C�digo de Pais debe ser de 2 letras");
					correcto = false;
				}
				
				if(correcto){
					String msg = cert.crearAutofirmado(alias, keyalg, sigalg, keysize,
						validity, keypass, keystore, storepass, cn, ou, o, l, s, c);
				
					if(msg == null){
						JOptionPane.showMessageDialog(this, "Certificado creado satisfactoriamente",
							"Certificado Creado", JOptionPane.INFORMATION_MESSAGE);
						this.dispose();
					}else{
						JOptionPane.showMessageDialog(this, msg, "ERROR", JOptionPane.ERROR_MESSAGE);
					}
				}else{
					JOptionPane.showMessageDialog(this, "Hay campos necesarios vacios o campos incorrectos", "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
			if(((JButton)e.getSource()).equals(jbCancel)){
				this.dispose();
			}
		}catch(Exception ex){
			//System.out.println(e.getSource());
		}		
	}

}
