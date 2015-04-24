/**
 * Proiectarea Algoritmilor, 2013
 * Lab 9: Arbori minimi de acoperire
 *
 * @author 	Alexandru Tudorica
 * @email	tudalex@gmail.com
 */

package lab9;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

import graph.Graph;
import graph.Node;
import graph.Pair;

public class AMA {

	final static int MAX_VALUE = 9999;

	/*
	 * Prim
	 * */
	public static ArrayList< Pair< Integer,Integer> > prim(Graph g) {

		ArrayList<Node> nodes = g.getNodes();
		ArrayList<Integer> cost = new ArrayList<Integer>(Collections.nCopies(nodes.size(), MAX_VALUE));
		ArrayList<Integer> parent = new ArrayList<Integer>(Collections.nCopies(nodes.size(), -1));
		PriorityQueue<Pair<Node, Integer>> queue = new PriorityQueue<Pair<Node, Integer>>(nodes.size(), new GraphComparator());
		ArrayList < Pair <Integer, Integer> > ama = new ArrayList < Pair <Integer, Integer> >();
		int source = 0;
		
		/*
		 * TODO
		 * initializare cost si parent
		 *
		 * Hint:
		 * boolean g.existEdgeBetween(Node, Node)
		 * int g.getCostBetween(Node, Node)
		 * */
		
		for (int i = 0; i < nodes.size(); i++) {
			
			
			if ((i != source) && g.existsEdgeBetween(nodes.get(source), nodes.get(i))) {
				
				cost.set(i, g.getCostBetween(nodes.get(source), nodes.get(i)));
				queue.add(new Pair<Node, Integer>(nodes.get(i), cost.get(i)));
				parent.set(i, source);
				
			}
			
		}

		parent.set(source, null);
		cost.set(source, 0);
		
		/*
		 * TODO
		 * aflati costul minim al transportului retinut in coada
		 * incercati sa relaxati muchiile
		 * daca o muchie poate fi relaxata si nodul nu este deja in AMA => ea va fi updatata
		 */

		for (int i = 0; i < nodes.size(); i++)
			queue.add(new Pair<Node, Integer>(nodes.get(i), cost.get(i)));
		
		Pair<Node, Integer> p;
		
		while (!queue.isEmpty()) {
			
			p = queue.poll();
			
			if (p.getFirst().isVisited())
				continue;
			
			p.getFirst().visit();
			
			if (p.getFirst().getId() != source) 
				ama.add(new Pair<Integer, Integer>(p.getFirst().getId(), parent.get(p.getFirst().getId())));
			
			for (int i = 0; i < nodes.size(); i++) {
				
				if ((p.getFirst().getId() != i) && (g.existsEdgeBetween(p.getFirst(), nodes.get(i)))) {
					
					if (g.getCostBetween(p.getFirst(), nodes.get(i)) < cost.get(i)) {
						
						cost.set(i, g.getCostBetween(p.getFirst(), nodes.get(i)));
						parent.set(i, nodes.indexOf(p.getFirst()));
						queue.add(new Pair<Node, Integer>(nodes.get(i), cost.get(i)));
						
					}
					
				}
				
			}
			
		}
		
		//queue.remove(new Pair<Node, Integer>(nodes.get(source), cost.get(source)));

		return ama;
	}

	static int get_mul(int mul[], int x) {
		
		return mul[x];
		
	}
	
	static void join_mul(int mul[], int x, int y) {
		
		int min, max;
		
		if (mul[x] < mul[y]) {
			
			min = mul[x];
			max = mul[y];
			
		} else {
			
			min = mul[y];
			max = mul[x];
			
		}
		
		for (int i = 0; i < mul.length; i++) {
			
			if (mul[i] == max)
				mul[i] = min;
			
		}
		
	}
	
	static ArrayList < Pair < Integer, Integer > > kruskal(Graph g) {
		ArrayList <Node> nodes = g.getNodes();
		ArrayList < Pair <Integer, Integer> > edges = g.getAllEdges();
		ArrayList < Pair <Integer, Integer> > ama = new ArrayList < Pair <Integer, Integer> >();
		int multimi[] = new int[g.nodeCount()];
		
		/*
		 * TODO
		 * Construiti o lista de muchii cu tot cu cost
		 * Sortati lista de muchii dupa costul lor
		 *
		 * Initializati vectorul multimi
		 *
		 * HINT: Collections.sort
		 */
		
		for (int i = 0; i < multimi.length; i++)
			multimi[i] = i;
		
		ArrayList<Pair<Pair <Integer, Integer>, Integer>> costuri = new ArrayList<Pair<Pair <Integer, Integer>, Integer>>();
		
		for (Pair <Integer, Integer> muchie : edges) {
			
			costuri.add(new Pair<Pair<Integer,Integer>, Integer>(muchie, g.getCostBetween(nodes.get(muchie.getFirst()), nodes.get(muchie.getSecond()))));
			
		}
		
		Collections.sort(costuri, new EdgeComparator());
		
		/*
		 * TODO
		 * Parcurgeti muchiile in ordine
		 * Pe cele care intra in AMA le bagati in vectorul ama
		 */

		
		Pair<Integer, Integer> muchie;
		
		for (Pair<Pair<Integer, Integer>, Integer> cost : costuri) {
			
			muchie = cost.getFirst();
			
			if (get_mul(multimi, muchie.getFirst()) != get_mul(multimi, muchie.getSecond())) {
				
				ama.add(muchie);
				join_mul(multimi, muchie.getFirst(), muchie.getSecond());
				
			}
			
		}
		
		return ama;
	}
	
	public static void main( String... args ) throws FileNotFoundException
	{
		
		final String fisiere[] = {"date1", "date2"};
		for (int test = 0; test < 2; ++test) {
			Scanner scanner = new Scanner(new File(fisiere[test]));
	
			Graph g = new Graph();
			g.readData(scanner);
			System.out.print(g);
	
			ArrayList<Node> nodes = g.getNodes();
			int sum = 0;
			ArrayList<Pair<Integer, Integer>> edges = kruskal(g);
			for (Pair<Integer, Integer> edge: edges) {
				sum+=g.getCostBetween(nodes.get(edge.getFirst()), nodes.get(edge.getSecond()));
				edge.sort();
			}
			Collections.sort(edges);
			System.out.println("Kruskal:");
			System.out.println(edges);
			System.out.println("Cost total: "+sum);
			
			g.reset();
			edges = prim(g);
			sum = 0;
			for (Pair<Integer, Integer> edge: edges) {
				sum+=g.getCostBetween(nodes.get(edge.getFirst()), nodes.get(edge.getSecond()));
				edge.sort();
			}
			Collections.sort(edges);
			System.out.println("Prim:");
			System.out.println(edges);
			System.out.println("Cost total: "+sum+"\n");
			
			scanner.close();
		}
	
	}

	/*
	 * TODO
	 * comparator pentru coada cu prioritati
	 */
	private static class GraphComparator implements Comparator<Pair<Node, Integer>> {

		@Override
		public int compare(Pair<Node, Integer> arg0, Pair<Node, Integer> arg1) {

			if (arg0.getSecond() > arg1.getSecond())
				return 1;
			else if (arg0.getSecond() < arg1.getSecond())
				return -1;
			else return 0;
			
		}
	}
	
	private static class EdgeComparator implements Comparator<Pair<Pair<Integer, Integer>, Integer>> {

		@Override
		public int compare(Pair<Pair<Integer, Integer>, Integer> o1,
				Pair<Pair<Integer, Integer>, Integer> o2) {
			
			if (o1.getSecond() < o2.getSecond())
				return -1;
			else if (o1.getSecond() > o2.getSecond())
				return 1;
			else return 0;
		}
		
	}

}

