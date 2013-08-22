package gestarchivos;

import java.util.ArrayList;
import java.util.List;

import bbdd.BBDD_Archivos;

public class Gestor_Consultas {
	//TEMPORALES
	private String usuarioactivo = null;
	private BBDD_Archivos bbdda = null;
	private List<Criterio> criterios = null;
	
	public Gestor_Consultas(String usuario, BBDD_Archivos bbdd){
		this.usuarioactivo = usuario;
		this.bbdda = bbdd;
		
	}
	
	
	/** añade un criterio a la lista */
	public String anadirCriterio(String relation, String restriction, String param, String value){
		criterios.add(new Criterio( relation, restriction, param, value ));
		return null;
	}
	
	/** Devuelve la lista con las coincidencias con la busqueda */
	public List<Archivo> buscar(){
		if (criterios.size() == 0)
			return bbdda.recuperarConsulta(usuarioactivo, "TRUE");
		
		List<Archivo> resultado;
		String busqueda = "";
		for (int i=0; i<criterios.size(); i++){
			busqueda += criterios.get(i).translate(); 
		}
		resultado =  bbdda.recuperarConsulta(usuarioactivo, busqueda);
		
		criterios = new ArrayList<Criterio>();
		
		return resultado;
	}
	
	
	/** Borra el criterio de la posición id */
	public String borrarCriterio(int id){
		if (id > 0 && id < this.criterios.size())
			this.criterios.remove(id);
		else
			return "No se puede borrar el criterio solicitado";
		return null;
	}
	
	
	/** Devuelve una descripcion textual de los criterios actuales	 */
	public List<String> getCriteriosActuales(){
		List<String> resultado = new ArrayList<String>();
		for (int i=0; i<this.criterios.size(); i++)
			resultado.add(this.criterios.get(i).translate());
		
		return resultado;
		
	}
	
	
}
