#include "graph.h"

Graph G;
Storage s;
int used_colours;
int costs[MAX_COL][MAX_COL] = {0};

void read_input(const char* fileName) {
	FILE* file_stream = fopen(fileName, "r");
	fscanf(file_stream, "%i%i%i", &G.N, &G.M, &G.C);
	int i, aux1, aux2;
	for(i = 0; i < G.N; i++) {
		Vertex new_node;
		new_node.p = NULL;
		new_node.colour = 'w';
		new_node.content = i;
		new_node.dist = INF;
		G.nodes.push_back(new_node);
	}
	if(s == MATRIX)
		for(i = 0; i < G.M; i++){
			fscanf(file_stream, "%i%i", &aux1, &aux2);
			G.adjacency_matrix[aux1][aux2] = 1;
			G.adjacency_matrix[aux2][aux1] = 1;
		}
	else if(s == LIST){
		for(i = 0; i < G.M; i++){
			fscanf(file_stream, "%i%i", &aux1, &aux2);
			G.adjacency_list[aux1].push_back(aux2);
			G.adjacency_list[aux2].push_back(aux1);
		}
	}
	int c1, c2, cost;
	for(i = 0; i < G.C * (G.C - 1) / 2; i++){
		fscanf(file_stream, "%i%i%i", &c1, &c2, &cost);
		costs[c1][c2] = cost;
		costs[c2][c1] = cost;
	}
}

bool check_degrees(){
	int i, j, max = -1;
	for(i = 0; i < G.M; i++){
		for(j = 0; j < G.M; j++)
			if(G.adjacency_matrix[i][j] != 0) {
				G.degrees[i]++;
				if(max < G.degrees[i]) max = G.degrees[i];
			}
	}
	printf("\n");
	return !(max > G.C);
}

void decolour(){
	int i;
	for(i = 0; i < G.N; i++){
		G.nodes[i].content = -1;
	}
}

bool valid(int node){
	int i;
	for(i = 0; i < G.N; i++){
		if(G.adjacency_matrix[node][i] && G.nodes[i].content == G.nodes[node].content) return false;
	}
	return true;
}

void write_solution(){
	int i;
	for(i = 0; i < G.N; i++){
		printf("%i: %i\n", i, G.nodes[i].content);
	}
	printf("\n");
}

bool backtrack(int node){
	int j;
	if(node == G.N) return true;
	else{
		for(j = 0; j < used_colours; j++){
			G.nodes[node].content = j;
			if(valid(node)){
				if(backtrack(node + 1)) return true;
				G.nodes[node].content = 0;
			}
		}
	}
	return false;
}

void colorize(){
	used_colours = 1;
	for(used_colours = 1; used_colours <= G.C; used_colours++)
		if(backtrack(0)){
			write_solution();
			// break;
		}
	printf("Finished colouring");
}

void explore_list(int node){
	G.nodes[node].colour = 'g';
	std::list<int>::iterator i;
	for(i = G.adjacency_list[node].begin(); i != G.adjacency_list[node].end(); i++) {
		if(G.nodes[*i].colour == 'w') {
			G.nodes[*i].dist = G.nodes[node].dist + 1;
			G.nodes[*i].p = &G.nodes[node];
			explore_list(*i);
		}
	}
	G.nodes[node].colour = 'b';
}

void dfs_list(int node) {
	int i;
	for(i = 0; i < G.N; i++) {
		if(G.nodes[i].colour == 'w') explore_list(i);
	}
}

int main(int argc, char *argv[]) {
	clock_t begin = clock();

	s = MATRIX;
	read_input("tests/p1_test7.in");

	if(check_degrees()){
		printf("OK\n");
		decolour();
		colorize();
	}
	else printf("FAIL\n-1\n");

	clock_t end = clock();
	double elapsed_secs = double(end - begin) / CLOCKS_PER_SEC;
	printf("\nTime: %f\n", elapsed_secs);
	return 0;
}