package bbdd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class BBDD_Usuarios {
	private Statement state;
	
	public BBDD_Usuarios(Statement state){
		this.state = state;
	}
	
	public String getUserId(String user){
		String sql = "SELECT * FROM usuarios WHERE nombre='"+user+"';";
		
		try {
            ResultSet res = state.executeQuery(sql);

            if(res.next()) {
        		return res.getString("id");
            }

        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null,e.getMessage(),"Consulta erronea",JOptionPane.INFORMATION_MESSAGE);
        }
        
		return null;
	}
	
	public String checkUserPasswd(String user, String passwd){
		String sql = "SELECT * FROM usuarios WHERE nombre='"+user+"';";
		
		try {
            ResultSet res = state.executeQuery(sql);

            while(res.next()) {
        		if(res.getString("password").compareTo(passwd) == 0){
        			return null;
        		}
        		else{
        			return "Contraseña o usuario incorrectos";
        		}
            }

        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null,e.getMessage(),"Consulta erronea",JOptionPane.INFORMATION_MESSAGE);
        }
        
		return "Contraseña o usuario incorrectos";
	}
	
	public boolean existUser(String user){
		String sql = "SELECT * FROM usuarios WHERE nombre='"+user+"';";
		
		try {
            ResultSet res = state.executeQuery(sql);

            if(res.next()){
        		return true;
            }else{
            	return false;
            }

        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null,e.getMessage(),"Consulta erronea",JOptionPane.INFORMATION_MESSAGE);
        }
        
		return false;
	}
	
	/* El id lo generamos aqui */
	public String addUser(String user, String passwd){
		String sql = "SELECT * FROM usuarios;";
		long users = 1;
		long aux;
		
		try {
            ResultSet res = state.executeQuery(sql);

            while(res.next()){
        		aux = Long.parseLong(res.getString("id"));
        		if(aux > users)
        			users = aux;
            }
            users++;

        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null,e.getMessage(),"Consulta erronea",JOptionPane.INFORMATION_MESSAGE);
        }
        
        if(!existUser(user)){        	
        	sql = "INSERT INTO usuarios VALUES('"+users+"','"+user+"','"+passwd+"');";

			try {
				state.execute(sql);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null,e.getMessage(),"Consulta erronea",JOptionPane.INFORMATION_MESSAGE);
			}
        	
        	return null;
        }else{
        	return "El usuario ya existe";
        }
	}
	
	/* Se supone autentificado previamente */
	public String removeUser(String user){
		if(existUser(user)){		
			String sql = "DELETE FROM usuarios WHERE nombre='"+user+"';";
			
			try {
				state.execute(sql);
				return null;
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null,e.getMessage(),"Consulta erronea",JOptionPane.INFORMATION_MESSAGE);
			}
		}
		return "Usuario no Existe";
	}
	
	/* Podemos cambiar nombre y contraseña, siempre que no haya otro usuario con el mismo nombre */
	public String updateUserData(String id, String user, String passwd){
		if(existUser(user)){		
			String sql = "SELECT * FROM usuarios WHERE nombre='"+user+"';";
			
			try {
				ResultSet res = state.executeQuery(sql);
				
				if(res.next()){
					String ident = res.getString("id");
					if(ident.compareToIgnoreCase(id) == 0){
						sql = "UPDATE usuarios SET id='"+id+"',nombre='"+user+
						"',password='"+passwd+"' WHERE id='"+id+"';";
						
						try {
							state.execute(sql);
							return null;
				        } catch (SQLException e) {
				        	JOptionPane.showMessageDialog(null,e.getMessage(),"Error en UPDATE",JOptionPane.INFORMATION_MESSAGE);
				        }
				        
					}else{
						return "El nombre de usuario dado ya existe";
					}					
				}else{
					return "Ocurrió un error con su identificador";
				}
				
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null,e.getMessage(),"ERROR EN SELECT",JOptionPane.INFORMATION_MESSAGE);
			}
		}
		return "Usuario ya Existe";
	}
}
