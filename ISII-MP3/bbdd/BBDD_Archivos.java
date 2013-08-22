package bbdd;

import gestarchivos.Archivo;
import gestarchivos.Constantes;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class BBDD_Archivos {
	private Statement state;
	
	public BBDD_Archivos(Statement state){
		this.state = state;
	}
	
	/* La consulta se generará a raiz de una serie de criterios
	 * combiados con AND, OR o NOT, para atacar a la BBDD.
	*/
	public List<Archivo> recuperarConsulta(String userid, String consulta){
		List<Archivo> al = new ArrayList<Archivo>();
		
		String sql = "SELECT * FROM archivos WHERE propietario='"+userid+"' AND ("+consulta+");";
		
		try {
            ResultSet res = state.executeQuery(sql);

            while (res.next()) {
            	Archivo temp = new Archivo(res.getString(Constantes.RUTA),
            			res.getString(Constantes.TITULO),
            			res.getString(Constantes.AUTOR),
            			res.getString(Constantes.ALBUM),
            			res.getString(Constantes.GENERO),
            			res.getInt(Constantes.ANYO));
            	al.add(temp);
            }
          
            return al;
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null,e.getMessage(),"Consulta erronea",JOptionPane.INFORMATION_MESSAGE);
        }
		
		return null;
	}
	
	/* Devolvemos la ruta del archivo, no basta con leerla de a, ya que
	 * seguramente no la posea, debemos recuperarla a partir de la información de
	 * a y del usuario actual. 
	*/
	public String conseguir(String userid, Archivo a){
		String sql = "SELECT * FROM archivos WHERE titulo='"+a.getTitulo()+"' AND propietario='"+userid+"';";
		
		try {
            ResultSet res = state.executeQuery(sql);

            if(res.next()){
            	String ruta = res.getString("ruta");
        		a.setRuta(res.getString(ruta));
        		return ruta;
            }else{
            	return null;
            }

        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null,e.getMessage(),"Consulta erronea",JOptionPane.INFORMATION_MESSAGE);
        }
        
		return null;
	}
	
	public String modificar(String userid, Archivo antiguo, Archivo nuevo){
		String sql = "UPDATE archivos SET titulo='"+nuevo.getTitulo()+"',autor='"+nuevo.getAutor()+
			"',album='"+nuevo.getAlbum()+"',anyo="+nuevo.getAnyo()+",genero='"+nuevo.getGenero()+"',ruta='"+
			nuevo.getRuta()+"' "+
			"WHERE propietario='"+userid+"' AND titulo='"+antiguo.getTitulo()+"';";
		
		try {
			state.execute(sql);
			
			// Actualizamos tambien el propio objeto
			antiguo.setTitulo(nuevo.getTitulo());
			antiguo.setAutor(nuevo.getAutor());
			antiguo.setAlbum(nuevo.getAlbum());
			antiguo.setAnyo(nuevo.getAnyo());
			antiguo.setGenero(nuevo.getGenero());
			antiguo.setRuta(nuevo.getRuta());
			
			return null;
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null,e.getMessage(),"Error en UPDATE",JOptionPane.INFORMATION_MESSAGE);
        }
        
        return "Cancion no encontrada";
	}
	
	public String agregar(String userid, Archivo a){
		String sql = "INSERT INTO archivos VALUES('"+userid+"','"+a.getTitulo()+"','"+a.getAutor()+"'," +
				"'"+a.getAlbum()+"',"+a.getAnyo()+",0,'"+a.getGenero()+"','"+a.getRuta()+"');";

		try {
			state.execute(sql);
			return null;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Consulta erronea",JOptionPane.INFORMATION_MESSAGE);
		}
    	
    	return "Error insertando";
	}
	
	public String eliminar(String userid, Archivo a){
		String sql = "DELETE FROM archivos WHERE titulo='"+a.getTitulo()+"' AND propietario='"+userid+"';";
		
		try {
			state.execute(sql);
			return null;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"Consulta erronea",JOptionPane.INFORMATION_MESSAGE);
		}
		
		return "No existe tal fichero";
	}
}
