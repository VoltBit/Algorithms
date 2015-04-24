package maze;

import java.util.ArrayList;

import ui.UI;
import mapGenerator.Coord;
import mapGenerator.Map;
import mapGenerator.MapPosition;
import mapGenerator.Coord.Direction;

public class MazeResolver {
	public enum Method {
		DFS, BFS
	}
	
	private UI ui;
	private Method searchingMethod;
	private Map map;
	private ArrayList<Coord> frontier;
	
	public MazeResolver(Map map, UI ui, Method searchingMethod) {
		this.searchingMethod = searchingMethod;
		this.map = map;
		this.ui = ui;
		this.frontier = new ArrayList<Coord>();
	}

	/*
	 * TODO:
	 * 	- explore the map
	 * 	- at each step call the function makeMove 
	 */
	public void resolve(){
		
		Coord start = map.getStartCoord();
		
		makeMove(start);
		addNeighbours(start); // frontiera
		
		Coord curent = start;
		
		while (!curent.equals(map.getFinishCoord())) {
			
			curent = getNextNode();
			makeMove(curent);
			addNeighbours(curent);
			
		}
		
	}
	
	private void makeMove(Coord myNewPosition) {
		map.setCell(myNewPosition, MapPosition.ME);	
		ui.paint(myNewPosition);
	}

	/*
	 * TODO: Add neighbours to the frontier list. 
	 * Take a look at the variable searchingMethod;
	 * 	Hint: use:
	 * 	- getPossibleMoves
	 * 	- insert differently depending on the searchingMethod
	 */
	
	public void addNeighbours(Coord myPosition){
	
		ArrayList<Coord> mutari_posibile = getPossibleMoves(myPosition);
		
		/**
		 * 
		 * adaug fiecare mutare posibila in frontiera
		 * adaugarea la BSF se face la inceput
		 * 
		 */
		
		for (Coord c : mutari_posibile) {
			
			if (searchingMethod == Method.BFS)
				frontier.add(c);
			else
				frontier.add(0, c);
			
		}
	
	}

	
	/*
	 * TODO: Extract the next neighbour from frontier;
	 */		
	public Coord getNextNode(){
	
		return frontier.remove(0);
		
	}
	
	
	/*
	 * TODO:
	 * 	Return an array with all possible moves
	 * 	Hint: use
	 * 	- Coord.getNeighbour
	 * 	- MapPosition.EMPTY
	 */
	public ArrayList<Coord> getPossibleMoves(Coord myPosition){
		
		ArrayList<Coord> rez = new ArrayList<Coord>();
	
		/*
		 * 
		 * adug in rez daca vecinii care sunt EMPTY
		 * 
		 */
		
		for (int i = 0; i < 4; i++) {
			
			Coord vecin = Coord.getNeighbour(myPosition, Direction.fromInt(i));
			if (map.getCell(vecin.getX(), vecin.getY()) == MapPosition.EMPTY)
				rez.add(vecin);
			
		}
		
		return rez;
	}

	public void setSearchingMethod(Method searchingMethod) {
		this.searchingMethod = searchingMethod;
	}	
	
}
