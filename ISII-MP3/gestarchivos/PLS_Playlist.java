package gestarchivos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;


public class PLS_Playlist implements Playlist {

	public void generar(List<Archivo> lista, File fichero) {
		try {
			PrintStream ps = new PrintStream(new FileOutputStream(fichero));
			ps.println("[playlist]");
			
			for(int i = 0; i < lista.size(); i++){
				ps.println("File"+(i+1)+"="+lista.get(i).getRuta());
				ps.println("Title"+(i+1)+"="+lista.get(i).getTitulo());
			}
			
			ps.println("NumberOfEntries="+lista.size());
			ps.println("Version=2");
			
			ps.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
