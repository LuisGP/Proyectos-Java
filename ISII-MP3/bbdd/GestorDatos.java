package bbdd;

import java.sql.*;

import javax.swing.JOptionPane;

public class GestorDatos {

	public static Statement mysqlConnect(){
		 
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        	JOptionPane.showMessageDialog(null,"Java jdbc driver no encontrado!","Driver Error",JOptionPane.ERROR_MESSAGE);
        }

        Connection con = null;
        Statement state = null;

        String db = "jdbc:mysql://"+MysqlConfig.server+"/"+MysqlConfig.db_name;
        String user = MysqlConfig.user;
        String pass = MysqlConfig.password;

        try {
            con = DriverManager.getConnection(db, user, pass);
            
            try {
            	state = con.createStatement();
            } catch (SQLException e) {
            	JOptionPane.showMessageDialog(null,"No se pudo crear statement","Error",JOptionPane.ERROR_MESSAGE);
            }
            
            JOptionPane.showMessageDialog(null,"Conexión establecida!","OK",JOptionPane.INFORMATION_MESSAGE);
            
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(null,"No se pudo crear una conexion con la base de datos!","Connection error",JOptionPane.ERROR_MESSAGE);
        }
        
        return state;
	}
}
