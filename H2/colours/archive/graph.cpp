#include "graph.h"

Graph::Graph() {
	int i, j;
	for(i = 0; i < MAX_N; i++) {
		for(j = 0; j < MAX_N; j++)
			this->adjacency_matrix[i][j] = 0;
		this->degrees[i] = 0;
	}
	// this->adjacency_list = new std::vector<vector>[MAX_N];
}

Graph::Graph(Storage s) {
	int i, j;
	if(s == MATRIX)
		for(i = 0; i < MAX_N; i++) {
			for(j = 0; j < MAX_N; j++)
				this->adjacency_matrix[i][j] = 0;
			this->degrees[i] = 0;
		}
	else
		printf("List graph not yet defined!");
}

Graph::Graph(int N, int M) {
	int i, j;
	this->N = N;
	this->M = M;
	for(i = 0; i < N; i++) {
		for(j = 0; j < N; j++)
			this->adjacency_matrix[i][j] = 0;
		this->degrees[i] = 0;
	}
}

Graph::Graph(int N, int M, int C) {
	int i, j;
	this->N = N;
	this->M = M;
	this->C = C;
	for(i = 0; i < N; i++) {
		for(j = 0; j < N; j++)
			this->adjacency_matrix[i][j] = 0;
		this->degrees[i] = 0;
	}
}

Graph::~Graph(){}

void Graph::bfs_matrix(int origin) {
	int i;
	std::queue<int> Q;
	Q.push(origin);
	nodes[origin].colour = 'g';
	nodes[origin].dist = 0;
	while(!Q.empty()){
		int top = Q.front();
		for(i = 0; i < N; i++) {
			if(adjacency_matrix[top][i] && nodes[i].colour == 'w'){
				Q.push(i);
				nodes[i].colour = 'g';
				nodes[i].dist = nodes[top].dist + 1;
				nodes[i].p = &nodes[top];
			}
		}
		printf("%i ", top);
		nodes[top].colour = 'b';
		Q.pop();
	} printf("\n");
}

void explore_matrix(int node, Graph* G){
	int i;
	G->nodes[node].colour = 'g';
	for(i = node; i < G->N; i++) {
		if(G->adjacency_matrix[node][i] && G->nodes[i].colour == 'w'){
			G->nodes[i].dist = G->nodes[node].dist + 1;
			G->nodes[i].p = &G->nodes[node];
			explore_matrix(i, G);
		}
	}
	G->nodes[node].colour = 'b';
	printf("%i ", node);
}

void Graph::dfs_matrix(int node){
	int i;
	this->nodes[node].dist = 0;
	for(i = 0; i < this->N; i++){
		if(this->nodes[i].colour == 'w'){
			explore_matrix(i, this);
			printf("\n");
		}
	}
}