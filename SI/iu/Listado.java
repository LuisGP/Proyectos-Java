package iu;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import crypto.Certificados;

public class Listado extends JFrame implements KeyStoreInfo, ActionListener{

	private String keystore;
	private String[] certificados;
	private char[] storepass;
	private JComboBox listaCerts;
	
	private JLabel jlAlias = new JLabel("Alias:");
	private JTextArea taInfo = new JTextArea();
	
	private JButton jbVerTodos = new JButton("Ver todos");
	
	public Listado(File keystore){
		if(keystore != null)
			this.keystore = keystore.getAbsolutePath();
		else
			this.keystore = System.getProperty("user.home")+"\\.keystore";
		new KeyStoreOpen(this);
	}
	
	public String getKeystorePath() {
		// TODO Auto-generated method stub
		return keystore;
	}
	
	private void setInfoText(){
		Certificados cert = new Certificados();
		String alias = (String)listaCerts.getSelectedItem();
		String info = cert.infoCertificado(storepass, keystore, alias);
		taInfo.setText(info);
		taInfo.moveCaretPosition(0);
	}

	public void mostrar() {
		// TODO Auto-generated method stub
		this.setTitle("Listar certificado");
		this.setSize(500,600);
		
		listaCerts = new JComboBox(certificados);
		listaCerts.addActionListener(this);
		
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
		JPanel jpC = new JPanel();
		gbc.gridx = 0;
		gbc.gridy = 0;
		jpA.add(jlAlias);
		jlAlias.setToolTipText("Elije el certificado a consultar");
		gbc.gridx = 1;
		gbc.gridy = 0;
		jpB.add(listaCerts);
		gbc.gridx = 2;
		gbc.gridy = 0;
		jpC.add(jbVerTodos);
		jbVerTodos.setToolTipText("Pulse aqu� si desea ver un resumen de todos los certificados");
		jbVerTodos.addActionListener(this);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weighty = 0.2;
		jp1.add(jpA);
		jp1.add(jpB);
		jp1.add(jpC);
		gbl.setConstraints(jp1, gbc);
		c.add(jp1);
		
		JScrollPane jp2 = new JScrollPane();
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weighty = 0.8;
		taInfo.setEditable(false);
		taInfo.setSize(400, 150);
		//taInfo.setText("Probando\nProbando\n1,2");
		//jp2.add(taInfo);
		jp2.setViewportView(taInfo);
		gbl.setConstraints(jp2, gbc);
		c.add(jp2);
		
		setInfoText();
		
		this.setVisible(true);
	}

	public void setCertificados(String[] certificados) {
		this.certificados = certificados;
	}

	public void setStorePass(char[] sp) {
		storepass = sp;
	}

	public void actionPerformed(ActionEvent e) {
		try{
			if(((JButton)e.getSource()).equals(jbVerTodos)){
				HashMap<String,ArrayList<String>> hm = new HashMap<String,ArrayList<String>>();
				ArrayList<String> certInfo;
				ArrayList<String> alias = new ArrayList<String>();
				String tmp, subject, sigAlg, key, validity, issuer, algorithm, signature;
				Certificados cert = new Certificados();
				for(int i = 0; i < certificados.length; i++){
					int aux, aux2;
					//System.out.println(certificados[i]);
					alias.add(certificados[i]);
					tmp = cert.infoCertificado(storepass, keystore, certificados[i]);
					aux = tmp.indexOf("Subject: ");
					subject = tmp.substring(aux, tmp.indexOf("\n", aux));
					//System.out.println(subject);
					aux = tmp.indexOf("Signature Algorithm: ");
					sigAlg = tmp.substring(aux, tmp.indexOf("\n", aux));
					//System.out.println(sigAlg);
					aux = tmp.indexOf("Key: ");
					key = tmp.substring(aux, tmp.indexOf("\n", aux));
					//System.out.println(key);
					aux = tmp.indexOf("Validity: ");
					validity = tmp.substring(aux, tmp.indexOf("\n", aux));
					//System.out.println(validity);
					aux = tmp.indexOf("Issuer: ");
					issuer = tmp.substring(aux, tmp.indexOf("\n", aux));
					//System.out.println(issuer);
					aux2 = tmp.indexOf("]");
					aux = tmp.indexOf("Algorithm: ",aux2); // Para que continue desde el ultimo
					algorithm = tmp.substring(aux, tmp.indexOf("\n", aux));
					//System.out.println(algorithm);
					aux = tmp.indexOf("Signature:",aux2);
					signature = tmp.substring(aux, tmp.indexOf("\n\n]", aux));
					//System.out.println(signature);
					
					// Rellenamos la informacion en el AL
					certInfo = new ArrayList<String>();
					certInfo.add(subject);
					certInfo.add(sigAlg);
					certInfo.add(key);
					certInfo.add(validity);
					certInfo.add(issuer);
					certInfo.add(algorithm);
					certInfo.add(signature);
					
					// La a�adimos al HM junto al alias correspondiente
					hm.put(alias.get(i), certInfo);
				}
				
				new ListadoResumen(alias,hm);
			}
		}catch(Exception exc){
			// Errores de casting, se ignoran
		}
		try{
			if(((JComboBox)e.getSource()).equals(listaCerts)){
				//System.out.println(e.getActionCommand());
				if(e.getActionCommand().equalsIgnoreCase("comboBoxChanged")){
					setInfoText();
				}
			}
		}catch(Exception exc){
			// Errores de Casting, se ignoran
		}
	}

}

class ListadoResumen extends JFrame{
	public ListadoResumen(ArrayList<String> alias, HashMap<String,ArrayList<String>> info){
		this.setTitle("Listado resumen de certificados");
		this.setSize(500,600);
		
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();
		Container c = this.getContentPane();
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weighty = 1;
		gbc.weightx = 1;
		c.setLayout(gbl);
		
		JScrollPane panel = new JScrollPane();
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		JTextArea text = new JTextArea(); 
		
		// Rellenamos la info
		for(int i = 0; i < alias.size(); i++){
			String act = alias.get(i);
			ArrayList<String> actInfo = info.get(act);
			text.append("Alias "+act+":\n");
			for(int j = 0; j < actInfo.size(); j++){
				text.append(actInfo.get(j)+"\n");
			}
			text.append("\n");
		}
		
		panel.setViewportView(text);
		
		gbl.setConstraints(panel, gbc);
		c.add(panel);
		
		this.setVisible(true);
	}
}
