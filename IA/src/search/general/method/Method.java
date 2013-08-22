package search.general.method;

import java.util.ArrayList;

import representation.Node;

public interface Method {
	public void insert(Node n, ArrayList<Node> open);
}
