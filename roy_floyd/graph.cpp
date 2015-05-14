#include "graph.h"

Graph::Graph() {
	int i, j;
	for(i = 0; i < MAX_N; i++) {
		for(j = 0; j < MAX_N; j++)
			this->adjacency_matrix[i][j] = 0;
		this->degrees[i] = 0;
	}
	costs = NULL;
}

Graph::Graph(Storage s) {
	int i, j;
	this->storage_type = s;
	if(s == MATRIX)
		for(i = 0; i < MAX_N; i++) {
			for(j = 0; j < MAX_N; j++)
				this->adjacency_matrix[i][j] = 0;
			this->degrees[i] = 0;
		}
	costs = NULL;
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
	costs = NULL;
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
	costs = NULL;
}

Graph::~Graph(){
	clear_costs();
}

void Graph::create_graph(const char* file_name) {
	printf("Reading from: %s\n", file_name);
	FILE* file_stream = fopen(file_name, "r");
	if(!fscanf(file_stream, "%i%i", &this->N, &this->M)){
		printf("Data read error");
		return;
	}
	printf("%i %i\n", N,M);
	int i, aux1, aux2;
	for(i = 0; i < this->N; i++) {
		Vertex new_node;
		new_node.p = -1;
		new_node.colour = 'w';
		new_node.content = i;
		new_node.dist = INF;
		this->nodes.push_back(new_node);
	}
	if(this->storage_type == MATRIX)
		for(i = 0; i < this->M; i++){
			if(fscanf(file_stream, "%i%i", &aux1, &aux2)){
				this->adjacency_matrix[aux1][aux2] = 1;
				this->adjacency_matrix[aux2][aux1] = 1;
			}
		}
	else if(this->storage_type == LIST){
		for(i = 0; i < this->M; i++){
			if(fscanf(file_stream, "%i%i", &aux1, &aux2)){
				this->adjacency_list[aux1].push_back(aux2);
				this->adjacency_list[aux2].push_back(aux1);
			}
		}
	}
	fclose(file_stream);
}

void Graph::reset_colours(){
	int i;
	for(i = 0; i < this->N; i++)
		this->nodes[i].colour = 'w';
}

void Graph::reset_parents(){
	int i;
	for(i = 0; i < this->N; i++)
		this->nodes[i].p = -1;
}

void Graph::init_costs(int state){
	int i,j;
	costs = (int**)malloc(N * sizeof(int*));
	for(i = 0; i < N; i++){
		costs[i] = (int*)malloc(N * sizeof(int));
		for(j = 0; j < N; j++){
			costs[i][j] = state;
		}
	}
}

void Graph::clear_costs(){
	int i;
	if(costs == NULL) return;
	for(i = 0; i < N; i++)
		free(costs[i]);
	free(costs);
}

void Graph::display_structure(){
	int i;
	
	printf("Nodes: [%i]\n", this->N);
	for(i = 0; i < this->N; i++){
		std::list<int>::iterator it;
		printf("Node %i -> (", i);
		for(it = adjacency_list[i].begin(); it != adjacency_list[i].end(); it++){
			printf("%i,", *it);
		}
		if(adjacency_list[i].size() > 0)printf("\b)\n");
		else printf(")[%i]\n", int(adjacency_list[i].size()));
	}
}

void Graph::display_path(int node){
	printf("Path found by search: \n");
	do{
		printf("%i ", node);
		node = nodes[node].p;
	}while(nodes[node].dist != 0);
	printf("\n");
}

void Graph::bfs_matrix(int origin) {
	reset_colours();
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
				nodes[i].p = top;
			}
		}
		printf("%i ", top);
		nodes[top].colour = 'b';
		Q.pop();
	} printf("\n");
}

void Graph::bfs_list(int origin){
	reset_colours();
	std::queue<int> Q;
	std::list<int>::iterator it;
	Q.push(origin);
	nodes[origin].colour = 'g';
	nodes[origin].dist = 0;
	while(!Q.empty()){
		int top = Q.front();
		for(it = adjacency_list[top].begin(); it != adjacency_list[top].end(); it++) {
			if(nodes[*it].colour == 'w') {
				Q.push(*it);
				nodes[*it].colour = 'g';
				nodes[*it].p = top;
				nodes[*it].dist = nodes[*it].dist + 1;
			}
		}
		// printf("%i ", top);
		nodes[top].colour = 'b';
		Q.pop();
	}
}

void explore_matrix(int node, Graph* G){
	int i;
	G->nodes[node].colour = 'g';
	printf("%i ", node);
	for(i = node; i < G->N; i++) {
		if(G->adjacency_matrix[node][i] && G->nodes[i].colour == 'w'){
			G->nodes[i].dist = G->nodes[node].dist + 1;
			G->nodes[i].p = node;
			explore_matrix(i, G);
		}
	}
	G->nodes[node].colour = 'b';
	printf("%i ", node);
}

void Graph::dfs_matrix(int node){
	reset_colours();
	int i;
	this->nodes[node].dist = 0;
	for(i = 0; i < this->N; i++){
		if(this->nodes[i].colour == 'w'){
			explore_matrix(i, this);
			printf("\n");
		}
	}
}

void explore_list(int node, Graph* G){
	G->nodes[node].colour = 'g';
	std::list<int>::iterator it;
	for(it = G->adjacency_list[node].begin(); it != G->adjacency_list[node].end(); it++)
		if(G->nodes[*it].colour == 'w'){
			G->nodes[*it].p = node;
			G->nodes[*it].dist = G->nodes[node].dist + 1;
			explore_list(*it, G);
		}
	G->nodes[node].colour = 'b';
	// printf("%i[%i] ", node, G->nodes[node].dist);
}

void Graph::dfs_list(){
	reset_colours();
	int i;
	for(i = 0; i < this->N; i++)
		if(this->nodes[i].colour == 'w'){
			this->nodes[i].dist = 0;
			printf("\n");
			explore_list(i, this);
		}
}