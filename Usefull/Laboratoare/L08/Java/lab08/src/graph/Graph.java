/**
 * Proiectarea Algoritmilor, 2013
 * Lab 8: Drumuri minime
 *
 * @author 	Emma Sevastian
 * @email	emma.sevastian@gmail.com
 */

package graph;

import java.util.ArrayList;
import java.util.Scanner;
import graph.Node;

public class Graph {

	public static final int MAX_VALUE = 9999;
	ArrayList< Node > Nodes;
	ArrayList< ArrayList< Pair< Node, Integer > > > Edges;

	public Graph()
	{
		Nodes = new ArrayList<Node>();
		Edges = new ArrayList< ArrayList< Pair< Node, Integer > > >();
	}

	public int nodeCount()
	{
		return Nodes.size();
	}

	public void insertEdge(Node node1, Node node2, Integer cost)
	{
		Edges.get(node1.getId()).add(new Pair< Node, Integer >(node2, cost));
	}

	public void insertNode(Node node)
	{
		Nodes.add(node) ;
		Edges.add(new ArrayList< Pair< Node, Integer > >());
	}

	public ArrayList< Node > getNodes()
	{
		return Nodes;
	}

	public int getNode(String city) {

		for (int i = 0; i < Nodes.size(); i++)
			if (Nodes.get(i).getCity().equals(city))
				return i;
		return -1;
	}

	public ArrayList< Pair< Node, Integer > > getEdges(Node node)
	{
		return Edges.get(node.getId());
	}

	public boolean existsEdgeBetween(Node node1, Node node2)
	{
		ArrayList< Pair< Node, Integer > > edges = Edges.get(node1.getId());
		for (int i = 0; i < edges.size(); i++)
			if (edges.get(i).getFirst().getId() == node2.getId())
				return true;

		return false;
	}

	public int getCostBetween(Node node1, Node node2)
	{
		ArrayList< Pair< Node, Integer > > edges = Edges.get(node1.getId());
		for (int i = 0; i < edges.size(); i++)
			if (edges.get(i).getFirst().getId() == node2.getId())
				return edges.get(i).getSecond();
		return MAX_VALUE;
	}

	/**
	 * Function to read all the tests
	 *
	 * Input Format:
	 * N = number of nodes
	 * N lines with the city names
	 * M = number of edges
	 * M lines with Nodei Nodej	costBetweenNodeiNodej	   -- list of edges
	 * @param filename
	 */
	public void readData (Scanner scanner)
	{

		if (scanner == null)
			return;

		int nodes = scanner.nextInt();
		for( int i = 0; i < nodes; ++i ) {
			String city = scanner.next();
			Node new_node = new Node(city, i);
			insertNode(new_node);
		}

		int edges = scanner.nextInt();
		for( int i = 0; i < edges; ++i ) {
			int node1 = scanner.nextInt();
			int node2 = scanner.nextInt();
			int cost = scanner.nextInt();
			insertEdge(Nodes.get(node1), Nodes.get(node2), cost);
		}
	}

	public String toString()
	{
		String ans = "Graph:\n";
		for( Node n : Nodes ) {
			ArrayList< Pair< Node, Integer> > edges = getEdges(n);
			ans += n.toString() + " ";
			for (Pair< Node, Integer > neighbour : edges) {
				ans += "( " + neighbour.getFirst().getCity() + "," + neighbour.getSecond() + " ) ";
			}
			ans += "\n";
		}
		ans += "\n";
		return ans;
	}

	/*
	 * reseteaza visited pentru toate nodurile
	 * */
	public void reset()
	{
		ArrayList<Node> nodes = getNodes();
		for (int i = 0; i < nodes.size(); i++)
			nodes.get(i).reset();
	}

}
