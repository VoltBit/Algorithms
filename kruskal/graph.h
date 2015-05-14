#include <cstdlib>
#include <iostream>
#include <cstdio>
#include <list>
#include <vector>
#include <queue>
#include <fstream>
#include <string>
#include <map>
#define MAX_N 31
#define MAX_COL 11
#define INF 20000
typedef struct vertex{
		int p;
		char colour;
		int content;
		int dist;
}Vertex;

enum Storage{
	MATRIX, LIST
};

class Graph {
	public:
		Graph();
		Graph(int a, int b);
		Graph(int a, int b, int c);
		Graph(Storage s);
		~Graph();

		Storage storage_type;
		char adjacency_matrix[MAX_N][MAX_N];
		std::vector<vertex> nodes;
		std::vector< std::list<int> >adjacency_list;
		std::map < std::pair<int, int>, int >edges;
		int N, M, C;
		int degrees[MAX_N];
		int **costs;

		void create_graph(const char* file_name);
		void display_structure();
		void display_path(int node);
		void bfs_matrix(int origin);
		void dfs_matrix(int origin);
		void bfs_list(int origin);
		void dfs_list();
		void reset_colours();
		void reset_parents();
		void init_costs(int state);
		void clear_costs();
};