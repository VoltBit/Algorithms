/**
 * Proiectarea Algoritmilor, 2013
 * Lab 9: Arbori minimi de acoperire
 *
 * @author 	Alexandru Tudorica
 * @email	tudalex@gmail.com
 */

package graph;

import java.util.ArrayList;
import java.util.Scanner;
import graph.Node;

public class Graph {

	public static final int MAX_VALUE = 9999;
	ArrayList< Node > Nodes;
	ArrayList< ArrayList< Pair< Node, Integer > > > Edges;
	ArrayList< Pair <Integer, Integer >  > AllEdges;
	public Graph()
	{
		Nodes = new ArrayList<Node>();
		Edges = new ArrayList< ArrayList< Pair< Node, Integer > > >();
		AllEdges = new ArrayList< Pair <Integer, Integer  > >();
	}

	public int nodeCount()
	{
		return Nodes.size();
	}

	public void insertEdge(Node node1, Node node2, Integer cost)
	{
		Edges.get(node1.getId()).add(new Pair< Node, Integer >(node2, cost));
		Edges.get(node2.getId()).add(new Pair< Node, Integer >(node1, cost));
		AllEdges.add(new Pair<Integer, Integer>(node1.getId(), node2.getId()));
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

	public ArrayList< Pair< Node, Integer > > getEdges(Node node)
	{
		return Edges.get(node.getId());
	}
	
	public ArrayList< Pair < Integer, Integer> > getAllEdges()
	{
		return AllEdges;
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
			Node new_node = new Node(i);
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
				ans += "( " + neighbour.getFirst().getId() + "," + neighbour.getSecond() + " ) ";
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
