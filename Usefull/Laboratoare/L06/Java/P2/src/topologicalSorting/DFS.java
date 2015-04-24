package topologicalSorting;

import java.util.ArrayList;
import java.util.Collections;

import graph.Graph;
import graph.Node;

public class DFS {

	private Graph graph;
	private int time = 0;
	public DFS(Graph graph) {
		this.graph = graph;
	}

	/*
	TODO: 
	Luati toate nodurile care nu au intrari ( root nodes ),
		pentru fiecare: parcurgeti subarborele asociat folosind DFS
		si adaugati in order in ordine inversa terminarii
	Hint: puteti folosii:
		- Graph.getAllRootNodes 
		- Node.getOutNodes
		- Node.getInitTime
		- Node.setInitTime
	 */
	
	private void Explore(Node x, ArrayList<Node> order) {
		
		x.setInitTime(time);
		time++;

		for (Node n : x.getOutNodes()) // incerc sa prelucrez vecinii
			if (n.getInitTime() != -1) {  // daca a fost prelucrati deja
				System.out.println("Avem ciclu!"); // inseamna ca avem ciclu
				break;
			}
		
		if (x.getOutNodes().isEmpty()) // daca nu mai are vecini
			x.setFinishTime(time);
		else {
			for (Node n : x.getOutNodes()) // incerc sa prelucrez vecinii
				if (n.getInitTime() == -1) Explore(n, order); // daca nu au fost prelucrati deja
			x.setFinishTime(time);
		}
		
		order.add(x);
		
	}
	
	public ArrayList<Node> setOrdering() {
		
		// initializare structura date
		ArrayList<Node> order = new ArrayList<Node>();
		
		for (Node n : graph.getAllRootNodes())
			Explore(n, order); // explorez fiecare root
		
		for (int i = 0; i < order.size() / 2; i++) {
			
			Node aux = order.get(i);
			order.set(i, order.get(order.size() - i - 1));
			order.set(order.size() - i - 1, aux);
			
		}
		
		for (int i = 0; i < order.size(); i++)
			System.out.println(order.get(i));
		
		return order;
	}

	
}
