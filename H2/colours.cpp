#include "graph.h"

Graph G;
Storage s;
int costs[MAX_COL][MAX_COL] = {0};
int best_config[MAX_N];
int total_cost = 0, min = INF;
long long fail_count = 0, fail_limit = 0;

void read_input(const char* fileName) {
	FILE* file_stream = fopen(fileName, "r");
	if(!fscanf(file_stream, "%i%i%i", &G.N, &G.M, &G.C)){
		printf("Data read error");
		return;
	}
	int i, aux1, aux2;
	for(i = 0; i < G.N; i++) {
		Vertex new_node;
		new_node.p = NULL;
		new_node.colour = 'w';
		new_node.content = i;
		new_node.dist = INF;
		G.nodes.push_back(new_node);
	}
	for(i = 0; i < G.M; i++){
		if(fscanf(file_stream, "%i%i", &aux1, &aux2)){
			G.adjacency_matrix[aux1][aux2] = 1;
			G.adjacency_matrix[aux2][aux1] = 1;
		}
	}
	int c1, c2, cost;
	for(i = 0; i < G.C * (G.C - 1) / 2; i++){
		if(fscanf(file_stream, "%i%i%i", &c1, &c2, &cost)){
			costs[c1][c2] = cost;
			costs[c2][c1] = cost;
		} else printf("Cost table read error");
	}
	fclose(file_stream);
}
/* Writes both best cost and best configuration if parameter is true or only -1
	otherwise. */
void write_sol(bool ok){
	FILE* file_stream = fopen("kcol.out", "w");
	int i;
	fprintf(file_stream, "%i\n", min);
	if(ok)
		for(i = 0; i < G.N; i++)
			fprintf(file_stream, "%i %i\n", i, best_config[i]);
	fclose(file_stream);
}
/* Function used to save a configuration for which the cost us minimal. */
void save_config(){
	int i;
	for(i = 0; i < G.N; i++){
		best_config[i] = G.nodes[i].content;
	}
}
/* Calculates degrees of all nodes and returs true if no degree is greater than
	the total number of colours or false otherwise */
bool check_degrees(){
	int i, j, max = -1;
	for(i = 0; i < G.N; i++) G.degrees[i] = 0;
	for(i = 0; i < G.N; i++){
		for(j = 0; j < G.N; j++)
			if(G.adjacency_matrix[i][j] != 0) {
				G.degrees[i]++;
				if(max < G.degrees[i]) max = G.degrees[i];
			}
	}
	return !(max > G.C);
}
/* Removes the colouring. */
void decolour(){
	int i;
	for(i = 0; i < G.N; i++){
		G.nodes[i].content = -1;
	}
}
/* Makes the sums of the costs for current configuration. */
int count_costs(){
	int i, j, sum = 0;
	for(i = 0; i < G.N; i++){
		for(j = i; j < G.N; j++){
			if(G.adjacency_matrix[i][j] && G.nodes[i].content != -1 && G.nodes[j].content != -1)
				sum += costs[G.nodes[i].content][G.nodes[j].content];
		}
	}
	return sum;
}
/* Only counts the cost of a configuration up until a given node. */
int count_costs_until(int node){
	int i, j, sum = 0;
	for(i = 0; i <= node; i++){
		for(j = i; j < node; j++){
			if(G.adjacency_matrix[i][j]) sum += costs[G.nodes[i].content][G.nodes[j].content];
		}
	}
	return sum;
}
/* Backtracking function - tells if there is a higher number colour and if so,
	assigns it to the current node. */
bool successor(int node){
	if(G.nodes[node].content < G.C - 1){
		G.nodes[node].content++;
		return true;
	}
	return false;
}
/* Removes the colouring for the nodes following the one given as parameter. 
	required for computing the cost of an incomplete coloring correctly. */
void reset_next(int node){
	int i;
	for(i = node + 1; i < G.N; i++){
		G.nodes[i].content = -1;
	}
}
/* Backtracking specific - has two criteria of evaluation: adjacent colours need
	to be different and the cost resulted from the current  colouring is smaller
	than the best found yet. */
bool valid(int node){
	int i;
	for(i = 0; i < G.N; i++){
		if(G.adjacency_matrix[node][i] && G.nodes[i].content == G.nodes[node].content) return false;
	}
	if(count_costs_until(node + 1) >= min){
		return false;
	}
	return true;
}
void bck(int node){
	if(node == G.N){
		total_cost = count_costs();
		if(min > total_cost){
			min = total_cost;
			save_config();
			fail_count = 0;
		} else fail_count++;
	}
	else {
		G.nodes[node].content = -1;
		while(successor(node)){
			/* Used to control the running time of the backtracking, by default 
				it is set to unlimited - the backtracking technique exhaust 
				all the possibilities. */
			if(fail_count > fail_limit && fail_limit > 0) return;
			if(valid(node)){
				bck(node + 1);
			}
		}
		reset_next(node);
	}
}
int main(int argc, char *argv[]) {
	// clock_t begin = clock();
	// double elapsed_secs;
	/* The algoritm uses an edge matrix because of better access speed and relatively small input. */
	s = MATRIX;
	//read_input("tests/p1_test6.in");
	read_input("kcol.in");
	if(check_degrees()){
		decolour();
		bck(0);
		// printf("Answer: %i\n", min);
		if(min != INF){ 
			write_sol(true);
		}
		else {
			min = -1;
			write_sol(false);
		}
	}
	else{
		min = -1;
		write_sol(false);
	}
	// clock_t end = clock();
	// elapsed_secs = double(end - begin) / CLOCKS_PER_SEC;
	// printf("\nTime: %f\n", elapsed_secs);
	return 0;
}
