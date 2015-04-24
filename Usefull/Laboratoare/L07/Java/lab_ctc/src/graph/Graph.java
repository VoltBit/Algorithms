/**
 * Proiectarea Algoritmilor, 2013
 * Lab 7: Aplicatii DFS
 * 
 * @author 	Radu Iacob
 * @email	radu.iacob23@gmail.com
 */

package graph;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import graph.Node;

public class Graph {

	public enum GraphType {
		DIRECTED,
		UNDIRECTED
	};

	public GraphType _type;

	ArrayList< Node > Nodes;
	ArrayList< ArrayList< Node > > Edges;
	
	/*
		Componente tari conexe
	*/
	
	public Stack< Node > stack;
	public ArrayList< ArrayList< Node > > ctc;
	
	/*
		Componente biconexe
	*/
	
	public Stack< Pair< Node, Node > > edge_stack; 
	public ArrayList< Node > puncte_de_articulatie;
	public ArrayList< Pair<Node,Node> > muchii_critice;
	
	public Graph( GraphType type )
	{
		Nodes = new ArrayList<Node>();
		Edges = new ArrayList< ArrayList<Node> >();
	
		stack = new Stack< Node >();
		ctc   = new ArrayList< ArrayList< Node > >();
		
		edge_stack = new Stack< Pair<Node,Node> >();
		
		puncte_de_articulatie = new ArrayList< Node >();
		muchii_critice  	  = new ArrayList< Pair<Node,Node> >();
		
		_type = type;
	}
	
	public int node_count()
	{
		return Nodes.size();
	}
	
	public void insert_edge( Node node1, Node node2 )
	{
		Edges.get( node1.get_id() ).add( node2 );
	}

	public void insert_node( Node node )
	{
		Nodes.add( node );
		Edges.add( new ArrayList< Node >() );
	}
	
	public ArrayList< Node > get_nodes()
	{
		return Nodes;
	}

	public ArrayList< Node > get_edges( Node node )
	{
		return Edges.get( node.get_id() );
	}
	
	public void reset()
	{
		for( Node n : Nodes )
			n.reset();
		
		stack.clear();
		ctc.clear();
		
		puncte_de_articulatie.clear();
		muchii_critice.clear();
	}
	
	public void print_ctc()
	{
		System.out.println("Strongly Connected Componenets:"); 
		
		for( ArrayList< Node > c_ctc : ctc ){
			System.out.println( c_ctc );		
		}
		
		System.out.println("\n");
	}
	
	

	/**
	 * Function to read all the tests
	 * 
	 * Input Format:
	 * N M
	 * Nodei Nodej				   -- list of edges
	 * ...
	 * where
	 * N = Number of Nodes
	 * M = Number of Edges
	 * @param filename
	 */
	public void readData ( Scanner scanner ) {

		if( scanner == null ) return;
		
		int nodes = scanner.nextInt();
		int edges = scanner.nextInt();
		
		for( int i = 0; i < nodes; ++i )
		{
			Node new_node = new Node(i);
			insert_node( new_node );
		}
		
		for( int i = 0; i < edges; ++i )
		{
			int node1 = scanner.nextInt();
			int node2 = scanner.nextInt();
			insert_edge( Nodes.get( node1 ), Nodes.get( node2 ) );
			
			if( _type == Graph.GraphType.UNDIRECTED ){
				insert_edge( Nodes.get(node2), Nodes.get(node1) );
			}
		}
		
	}
	
	public String toString()
	{
		String ans = "Graph:\n";
		for( Node n : Nodes )
		{
			ans += n.toString() + " : ";
			ans += Edges.get(n.get_id()).toString(); 
			ans += "\n";
		}
		ans += "\n";
		return ans;
	}
	
}
