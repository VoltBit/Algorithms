package mapGenerator;

import java.util.TreeMap;

public class Coord {
	private int x;
	private int y;

	public Coord(){
	}

	public Coord(int x, int y){
		this.x = x;
		this.y = y;
	}

	public int setX(int x){
		this.x = x;
		return x;
	}

	public int getX(){
		return this.x;
	}

	public int setY(int y){
		this.y = y;
		return y;
	}

	public int getY(){
		return this.y;
	}

	public enum Direction {
		NORTH(0), SOUTH(1), EAST(2), WEST(3);

		private int index;

		private Direction(int index) {
			this.index = index;
		}

		private static TreeMap<Integer, Direction> ss = new TreeMap<Integer,Direction>();
		static {
			for(int i=0;i<values().length;i++)			{
				ss.put(values()[i].index, values()[i]);
			}
		}

		public static Direction fromInt(int i) {
	        return ss.get(i);
	    }
		
		public int getIndex(){
			return index;
		}
	}

	private static int directions[][]=
		{
		{1,0},	//NORTH
		{-1,0},	//SOUTH
		{0,1},	//EAST
		{0,-1},	//WEST
		};

	public static Coord getNeighbour(Coord position, Direction direction){
		return new Coord(
				position.x + directions[direction.getIndex()][0], 
				position.y + directions[direction.getIndex()][1]
				);
	}

	public static Coord north(Coord position){
		return new Coord(position.x+1, position.y);
	}

	public static Coord south(Coord position){
		return new Coord(position.x-1, position.y);
	}

	public static Coord east(Coord position){
		return new Coord(position.x, position.y+1);
	}

	public static Coord west(Coord position){
		return new Coord(position.x, position.y-1);
	}

	@Override
	public boolean equals(Object coord){
		Coord c = (Coord)coord;
		return c.x == this.x && c.y == this.y;			 
	}

	@Override
	public int hashCode(){
		return this.x * 11 + this.y * 17 ;
	}
	
	@Override
	public String toString(){
		return "[" + this.x + "," + this.y + "]";  
	}
}