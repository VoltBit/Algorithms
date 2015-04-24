package lab9;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import graph.*;

public class Maze {

	final static int MAX_SIZE = 5;
	static char[][] maze = new char[ 2*MAX_SIZE+1 ][2*MAX_SIZE+1 ];
	
	final int dx[] = {  0, 0, -1, 1 };
	final int dy[] = { -1, 1,  0, 0 };
	
	Graph g = new Graph();
	
	public Maze(){
		
		for( int i = 0; i < MAX_SIZE * MAX_SIZE; ++i ){
			g.insertNode( new Node(i) );
		}
		
		/*
		 * TODO
		 * Construieste legaturile intre nodurile din labirint
		 * Nodul de la pozitia (i,j) va avea id-ul i*maze_size+j
		 *
		 * ATENTIE SA NU IESITI DIN LABIRINT
		 */
		
		ArrayList<Node> nodes = g.getNodes();
		int maze_size = MAX_SIZE;
		
		for (int i = 0; i < MAX_SIZE; i++) {
			
			for (int j = 0; j < MAX_SIZE; j++) {
				
				if (i > 0) {
					g.insertEdge(nodes.get((i - 1) * maze_size + j), nodes.get(i * maze_size + j), 1);
					g.insertEdge(nodes.get(i * maze_size + j),nodes.get((i - 1) * maze_size + j), 1);
				}
				
				if (i < maze_size - 1) {
					g.insertEdge(nodes.get((i + 1) * maze_size + j), nodes.get(i * maze_size + j), 1);
					g.insertEdge(nodes.get(i * maze_size + j), nodes.get((i + 1) * maze_size + j), 1);
				}
				
				if (j > 0) {
					g.insertEdge(nodes.get(i * maze_size + (j - 1)), nodes.get(i * maze_size + j), 1);
					g.insertEdge(nodes.get(i * maze_size + j), nodes.get(i * maze_size + (j - 1)), 1);
				}
				
				if (j < maze_size - 1) {
					g.insertEdge(nodes.get(i * maze_size + (j + 1)), nodes.get(i * maze_size + j), 1);
					g.insertEdge(nodes.get(i * maze_size + j), nodes.get(i * maze_size + (j + 1)), 1);
				}
				
			}
			
		}
		
	}
	
	public void printMaze( ArrayList< Pair<Integer, Integer> > edges )
	{
		for( int i = 0; i < maze.length; ++i )
			for( int j = 0; j < maze.length; ++j )
			{
				maze[i][j] = '#';
			}
		
		for( int i = 0; i < MAX_SIZE ; ++i )
			for( int j = 0; j < MAX_SIZE; ++j )
			{
				//maze[2 * i + 1][ 2 * j + 1] = '_';
				
				for( int k = 0; k < 4; ++k )
				{
					int x = i + dx[k];
					int y = j + dy[k];
					
					if( edges.contains( new Pair<Integer,Integer>( i * MAX_SIZE + j, x * MAX_SIZE + y ) ) ||
						edges.contains( new Pair<Integer,Integer>( x * MAX_SIZE + y, i * MAX_SIZE + j ) ) )
						maze[ 2 * i + 1 + dx[k] ][ 2 * j + 1 + dy[k] ] = '_';
				}
			}
		
		System.out.println(this);
	}
	
	// http://weblog.jamisbuck.org/2011/1/3/maze-generation-kruskal-s-algorithm
	
	void makeMaze()
	{
		ArrayList< Pair< Integer,Integer > > edges = new ArrayList< Pair< Integer,Integer > >();
		ArrayList< Pair< Integer,Integer > > edges2 = new ArrayList< Pair< Integer,Integer > >();
		
		ArrayList<Integer> sets = new ArrayList<Integer>(Collections.nCopies(g.getNodes().size(), 99999));
		
		/*
		 * TODO
		 * Aplica un algorithm de AMA cu alegere randomizata
		 */
		
		int i = 0;
		for (Node n : g.getNodes()) {
			
			sets.set(n.getId(), i);
			i++;
			
		}
		
		int number_of_sets = i;
		int number_of_edges, random;
		
		Random rand = new Random();
		edges = g.getAllEdges();
		ArrayList<Node> nodes = g.getNodes();
		
		while (number_of_sets != 1) {
			
			number_of_edges = edges.size();
			random = rand.nextInt(number_of_edges);
			
			Pair<Integer, Integer> muchie = edges.get(random);
			
			Node nod1 = nodes.get(muchie.getFirst());
			Node nod2 = nodes.get(muchie.getSecond());
			
			int set_of_nod1 = sets.get(nod1.getId());
			int set_of_nod2 = sets.get(nod2.getId());
		
			if (set_of_nod1 == set_of_nod2) continue;
			
			for (i = 0; i < nodes.size(); i++) {
				
				if (sets.get(i) == set_of_nod2)
					sets.set(i, set_of_nod1);
				
			}
			
			//System.out.println(number_of_sets + ": " + muchie);
			
			edges.remove(new Pair<Integer, Integer>(nod1.getId(), nod2.getId()));
			edges.remove(new Pair<Integer, Integer>(nod2.getId(), nod1.getId()));
			
			edges2.add(new Pair<Integer, Integer>(nod1.getId(), nod2.getId()));
			edges2.add(new Pair<Integer, Integer>(nod2.getId(), nod1.getId()));
			
			number_of_sets--;
			
		}
		
		//System.out.println(sets);
		//System.out.println(edges);
		
		printMaze( edges2 );
	}
	
	public String toString()
	{
		String str = "";
		for( int i = 0; i < maze.length; ++i ){
			for( int j = 0; j < maze.length; ++j ){
				str += maze[i][j];
			}
			str += "\n";
		}
		return str;
	}
	
	public static void main( String... args )
	{
		Maze maze = new Maze();
		maze.makeMaze();
	}
	
}