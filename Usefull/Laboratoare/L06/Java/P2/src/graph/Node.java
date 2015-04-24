package graph;

import java.util.ArrayList;

public class Node {
	
	private ArrayList<Node> inNodes;
	private ArrayList<Node> outNodes;
	private int id = -1;
	private int initTime = -1;
	private int finishTime = -1;
	
	public Node(int id){
		this.id = id;
		inNodes = new ArrayList<Node>();
		outNodes = new ArrayList<Node>();
	}
	
	private ArrayList<Node> getList(Type listType){
		if ( listType.equals(Type.IN) ){
			return inNodes;			
		} else {
			return outNodes;
		}
	}
	
	
	
	public void addNeighbour(Node neighbour, Type neighbourType){
		getList(neighbourType).add(neighbour);
	}
	
	public boolean removeNeighbour(Node neighbour, Type neighbourType){
		return getList(neighbourType).remove(neighbour);
	}

	public boolean isRootNode(){
		return inNodes.isEmpty();
	}
	
	public int getId() {
		return this.id;
	}
	
	public ArrayList<Node> getInNodes() {
		return inNodes;
	}

	public ArrayList<Node> getOutNodes() {
		return outNodes;
	}
	
	public enum Type {
		IN, OUT;
	}
	
	@Override
	public String toString(){
		String text =  id + " - ";
		for (Node node : outNodes) {
			text += node.getId() + " ";
		}
		return text;
	}

	public int getInitTime() {
		return initTime;
	}

	public void setInitTime(int initTime) {
		this.initTime = initTime;
	}

	public int getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(int finishTime) {
		this.finishTime = finishTime;
	}

	
	
}
