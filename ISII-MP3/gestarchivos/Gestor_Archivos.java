package gestarchivos;

import java.io.File;
import java.io.IOException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.blinkenlights.jid3.ID3Exception;
import org.blinkenlights.jid3.MP3File;
import org.blinkenlights.jid3.v1.ID3V1Tag;
import org.blinkenlights.jid3.v2.ID3V2Tag;

import bbdd.BBDD_Archivos;
import bbdd.GestorDatos;


public class Gestor_Archivos {
	String usuarioactivo = null;
	BBDD_Archivos bbdda = null;
	List<Archivo> lista_archivos = null;
	Gestor_Consultas gc = null;

		
	
		/** CONSTRUCTOR */
		public Gestor_Archivos (String usuario) {
			this.usuarioactivo = usuario;
			Statement state = GestorDatos.mysqlConnect();
			this.bbdda =  new BBDD_Archivos(state);
			this.gc = new Gestor_Consultas(this.usuarioactivo, this.bbdda);
			
		}
	
	
		/** Crea el archivo a partir de su ruta, sacando los ID3 de el. Si lo quieres modificar luego, usa el "modificarArchivos" */
		public String nuevoArchivo(String ruta){
			String titulo = ruta;
			String autor = "";
			String album = "";
			String genero = "";
			int anyo = 0;
			
			File oSourceFile = new File(ruta);
			if (!oSourceFile.exists()){
				return "La ruta especificada no es valida";
			}
	        MP3File oMP3File = new MP3File(oSourceFile);
	        try {
				ID3V2Tag tag = oMP3File.getID3V2Tag();
				album = tag.getAlbum();
				titulo = tag.getTitle();
				autor = tag.getArtist();
				genero = tag.getGenre();
				anyo = tag.getYear();
			} catch (ID3Exception e) {
					try {
						ID3V1Tag tag = oMP3File.getID3V1Tag();
						album = tag.getAlbum();
						titulo = tag.getTitle();
						autor = tag.getArtist();
						genero = tag.getGenre().toString();
						anyo = new Integer(tag.getYear());
					} catch (ID3Exception e1){}
			}
			Archivo a = new Archivo(ruta,titulo,autor,album,genero,anyo);
			this.lista_archivos.add(a);
			return (this.bbdda.agregar(this.usuarioactivo, a));
		}
		
		
		
		/** PASA UN HASHMAP CON LOS DATOS QUE VA A CONTENER - Mejor usarlo pa cuando el archivo no existe  */
		public String agregarArchivo(HashMap<String,String> hm){
			String ruta = hm.get(Constantes.RUTA);
			String titulo = "";
			String autor = "";
			String album = "";
			String genero = "";
			int anyo = 0;
			
			if (!hm.containsKey(Constantes.TITULO))
				return "Es necesario introducir el titulo de la cancion"; 
			
			File oSourceFile = new File(ruta);
			if (oSourceFile.exists()){	// si existe el archivo sacar ID3
				MP3File oMP3File = new MP3File(oSourceFile);
	            try {
					ID3V2Tag tag = oMP3File.getID3V2Tag();
					album = tag.getAlbum();
					titulo = tag.getTitle();
					autor = tag.getArtist();
					genero = tag.getGenre();
					anyo = tag.getYear();
				} catch (ID3Exception e) {
					try {
						ID3V1Tag tag = oMP3File.getID3V1Tag();
						album = tag.getAlbum();
						titulo = tag.getTitle();
						autor = tag.getArtist();
						genero = tag.getGenre().toString();
						anyo = new Integer(tag.getYear());
					} catch (ID3Exception e1){}
				}
			}
			Archivo a = new Archivo(hm.get(Constantes.RUTA),titulo,autor,album,genero,anyo);
			Iterator it = hm.entrySet().iterator();
			while (it.hasNext()){
				String s = (String)it.next();
				String mod = this.modificarArchivo(a, s, hm.get(s));
				if (mod != null)
					return mod;
			}
			return null;
		}
		
		
		/** Elimina los archivos seleccionados */
		public String eliminarArchivos(List<Integer> ids){
			for (int i=0; i<ids.size(); i++){
				String s = bbdda.eliminar(this.usuarioactivo, this.lista_archivos.get(ids.get(i)));
				if ( s == null )
					this.lista_archivos.remove(ids.get(i));
				else
					return s;
			}
			return "";
		}

		
		/** Reproduce el archivo si se usa Windows o Mac */
		public String reproducirArchivo(int id){ 
			if (this.lista_archivos.get(id).existe()) {
				String f = this.lista_archivos.get(id).getRuta();
				try {
					String so = System.getProperty("os.name");
					if (so.length() > 6){
						if (so.substring(0, 6).equals("Windows")){
							Runtime.getRuntime().exec("cmd.exe /c start " + f);
							return null;
						} else if (so.substring(0, 2).equals("Mac")){
							Runtime.getRuntime().exec("open " + f);
							return null;
						}
					} else 
						return "La funcion no es compatible con el sistema operativo actual";					
				} catch (IOException e) {
					return "No se puede reproducir el archivo";
				}
			}
			return "El archivo no existe";
		}
		
		
		
		/** LAS IDS SON LA POSICION EN LA LISTA DE LOS ARCHIVOS A MODIFICAR Y EL HASHMAP DEL TIPO <"titulo","NUEVO TITULO"> ...*/
		public String modificarArchivos(List<Integer> ids, HashMap<String,String> datos ){
			for (int i=0; i<ids.size(); i++){
				Archivo a = lista_archivos.get(ids.get(i));
				Iterator it = datos.entrySet().iterator();
				while (it.hasNext()){
					String s = (String)it.next();
					String mod = this.modificarArchivo(a, s, datos.get(s));
					if ( mod != null )
						return mod;
				}
					
			}
			return null;
		}
		
		
		/** Interaccion con la clase Archivo */
		private String modificarArchivo(Archivo a, String campo, String valor){
			Archivo a2 = a.copiar();
			String s = null;
			if (campo.equals(Constantes.RUTA))
				a2.setRuta(valor);
			else if (campo.equals(Constantes.TITULO))
				a2.setTitulo(valor);
			else if (campo.equals(Constantes.AUTOR))
				a2.setAutor(valor);
			else if (campo.equals(Constantes.ALBUM))
				a2.setAlbum(valor);
			else if (campo.equals(Constantes.GENERO))
				a2.setGenero(valor);
			else if (campo.equals(Constantes.ANYO))
				a2.setAnyo(Integer.getInteger(valor));
				else
					return "Nombre de atributo desconocido: "+campo;
			s = bbdda.modificar(this.usuarioactivo, a, a2);
			a = a2;
			return s;
		}
		
		
		/** Crea una lista de reproducción a partir de las canciones escogidas (si no se elige ninguna, todas)
		 *  del formato que se le pase "M3U" o "PLS"
		 *   en el archivo 'destino' */
		public void crearListaRepr( List<Integer> canciones, String format, String destino ){
			List<Archivo> archivos = new ArrayList<Archivo>();
			if (canciones.size() == 0)
				archivos = this.lista_archivos;
			for ( int i=0; i<canciones.size(); i++ )
				archivos.add(this.lista_archivos.get(canciones.get(i)));
			
			Playlist pl = null;
			if ( format.equals(Constantes.M3U) )
				pl = new M3U_Playlist();
			else if (format.equals(Constantes.PLS))
				pl = new PLS_Playlist();
			
			pl.generar(archivos, new File(destino));
		}
		
		
		/** Devuelve una representacion textual de la lista de archivos */
		public List<String> getLista(){
			List<String> resultado = new ArrayList<String>();
			for (int i=0; i<this.lista_archivos.size(); i++)
				resultado.add(this.lista_archivos.get(i).toString());
			
			return resultado;
		}
		
		
		/** AÑADIR UN NUEVO CRITERIO DE BUSQUEDA */
		public String anadirCriterio(String relation, String restriction, String param, String value){
			return anadirCriterio(relation, restriction, param, value);
		}
		
		/** LLAMA A ESTE METODO PARA BORRAR UN CRITERIO EN CONCRETO */
		public String borrarCriterio(int i){
			return this.gc.borrarCriterio(i);
		}
		
		/** EFECTUA LA BUSQUEDA Y ACTUALIZA LA LISTA DE ARCHIVOS */
		public void buscar(){
			this.lista_archivos = this.gc.buscar();
		}
		
		/** DEVUELVE LA INFORMACION QUE SEA COMUN EN TODOS LOS ARCHIVOS ELEGIDOS EN ESTE MOMENTO */
		public HashMap<String,String> getInfo (List<Integer> ids){
			HashMap<String,String> hm = new HashMap<String,String>();
			String temp;
			
			temp = this.lista_archivos.get(0).getAlbum();
			for (int i=1; i<ids.size(); i++)
				if (this.lista_archivos.get(i).getAlbum() != temp){
					temp = "";
					break;
				}
			hm.put(Constantes.ALBUM, temp);
			temp = this.lista_archivos.get(0).getAutor();
			for (int i=1; i<ids.size(); i++)
				if (this.lista_archivos.get(i).getAutor() != temp){
					temp = "";
					break;
				}
			hm.put(Constantes.AUTOR, temp);
			temp = this.lista_archivos.get(0).getGenero();
			for (int i=1; i<ids.size(); i++)
				if (this.lista_archivos.get(i).getGenero() != temp){
					temp = "";
					break;
				}
			hm.put(Constantes.GENERO, temp);
			temp = this.lista_archivos.get(0).getRuta();
			for (int i=1; i<ids.size(); i++)
				if (this.lista_archivos.get(i).getRuta() != temp){
					temp = "";
					break;
				}
			hm.put(Constantes.RUTA, temp);
			temp = this.lista_archivos.get(0).getTitulo();
			for (int i=1; i<ids.size(); i++)
				if (this.lista_archivos.get(i).getTitulo() != temp){
					temp = "";
					break;
				}
			hm.put(Constantes.TITULO, temp);
			int temp2 = this.lista_archivos.get(0).getAnyo();
			for (int i=1; i<ids.size(); i++)
				if (this.lista_archivos.get(i).getAnyo() != temp2){
					temp = null;
					break;
				}
			if (temp==null)
				hm.put(Constantes.ANYO, "");
			else
				hm.put(Constantes.ANYO, String.valueOf(temp2));
			
			return hm;
		}
		
}
