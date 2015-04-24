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

public class p1 {

	/**
		Parcurgere DFS pentru determinarea componentelor tare conexe
		Algoritmul lui Tarjan 	  <br>
		Complexitate: O( N + M )  <br>
				unde  N - nr de noduri	<br>
					  M - nr de muchii	<br>
	
		Useful API: 			  <br>
		graph.get_edges( *node ); <br>
		graph.stack				  <br>
	
		node.visited()			  <br>
		
		node.discoveryTime		  <br> 
		node.lowlink			  <br>
		node.in_stack			  <br>
		
		optional:
		node.set_property( CTC, indexul_componentei_conexe_curente )
	 */

        static int index, comCount = 0;
	static void dfsCTC( Graph g, Node node )
	{
		/*
		 * TODO: Initializeaza level si lowlink
		 */
                node.discoveryTime = index;
                node.lowlink = index;
                index++;
                
                g.stack.push(node);

                for( Node n : g.getNodes()){
                    if(!n.visited()){
                        dfsCTC(g, n);
                        if(node.lowlink > n.lowlink) node.lowlink = n.lowlink;
                    }
                    else {
                        if(n.inStack && node.lowlink > n.discoveryTime)
                            node.lowlink = n.discoveryTime;
                    }
                }
                if(node.lowlink == node.discoveryTime){
                    ArrayList<Node> tmp = new ArrayList();
                    while(g.stack.peek().getId() != node.getId()){
                        tmp.add(g.stack.pop());
                    }
                    tmp.add(g.stack.pop());
                    if(!tmp.isEmpty())
                        g.ctc.add(tmp);
                }
		/*
		 * TODO: Salveaza componenta tare conexa curenta
		 */
	}

	/**
	 * Identifica componentele tare conexe din graful primit ca parametru
	 * Complexitate: O( N + M )
	 * N - numarul de noduri
	 * M - numarul de muchii 
	 */
	
	static void StronglyConnectedComponents( Graph g )
	{
		g.reset(); // reseteaza variabilele auxiliare
		index = 0;
		/*
			TODO: Apeleaza dfs_ctc pentru fiecare nod nevizitat.
		*/
//		for( Node n : g.getNodes()){
//                    if(!n.visited()){
//                        dfsCTC(g, n);
//                    }
//                }
                int i;
                for(i = 0 ; i < g.nodeCount(); i++){
                    if(!g.getNodes().get(i).visited()){
                        dfsCTC(g, g.getNodes().get(i));
                    }
                }
                g.printCTC(); // afiseaza componentele tare conexe
	}
	
	final static String PATH = "./res/test01";
	
	public static void main( String... args ) throws FileNotFoundException
	{
		Scanner scanner = new Scanner(new File(PATH));
		int test_count = scanner.nextInt();
		
		while( test_count-- > 0 )
		{	
			Graph g = new Graph( Graph.GraphType.DIRECTED );
			g.readData(scanner);
			System.out.println(g);
			StronglyConnectedComponents(g);
			//bonus(g);
			
			System.out.println("###########################");
		}
		
		scanner.close();
	}
	
	
	
	/*
		HINT: In urma compactarii grafului original rezulta tot 
			  un graf orientat, fara cicluri.
	*/

	static void bonus( Graph g )
	{
		if( g.ctc.size() == 0 ){
			StronglyConnectedComponents(g);
		}

		/*
			Construim un nou graf 'condensand' componentele tare conexe intr-un singur
			nod.
		*/
	
		Graph cluster = new Graph( Graph.GraphType.DIRECTED );
		for( int i = 0, sz = g.ctc.size(); i < sz; ++i ){
			cluster.insertNode( new Node( i ) );
		}

		ArrayList< Node > all_clusters = cluster.getNodes();

		for( int i = 0, sz = g.ctc.size(); i < sz; ++i ){
	
			// nodurile care alcatuiesc componenta conexa curenta
			ArrayList< Node > inner_nodes = g.ctc.get(i);
	
			// avoid duplicate edges
	
			HashSet< Integer > connections = new HashSet< Integer >(); 
	
			// pentru fiecare nod..
			for( Node n : inner_nodes )
			{
				ArrayList<Node> edges = g.getEdges( n );
	
				// pentru fiecare muchie..
				for( Node v : edges )
				{
					int idx_ctc = v.getProperty( Node.Property.CTC );
			
					if( idx_ctc != i && !connections.contains( idx_ctc ) )
					{
						cluster.insertEdge( all_clusters.get(i), all_clusters.get(idx_ctc) );
						connections.add( idx_ctc );
					}
				}
			}
	
			//System.out.println(inner_nodes);
			all_clusters.get(i).sum = inner_nodes.size();
		}
	
		System.out.println( "Graful rezultat in urma compactarii: ");
		System.out.println( cluster );
		
		/*
		 * TODO: Calculeaza numarul maxim de jucatori daca s-ar adauga o legatura artificiala
		 * Complexitate solutie: O( N + M )
		 * N - numarul de noduri din graful comprimat
		 * M - numarul de muchii  
		 */
		
		System.out.println("Daca s-ar adauga o legatura artificiala, numarul maxim de jucatori ");

		
	}

}

