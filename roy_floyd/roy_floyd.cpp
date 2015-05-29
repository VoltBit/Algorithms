#include "graph.h"

std::string labels[MAX_N];

void read_input(const char* file_name, Graph* G){
	std::ifstream file_stream(file_name);
	file_stream >> G->N;
	std::getline(file_stream,labels[0]);
	G->init_costs(INF);
	int i, aux1, aux2;
	for(i = 0; i < G->N; i++) {
		Vertex new_node;
		new_node.p = -1;
		new_node.colour = 'w';
		new_node.content = i;
		new_node.dist = INF;
		G->nodes.push_back(new_node);
		std::list<int> l;
		G->adjacency_list.push_back(l);
		G->degrees[i] = 0;
		std::getline(file_stream, labels[i]);
	}
	file_stream >> G->M;
	while(file_stream >> aux1 >> aux2){
		G->adjacency_list[aux1].push_back(aux2);
		// G->adjacency_list[aux2].push_back(aux1);
		file_stream >> G->costs[aux1][aux2];
		// G->costs[aux2][aux1] = G->costs[aux1][aux2];
		G->costs[aux2][aux1] = INF;
	}
	file_stream.close();
	printf("Costuri: \n");
	for(i = 0; i < G->N; i++){
		for(int j = 0; j < G->N; j++)
			printf("%i\t", G->costs[i][j]);
		printf("\n");
	}
	printf("\n");
}

void display_path(int mat[MAX_N][MAX_N], int i, int j){
	std::cout << "[" << i << "]" << labels[i] << " ";
	if(i != j)
		display_path(mat, mat[i][j], j);
}

void display_all(int mat[MAX_N][MAX_N], Graph* G){
	int i, j;
	for(i = 0; i < G->N; i++){
		for(j = 0; j < G->N; j++){
			printf("(%i,%i)\t", i, j);
			display_path(mat, i, j);
			printf("\n");
		}
	}
}

void roy_floyd(Graph* G, int origin, int destination){
	int i, j, k;
	int P[MAX_N][MAX_N], Next[MAX_N][MAX_N];

	for(i = 0; i < G->N; i++)
		for(j = 0; j < G->N; j++){
			P[i][j] = G->costs[i][j];
			if(G->costs[i][j] != 0 && G->costs[i][j] != INF)
				Next[i][j] = j;
			else Next[i][j] = -1;
		}

	for(k = 0; k < G->N; k++)
		for(i = 0; i < G->N; i++)
			for(j = 0; j < G->N; j++)
				if(P[i][j] > P[i][k] + P[k][j]){
					P[i][j] = P[i][k] + P[k][j];
					Next[i][j] = Next[i][k];
				}

	for(i = 0; i < G->N; i++){
		for(j = 0; j < G->N; j++)
			printf("%i\t", P[i][j]);				
		printf("\n");
	}
	printf("Shortest path: \n");
	display_path(Next, origin, destination);
	// printf("\n");
	// display_all(Next, G);
}

int main(){
	Graph G;
	printf("Roy-Floyd Algorithm\n");
	read_input("test1.in", &G);
	G.display_structure();
	for(int i = 0; i < G.N; i++) std::cout << "[" << i << "]" << labels[i] << " ";
	std::cout << std::endl;
	roy_floyd(&G, 0, 5);
	// printf("Shortest path: \n");
	// for(int i = 0; i < (int)path.size(); i++){
	// 	std::cout << labels[path[i]] << "[" << path[i] << "]" << " ";
	// } std::cout << std::endl;
	return 0;
}