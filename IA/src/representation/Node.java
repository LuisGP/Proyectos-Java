package representation;

import java.util.ArrayList;

public interface Node {
	public ArrayList<Node> expand();
	public void setPadre(Node padre);
}
