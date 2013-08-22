package iu;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import crypto.Certificados;

public class KeyStoreOpen extends JFrame implements ActionListener{

	private JButton jbOK = new JButton("Aceptar");
	private JButton jbCancel = new JButton("Cancelar");
	private JLabel jlPass = new JLabel("Contrase�a del Keystore");
	private JPasswordField jpPass = new JPasswordField(20);
	private JLabel info = new JLabel();
	
	private KeyStoreInfo exportar;
	
	public KeyStoreOpen(KeyStoreInfo exp){
		super("Abrir Keystore");
		this.setSize(450,150);
		//this.setResizable(false);
		
		info.setText("Keystore: "+exp.getKeystorePath());
		info.setToolTipText("Si desea modificar el Keystore actual, h�galo en la ventana principal de la aplicaci�n");
		
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
		jpA.add(jlPass);
		jlPass.setToolTipText("Contrase�a del Keystore");
		gbc.gridx = 1;
		gbc.gridy = 0;
		jpB.add(jpPass);
		gbc.gridx = 0;
		gbc.gridy = 0;
		jp1.add(jpA);
		jp1.add(jpB);		
		gbl.setConstraints(jp1, gbc);
		c.add(jp1);		
		
		jp1 = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 1;
		jp1.add(info);
		gbl.setConstraints(jp1, gbc);
		c.add(jp1);
		//this.getContentPane().add(info);
		
		jp1 = new JPanel();
		jpA = new JPanel();		
		gbc.gridx = 0;
		gbc.gridy = 0;
		jpA.add(jbOK);
		jbOK.setToolTipText("Confirmar");
		
		jpB = new JPanel();		
		gbc.gridx = 1;
		gbc.gridy = 0;
		jpB.add(jbCancel);
		jbCancel.setToolTipText("Cancelar");
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		jp1.add(jpA);
		jp1.add(jpB);
		gbl.setConstraints(jp1, gbc);
		c.add(jp1);
		
		exportar = exp;
		
		jbOK.addActionListener(this);
		jbCancel.addActionListener(this);
		
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		try{
			if(((JButton)e.getSource()).equals(jbOK)){
				Certificados cert = new Certificados();
				ArrayList<String> al = cert.listaCertificados(jpPass.getPassword(), exportar.getKeystorePath());
				String[] lista = new String[al.size()];
				for(int i = 0; i < al.size(); i++){
					lista[i] = al.get(i);
				}
				
				if(al.size() == 0){
					jpPass.setText("");
				}else{
					exportar.setStorePass(jpPass.getPassword());
					exportar.setCertificados(lista);
				
					exportar.mostrar();
				
					this.dispose();
				}
			}
			if(((JButton)e.getSource()).equals(jbCancel)){
				this.dispose();
			}
		}catch(Exception ex){
			System.out.println(e.getSource());
		}		
	}
	
}
