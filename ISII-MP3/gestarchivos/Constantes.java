package gestarchivos;



public final class Constantes {
	public static final String EQ = "==";
	public static final String GRT = ">";
	public static final String LESS = "<";
	public static final String NOT = "NOT";
	public static final String AND = "AND";
	public static final String OR = "OR";
	
	public static final String M3U = "M3U";
	public static final String PLS = "PLS";
	
	public static final String RUTA = "ruta";
	public static final String TITULO = "titulo";
	public static final String AUTOR = "autor";
	public static final String ALBUM = "album";
	public static final String GENERO = "genero";
	public static final String ANYO = "anyo";

	
	
	
	public static String[] getRelaciones(){
		String[] rels = {EQ,GRT,LESS,NOT,AND,OR};
		return rels;
	}
	
	public static String[] getTiposLista(){
		String[] rels = {M3U,PLS};
		return rels;
	}
	
	public static String[] getTiposAtrib(){
		String[] rels = {RUTA,TITULO,AUTOR,ALBUM,GENERO,ANYO};
		return rels;
	}
}
