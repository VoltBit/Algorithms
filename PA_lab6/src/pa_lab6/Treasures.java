package pa_lab6;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Treasures {

    class Vertex{
        char colour;
        Integer content;
        Integer ID;
        Integer dist;
    }
    class Pair{
        Integer line;
        Integer row;
        public Pair(int line, int row){
            this.line = line;
            this.row = row;
        }
    }
    String[] treasureMap;
    Vertex[][] nodes = new Vertex[8][8];
    Integer[][] costs = new Integer[8][8];
    public void initNodes(){
        int i, j;
        for(i = 0; i < 8; i++){
            for(j = 0; j < 8; j++){
                nodes[i][j] = new Vertex();
                if(treasureMap[i].charAt(j) == '#'){
                    nodes[i][j].colour = 'b';
                    nodes[i][j].dist = -1;
                }
                else{
                    nodes[i][j].colour = 'w';
                    nodes[i][j].dist = 0;
                }
            }
        }
    }
    
    ArrayList<Pair> neighbours = new ArrayList<Pair>();
    
    public boolean getNeighbour(Pair node){
        neighbours.clear();
        int index1 = 0, index2 = 0;
        if(node.line > 0 && node.row > 0){
            neighbours.add(new Pair(node.line - 1, node.row - 1));
        } else if(node.line > 0) neighbours.add(new Pair(node.line - 1, 0));
        else if(node.row > 0) neighbours.add(new Pair(0, node.row - 1));
        else neighbours.add(new Pair(0, 0));
        
        if(node.line < 7 && node.row < 7)
            neighbours.add(new Pair(node.line + 1, node.row + 1));
        else if(node.line < 7)
            neighbours.add(new Pair(node.line + 1, 7));
        else if(node.row < 7)
            neighbours.add(new Pair(7, node.row + 1));
        else neighbours.add(new Pair(7, 7));
        
        return false;
    }
    
    public void bfs(Pair node){
        Integer i, j;
        LinkedList<Pair> Q = new LinkedList();
        Q.push(node);
        nodes[node.line][node.row].colour = 'g';
        while(!Q.isEmpty()){
            Pair top = Q.getFirst();
            getNeighbour(top);
            for(i = 0; i < neighbours.size(); i++){
                if(nodes[neighbours.get(i).line][neighbours.get(i).row].colour == 'w'){
                    nodes[neighbours.get(i).line][neighbours.get(i).row].dist = 
                            nodes[top.line][top.row].dist + 1;
                    
                    nodes[neighbours.get(i).line][neighbours.get(i).row].colour = 'g';
                    Q.push(neighbours.get(i));
                }
            }
            nodes[top.line][top.row].colour = 'b';
            Q.pop();
        }
    }
    
    void initCosts(){
        int i, j;
        for(i = 0 ; i < 8; i++){
            for(j = 0 ; j < 8; j++){
                if('.' == treasureMap[i].charAt(j))costs[i][j] = 0;
                if('#' == treasureMap[i].charAt(j))costs[i][j] = -1;
            }
        }
    }
    
    public void display(){
        int i, j;
        for(i = 0; i < 8; i++){
            for(j = 0; j < 8; j++){
//                System.out.print(nodes[i][j].dist + " " + nodes[i][j].colour + " ");
                System.out.print(nodes[i][j].dist + " ");
            }
            System.out.println("");
        }
    }
    
    public void run(){
        int i;
        treasureMap  = new String[8];
        nodes = new Vertex[8][8];
        for(i = 0; i < 8; i++)
            treasureMap[i] = new String();
        treasureMap[0] = "...#....";
        treasureMap[1] = "#..#..#.";
        treasureMap[2] = ".##.P..#";
        treasureMap[3] = "..#.#.#.";
        treasureMap[4] = "........";
        treasureMap[5] = "........";
        treasureMap[6] = "###...##";
        treasureMap[7] = "..P.....";
//        initCosts();
        initNodes();
        bfs(new Pair(4, 2));
        display();
    }
}
