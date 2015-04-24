/**
 * Proiectarea Algoritmilor, 2013
 * Lab 9: Arbori minimi de acoperire
 *
 * @author 	Alexandru Tudorica
 * @email	tudalex@gmail.com
 */
package graph;

public class Node {

	int _id;
	boolean _visited;

	public Node(int id)
	{
		_id = id;
	}

	public int getId()
	{
		return _id;
	}

	public boolean isVisited()
	{
		return _visited;
	}

	public void visit()
	{
		_visited = true;
	}

	public String toString()
	{
		String res = "Node : ";

		return res += (_id);
	}

	public void reset()
	{
		_visited = false;
	}
}
