package graphs;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class GraphSearch {
    
    public static void dfs(){
        
    }
    LinkedList test = new LinkedList();
    public static void bfs(Graph G, Vertex s){
        Vertex auxV;
        ArrayList<Vertex> auxAdj = new ArrayList<Vertex>();
        int i;
        List<List<Vertex>> lists = G.getList();
        G.reset();
        s.setColour(Vertex.colour.GREY);
        s.time = 0;
        GraphQueue Q = new GraphQueue();
        Q.add(s);
        while(!Q.isEmpty()){
            auxV = Q.poll();
            auxAdj = (ArrayList<Vertex>) lists.get(auxV.id);
            for(i = 0 ; i < auxAdj.size(); i++){
                if(auxAdj.get(i).c == Vertex.colour.WHITE){
                    auxAdj.get(i).setColour(Vertex.colour.GREY);
                    auxAdj.get(i).time = auxV.time + 1;
                    auxAdj.get(i).pred = auxV;
                    Q.add(auxAdj.get(i));
                }
            }
            auxV.setColour(Vertex.colour.BLACK);
        }
    }
}
