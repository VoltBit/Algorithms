#include "graph.h"
using namespace std;
std::string labels[MAX_N];
std::vector< std::pair<int,int> > E;

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
		file_stream >> G->costs[aux1][aux2];
		G->costs[aux2][aux1] = INF;
		G->edges.insert(make_pair(make_pair(aux1, aux2), make_pair(G->costs[aux1][aux2], 'w')));
	}
	file_stream.close();
}

void display_path(Graph* G, int destination){
	for(int i = 0; i < G->N; i++){
		cout << G->nodes[i].p << " ";
	}
	cout << endl;
	cout << "destination: " << destination << " next: " << G->nodes[destination].p << endl;
}

void bellman_ford(Graph* G, int origin, int destination){
	int i, j;
	G->reset_colours();
	G->reset_parents();
	std::list<int>::iterator it;
	G->nodes[origin].dist = 0;
	G->nodes[origin].colour = 'b';
	G->nodes[origin].p = -1;
	map < pair<int, int>, pair<int, char> >::iterator mit;
	for(it = G->adjacency_list[origin].begin(); it != G->adjacency_list[origin].end(); it++){
		G->nodes[*it].dist = G->costs[origin][*it];
	}
	// for(i = 0; i < G->N - 1; i++){
	// 	for(mit = G->edges.begin(); mit != G->edges.end(); mit++){
	// 		if(G->nodes[mit->first.second].dist > G->nodes[mit->first.first].dist + mit->second.second){
	// 			G->nodes[mit->first.second].dist = G->nodes[mit->first.first].dist + mit->second.second;
	// 			G->nodes[mit->first.second].p = mit->first.first;
	// 		}
	// 	}
	// }
	for(i = 0; i < G->N - 1; i++){
		for(it = G->adjacency_list[i].begin(); it != G->adjacency_list[i].end(); it++){
			if(G->nodes[*it].dist > G->nodes[*it].dist + G->costs[i][*it]){
				cout << "The graph has negative cycles" << endl;
				return;
			}
		}
	}
		for(mit = G->edges.begin(); mit != G->edges.end(); mit++){
		printf("(%i,%i) -> %i [%c]\n", mit->first.first, mit->first.second, mit->second.first, mit->second.second);
	}
}

int main(){
	Graph G;
	printf("Bellman-Ford Algorithm\n");
	read_input("test1.in", &G);
	G.display_structure();
	bellman_ford(&G, 0, 3);
	display_path(&G, 3);
	// for(int i = 0; i < G.N; i++) std::cout << "[" << i << "]" << labels[i] << " ";
	std::cout << std::endl;
	// printf("Shortest path: \n");
	// for(int i = 0; i < (int)path.size(); i++){
	// 	std::cout << labels[path[i]] << "[" << path[i] << "]" << " ";
	// } std::cout << std::endl;
	return 0;
}