package search.general.method;

import java.util.ArrayList;

import representation.Node;

public class Depth implements Method {

	public void insert(Node n, ArrayList<Node> open) {
		open.add(0, n);
	}

}
