bool check(int node){
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
	if(node == G.N) return true;
	else{
		int sorted_colours[MAX_COL];
		sort_colours(node, sorted_colours);
		for(j = 0; j < G.C; j++){
			G.nodes[node].content = sorted_colours[j];
			if(check(node)){
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
void colorize(){
	int i, min = INF;
	for(i = 0; i < G.C; i++){
		decolour();
		G.nodes[0].content = i;
		total_cost = 0;
		if(solver(1)){
			if(total_cost < min){
				save_config();
				min_cost_greedy = total_cost;
				min = total_cost;
			}
		}
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

int config_cost(int config[MAX_N]){
	int i, j, sum = 0;
	for(i = 0; i < G.N; i++){
		for(j = 0; j < G.N; j++){
			if(G.adjacency_matrix[config[i]][config[j]])
				sum += costs[config[i]][config[j]];
		}
	}
	return sum;
}


bool debug_func1(){
	int i, j;
	for(i = 0; i < G.N; i++){
		for(j = 0 ; j < G.N; j++){
			if(G.adjacency_matrix[i][j] && G.nodes[i].content == G.nodes[j].content)
				return false;
		}
	}
	return true;
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
	int i, j, colour1 = -1, colour2 = -1, min_col_cost = INF;
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
				return false;
			}
		}
	}
	return true;
}