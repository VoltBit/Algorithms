package vizgraph;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.UndirectedOrderedSparseMultigraph;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GraphOps {
    public static Integer V;
    public static Integer E;
    public static ArrayList<Integer> vertexList = new ArrayList();
    public static ArrayList<Integer> edgeList = new ArrayList();
    public static Graph<Integer, String> g = new SparseGraph<Integer, String>();
//    public static Graph<Integer, String> g = new UndirectedOrderedSparseMultigraph<>();
    public static void parseFileIinput(String fileName) throws FileNotFoundException{
        Scanner inputStream = new Scanner(new File(fileName));
        V = inputStream.nextInt();
        E = inputStream.nextInt();
        Integer i, n1, n2;
        for(i = 0; i < V; i++)
            g.addVertex(i);
        for(i = 0 ; i < E; i++){
            n1 = inputStream.nextInt();
            n2 = inputStream.nextInt();
            g.addEdge("Edge " + i.toString(), n1, n2);
        }
    }
    
    public static void run(){
        try {
            parseFileIinput("input.in");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GraphOps.class.getName()).log(Level.SEVERE, null, ex);
        }
        Visualizer.display(g);
    }
}
