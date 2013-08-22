package crypto;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.swing.JOptionPane;

import sun.security.x509.CertAndKeyGen;
import sun.security.x509.X500Name;

public class Certificados {

	private String keyStorePath = null;
	
	public String crearAutofirmado(String alias, String keyalg, String sigalg,
			Integer keysize, Integer validity, String keypass, String keystore,
			String storepass, String cn, String ou, String o, String l,
			String s, String c){
		
		String ss ="keytool -genkeypair -alias "+alias+" -keyalg "+keyalg+" -sigalg "+sigalg+
			" -keypass "+keypass+" -storepass "+storepass+" -dname USERDATA"+" -storetype JCEKS";
		if(keysize != null)
			ss += " -keysize "+keysize;
		if(keystore != null)
			ss += " -keystore "+keystore;
		else if(keyStorePath != null)
			ss += " -keystore "+keyStorePath;
		/*if(storetype != null)
			ss += " -storetype "+storetype;*/
		if(validity != null)
			ss += " -validity "+validity;
		String[] array = ss.split(" ");
		// Sustituimos "USERDATA" por la informacion
		array[13] = "CN="+cn+", OU="+ou+", O="+o+", L="+l+", S="+s+", C="+c;
		
		try{
			Runtime runtime = Runtime.getRuntime();
			Process proc = runtime.exec(array);
			InputStream is = proc.getInputStream();
			proc.waitFor();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String msg = br.readLine();
			
			// Pruebas
			try{
				KeyStore ks = KeyStore.getInstance("JCEKS");

				char[] password = storepass.toCharArray();
				
				if(keystore == null)
					keystore = System.getProperty("user.home")+File.separator+".keystore";
				File keystoreFile = new File(keystore);

				FileInputStream fis = new java.io.FileInputStream(keystoreFile);

				ks.load(fis, password);

				CertAndKeyGen keypair = new CertAndKeyGen(keyalg, sigalg, null);
				X500Name x500Name = new X500Name(cn, ou, o, l, s, c);

				if(keysize != null)
					keypair.generate(keysize);
				else
					keypair.generate(1024);
				PrivateKey privKey = keypair.getPrivateKey();

				X509Certificate[] chain = new X509Certificate[1];
				if(validity == null)
					validity = 90;
				chain[0] = keypair.getSelfCertificate(x500Name, (long)validity*24*60*60);

				ks.setKeyEntry(alias, privKey, keypass.toCharArray(), chain);
				fis.close();
				ks.store(new FileOutputStream(keystoreFile), storepass.toCharArray());
				// Fin pruebas
			}catch(Exception ex){
				ex.printStackTrace();
			}
			return msg;
		}catch(Exception e){
			String error = "No se pudo ejecutar el comando: "+array+"\nMotivo: "+e.getMessage();
			return error;
		}
	}
	
	public String importar(String alias, String filein, String keypass,
			String storepass, String ks){
		String ss ="keytool -importcert -noprompt -alias "+alias+" -file "+filein+
		" -keypass "+keypass+" -storepass "+storepass+" -storetype JCEKS";
		if(ks != null)
			ss += " -keystore "+ks;
		else if(keyStorePath != null)
			ss += " -keystore "+keyStorePath;
		String[] array = ss.split(" ");
		try{
			Runtime runtime = Runtime.getRuntime();
			Process proc = runtime.exec(array);
			InputStream is = proc.getInputStream();			
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String msg;
			
			proc.waitFor();

			msg = br.readLine();

			return msg;
		}catch(Exception e){
			String error = "No se pudo ejecutar el comando: "+array+"\nMotivo: "+e.getMessage();
			return error;
		}
	}
	
	public String exportar(String alias, String fileout, String keypass,
			String storepass, String ks){
		String ss ="keytool -exportcert -alias "+alias+" -file "+fileout+
			" -keypass "+keypass+" -storepass "+storepass+" -storetype JCEKS";
		if(ks != null)
			ss += " -keystore "+ks;
		else if(keyStorePath != null)
			ss += " -keystore "+keyStorePath;
		String[] array = ss.split(" ");
		
		try{
			Runtime runtime = Runtime.getRuntime();
			//runtime.exec(array).waitFor();
			Process proc = runtime.exec(array);
			InputStream is = proc.getInputStream();
			proc.waitFor();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String msg = br.readLine();
			
			return msg;
		}catch(Exception e){
			String error = "No se pudo ejecutar el comando: "+array+"\nMotivo: "+e.getMessage();
			return error;
		}
	}

	public ArrayList<String> listaCertificados(char[] passwd, String rutaKs){
		ArrayList<String> lista = new ArrayList<String>();
		
		try{
			KeyStore ks = KeyStore.getInstance("JCEKS");

			char[] password = passwd;

			File keystoreFile = new File(rutaKs);

			FileInputStream fis = new java.io.FileInputStream(keystoreFile);

			ks.load(fis, password);
			
			Enumeration<String> aliases = ks.aliases();
			
			while(aliases.hasMoreElements()){
			   String alias = aliases.nextElement();
			   lista.add(alias);
			}

			fis.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
		return lista;
	}
	
	public String infoCertificado(char[] passwd, String rutaKs, String cert){
		String info = null;
		try{
			KeyStore ks = KeyStore.getInstance("JCEKS");

			char[] password = passwd;

			File keystoreFile = new File(rutaKs);

			FileInputStream fis = new java.io.FileInputStream(keystoreFile);

			ks.load(fis, password);
			Certificate cer = ks.getCertificate(cert);
			
			info = cer.toString();

			fis.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
			JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
		return info;
	}
	
	public String rutaKeyStore(){
		return this.keyStorePath;
	}
	
	public String firmar(String alias, char[] keypass, String rutaKs, char[] storepass, File file,
			String rutaFirma, String rutaClave){
		try{
			KeyStore ks = KeyStore.getInstance("JCEKS");
			char[] password = storepass;
			File keystoreFile = new File(rutaKs);
			FileInputStream fis = new java.io.FileInputStream(keystoreFile);
			ks.load(fis, password);
			
			// Obtenemos la clave privada y publica
			KeyPair kp = this.getKeyPair(ks, alias, keypass);
			PrivateKey priv = kp.getPrivate();
			PublicKey pub = kp.getPublic();
			
			Certificate cer = ks.getCertificate(alias);
			fis.close();
			
			// Obtenemos el tipo de algoritmo para firmar
			String cerStr = cer.toString();
			String aux = new String("Signature Algorithm: ");
			int ind = cerStr.indexOf(aux)+aux.length();
			String sigalg = cerStr.substring(ind, cerStr.indexOf(",", ind));
			
			//System.out.println(sigalg);
			
			// Firmamos el archivo
			Signature sig = Signature.getInstance(sigalg);			

			sig.initSign(priv);
			
			if(file != null){
				fis = new FileInputStream(file);
				BufferedInputStream bufin = new BufferedInputStream(fis);
				byte[] buffer = new byte[1024];
				int len;
				while (bufin.available() != 0) {
					len = bufin.read(buffer);
					sig.update(buffer, 0, len);
				};
				bufin.close();
			}else{
				return "Seleccione el fichero a firmar";
			}
			
			// Generamos la firma
			byte[] realSig = sig.sign();
			
			/* guardamos la firma en un archivo */
			if(rutaFirma != null && !rutaFirma.equalsIgnoreCase("")){
				FileOutputStream sigfos = new FileOutputStream(rutaFirma);//filename+".sig");
				sigfos.write(realSig);
				sigfos.close();
			}else{
				return "Es preciso indicar el fichero que contendrá la firma";
			}
			
			/*guardamos la llave publica en un archivo */
			if(rutaClave != null  && !rutaClave.equalsIgnoreCase("")){
				byte[] key = pub.getEncoded(); 
				FileOutputStream keyfos = new FileOutputStream(rutaClave);//filename+".pk");
				keyfos.write(key); 
				keyfos.close();
			}
		}catch(UnrecoverableKeyException uke){
			return "Contraseña incorrecta";
		}catch(Exception e){
			return e.getMessage();
		}
		
		return null;
	}
	
	private KeyPair getKeyPair(KeyStore keystore, String alias, char[] password) throws UnrecoverableKeyException, KeyStoreException, NoSuchAlgorithmException{

		// Get private key
		Key key = keystore.getKey(alias, password);
		if (key instanceof PrivateKey) {
			// Get certificate of public key
			java.security.cert.Certificate cert = keystore.getCertificate(alias);

			// Get public key
			PublicKey publicKey = cert.getPublicKey();

			// Return a key pair
			return new KeyPair(publicKey, (PrivateKey)key);
		}

		return null;
    }
	
	public String checkFirma(String alias, char[] keypass, String rutaKs, char[] storepass, File file,
			String rutaFirma, String rutaClave, String sigalg, String keyalg){
		
		try{
			PublicKey pubKey;	

			if(rutaClave != null && sigalg != null && keyalg != null){ // Con clave en fichero
				FileInputStream keyfis = new FileInputStream(rutaClave);

				byte[] encKey = new byte[keyfis.available()];  
				keyfis.read(encKey);
				keyfis.close();
				
				X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encKey);
				
				KeyFactory keyFactory = KeyFactory.getInstance(keyalg);
				
				//PublicKey pubKey = keyFactory.generatePublic(pubKeySpec);
				pubKey = keyFactory.generatePublic(pubKeySpec);

			}else if(alias != null && rutaKs != null){ // Con clave en certificado
				KeyStore ks = KeyStore.getInstance("JCEKS");
				char[] password = storepass;
				File keystoreFile = new File(rutaKs);
				FileInputStream fis = new java.io.FileInputStream(keystoreFile);
				ks.load(fis, password);
				
				// Obtenemos la clave privada y publica
				KeyPair kp = this.getKeyPair(ks, alias, keypass);
				pubKey = kp.getPublic();
				
				Certificate cer = ks.getCertificate(alias);
				fis.close();
				
				String cerStr = cer.toString();
				String aux = new String("Signature Algorithm: ");
				int ind = cerStr.indexOf(aux)+aux.length();
				sigalg = cerStr.substring(ind, cerStr.indexOf(",", ind));
				
			}else{
				return "Debe indicar un fichero con la clave y los algoritmos utilizados o " +
						"un alias y un keystore que la contengan";
			}

			if(rutaFirma != null && !rutaFirma.equalsIgnoreCase("")){
				FileInputStream sigfis = new FileInputStream(rutaFirma);

				byte[] sigToVerify = new byte[sigfis.available()];
				sigfis.read(sigToVerify);
				sigfis.close();


				Signature sig = Signature.getInstance(sigalg);
				sig.initVerify(pubKey);

				FileInputStream datafis = new FileInputStream(file);
				BufferedInputStream bufin = new BufferedInputStream(datafis);

				byte[] buffer = new byte[1024];
				int len;

				while (bufin.available() != 0) {
					len = bufin.read(buffer);
					sig.update(buffer, 0, len);
				};

				boolean verifies = sig.verify(sigToVerify);
				
				if(verifies)
					return null;
				else
					return "La firma no se corresponde";
			}else{
				return "Debe especificar el fichero que contiene la firma";
			}
		}catch(UnrecoverableKeyException uke){
			return "Contraseña incorrecta";			
		}catch(Exception e){
			return e.getMessage();
		}
	}
	
	public String checkMD(String md5, File file){
		try{
			FileInputStream fis = new FileInputStream(file);
			MessageDigest md = MessageDigest.getInstance("MD5");

			byte[] data = new byte[2048];
			int leidos;
			while ((leidos = fis.read(data)) != -1) {
				md.update(data, 0, leidos);
			}

			String md5Hex = toHexString(md.digest());
			System.out.println("El MD5 de " + file + " es: " + md5Hex);
			if(!md5.equalsIgnoreCase(md5Hex)){
				return "El resumen no se corresponde con el fichero dado, o éste está corrupto";
			}else{
				return null;
			}
		}catch(Exception e){
			return e.getMessage();
		}
	}
	
	private static void byte2hex(byte b, StringBuffer buf) {
		char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };
		int high = ((b & 0xf0) >> 4);
		int low = (b & 0x0f);
		buf.append(hexChars[high]);
		buf.append(hexChars[low]);
	}

	private static String toHexString(byte[] block) {
		StringBuffer buf = new StringBuffer();
		int len = block.length;
		for (int i = 0; i < len; i++) {
			byte2hex(block[i], buf);
			if (i < len - 1) {
				buf.append(":");
			}
		}
		return buf.toString();
	}
	
	public String cifrar(String clearfile, String ciffile, char[] pass){
		
		try{
			PBEKeySpec pbeKeySpec;
			PBEParameterSpec pbeParamSpec;
			SecretKeyFactory keyFac;

			// Salt 
			byte[] salt = new byte[8];
			SecureRandom random = new SecureRandom();
			for(int i=0; i<salt.length; i++){
				salt[i] = (byte) random.nextInt(256); 
			}

			// Iteration count
			int count = 100;

			// Create PBE parameter set
			pbeParamSpec = new PBEParameterSpec(salt, count);
			pbeKeySpec = new PBEKeySpec(pass);

			keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
			SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);

			// Create PBE Cipher
			Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");

			// Initialize PBE Cipher with key and parameters
			pbeCipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);

			File file = new File(clearfile);
			FileInputStream fis = new FileInputStream(file);

			File fileCif = new File(ciffile);
			DataOutputStream fisc = new DataOutputStream(new FileOutputStream(fileCif));
			fisc.write(salt);
			fisc.writeInt(count);

			byte[] datos = new byte[2048];
			byte[] datosAnteriores = new byte[2048];
			int leidos = -1;
			int leidosAnteriores = -1;

			while (true) {
				leidos = fis.read(datos);
				if(leidos == -1){
					if(leidosAnteriores != -1){
						fisc.write(pbeCipher.doFinal(datosAnteriores,0,leidosAnteriores));
					}
					break;
				} else {
					if(leidosAnteriores != -1){
						fisc.write(pbeCipher.update(datosAnteriores, 0, leidosAnteriores));
					} 
					datosAnteriores = datos;
					leidosAnteriores = leidos;
				}
			}

			fis.close();
			fisc.close();

		}catch(Exception e){
			return e.getMessage();
		}

		return null;
	}
	
	public String descifrar(String clearfile, String ciffile, char[] pass){

		try{
			File file = new File(ciffile);
			DataInputStream fis = new DataInputStream(new FileInputStream(file));
			byte[] salt = new byte[8];
			int totalLeidos = 0;
			int leidosSalt;
			while(totalLeidos < 8){
				leidosSalt = fis.read(salt,totalLeidos,8-totalLeidos);
				totalLeidos += leidosSalt;
			}
			int count = fis.readInt();

			// Create PBE parameter set
			PBEParameterSpec pbeParamSpec = new PBEParameterSpec(salt, count);
			PBEKeySpec pbeKeySpec = new PBEKeySpec(pass);

			SecretKeyFactory keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
			SecretKey pbeKey = keyFac.generateSecret(pbeKeySpec);

			// Create PBE Cipher
			Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");

			// Initialize PBE Cipher with key and parameters
			pbeCipher.init(Cipher.DECRYPT_MODE, pbeKey, pbeParamSpec);

			File fileCif = new File(clearfile);
			FileOutputStream fisc = new FileOutputStream(fileCif);

			byte[] datos = new byte[2048];
			byte[] datosAnteriores = new byte[2048];
			int leidos = -1;
			int leidosAnteriores = -1;

			while (true) {
				leidos = fis.read(datos);
				if (leidos == -1) {
					if (leidosAnteriores != -1) {
						fisc.write(pbeCipher.doFinal(datosAnteriores, 0,
								leidosAnteriores));
					}
					break;
				} else {
					if (leidosAnteriores != -1) {
						fisc.write(pbeCipher.update(datosAnteriores, 0,
								leidosAnteriores));
					}
					datosAnteriores = datos;
					leidosAnteriores = leidos;
				}
			}

			fis.close();
			fisc.close();
		}catch(BadPaddingException bpe){
			return "Contraseña incorrecta";
		}catch(Exception e){
			return e.getMessage();
		}
		
		return null;
	}
}

