package gestarchivos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;


public class M3U_Playlist implements Playlist {

	public void generar(List<Archivo> lista, File fichero) {
		try {
			PrintStream ps = new PrintStream(new FileOutputStream(fichero));
			ps.println("#EXTM3U");
			for(int i = 0; i < lista.size(); i++){
				ps.println(lista.get(i).getRuta());
			}
			
			ps.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

}
