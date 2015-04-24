package test;

import java.util.ArrayList;

import topologicalSorting.DFS;
import topologicalSorting.Kahn;
import graph.Graph;
import graph.Node;

public class Test {
	
	public static final String fileName = "date.in";

	public static void main(String[] args) {
		
		Graph dfsGraph = new Graph(fileName);
		DFS dfsMethod = new DFS(dfsGraph);
		ArrayList<Node> dfsOrder = dfsMethod.setOrdering();
		VerifySolution(dfsOrder, "DFS");
		
		Graph kahngraph = new Graph(fileName);
		Kahn kahnMethod = new Kahn(kahngraph);
		ArrayList<Node> kahnOrder = kahnMethod.setOrdering();
		VerifySolution(kahnOrder, "Kahn");		
	}

	
	private static void VerifySolution(ArrayList<Node> order, String method) {
		String errorMesage = VerifyBackArcs(order);
		if ( errorMesage != null ){
			System.out.println("Error! " + method + ": " + errorMesage);
		} else {
			System.out.println("Success! " + method);
		}
	}


	private static String VerifyBackArcs(ArrayList<Node> order) {
		Graph graph = new Graph(fileName);
		
		if ( order == null ) {
			return "No order received!";
		}
		
		for(int index = 0; index < order.size(); index++) {
			
			int currentNodeId = order.get(index).getId();
			Node currentNode = graph.getNodeWithId(currentNodeId);
			
			for(int previousIndex = 0; previousIndex < index; previousIndex++){
				
				int previousId = order.get(previousIndex).getId();
				Node previousNode =  graph.getNodeWithId(previousId);
				
				if ( currentNode.getOutNodes().contains(previousNode) ) {
					
					return previousNode.getId() + " <- " + currentNode.getId();
				}
			}
		}
		return null;
	}
}
