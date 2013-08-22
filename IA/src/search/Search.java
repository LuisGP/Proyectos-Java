package search;

import java.util.ArrayList;

import representation.Node;

public interface Search {
	public ArrayList<Node> getSoluciones();
	public Node solve();
	public ArrayList<Node> solveAll();
}
