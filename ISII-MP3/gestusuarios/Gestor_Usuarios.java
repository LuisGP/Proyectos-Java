package gestusuarios;

import java.sql.Statement;

import bbdd.BBDD_Usuarios;
import bbdd.GestorDatos;

public class Gestor_Usuarios {
	BBDD_Usuarios bbddu = null;
	
	
	
	public Gestor_Usuarios (){
		Statement state = GestorDatos.mysqlConnect();
		bbddu = new BBDD_Usuarios(state);
	}
	
	public boolean login(String usrname, String passwd){
		return (bbddu.checkUserPasswd(usrname, passwd) == null);
	}
	
	public boolean newUser(String usrname, String passwd){
		if (bbddu.existUser(usrname))
			return false;
		else if (bbddu.addUser(usrname, passwd) != null)
			return false;
		
		return true;
	}
	
	public boolean deleteUser(String usrname, String passwd){
		if (bbddu.checkUserPasswd(usrname, passwd) == null){
			bbddu.removeUser(usrname);
			return true;
		} else
			return false;
	}

	
	public String modifyUser(String usrname, String newusrname, String newpasswd){
		return this.bbddu.updateUserData(this.bbddu.getUserId(usrname), newusrname, newpasswd);

	}
	
}
