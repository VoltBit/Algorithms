package topologicalSorting;

import java.util.ArrayList;
import java.util.Random;

import graph.Graph;
import graph.Node;
import graph.Node.Type;

public class Kahn {

	private Graph graph;
	private Random random;
	public Kahn(Graph graph) {
		this.graph = graph;
		random = new Random();
	}

	/*
	TODO:
	Folositi algorintmul lui kahn ca sa scoateti o sortare topologica
		Hint: puteti folosii:
			- Graph.getAllRootNodes
			- Node.getOutNodes
			- Node.removeNeighbour
	 */
	
	public ArrayList<Node> setOrdering() {
		
		ArrayList<Node> order = new ArrayList<Node>();
		ArrayList<Node> allNodes = graph.getAllNodes();
		
		ArrayList<Node> rez = new ArrayList<Node>() ;
		
		// initializare S cu nodurile care nu au in-muchii
		for (Node n : allNodes)
			if (n.getInNodes().isEmpty()) rez.add(n);
		

		while (!rez.isEmpty()) { // cat timp mai am noduri de prelucrat
				
			Node aux = rez.remove(0);  // se scoate un nod din multimea S
			order.add(aux); // adaug U la lista finala
				
			// pentru toti vecinii
			ArrayList<Node> vecini = aux.getInNodes(); 
			for (Node v : aux.getOutNodes())
				vecini.add(v);
				
				
			for (Node v : vecini) {
					
				if (v.getInNodes().contains(aux)) {
					v.removeNeighbour(aux, Type.IN);
				}
				/*
				// sterge muchia u-v
				if (aux.getInNodes().contains(v)) {
					aux.removeNeighbour(v, Type.IN);
					break;
				} */
				/*
				else if (aux.getOutNodes().contains(v)) {
					aux.removeNeighbour(v, Type.OUT);
					break;
				}
				 */
				
				// adauga v la multimea S	
				if (v.getInNodes().isEmpty()) // if (v nu are in-muchii )
					rez.add(v);
					
			}	
		}	
		
		for (int i = 0; i < order.size(); i++)
			System.out.println(order.get(i));
		
		return order;
	}

}
