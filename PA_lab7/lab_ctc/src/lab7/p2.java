/**
 * Proiectarea Algoritmilor, 2013
 * Lab 7: Aplicatii DFS
 * 
 * @author 	Radu Iacob
 * @email	radu.iacob23@gmail.com
 */

package lab7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import graph.Graph;
import graph.Node;
import graph.Pair;

public class p2 {

	/**
	 * Afiseaza componentele biconexe, salvate in stiva
	 */
	
	static void printBiconexComponent( Graph g, Node n1, Node n2 )
	{
		System.out.println("Componenta biconexa: ");
		
		HashSet< Node > comp = new HashSet< Node >();
		Pair< Node, Node > aux;
		
		do
		{
			aux = g.edgeStack.pop();
			comp.add( aux.getFirst() );
			comp.add( aux.getSecond() );
			
		}while( aux.getFirst() != n1 || aux.getSecond() != n2 );
		
		System.out.println( comp );
	}
	
	/**
	 * Useful API:
	 * 
	 * graph.muchiiCritice			
	 * graph.articulationPoints		
	 * graph.getEdges
	 * 
	 * graph.edgeStack
	 * 
	 * node.discoveryTime
	 * node.lowlink
	 * node.inStack
	 */

	static void dfsBiconex( Graph g, Node node, int father_id )
	{
		/*
		 * TODO: Initializati level si lowlink
		 */
		
		/*
		 * TODO: Identifica daca nodul este punct de articulatie
		 */
		
		
	}
	
	/**
	 * Descompune graful in componente biconexe
	 *
	 * Useful API:
	 * 
	 * g.getNodes()
	 * node.visited()
	 */

	static void ComponenteBiconexe( Graph g )
	{
		g.reset();
		
		/*
		 * TODO: Parcurgere df pentru identificarea componentelor biconexe
		 */
		
		

		System.out.println("\nPuncte. de articulatie: \n" + g.articulationPoints );
		System.out.println("\nMuchii critice: \n" + g.muchiiCritice );
	}
	
	final static String PATH = "./res/test02";
	
	public static void main( String... args ) throws FileNotFoundException
	{
		Scanner scanner = new Scanner(new File(PATH));
		int test_count = scanner.nextInt();
		
		while( test_count-- > 0 )
		{
			Graph g = new Graph( Graph.GraphType.UNDIRECTED );	
			g.readData( scanner );
			System.out.println(g);
			ComponenteBiconexe(g);
		}
		
		scanner.close();
	}
}
