package principal;

import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import bbdd.BBDD_Archivos;
import bbdd.BBDD_Usuarios;
import bbdd.GestorDatos;
import gestarchivos.Archivo;
import gestarchivos.Playlist;

import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class Prueba {
	static public void main(String[] args){
		Statement state = GestorDatos.mysqlConnect();
		/*
		if(state != null){
			JOptionPane.showMessageDialog(null,"TODO OK","OK",JOptionPane.INFORMATION_MESSAGE);
			
			String sql = "INSERT INTO usuarios VALUES('1','Luis','jeje');";

			try {
				state.execute(sql);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null,e.getMessage(),"Consulta erronea",JOptionPane.INFORMATION_MESSAGE);
			}
			
		}else{
			JOptionPane.showMessageDialog(null,"TRATA DE ARRANCARLO CARLOS","LA CAGAMOS LUIS",JOptionPane.ERROR_MESSAGE);
		}*/
		
		BBDD_Usuarios users = new BBDD_Usuarios(state);
		BBDD_Archivos files = new BBDD_Archivos(state);
		
		/*String salida = users.checkUserPasswd("Luis","jeje");
		if(salida == null){
			System.out.println("OK!");
		}else{
			System.out.println(salida);
		}
		
		boolean existe = users.existUser("Luis");
		if(existe){
			System.out.println("EXISTE");
		}else{
			System.out.println("NO EXISTE");
		}
		
		String add = users.addUser("Ruben","ruben");
		
		if(add == null){
			System.out.println("AÑADIDO!!");
		}else{
			System.out.println(add);
		}
		
		String rem = users.removeUser("Pepe");
		
		if(rem == null){
			System.out.println("ELIMINADO!!");
		}else{
			System.out.println(rem);
		}
		*/
		String mod = users.updateUserData("5","Luis","luis");
		
		if(mod == null){
			System.out.println("MODIFICADO!!");
		}else{
			System.out.println(mod);
		}
		
		
		// Hay que escapar de JAVA y de MySQL "\" == "\\\\"
		Archivo a = new Archivo("E:"+File.separator+File.separator+"Musica"+File.separator+File.separator+
				"La Oreja De Van Gogh - Guapa (2006) - Pop [www.torrentazos.com]"+File.separator+File.separator+
				"01 - La Oreja De Van Gogh - Alguna De Mis Noches - www.torrentazos.com.mp3",
				"La Oreja De Van Gogh - Alguna De Mis Noches","La Oreja De Van Gogh",null,null,2005);
		/*		
		String add = files.agregar("5",a);
		
		if(add == null){
			System.out.println("AÑADIDA Cancion!");
		}else{
			System.out.println(add);
		}*/
		
		List<Archivo> lista = new ArrayList<Archivo>();
		lista.add(a);
		
		File m3u = new File("C:"+File.separator+"Documents and Settings"+File.separator+"LuisGP"+File.separator+"Escritorio"+File.separator+"prueba.m3u");
		File pls = new File("C:"+File.separator+"Documents and Settings"+File.separator+"LuisGP"+File.separator+"Escritorio"+File.separator+"prueba.pls");
		
		//Playlist.generarM3U(lista,m3u);
		//Playlist.generarPLS(lista,pls);
	}
}
