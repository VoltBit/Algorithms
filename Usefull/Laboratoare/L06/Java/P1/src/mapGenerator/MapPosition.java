package mapGenerator;

import java.util.ArrayList;

public enum MapPosition {
	EMPTY(1), WALL(0), ME(10), BORDER(8);

	private final int val;
	private MapPosition(int v) { val = v; }
	public int getVal() { return val; }
	
	private static ArrayList<MapPosition> list = new ArrayList<MapPosition>();
	static{
		list.add(EMPTY);
		list.add(WALL);
		list.add(ME);
		list.add(BORDER);
	}
	
	public static MapPosition get(int val) {
		for (MapPosition el : list) {
			if ( el.getVal() == val )
				return el;
		}
		return null;
	}
}
