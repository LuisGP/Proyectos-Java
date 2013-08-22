package iu;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import crypto.Certificados;

public class Importar extends JFrame implements ActionListener, DestinoFichero{
	private File ksFile = null;
	private File certificado = null;
	
	private JLabel alias = new JLabel("Alias: ");
	private JLabel ruta = new JLabel("Ruta: ");
	private JLabel keypass = new JLabel("Contraseña certificado: ");
	private JLabel keystore = new JLabel("Keystore: ");
	private JLabel storepass = new JLabel("Contraseña keystore: ");
	
	private JTextField tfAlias = new JTextField(20);
	private JTextField tfFile = new JTextField(20);
	private JPasswordField tfKeypass = new JPasswordField(20);
	private JLabel tfKeystore = new JLabel();
	private JPasswordField tfStorepass = new JPasswordField(20);
	
	private JButton jbOk = new JButton("Importar");
	private JButton jbCancel = new JButton("Cancelar");
	private JButton jbFileCert = new JButton("Examinar");
	
	public Importar(File keystore){
		super("Importar Certificado Autofirmado");
		
		if(keystore != null){
			//JOptionPane.showMessageDialog(this, keystore.getAbsolutePath(), "WOW", JOptionPane.INFORMATION_MESSAGE);
			ksFile = keystore;
		}
		//else
		//	JOptionPane.showMessageDialog(this, "Por defecto", "WOW", JOptionPane.INFORMATION_MESSAGE);
		
		//this.setSize(400,250);
		
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		Container c = this.getContentPane();
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 1;
		gbc.weightx = 1;
		
		c.setLayout(gbl);
		
		JPanel jp = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 0;
		jp.add(alias);
		alias.setToolTipText("Alias para el certificado (Obligatorio). Ha de ser único en el keystore");
		gbl.setConstraints(jp, gbc);
		c.add(jp);
		
		jp = new JPanel();
		gbc.gridx = 1;
		gbc.gridy = 0;
		jp.add(tfAlias);
		gbl.setConstraints(jp, gbc);
		c.add(jp);
		
		/*jp = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 1;
		jp.add(ruta);
		ruta.setToolTipText("Archivo a importar");
		gbl.setConstraints(jp, gbc);
		c.add(jp);*/
		
		jp = new JPanel();
		gbc.gridx = 1;
		gbc.gridy = 1;
		jp.add(tfFile);
		gbl.setConstraints(jp, gbc);
		c.add(jp);
		
		jp = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 1;
		jp.add(jbFileCert);
		jbFileCert.addActionListener(this);
		jbFileCert.setToolTipText("Seleccione el archivo a importar");
		gbl.setConstraints(jp, gbc);
		c.add(jp);
		
		jp = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 2;
		jp.add(keypass);
		keypass.setToolTipText("Contraseña del certificado (Obligatorio)");
		gbl.setConstraints(jp, gbc);
		c.add(jp);
		
		jp = new JPanel();
		gbc.gridx = 1;
		gbc.gridy = 2;
		jp.add(tfKeypass);
		gbl.setConstraints(jp, gbc);
		c.add(jp);
		
		jp = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 3;
		jp.add(this.keystore);
		this.keystore.setToolTipText("Ruta al almacén de claves. Para modificarlo hágalo en la ventana principal" +
				"de la aplicación, en la pestaña \"Gestión de Certificados\". Si no existe, se creará.");
		gbl.setConstraints(jp, gbc);
		c.add(jp);
		
		jp = new JPanel();
		gbc.gridx = 1;
		gbc.gridy = 3;
		if(keystore != null)
			tfKeystore.setText(keystore.getAbsolutePath());
		else
			tfKeystore.setText(System.getProperty("user.home")+"\\.keystore");
		jp.add(tfKeystore);
		gbl.setConstraints(jp, gbc);
		c.add(jp);
		
		jp = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 4;
		jp.add(storepass);
		storepass.setToolTipText("Contraseña para el almacén de claves (Obligatorio)");
		gbl.setConstraints(jp, gbc);
		c.add(jp);
		
		jp = new JPanel();
		gbc.gridx = 1;
		gbc.gridy = 4;
		jp.add(tfStorepass);
		gbl.setConstraints(jp, gbc);
		c.add(jp);
		
		// Botones OK y Cancel
		jp = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 5;
		jbOk.addActionListener(this);
		jp.add(jbOk);
		gbl.setConstraints(jp, gbc);
		c.add(jp);
		
		jp = new JPanel();
		gbc.gridx = 1;
		gbc.gridy = 5;
		jbCancel.addActionListener(this);
		jp.add(jbCancel);
		gbl.setConstraints(jp, gbc);
		c.add(jp);

		this.setSize(this.getPreferredSize().width+10,this.getPreferredSize().height+20);
		//this.setSize(this.getPreferredSize());
		
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		try{
			if(((JButton)e.getSource()).equals(jbOk)){
				Certificados cert = new Certificados();
				String file = "\""+tfFile.getText()+"\"";
				String ks = "\""+tfKeystore.getText()+"\"";
				String msg = cert.importar(tfAlias.getText(),
						file, new String(tfKeypass.getPassword()),
						new String(this.tfStorepass.getPassword()), ks);
				if(msg == null){
					JOptionPane.showMessageDialog(this, "Certificado importado satisfactoriamente",
						"Certificado Importado", JOptionPane.INFORMATION_MESSAGE);
					this.dispose();
				}else{
					JOptionPane.showMessageDialog(this, msg, "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
			if(((JButton)e.getSource()).equals(jbCancel)){
				this.dispose();
			}
			if(((JButton)e.getSource()).equals(jbFileCert)){
				JFrame selFichero = new JFrame("Seleccione el fichero a importar");
				MiJFileChooser jFileChooser = new MiJFileChooser(selFichero,this,MiJFileChooser.CER);
				selFichero.getContentPane().add(jFileChooser);
				selFichero.setSize(selFichero.getPreferredSize());
				selFichero.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				//jFileChooser.addActionListener(this);
				selFichero.setVisible(true);
			}
		}catch(Exception ex){
			//System.out.println(e.getSource());
		}		
	}

	public void setFichero(File f) {
		String path = f.getAbsolutePath();
		if(!f.exists() && !path.contains(".cer")){
			path += ".cer";
		}
		tfFile.setText(path);
	}
}
