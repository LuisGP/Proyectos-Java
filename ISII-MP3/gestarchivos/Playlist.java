package gestarchivos;

import java.io.File;
import java.util.List;


public interface Playlist {

	public void generar(List<Archivo> lista, File fichero); /**{

	
	public static void generarM3U(List<Archivo> lista, File fichero){
		try {
			PrintStream ps = new PrintStream(new FileOutputStream(fichero));
			ps.println("#EXTM3U");
			for(int i = 0; i < lista.size(); i++){
				//ps.println("#EXTINF:"+lista.get(i).getDuracion()+","+lista.get(i).getTitulo());
				ps.println(lista.get(i).getRuta());
			}
			
			ps.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}*/
}
