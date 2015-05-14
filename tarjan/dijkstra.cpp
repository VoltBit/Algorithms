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
	}
	file_stream.close();
}

Graph* auxG;

class Compare{
public:
    bool operator() (int x, int y){
        return auxG->nodes[x].dist > auxG->nodes[y].dist;
    }
};

std::vector<int> dijkstra(Graph* G, int origin, int destination){
	int i;
	auxG = G;
	// std::priority_queue<int, std::vector<int>, std::greater<int> > Q;
	std::priority_queue<int, std::vector<int>, Compare> Q;
	// std::queue<int> Q;
	G->reset_colours();
	G->reset_parents();
	std::list<int>::iterator it;

	G->nodes[origin].dist = 0;
	G->nodes[origin].colour = 'b';

	for(i = 0; i < G->N; i++){
		for(it = G->adjacency_list[origin].begin(); it != G->adjacency_list[origin].end(); it++){
			if(*it == i){
				G->nodes[i].dist = G->costs[origin][i];
				G->nodes[i].p = origin;
				Q.push(i);
				// break;
			}
		}
	}

	printf("\nTesting queue: \n\n");
	// std::priority_queue<int, std::vector<int>, Compare> auxQ = Q;
	// while(!auxQ.empty()){
	// 	printf("%i %i ", auxQ.top(), G->nodes[auxQ.top()].dist);
	// 	auxQ.pop();
	// }

	printf("\n");
	while(!Q.empty()){
		int node = Q.top();
		// int node = Q.front();
		Q.pop();
		G->nodes[node].colour = 'b';
		for(it = G->adjacency_list[node].begin(); it != G->adjacency_list[node].end(); it++){
			if(G->nodes[*it].colour == 'w' && G->nodes[*it].dist > G->nodes[node].dist + G->costs[node][*it]){
				G->nodes[*it].dist = G->nodes[node].dist + G->costs[node][*it];
				G->nodes[*it].p = node;
				Q.push(*it);
			}
			// printf("%i ? %i\n", G->nodes[*it].dist, G->costs[node][*it]);
		}
	}
	std::vector<int> path;
	path.push_back(destination);
	int trace = G->nodes[destination].p;
	while(trace != -1){
		path.push_back(trace);
		trace = G->nodes[trace].p;
	}
	return path;
}

int main(){
	Graph G;
	printf("Dijkstra Algorithm\n");
	// G.init_costs(-1);
	read_input("test1.in", &G);
	G.display_structure();
	std::vector<int> path = dijkstra(&G, 0, 5);
	for(int i = 0; i < G.N; i++) std::cout << "[" << i << "]" << labels[i] << " ";
	std::cout << std::endl;
	int sum = 0;
	printf("Shortest path: \n");
	int i;
	for(i = 0; i < (int)path.size(); i++){
		std::cout << labels[path[i]] << "[" << path[i] << "]" << " ";
		sum += G.nodes[path[i]].dist;
		printf("%i ", G.nodes[path[i]].dist);
	} std::cout << std::endl;
	printf("Suma: %i\n", G.nodes[path[0]].dist);
	return 0;
}