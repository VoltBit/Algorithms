package mapGenerator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import mapGenerator.Coord.Direction;

public class MapGenerator implements Map{



	private MapPosition map[][];
	private int width;
	private int height;
	private Coord start;
	private Coord finish;
	private BufferedReader br;

	protected MapGenerator(MapPosition map[][], int width, int height, Coord start, Coord finish){
		this.map = map;
		this.width = width;
		this.height = height;
		this.start = start;
		this.finish = finish;
	}

	public MapGenerator(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public MapGenerator() {
		this(10, 10);
	}

	private Boolean isEmpty(Coord posit)
	{
		if (map[posit.getX()][posit.getY()] == MapPosition.WALL)
		{
			Coord south = Coord.south(posit);
			Coord north = Coord.north(posit);
			Coord east = Coord.east(posit);
			Coord west = Coord.west(posit);

			int sum=map[south.getX()][south.getY()].getVal()+
					map[north.getX()][north.getY()].getVal()+
					map[east.getX()][east.getY()].getVal()+
					map[west.getX()][west.getY()].getVal();

			return (sum % MapPosition.BORDER.getVal() ) <= 1;
		}

		return false;
	}

	private Boolean posibNextCell(Coord posit)
	{
		return (isEmpty(Coord.south(posit)) ||
				isEmpty(Coord.north(posit)) ||
				isEmpty(Coord.east(posit) ) ||
				isEmpty(Coord.west(posit) ) );
	}

	private Coord getNextCell(Coord posit)
	{
		Coord aux = null;
		Boolean ok = false;
		Random rand = new Random();

		while ( !ok )
		{
			int gen = rand.nextInt(4);			
			aux = Coord.getNeighbour(posit, Direction.fromInt(gen));
			if ( isEmpty(aux) ) 
				ok = true;
		}
		return aux;
	}


	private void borderMap()	{
		for (int y = 0; y < width+1; y++) {
			map[0][y] = MapPosition.BORDER; 
			map[height+1][y] = MapPosition.BORDER;		
		}


		for (int x = 0; x < height+1; x++) {
			map[x][0] = MapPosition.BORDER;
			map[x][width+1] = MapPosition.BORDER; 
		}
	}


	public void printMap()	{
		for (int x = 0; x < height+2; x++)		{
			for(int y = 0; y < width+2; y++) 
				System.out.print(map[x][y].getVal() + "  ");
			System.out.println();
		}
	}

	public Coord getStartCoord()	{
		return start;
	}

	public Coord getFinishCoord()	{
		return finish;
	}


	public void genMatrix(boolean random){
		if ( random == false ){
			readMap();
			return;
		}

		this.map = new MapPosition[height+2][width+2];
		for (int i = 0; i < height+2; i++) {
			for (int j = 0; j < width+2; j++) {
				if ( i == 0 || i == height+1 || j == 0 || j == width+1){
					map[i][j] = MapPosition.BORDER;
					continue;
				}

				map[i][j] = MapPosition.WALL;
			}
		}

		start = new Coord(1,1);
		finish = new Coord(height,width);

		Coord cell = start;

		Stack<Coord> trail = new Stack<Coord>();  
		trail.push(cell);      

		borderMap();       
		map[cell.getX()][cell.getY()] = MapPosition.EMPTY;

		while( !trail.empty() ){
			cell = trail.peek();
			if ( posibNextCell(cell) ){
				cell = getNextCell(cell);
				trail.push(cell);
				map[cell.getX()][cell.getY()] = MapPosition.EMPTY;
			}
			else{
				trail.pop();
			}
		}

		createPathBetweenStartAndFinish();
	}

	private void readMap() {

		try {
			this.map = new MapPosition[height+2][width+2];
			
			br = new BufferedReader(new FileReader("example"));

			for ( int i = 0; i < height+2; i++ ){
				String[] aLine = br.readLine().split(" ");
				for (int j = 0; j < aLine.length; j++) {
					map[i][j] = MapPosition.get(Integer.parseInt(aLine[j]));
				}
			}
			
			start = new Coord(1,1);
			finish = new Coord(height,width);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void createPathBetweenStartAndFinish() {
		ArrayList<Coord> territory = new ArrayList<Coord>();
		ArrayList<Coord> frontier = new ArrayList<Coord>();
		frontier.add(start);

		while ( true ){
			if ( frontier.size() == 0 ){
				Coord node = removeWall(territory);
				map[node.getX()][node.getY()] = MapPosition.EMPTY;
				frontier.add(node);
				System.out.println("Node" + node.toString());
			}

			Coord node = frontier.get(frontier.size() - 1);
			frontier.remove(frontier.size()-1);
			if ( map[node.getX()][node.getY()] != MapPosition.EMPTY)
				continue;			

			territory.add(node);

			if ( node.equals(finish) ){
				return;
			}

			for ( int i = 0; i < 4; i++){
				Coord neighbour = Coord.getNeighbour(node, Direction.fromInt(i));
				if ( territory.contains(neighbour) )
					continue;
				frontier.add(neighbour);
			}		
		}

	}

	private Coord removeWall(ArrayList<Coord> territory) {
		Coord closest = null;
		int value = -1;
		for (Coord coord : territory) {
			if ( value < coord.getX() + coord.getY() ){
				closest = coord;
				value = coord.getX() + coord.getY();
			}
		}

		for ( int i = 0; i < 4; i++){
			Coord neighbour = Coord.getNeighbour(closest, Direction.fromInt(i));
			if ( map[neighbour.getX()][neighbour.getY()] == MapPosition.WALL ){
				map[neighbour.getX()][neighbour.getY()] = MapPosition.WALL;
				return neighbour;
			}
		}	

		return closest;

	}

	public MapPosition[][] getMap() {
		return map;
	}

	@Override
	public MapPosition getCell(int x, int y) {
		return map[x][y];
	}

	@Override
	public int getWidth() {
		return this.width;
	}

	@Override
	public int getHeight() {
		return this.height;
	}

	@Override
	public Map clone() {
		MapPosition newMap [][] = new MapPosition[height+2][width+2];
		for (int i = 0; i < height+2; i++) {
			for (int j = 0; j < width+2; j++) {
				newMap[i][j] = map[i][j];
			}
		}
		return new MapGenerator(newMap, width, height, start, finish);
	}

	@Override
	public void setCell(Coord poz, MapPosition me) {
		map[poz.getX()][poz.getY()] = me;		
	}

}

