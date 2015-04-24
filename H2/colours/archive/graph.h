#include <cstdlib>
#include <iostream>
#include <cstdio>
#include <list>
#include <queue>
#define MAX_N 31
#define MAX_COL 11
#define INF 65535
typedef struct vertex{
		vertex* p;
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

		char adjacency_matrix[MAX_N][MAX_N];
		std::vector<vertex> nodes;
		std::list<int> *adjacency_list;
		int N, M, C;
		int degrees[MAX_N];

		void bfs_matrix(int origin);
		void dfs_matrix(int origin);

};