/**
 * Proiectarea Algoritmilor, 2013
 * Lab 8: Drumuri minime
 *
 * @author 	Emma Sevastian
 * @email	emma.sevastian@gmail.com
 */

package lab8;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

import javax.xml.crypto.NodeSetData;

import graph.Graph;
import graph.Node;
import graph.Pair;

public class DrumuriMinime {

	final static int MAX_VALUE = 9999;
	static int costFloydWarshall[][];
	static int detourFloydWarshall[][];

	/*
	 * Dijkstra
	 * */
	public static ArrayList<Integer> dijkstra(Graph g, int source, int destination) {

		ArrayList<Node> nodes = g.getNodes();
		ArrayList<Integer> cost = new ArrayList<Integer>(Collections.nCopies(nodes.size(), 0));
		ArrayList<Integer> parent = new ArrayList<Integer>(Collections.nCopies(nodes.size(), 0));
		PriorityQueue<Pair<Node, Integer>> queue = new PriorityQueue<Pair<Node, Integer>>(nodes.size(), new GraphComparator());

		/*
		 * TODO
		 * initializare cost si parent
		 *
		 * Hint:
		 * boolean g.existEdgeBetween(Node, Node)
		 * int g.getCostBetween(Node, Node)
		 * */
		
		nodes.get(source).visit();
		Node sursa = nodes.get(source);
		
		for (Node n : nodes) {
			
			if (g.existsEdgeBetween(sursa, n)) {
				
				cost.set(n.getId(), g.getCostBetween(sursa, n));
				queue.add(new Pair<Node, Integer>(n, g.getCostBetween(sursa, n)));
				parent.set(n.getId(), source);
				
			} else {
				
				cost.set(n.getId(), MAX_VALUE);
				parent.set(n.getId(), null);
				
			}
			
		}

		/*
		 * TODO
		 * scoateti costul minim din coada
		 * relaxati muchiile
		 * daca o muchie poate fi relaxata => trebuie updatata in coada
		 * */

		while (!queue.isEmpty()) {
			
			Pair<Node, Integer> u = queue.poll();
			Node uu = u.getFirst();
			nodes.get(uu.getId()).visit();
			
			for (Pair<Node, Integer> n : g.getEdges(uu)) {
				
				if (!n.getFirst().isVisited() && (cost.get(nodes.indexOf(n.getFirst())) > u.getSecond() + g.getCostBetween(u.getFirst(), n.getFirst()))) {
					
					cost.set(nodes.indexOf(n.getFirst()), u.getSecond() + g.getCostBetween(u.getFirst(), n.getFirst()));
					parent.set(nodes.indexOf(n.getFirst()), nodes.indexOf(u.getFirst()));
					queue.add(new Pair<Node, Integer>(n.getFirst(), u.getSecond() + g.getCostBetween(u.getFirst(), n.getFirst())));
					
				}
				
			}
			
		}

		ArrayList<Integer> path = new ArrayList<Integer>();

		for (int i = 0; i < cost.size(); i++) {
			
			//System.out.println(i + ":\t" + cost.get(i) + "\t" + parent.get(i));
			
		}
		
		/*
		 * TODO
		 * aflati drumul de la sursa la destinatie (plecand din destinatie)
		 * */

		int index = parent.get(destination);
		Node nod = nodes.get(index);
		
		while (nod != null) {
			
			path.add(nod.getId());
			if (index == source) break;
			index = parent.get(nod.getId());
			nod = nodes.get(index);
			
		}
		
		path.add(0, destination);
		
		return path;
	}
	
	/*
	 * Dijkstra
	 * */
	public static ArrayList<Integer> dijkstra_negativ(Graph g, int source, int destination) {

		ArrayList<Node> nodes = g.getNodes();
		ArrayList<Integer> cost = new ArrayList<Integer>(Collections.nCopies(nodes.size(), 0));
		ArrayList<Integer> parent = new ArrayList<Integer>(Collections.nCopies(nodes.size(), 0));
		PriorityQueue<Pair<Node, Integer>> queue = new PriorityQueue<Pair<Node, Integer>>(nodes.size(), new GraphComparator());

		/*
		 * TODO
		 * initializare cost si parent
		 *
		 * Hint:
		 * boolean g.existEdgeBetween(Node, Node)
		 * int g.getCostBetween(Node, Node)
		 * */
		
		nodes.get(source).visit();
		Node sursa = nodes.get(source);
		
		for (Node n : nodes) {
			
			if (g.existsEdgeBetween(sursa, n)) {
				
				cost.set(n.getId(), g.getCostBetween(sursa, n));
				queue.add(new Pair<Node, Integer>(n, g.getCostBetween(sursa, n)));
				parent.set(n.getId(), source);
				
			} else {
				
				cost.set(n.getId(), MAX_VALUE);
				parent.set(n.getId(), null);
				
			}
			
		}

		/*
		 * TODO
		 * scoateti costul minim din coada
		 * relaxati muchiile
		 * daca o muchie poate fi relaxata => trebuie updatata in coada
		 * */

		while (!queue.isEmpty()) {
			
			Pair<Node, Integer> u = queue.poll();
			Node uu = u.getFirst();
			//nodes.get(uu.getId()).visit();
			
			for (Pair<Node, Integer> n : g.getEdges(uu)) {
				//!n.getFirst().isVisited() &&
				if ( (cost.get(nodes.indexOf(n.getFirst())) > u.getSecond() + g.getCostBetween(u.getFirst(), n.getFirst()))) {
					
					cost.set(nodes.indexOf(n.getFirst()), u.getSecond() + g.getCostBetween(u.getFirst(), n.getFirst()));
					parent.set(nodes.indexOf(n.getFirst()), nodes.indexOf(u.getFirst()));
					queue.add(new Pair<Node, Integer>(n.getFirst(), u.getSecond() + g.getCostBetween(u.getFirst(), n.getFirst())));
					
				}
				
			}
			
		}

		ArrayList<Integer> path = new ArrayList<Integer>();

		for (int i = 0; i < cost.size(); i++) {
			
			//System.out.println(i + ":\t" + cost.get(i) + "\t" + parent.get(i));
			
		}
		
		/*
		 * TODO
		 * aflati drumul de la sursa la destinatie (plecand din destinatie)
		 * */

		int index = parent.get(destination);
		Node nod = nodes.get(index);
		
		while (nod != null) {
			
			path.add(nod.getId());
			if (index == source) break;
			index = parent.get(nod.getId());
			nod = nodes.get(index);
			
		}
		
		path.add(0, destination);
		
		return path;
	}

	/*
	 * TODO
	 * afiseaza drumul intre 2 orase
	*/
	
	public static void printPath(Graph g, int i, int j)
	{

		ArrayList<Node> nodes = g.getNodes();
		
		if (costFloydWarshall[i][j] == MAX_VALUE) {
			//System.out.println("nu exista drum");
		} else {
			
			if (!((nodes.get(i).isVisited() && nodes.get(j).isVisited()) || detourFloydWarshall[i][j] == MAX_VALUE)) {
				
				nodes.get(i).visit();
				nodes.get(j).visit();
				
				printPath(g, i, detourFloydWarshall[i][j]);
				System.out.print(nodes.get(detourFloydWarshall[i][j]).getCity() + " -> ");
				printPath(g, detourFloydWarshall[i][j], j);
				
			}
			
		}
		
	}

	/*
	 * Floyd-Warshall
	 * */
	public static void FloydWarshall(Graph g)
	{

		ArrayList<Node> nodes = g.getNodes();
		int n = nodes.size();

		/*
		 * TODO
		 * initializati costFloydWarshall si detourFloydWarshall
		 * */

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				
				costFloydWarshall[i][j] = g.getCostBetween(nodes.get(i), nodes.get(j));
				
				if (!g.existsEdgeBetween(nodes.get(i), nodes.get(j)) || (i == j))
					detourFloydWarshall[i][j] = MAX_VALUE;
				else {
					detourFloydWarshall[i][j] = i; // exista muchie
					
				}
			}
		}

		/*
		 * TODO
		 * incercati sa relaxati muchiile
		 * tineti cont de faptul ca cel mai lung drum de cost minim intre 2 orase
		 * poate avea maxim n-1 muchii
		 * */

		for (int k = 0; k < n; k++)
			for (int i = 0; i < n; i++)
				for (int j = 0; j < n; j++)
					if (costFloydWarshall[i][j] > costFloydWarshall[i][k] + costFloydWarshall[k][j]) {
						costFloydWarshall[i][j] = costFloydWarshall[i][k] + costFloydWarshall[k][j];
						detourFloydWarshall[i][j] = detourFloydWarshall[k][j];
					}

	}

	/*
	 * Bellman-Ford
	 * */
	public static void BellmanFord(Graph g, int source)
	{
		ArrayList<Node> nodes = g.getNodes();
		ArrayList<Integer> cost = new ArrayList<Integer>(Collections.nCopies(nodes.size(), 0));
		ArrayList<Integer> parent = new ArrayList<Integer>(Collections.nCopies(nodes.size(), 0));

		/*
		 * TODO
		 * initializare cost si parent
		 * */

		Node sursa = nodes.get(source);
		
		for (Node n : nodes) {
			
			if (g.existsEdgeBetween(sursa, n)) {
				
				cost.set(n.getId(), n.getId());
				parent.set(n.getId(), source);
				
			} else {
				
				cost.set(n.getId(), MAX_VALUE);
				parent.set(n.getId(), MAX_VALUE);
				
			}
			
		}

		cost.set(source, 0);
		parent.set(source, MAX_VALUE);
		
		/*
		 * TODO
		 * relaxare noduri
		 * */

		for (int i = 0; i < nodes.size() - 2; i++) {
			
			for (Node u : nodes) {
				
				for (Node v : nodes) {
					
					if (g.existsEdgeBetween(u, v)) {
						
						if (cost.get(v.getId()) > cost.get(u.getId()) + g.getCostBetween(u, v)) {
							
							cost.set(v.getId(), cost.get(u.getId()) + g.getCostBetween(u, v));
							parent.set(v.getId(), u.getId());
							
						}
						
					}
					
				}
				
			}
			
		}

		/*
		 * TODO
		 * daca mai exista muchii ce pot fi relaxate => exista un ciclu de cost negativ
		 * */

		for (Node u : nodes) {
			
			for (Node v : nodes) {
				
				if (g.existsEdgeBetween(u, v)) {
					
					if (cost.get(v.getId()) > cost.get(u.getId()) + g.getCostBetween(u, v))
						System.out.println("cicluri negative");
					
				}
				
			}
			
		}

	}

	public static void main( String... args ) throws FileNotFoundException
	{
		Scanner scanner = new Scanner(new File("date1"));

		Graph g = new Graph();
		g.readData(scanner);
		System.out.print(g);

		ArrayList<Node> nodes = g.getNodes();

		/*
		 * aflare drum de cost minim intre Bucuresti si Paris
		 * */
		System.out.println("Drumul de cost minim intre Bucuresti - Paris :");
		ArrayList<Integer> path = dijkstra(g, g.getNode("Bucuresti"), g.getNode("Paris"));

		for (int i = path.size()-1; i >= 0; i--)
			System.out.print(nodes.get(path.get(i)).getCity() + " ");
		System.out.println("\n");

		/*
		 * Gasiti drumul de cost minim intre oricare 2 orase
		 * */
		System.out.println("Drumul minim de la Paris la Viena");
		costFloydWarshall = new int[nodes.size()][nodes.size()];
		detourFloydWarshall = new int[nodes.size()][nodes.size()];

		g.reset();
		FloydWarshall(g);
		printPath(g, g.getNode("Paris"), g.getNode("Viena"));
		System.out.println();
		g.reset();
		FloydWarshall(g);
		printPath(g, g.getNode("Bucuresti"), g.getNode("Paris"));
		System.out.println();
		
		/*
		 * Aflati daca exista drumuri de cost negativ
		 * */
		Scanner scannerNew = new Scanner(new File("date2"));

		Graph gNew = new Graph();
		gNew.readData(scannerNew);
		System.out.print(gNew);

		System.out.println("Exista drumuri de cost negativ?");
		BellmanFord(gNew, 0);
		
		gNew.reset();
		ArrayList<Integer> path_neg = dijkstra(gNew, gNew.getNode("Viena"), gNew.getNode("Roma"));
		nodes.clear();
		nodes = gNew.getNodes();
		for (int i = path_neg.size()-1; i >= 0; i--)
			System.out.print(nodes.get(path_neg.get(i)).getCity() + " ");
		System.out.println("\n");

		scanner.close();
		scannerNew.close();
	}

	/*
	 * TODO
	 * comparator pentru coada cu prioritati
	 */
	private static class GraphComparator implements Comparator<Pair<Node, Integer>> {

		@Override
		public int compare(Pair<Node, Integer> arg0, Pair<Node, Integer> arg1) {

			if (arg0.getSecond() < arg1.getSecond())
				return -1;
			else if (arg0.getSecond() > arg1.getSecond())
				return 1;
			else
				return 0;
			
		}
	}

}

