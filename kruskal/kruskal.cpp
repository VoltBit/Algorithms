#include "graph.h"
using namespace std;

// void sort_edges(Graph* G){
// 	std::map < std::pair<int, int>, int >::iterator it, subit;
// 	for(it = G->files.begin(); it != G->files.end(); it++){
// 		for(subit = G->files.begin(); subit != G->files.end(); subit++){
			
// 		}
// 	}
// }

void read_input(const char* file_name, Graph* G){
	std::ifstream file_stream(file_name);
	file_stream >> G->N >> G->M;
	G->init_costs(INF);
	int i, aux1, aux2, cost;
	for(i = 0; i < G->N; i++) {
		Vertex new_node;
		new_node.p = -1;
		new_node.colour = 'w';
		new_node.content = i;
		new_node.dist = INF;
		G->nodes.push_back(new_node);
		std::list<int> l;
		G->adjacency_list.push_back(l);
	}
	while(file_stream >> aux1 >> aux2 >> cost){
		G->adjacency_list[aux1].push_back(aux2);
		G->edges.insert(make_pair(make_pair(aux1, aux2), cost));		
	}
	file_stream.close();
}

void kruskal(Graph* G){
	// std::sort(G->edges.begin(), G->edges.end(), cmp);
}

int main(){
	Graph G;
	G.storage_type = LIST;
	cout << "Kruskal algorithm" << endl;
	read_input("test.in", &G);
	// read_input("test.in", &G);
	G.display_structure();
	// kruskal(&G);
	return 0;
}