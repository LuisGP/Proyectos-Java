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

public class Exportar extends JFrame implements ActionListener, DestinoFichero, KeyStoreInfo{

	private String[] certificados;
	private JComboBox listaCerts;
	
	private JLabel jlAlias = new JLabel("Alias:");
	private JLabel jlFile = new JLabel("Fichero:");
	private JLabel jlKeypass = new JLabel("Contraseņa del certificado:");
	private String keystore;
	private char[] storepass;
	
	private JTextField tfFile = new JTextField(20);
	private JPasswordField tfKeypass = new JPasswordField(20);
	private JButton jbFileExm = new JButton("Examinar");
	private JButton jbExportar = new JButton("Exportar");
	
	public void setStorePass(char[] sp){
		storepass = sp;
	}
	
	public void setFichero(File f){
		String path = f.getAbsolutePath();
		if(!f.exists() && !path.contains(".cer")){
			path += ".cer";
		}
		tfFile.setText(path);
	}
	
	public Exportar(File keystore){
		if(keystore != null)
			this.keystore = keystore.getAbsolutePath();
		else
			this.keystore = System.getProperty("user.home")+"\\.keystore";
		new KeyStoreOpen(this);
	}
	
	public void mostrar(){
		this.setTitle("Exportar certificado");
		this.setSize(500,250);
		
		listaCerts = new JComboBox(certificados);
		
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		Container c = this.getContentPane();
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 1;
		gbc.weightx = 1;
		c.setLayout(gbl);
		
		JPanel jp1 = new JPanel();
		JPanel jpA = new JPanel();	
		JPanel jpB = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 0;
		jpA.add(jlAlias);
		jlAlias.setToolTipText("Elije el certificado a exportar");
		gbc.gridx = 1;
		gbc.gridy = 0;
		jpB.add(listaCerts);
		gbc.gridx = 0;
		gbc.gridy = 0;
		jp1.add(jpA);
		jp1.add(jpB);		
		gbl.setConstraints(jp1, gbc);
		c.add(jp1);		
		//this.add(listaCerts);
		
		jp1 = new JPanel();
		jpA = new JPanel();	
		jpB = new JPanel();
		JPanel jpC = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 0;
		jpA.add(jlFile);
		jlFile.setToolTipText("Escriba fichero de destino");
		gbc.gridx = 1;
		gbc.gridy = 0;
		jpB.add(tfFile);
		gbc.gridx = 2;
		gbc.gridy = 0;
		jbFileExm.addActionListener(this);
		jpC.add(jbFileExm);		
		gbc.gridx = 0;
		gbc.gridy = 1;
		jp1.add(jpA);
		jp1.add(jpB);
		jp1.add(jpC);
		gbl.setConstraints(jp1, gbc);
		c.add(jp1);		
		
		jp1 = new JPanel();
		jpA = new JPanel();	
		jpB = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 0;
		jpA.add(jlKeypass);
		jlKeypass.setToolTipText("Contraseņa del certificado");
		gbc.gridx = 1;
		gbc.gridy = 0;
		jpB.add(tfKeypass);
		gbc.gridx = 0;
		gbc.gridy = 2;
		jp1.add(jpA);
		jp1.add(jpB);		
		gbl.setConstraints(jp1, gbc);
		c.add(jp1);
		
		jp1 = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 3;
		jp1.add(jbExportar);
		jbExportar.addActionListener(this);
		gbl.setConstraints(jp1, gbc);
		c.add(jp1);
		
		this.setVisible(true);
	}
	
	public void setCertificados(String[] certificados) {
		this.certificados = certificados;
	}
	
	public String getKeystorePath(){
		return keystore;
	}

	public void actionPerformed(ActionEvent e) {
		try{
			if(((JButton)e.getSource()).equals(jbFileExm)){
				JFrame selFichero = new JFrame("Seleccione el fichero de destino");
				MiJFileChooser jFileChooser = new MiJFileChooser(selFichero,this,MiJFileChooser.CER);
				selFichero.getContentPane().add(jFileChooser);
				selFichero.setSize(selFichero.getPreferredSize());
				selFichero.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				selFichero.setVisible(true);
			}
			if(((JButton)e.getSource()).equals(jbExportar)){
				Certificados cert = new Certificados();
				String file = "\""+tfFile.getText()+"\"";
				String ks = "\""+keystore+"\"";
				String msg = cert.exportar((String)listaCerts.getSelectedItem(),
						file, new String(tfKeypass.getPassword()),
						new String(storepass), ks);
				if(msg == null){
					JOptionPane.showMessageDialog(this, "Certificado exportado satisfactoriamente",
						"Certificado Exportado", JOptionPane.INFORMATION_MESSAGE);
					this.dispose();
				}else{
					JOptionPane.showMessageDialog(this, msg, "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}
		}catch(Exception ex){
			System.out.println(e.getSource());
		}	
	}
	
}

