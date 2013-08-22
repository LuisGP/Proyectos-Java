package gestarchivos;

class Criterio {
//	public static final String EQ = "==";
//	public static final String GRT = ">";
//	public static final String LESS = "<";
//	public static final String NOT = "NOT";
//	public static final String AND = "AND";
//	public static final String OR = "OR";
	
	private String relacion;
	private String restriccion;
	private String propiedad;
	private String valor;
	
	public Criterio (String relation, String restriction, String param, String value){
		this.relacion = relation;
		this.restriccion = restriction;
		this.propiedad = param;
		this.valor = value;
	}
	
	public String translate(){
		String s = "";
		s += relacion + "("+propiedad+" "+restriccion+" "+valor+")";
		return s;
	}
}
