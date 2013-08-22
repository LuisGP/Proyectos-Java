package bbdd;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MysqlConfig {
	public static String user;// = "root";
	public static String password;// = "pisi";
	public static String server;// = "localhost";
	public static String db_name;// = "isii";

	static {
		String configfile = "bbdd-config.xml";
		Element raiz = null;
		File fichero = null;

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = null;
		
		try {
			db = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			System.err.println("Error al crear el DocumentBuilder");
		}
		
		fichero = new File(configfile);
		try {
			raiz = db.parse(fichero).getDocumentElement();
		} catch (Exception e) {
			System.err.println("Error al analizar el fichero " + configfile);
		}
		
		if (!raiz.getNodeName().equalsIgnoreCase("bbdd-config")) {
			System.err.println("Error en " + configfile + ": No sigue el formato");
		}
		
		/* Hasta aqui todo bien, ahora recuperamos datos */
		
		NodeList nodos = raiz.getChildNodes();
		
		for (int i = 0; i < nodos.getLength(); i++) {
			Node nodo = nodos.item(i);
			String nombre = nodo.getNodeName();
			String value = null;

			if (nodo.getFirstChild() != null)
				value = nodo.getFirstChild().getNodeValue();
			if (nombre.equalsIgnoreCase("user"))
				user = value;
			if (nombre.equalsIgnoreCase("password"))
				password = value;
			if (nombre.equalsIgnoreCase("server"))
				server = value;
			if (nombre.equalsIgnoreCase("name"))
				db_name = value;
		}
	}
}
