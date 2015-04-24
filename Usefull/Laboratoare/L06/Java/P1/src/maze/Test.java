package maze;

import ui.UI;
import mapGenerator.Map;
import mapGenerator.MapGenerator;
import maze.MazeResolver.Method;

public class Test {

	public static final int  width = 40;
	public static final int height = 40;
	//public static final Method method = Method.BFS;
	public static final Method method = Method.DFS;
	public static final boolean random = false;
	
	public static void main(String args[]){

		Map map = new MapGenerator(width, height);
		map.genMatrix(random);

		UI ui = new UI(map);

		//map.printMap();
		
		MazeResolver agent = new MazeResolver(map.clone(), ui, method);
		agent.resolve();
	}

}
