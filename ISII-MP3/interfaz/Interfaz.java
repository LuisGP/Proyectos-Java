package interfaz;

import gestusuarios.Gestor_Usuarios;

public class Interfaz {
	public static void main(String[] args) {
		new DialogoLogin(new Gestor_Usuarios());
	}
}
