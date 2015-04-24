#include "graph.h"

Graph G;
Storage s;
int used_colours;
int costs[MAX_COL][MAX_COL] = {0};
int sorted_nodes[MAX_N];
int total_cost = 0, sums[MAX_COL], min = INF, sol[MAX_N], min_cost_greedy;;
long long fail_count = 0, fail_limit = 295;

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
	// printf("\n");
	// printf("max degree: %i\tmax colours: %i\n", max, G.C);
	return !(max > G.C);
}

void decolour(){
	int i;
	for(i = 0; i < G.N; i++){
		G.nodes[i].content = -1;
	}
}

void disp(){
	int i;
	for(i = 0; i < G.N; i++)
		printf("%i: %i\n", i, G.nodes[i].content);
	printf("\n");
}
int count_costs(){
	int i, j, sum = 0;
	for(i = 0; i < G.N; i++){
		for(j = i; j < G.N; j++){
			if(G.adjacency_matrix[i][j]) sum += costs[G.nodes[i].content][G.nodes[j].content];
		}
	}
	return sum;
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

bool valid(int node){
	int i;
	for(i = 0; i < G.N; i++){
		if(G.adjacency_matrix[node][i] && G.nodes[i].content == G.nodes[node].content) return false;
	}
	return true;
}

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

bool solver(int node){
	int j;
	// if(node > G.N) node = G.N - node;
	if(node == G.N) return true;
	else{
		int sorted_colours[MAX_COL];
		sort_colours(node, sorted_colours);
		for(j = 0; j < G.C; j++){
			G.nodes[node].content = sorted_colours[j];
			if(valid(node)){
				if(solver(node + 1)){
					total_cost += sums[j];
					return true;
				}
				G.nodes[node].content = 0;
			}
		}
	}
	return false;
}
void colorize(int best_config[MAX_N], int* best_cost){
	int i, min = INF;
	for(i = 0; i < G.C; i++){
		decolour();
		G.nodes[0].content = i;
		total_cost = 0;
		if(solver(1)){
			if(total_cost < min){
				save_config(best_config);
				*best_cost = total_cost;
				min = total_cost;
			}
			// debug_display();
		}
	}
	write_solution(best_config, *best_cost);
	printf("Finished colouring");
}

void sort_nodes(){
	int i;
	bool check;
	do{
		check = false;
		for(i = 0; i < G.N - 1; i++){
			if(G.degrees[i] > G.degrees[i + 1]){
				G.degrees[i] += G.degrees[i + 1];
				G.degrees[i + 1] = G.degrees[i] - G.degrees[i + 1];
				G.degrees[i] = G.degrees[i] - G.degrees[i + 1];
				sorted_nodes[i] += sorted_nodes[i + 1];
				sorted_nodes[i + 1] = sorted_nodes[i] - sorted_nodes[i + 1];
				sorted_nodes[i] = sorted_nodes[i] - sorted_nodes[i + 1];
				check = true;
			}
		}
	}while(check);	
}

int get_max_degree(){
	check_degrees();
	int i, max = -1;
	for(i = 0; i < G.N; i++){
		if(max < G.degrees[i]) max = G.degrees[i];
	}
	return max;
}

void init_config(int config[MAX_N]){
	int i;
	for(i = 0; i < G.N; i++){
		config[i] = 0;
	}
}

int config_cost(int config[MAX_N]){
	int i, j, sum = 0;
	for(i = 0; i < G.N; i++){
		for(j = 0; j < G.N; j++){
			if(G.adjacency_matrix[config[i]][config[j]])
				sum += costs[config[i]][config[j]];
		}
	}
}

bool successor(int node){
	if(G.nodes[node].content < G.C - 1){
		G.nodes[node].content++;
		return true;
	}
	return false;
}

bool valid_2(int node){
	int i;
	// if(fail_count == 296){
	// 	printf("fail count: %i\tcost: %i\n",fail_count, count_costs());
	// 	disp();
	// }
	if(count_costs() >= min_cost_greedy){
		return false;
	}
	for(i = 0; i < G.N; i++){
		if(G.adjacency_matrix[node][i] && G.nodes[i].content == G.nodes[node].content) return false;
	}
	return true;
}

void bck(int node){
	disp();
	if(node == G.N){
		total_cost = count_costs();
		if(min > total_cost){
			min = total_cost;
			printf("Cost: %i\n", total_cost);
			// disp();
			fail_count = 0;
		} else fail_count++;
		// printf("%lli: %i\n", fail_count, min);
		printf("Cost: %i\n", total_cost);
		return;
	}
	G.nodes[node].content = -1;
	while(successor(node)){
		if(fail_count > fail_limit && fail_limit > 0) return;
		if(valid_2(node)){
			bck(node + 1);
		}
	}
}

void explore_matrix(int node, int colour1, int colour2, bool *check){
	int i;
	G.nodes[node].colour = 'g';
	for(i = 0; i < G.N; i++) {
		if(i != node && G.adjacency_matrix[node][i] &&
		 G.nodes[i].content == G.nodes[node].content){
			// printf("??? \t%i\n", i);
			*check = false;
			return;
		}
		if(G.adjacency_matrix[node][i] && G.nodes[i].colour == 'w'){
			G.nodes[i].dist = G.nodes[node].dist + 1;
			G.nodes[i].p = &G.nodes[node];
			if(G.nodes[node].content == colour1) G.nodes[i].content = colour2;
			else G.nodes[i].content = colour1;
			explore_matrix(i, colour1, colour2, check);
		}
	}
	G.nodes[node].colour = 'b';
	printf("%i ", node);
}

bool determine_bipartiteness(int node){
	int i, j, colour1, colour2, min_col_cost = INF;
	bool check = true;
	for(i = 0; i < G.C; i++){
		for(j = 0; j < G.C; j++){
			if(costs[i][j] != 0 && costs[i][j] < min_col_cost){
				min = costs[i][j];
				colour1 = i;
				colour2 = j;
			}
		}
	}

	decolour();

	G.nodes[node].dist = 0;
	G.nodes[node].content = colour1;
	for(i = 0; i < G.N; i++){
		if(G.nodes[i].colour == 'w'){
			explore_matrix(i, colour1, colour2, &check);
			if(!check){
				// printf("\n!!!!\t%i\n", i);
				return false;
			}
			// printf("\n");
		}
	}
	return true;
}

int main(int argc, char *argv[]) {
	int best_greedy = INF, best_config[MAX_N];
	clock_t begin = clock();
	double elapsed_secs;
	s = MATRIX;
	read_input("tests/p1_test6.in");

	if(check_degrees()){
		printf("OK\n");
		decolour();
		colorize(best_config, &best_greedy);
		decolour();
		min_cost_greedy = best_greedy;
		bck(0);
		printf("\nAnswer greedy: %i\tAnswer backtrack: %i\n", best_greedy, min);
		// if(get_max_degree() == 2 && G.C >= 2){
		// 	decolour();
		// 	colorize(best_config, &best_greedy);
		// }
		if(determine_bipartiteness(0)){
			printf("Bipartieness test: True\n");
			G.dfs_matrix(0);
		} else printf("Bipartieness test: False\n");
	}
	else printf("FAIL\n-1\n");
	// int i;
	// for(i = 0; i < G.N; i++){
	// }
	// determine_bipartiteness();

	G.nodes[0].content = 7;
	G.nodes[1].content = 3;
	G.nodes[2].content = 8;
	G.nodes[3].content = 4;
	G.nodes[4].content = 7;
	G.nodes[5].content = 3;
	G.nodes[6].content = 8;
	G.nodes[7].content = 9;
	G.nodes[8].content = 6;
	G.nodes[9].content = 7;
	G.nodes[10].content = 4;
	G.nodes[11].content = 4;
	printf("Cost check %i\n", count_costs());

	clock_t end = clock();
	elapsed_secs = double(end - begin) / CLOCKS_PER_SEC;
	printf("\nTime: %f\n", elapsed_secs);
	return 0;
}