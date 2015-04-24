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

int total_cost = 0, sums[MAX_COL];

void write_solution(int cfg[], int cost){
	int i;
	printf("Total: %i\n", cost);
	for(i = 0; i < G.N; i++){
		printf("%i: %i\n", i, cfg[i]);
	}
	printf("\n");
}

void sort_colours(int node, int sorted_colours[]){
	int i, j, sum;
	bool check;

	for(i = 0; i < G.C; i++){
		sum = 0;
		for(j = 0; j < G.N; j++){
			if(G.adjacency_matrix[node][j] && G.nodes[j].content != -1)
				sum += costs[G.nodes[j].content][i];
		}
		sums[i] = sum;
		sorted_colours[i] = i;
	}

	do{
		check = false;
		for(i = 0; i < G.C - 1; i++){
			if(sums[i] > sums[i + 1]){
				sums[i] += sums[i + 1];
				sums[i + 1] = sums[i] - sums[i + 1];
				sums[i] = sums[i] - sums[i + 1];
				sorted_colours[i] += sorted_colours[i + 1];
				sorted_colours[i + 1] = sorted_colours[i] - sorted_colours[i + 1];
				sorted_colours[i] = sorted_colours[i] - sorted_colours[i + 1];
				check = true;
			}
		}
	}while(check);
}

bool backtrack(int node){
	int j;
	if(node == G.N) return true;
	else{
		int sorted_colours[MAX_COL];
		sort_colours(node, sorted_colours);
		for(j = 0; j < G.C; j++){
			G.nodes[node].content = sorted_colours[j];
			if(valid(node)){
				if(backtrack(node + 1)){
					total_cost += sums[j];
					return true;
				}
				G.nodes[node].content = 0;
			}
		}
	}
	return false;
}

void save_config(int cfg[]){
	int i;
	for(i = 0; i < G.N; i++){
		cfg[i] = G.nodes[i].content;
	}
}
void debug_display(){
	int i;
	printf("%i\n", total_cost);
	for(i = 0; i < G.N; i++){
		printf("%i: %i\n", i, G.nodes[i].content);
	}
}
void colorize(){
	int i = 1, min = INF, best_config[MAX_N], best_cost = INF;
	for(i = 0; i < G.C; i++){
		decolour();
		G.nodes[0].content = i;
		total_cost = 0;
		if(backtrack(1)){
			if(total_cost < min){
				save_config(best_config);
				best_cost = total_cost;
				min = total_cost;
			}
			debug_display();
		}
	}
	write_solution(best_config, best_cost);
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
	read_input("tests/p1_test2.in");

	if(check_degrees()){
		printf("OK\n");
		G.bfs_matrix(0);
		decolour();
		colorize();
	}
	else printf("FAIL\n-1\n");

	clock_t end = clock();
	double elapsed_secs = double(end - begin) / CLOCKS_PER_SEC;
	printf("\nTime: %f\n", elapsed_secs);
	return 0;
}