package test;

import problem.Problem;
import representation.Node;
import search.Search;
import search.general.GeneralSearch;
import search.general.method.Depth;
import search.general.method.Method;
import search.general.method.Width;

public class Test implements Problem {
	Estado inicial;
	Search busq;
	Estado sol;
	
	public boolean isSolution(Node nodo) {
		if(((Estado)nodo).numerito >= 17){
			return true;
		}
		return false;
	}

	public Node getInicial() {
		return inicial;
	}
	
	public Test(Method m){
		inicial = new Estado(0);
		busq = new GeneralSearch(m,this);
		
		sol = (Estado)busq.solve();
		
		System.out.println(sol.toString());
	}

	public static void main(String args[]){
		new Test(new Depth());
		System.out.println();
		new Test(new Width());
	}
}
