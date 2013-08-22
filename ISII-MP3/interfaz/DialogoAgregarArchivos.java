package interfaz;

import java.io.File;

import gestarchivos.Gestor_Archivos;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DialogoAgregarArchivos extends JFileChooser {
	private static final long serialVersionUID = 2008621529914715502L;
	private Gestor_Archivos gestor;

	public DialogoAgregarArchivos(JFrame padre, Gestor_Archivos gestor) {
		super("Agregue todos los archivos que quiera y pulse \"Cancelar\"");
		this.gestor = gestor;

		//this.setApproveButtonText("Añadir");
		this.setFileFilter(new MP3FileFilter());
		this.setMultiSelectionEnabled(true);
		this.showDialog(padre, "Añadir");
	}

	public void approveSelection() {
		File[] files = this.getSelectedFiles();

		for (int i = 0; i < files.length; i++)
			//this.gestor.nuevoArchivo(files[i].getAbsolutePath());
			JOptionPane.showMessageDialog(this, files[i].getAbsolutePath());
	}
}
