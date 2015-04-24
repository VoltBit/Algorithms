/**
 * Proiectarea Algoritmilor, 2013
 * Lab 8: Drumuri minime
 *
 * @author 	Emma Sevastian
 * @email	emma.sevastian@gmail.com
 */

package graph;

public class Node {

	String _city;
	int _id;
	boolean _visited;

	public Node(String city, int id)
	{
		_city = city;
		_id   = id;
		_visited = false;
	}

	public Node(int id)
	{
		_city = "";
		_id = id;
	}

	public String getCity()
	{
		return _city;
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

		if( _city.length() != 0 ) res += _city + " , ";
		return res += (_id);
	}

	public void reset()
	{
		_visited = false;
	}
}
