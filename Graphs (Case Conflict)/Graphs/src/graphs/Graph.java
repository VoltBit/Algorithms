package graphs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Graph<T> {
    private Map<T, Set<T>> adjacencyList;
    private Object[][] adjacencyMatrix;
    private Integer E; /* number of edges */
    private Integer V; /* number of vertices */
    
    public static enum storageType{
        MATRIX, LIST;
    }
    
    public Graph(){
        this.adjacencyList = new HashMap<T, Set<T>>();
    }
    
    public boolean add(T parent, T child){
        this.adjacencyList.put(child, null);
        return true;
    }
    
    private void initializeList(){
        int i;
        for(i = 0; i < this.V; i++)
            this.adjacencyList.add(new ArrayList());
    }
    
    public Integer getSize(){
        return this.V;
    }
    
    public List<List<Vertex>> getList(){
        return this.adjacencyList;
    }
    
    
    public Object[][] getMatrix(){
        return this.adjacencyMatrix;
    }
    
    public void setColor(){
        
    }
    
    public void reset(){
        int i;
        Iterator<Vertex> itr;
        for(i = 0; i < this.V; i++){
            itr = this.adjacencyList.get(i).iterator();
            while(itr.hasNext()){
                itr.next().c = Vertex.colour.WHITE;
            }
        }
    }
    
    public boolean parseInput(String fileName, storageType... storeType) throws FileNotFoundException{
        Scanner inputStream = new Scanner(new File(fileName));
        this.E = inputStream.nextInt();
        this.V = inputStream.nextInt();
        initializeList();
        
        int i;
        switch(storeType.length){
            case 0: /* adjacency         frame.setSize(new Dimension(200, 200));list is used by default */
                for(i = 0; i < this.E; i++){
                    int index = inputStream.nextInt();
                    this.adjacencyList.get(index).add(new Vertex(i, 1, null));
                }
            break;
            case 1:
                if(storeType[0] == Graph.storageType.LIST)
                    for(i = 0; i < this.E; i++){
                        int index = inputStream.nextInt();
                        this.adjacencyList.get(index).add(new Vertex(i, 1, null));
                    }
                else
                    System.out.println("Matrix input not defined yet.");
                break;
            default:
                System.err.println("Wrong input for input parser.");
                return false;
        }
        return true;
    }
    
    public void display(){
        int i;
        for(i = 0; i < this.V; i++){
            System.out.println("Node " + i + ": " + this.adjacencyList.get(i));
        }
    }
}
