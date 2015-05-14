package graphs;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Graphs {

    public static void main(String[] args) throws FileNotFoundException {
        Graph g = new Graph();
        g.parseInput("input.in", Graph.storageType.LIST);
        g.display();
        ArrayList ar = (ArrayList) g.getList();
        int i, j;
        for(i = 0; i < g.getSize(); i++){
            ArrayList<Vertex> l = (ArrayList) ar.get(i);
            for(j = 0; j < l.size(); j++){
                l.get(j).setColour(Vertex.colour.GREY);
            }
        }
        g.reset();
        int n = 10;
        Vertex origin = new Vertex(0, 1, null);
        for(i = 0; i < n; i++){
            g.add(origin, new Vertex(i, 1, null));
        }
//        GraphSearch.bfs(g, new Vertex(0, 1, null));
        
    }

}
