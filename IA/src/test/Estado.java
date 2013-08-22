package test;

import java.util.ArrayList;

import representation.Node;

public class Estado implements representation.Node{
	public int numerito;
	private Estado padre;

	public Estado(int n){
		numerito = n;
	}
	
	public ArrayList<Node> expand() {
		ArrayList<Node> aux = new ArrayList<Node>();
		aux.add(new Estado(numerito+1));
		aux.add(new Estado(numerito+2));
		return aux;
	}

	public void setPadre(Node padre) {
		this.padre = (Estado)padre;
	}
	
	public String toString(){
		return new String((new Integer(numerito)).toString());
	}

}
