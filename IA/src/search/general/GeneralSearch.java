package search.general;

import java.util.ArrayList;

import problem.Problem;

import representation.Node;
import search.Search;
import search.general.method.Method;

public class GeneralSearch implements Search{
	private Method metodo;
	private ArrayList<Node> abierta = new ArrayList<Node>();
	private Node inicial;
	private Problem p;
	
	private ArrayList<Node> soluciones = new ArrayList<Node>();
	
	public GeneralSearch(Method m, Problem p){
		metodo = m;
		inicial = p.getInicial();
		this.p = p;
	}
	
	public ArrayList<Node> getSoluciones() {
		return soluciones;
	}
	public Node getInicial() {
		return inicial;
	}
	public void setInicial(Node inicial) {
		this.inicial = inicial;
	}
	public ArrayList<Node> getAbierta() {
		return abierta;
	}
	public void setAbierta(ArrayList<Node> abierta) {
		this.abierta = abierta;
	}
	public Method getMetodo() {
		return metodo;
	}
	public void setMetodo(Method metodo) {
		this.metodo = metodo;
	}
	
	public Node solve(){
		Node nodo, n;
		ArrayList<Node> sucesores;
		abierta.add(inicial);
		do{
			if(abierta.isEmpty()){
				return null;
			}
			nodo = abierta.remove(0);
			if(p.isSolution(nodo)){
				soluciones.add(nodo);
				return nodo;
			}
			sucesores = nodo.expand();
			for(int i = 0; i < sucesores.size(); i++){
				n = sucesores.get(i);
				n.setPadre(nodo);
				metodo.insert(n, abierta);
			}
		}while(true);
	}
	
	public ArrayList<Node> solveAll(){
		return null;
	}
	
}
