import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;


class StateComparator implements Comparator<State> {

	enum Algorithm {
		AStar
	}

	private Algorithm algorithm;

	/**
	 * Create a new StateComparator which compares two different states given
	 * the wanted algorithm.
	 * @param algorithm BestFirst or AStar
	 * @param solutionState desired solution state
	 */
	public StateComparator(Algorithm algorithm) {
		this.algorithm = algorithm;
	}

	private int computeF(State state) {
		int result = 0;
		switch(algorithm) {
		case  AStar:
			/* f(n) = g(n) + h(n) */
			/* g(n) = numarul de mutari din pozitia initiala */
			result = state.getDistance() + state.approximateDistance();
		}
		return result;
	}

	@Override
	public int compare(State arg0, State arg1) {
		return computeF(arg0) - computeF(arg1);
	}
}

public class MainClass {

	static State initialState;
	static State solutionState;

	public static void setData() {
		
		initialState = new State(3, 3, 0, 0, State.E, null, 0);
		solutionState = new State(0, 0, 3, 3, State.W, null, 0);
	}

	public static void main(String[] args) {
		/* setare stare initiala si finala */
		setData();

		Comparator<State> stateComparator = new StateComparator(StateComparator.Algorithm.AStar);
		PriorityQueue<State> open = new PriorityQueue<State>(1, stateComparator);
		
		/* Initial doar nodul de start este in curs de explorare */
		open.add(initialState);

		/* Pentru nodurile care au fost deja expandate. */
		ArrayList<State> closed = new ArrayList<State>();

		/* TODO: A* */
		ArrayList<State> toadd = new ArrayList<State>();
		
		while (!open.isEmpty()) {
			
			State s = open.poll();
			
			if (s.equals(solutionState))
				s.printPath();
			
			for (State state : s.expand()) {
				
				if (!open.contains(state) && !closed.contains(state))
					toadd.add(state);
				
				state.setParent(s);
				state.setDistance(s.getDistance() + 1);
				
			}
			
			closed.add(s);
			open.addAll(toadd);
			toadd.clear();
			
		}
		
	}

}
