/**
 * Proiectarea Algoritmilor, 2013
 * Lab 10: Flux Maxim
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;

public class Lab10 {

  /* Fisiere de intrare */
  final static String TASK_1_INPUT_FILE = "GraphBinary.in";
  final static String TASK_2_INPUT_FILE = "GraphDegree.in";

  /* Sursa si destinatia pentru flux */
  final static int flowSource = 0;
  final static int flowDest = 12;

  final static int NONE = -1;
private static Scanner inputScanner;

  public static ArrayList<Integer> bfs(Graph g, int u, int v) {
    /* Ne vom folosi de vectorul de parinti pentru a spune daca un nod a fost
     * adaugat sau nu in coada. */
    ArrayList<Integer> parent = new ArrayList<Integer>(g.getSize());

    for (int i = 0; i < g.getSize(); ++i) {
      parent.add(NONE);
    }

    LinkedList<Integer> q = new LinkedList<Integer>();
    q.add(u);

    while (parent.get(v) == NONE && q.size() > 0) {
      int node = q.get(0);
      q.remove(0);

      for (int i = 0; i < g.getSize(); ++i) {
        if (g.capacityMatrix[node][i] > 0 && parent.get(i) == NONE) { // - g.flow[node][i]
          parent.set(i, node);
          q.add(i);
        }
      }
    }

    /* Daca nu s-a atins destinatia, atunci nu mai exista drumuri de crestere. */

    if (parent.get(v) == NONE) {
      return new ArrayList<Integer>(0);
    }

    /* Reconstituim drumul de la destinatie spre sursa. */
    ArrayList<Integer> returnValue = new ArrayList<Integer>();
    for (int node = v; ; node = parent.get(node)) {
      returnValue.add(0, node);

      if (node == u) {
        break;
      }
    }

    return returnValue;
  }

  public static int saturate_path(Graph g, ArrayList<Integer> path) {
    /* Niciodata nu ar trebui sa se intample asta pentru ca sursa si destinatia
     * sunt noduri distincte si cel putin unul dintre ele se afla in path. */
    if (path.size() < 2) {
      return 0;
    }

    /* Determinam fluxul maxim prin drum. */
    int flow = g.capacityMatrix[path.get(0)][path.get(1)];
    for (int i = 0; i < path.size() - 1; ++i) {
      int u = path.get(i), v = path.get(i + 1);
      /* TODO - Determinati fluxul in functie de capacitata muchiei (u, v) */
      
      if (flow > g.capacityMatrix[u][v]) {
    	  flow = g.capacityMatrix[u][v];
      }
      
    }

    /* Si il saturam in graf. */
    for (int i = 0; i < path.size() - 1; ++i) {
      int u = path.get(i), v = path.get(i + 1);

      /* TODO - Modificati fluxul in functie de capacitatea muchiei (u, v) */
      
      g.capacityMatrix[u][v] -= flow;
      g.capacityMatrix[v][u] = g.capacityMatrix[u][v] * (-1);

      
    }

    /* Raportam fluxul cu care am saturat graful. */
    return flow;
    
  }

  public static int maxFlow(Graph g, int u, int v) {
    int maxFlow = 0;

    /* Vom incerca in mod repetat sa determinam drumuri de crestere folosind
     * BFS si sa le saturam pana cand nu mai putem determina un astfel de drum in
     * graf. */
    while (true) {
      /* Determina drum de crestere. */
      ArrayList<Integer> saturation_path = bfs(g, u, v);

      /* TODO - In functie de saturation_path determinati daca fluxul trebuie
       * marit sau trebuie iesit din while */
      
      if (saturation_path.isEmpty())
    	  break;
      
      maxFlow += saturate_path(g, saturation_path);
      
    }

    return maxFlow;
  
  }

  public static void minCut(Graph g, int u, int v) {
	  
    /* Facem o parcurgere BFS din nodul sursa si punem nodurile atinse de
     * parcurgere in source_set. Toate celelalte noduri se vor afla in
     * sink_set. */

    ArrayList<Boolean> in_queue = new ArrayList<Boolean>();
    for (int i = 0; i < g.getSize(); ++i) {
      in_queue.add(false);
    }
    LinkedList<Integer> q = new LinkedList<Integer>();
    q.add(u);
    in_queue.set(u, true);

    /* Rulam BFS din sursa si marcam nodurile atinse. */
    while (q.size() > 0) {
      int node = q.get(0);
      q.remove(0);

      for (int i = 0; i < g.getSize(); ++i) {
        if (in_queue.get(i) == false && g.capacityMatrix[node][i] > 0) {
          in_queue.set(i, true);
          q.add(i);
        }
      }
    }

    System.out.println("The minimum cut associated with the flow yields:");
    /* TODO - In functie de marcajele obtinute de la BFS-ul anterior determinati
     * muchiile ce fac parte din taietura minima */
    
    ArrayList<Integer> multimeaS = new ArrayList<Integer>();
    
    for (int nod : g.getNodes())
    	if (in_queue.get(nod))
    		multimeaS.add(nod);
    
    for (Graph.Edge muchie : g.getEdges())
    	if (in_queue.get(muchie.x) && !in_queue.get(muchie.y)) {
    		
    		boolean il_bag = true;
    		for (int nod : multimeaS)
    			if (g.isEdge(muchie.y, nod)) {
    				il_bag = false;
    				break;
    			}
    		
    		if (il_bag)
    			System.out.println("(" + muchie.x + ", " + muchie.y + ")");
    		
    	}
    		
  }

  static void compute_path(Graph g,
                           int cur_node,
                           boolean[][] usedEdge,
                           ArrayList<Integer> path) {
    path.add(cur_node);

    for (int i = 0; i < g.getSize(); ++i) {
      /* TODO - Selctati o posibila urmatoare muchie care sa faca parte din
       * drumul curent */
    	
    	for (int vecin : g.getNeighbours(cur_node)) {
    		
    		if (!usedEdge[cur_node][vecin]) {
    			usedEdge[cur_node][vecin] = true;
    			path.add(vecin);
    			cur_node = vecin;
    			break;
    		}
    		
    	}
    	
    }
  }

  public static void disjointPaths(Graph g, int u, int v) {
    boolean[][] usedEdge = new boolean[g.getSize()][g.getSize()];

    for (int i = 0; i < g.getSize(); ++i) {
      for (int j = 0; j < g.getSize(); ++j) {
        usedEdge[i][j] = false;
      }
    }

    int nr_path = 0;

    for (int i = 0; i < g.getSize(); ++i) {
      if (g.isSaturated(u, i)) {
        ArrayList<Integer> new_path = new ArrayList<Integer>(g.getSize());
        new_path.add(u);

        /* Incercam sa determinam un drum ce pleaca catre sink pe directia
         * (source, i) */
        compute_path(g, i, usedEdge, new_path);

        ++nr_path;
        System.out.print("Path " + nr_path + ":");
        for (int j = 0; j < new_path.size(); j++) {
          System.out.print(" " + new_path.get(j));
        }
        System.out.println();
      }
    }
  }

  public static void main(String[] args) throws FileNotFoundException {
    /* Creem graful din primul fisier de intrare */
    Graph g = new Graph(TASK_1_INPUT_FILE, Graph.Type.DIRECTED);

    /* Calculam fluxul maxim de date care poate fi suportat de retea
     * intre nodurile 0 si 12. */
    int flow = maxFlow(g, flowSource, flowDest);

    /* Calculam si afisam o taietura minimala a grafului. */
    minCut(g, flowSource, flowDest);

    /* Printam un numar maximal de drumuri disjuncte intre aceleasi doua noduri
     * din graf. */
    System.out.println("Maximum number of disjoint paths from source to sink "
        + flow);

    disjointPaths(g, flowSource, flowDest);

    inputScanner = new Scanner(new File(TASK_2_INPUT_FILE));

    int nr_nodes = inputScanner.nextInt();

    /* Vom forma urmatorul graf:
               o1       i1
               o2       i2
               .        .
     source    .        .     sink
               .        .
               on       in

    In partea stanga vom controla gradul de iesire al fiecarui nod -
    capacitatea de la source la oi va fi numarul maxim de muchii ce pot iesi
    din oi, adica exact gradul de iesire pe care acel nod il poate avea

    In partea stanga vom controla gradul de intrare al fiecarui nod -
    capacitatea de la ii la sink va fi numarul maxim de muchii ce pot intra
    in ii, adica exact gradul de intrare pe care acel nod il poate avea.

    Pompand flux maxim, vom umple capacitatile, deci vom satisface conditiile
    pentru gradele de intrare si de iesire.
    */

    /* TODO - Creati-va structura de graph cu un numar de noduri corespunzatoare */
    // Graph ...
    
    Graph g2 = new Graph(2 * nr_nodes + 2,  Graph.Type.DIRECTED);

    for (int i = 1; i <= nr_nodes; ++i) {
      int in_degree = inputScanner.nextInt(),
          out_degree = inputScanner.nextInt();

      /* TODO - Adaugati muchiile corespunzatoare care sa reliefeze gradul de
       * intrare si de iesire al nodului curent */
      
      g2.addEdge(0, i, out_degree);
      g2.addEdge(nr_nodes + i, 2 * nr_nodes + 1, in_degree);
      
    }

    for (int i = 1; i <= nr_nodes; ++i) {
      for (int j = nr_nodes + 1; j <= 2 * nr_nodes; ++j) {
        if (i != j - nr_nodes) {
          /* TODO - Adaugati muchiile corespunzatoare intre noduri */
        	
        	g2.addEdge(i, j, 1);
        	
        }
      }
    }
    
    /* TODO - Calculati fluxul maxim in graful creat */
    System.out.println();
    int flux2 = maxFlow(g2, 0, 2 * nr_nodes + 1);
    System.out.println("maxFlow Problema 2: " + flux2);
    
    System.out.println("List of possible edges: ");
    
    for (int i = 1; i <= nr_nodes; ++i) {
      for (int j = nr_nodes + 1; j <= 2 * nr_nodes; ++j) {
        /* TODO - Afisati muchiile gasite in urma algoritmului de flux */
    	  
    	  if (g2.capacityMatrix[i][j] == 0 && g2.isEdge(i, j))
    		  System.out.println("(" + i + ", " + (j - nr_nodes) + ")");
    	  
      }
    }

  }
}
