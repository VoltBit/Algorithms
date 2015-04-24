package graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

public class Graph {

	private int nrNodes = 0;
	private ArrayList<Node> nodes;
	private Iterator<Node> rootNodeIterator = null;

	public  Graph(String fileName) {
		nodes = new ArrayList<Node>();
		readData(fileName);
		
	}

	
	public void readData ( String fileName ) {
		BufferedReader br = null;						
		
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(fileName));
			
			nrNodes = Integer.parseInt(br.readLine());
			createNodes(nrNodes);
			
			int nrArcs = Integer.parseInt(br.readLine());
			
			int src = -1;
			int dest= -1;
			for (int i = 0; i < nrArcs; i++) {
				sCurrentLine = br.readLine();
				src = Integer.parseInt(sCurrentLine.split(" ")[0]);
				dest = Integer.parseInt(sCurrentLine.split(" ")[1]);
				
				Node srcNode = nodes.get(src);
				Node destNode = nodes.get(dest);
				
				srcNode.addNeighbour(destNode, Node.Type.OUT);
				destNode.addNeighbour(srcNode, Node.Type.IN);				
			}
		} catch (Exception e) {		
			e.printStackTrace();
		} finally { 					
			
			try {
				if (br != null) br.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	private void createNodes(int nrNodes) {
		for(int i = 0; i < nrNodes; i++){
			nodes.add(new Node(i));
		}
	}
	
	public ArrayList<Node> getAllRootNodes(){
		ArrayList<Node> rootNodes = new ArrayList<Node>();
		for (Node node : nodes) {
			if ( node.isRootNode() )
				rootNodes.add(node);
		}
		return rootNodes;
	}
	
	public ArrayList<Node> getAllNodes(){
		return nodes;
	}
	
	public Node getNextRootNode(){
		if ( rootNodeIterator == null )
			rootNodeIterator = nodes.iterator();
		
		if ( rootNodeIterator.hasNext() ) {
			Node node = rootNodeIterator.next();
			if ( node.isRootNode() ){
				return node;
			} else {
				return getNextRootNode();
			}
		}
		return null;
	}
	
	public void resetRootIterator(){
		rootNodeIterator = null;
	}
	
	@Override
	public String toString(){
		return nodes.toString();
	}


	public Node getNodeWithId(int id) {
		for (Node node : nodes) {
			if ( node.getId() == id )
				return node;
		}
		return null;
	}
}
