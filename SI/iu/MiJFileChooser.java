package iu;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

public class MiJFileChooser extends JFileChooser{
	private JFrame jf = null;
	private DestinoFichero df = null;
	public static int CER = 0;
	public static int KEYSTORE = 1;
	public static int ALL = 2;
	
	public MiJFileChooser(JFrame jf, DestinoFichero root, int tipo){
		this.jf = jf;
		this.df = root;
		if(tipo == CER){
			this.addChoosableFileFilter(new FiltroCer());
		}else if(tipo == KEYSTORE){
			this.addChoosableFileFilter(new FiltroKS());
		}
	}
	
	public void approveSelection() {
		if(df != null)
			df.setFichero(this.getSelectedFile());
		//System.out.println(fichero);
		jf.setVisible(false);
	}
	
	public void cancelSelection(){
		jf.setVisible(false);
	}
}

class FiltroCer extends FileFilter{

	@Override
	public boolean accept(File f) {
		String nombre = f.getName();
		if(nombre.contains(".cer"))
			return true;
		if(f.isDirectory())
			return true;
		return false;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Ficheros de Certificado (*.cer)";
	}
	
}

class FiltroKS extends FileFilter{

	@Override
	public boolean accept(File f) {
		String nombre = f.getName();
		if(nombre.contains("keystore"))
			return true;
		if(f.isDirectory())
			return true;
		return false;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Ficheros de KeyStore Java JCEKS (*.keystore)";
	}
	
}
