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

void sort_it(int d[], int p[], int n){
	int aux, i, j, check;
	do{
		check = 0;
		for(i = 0; i < n-1; i++){
			if(d[i] < d[i + 1]){
				aux = d[i];
				d[i] = d[i + 1];
				d[i + 1] = d[i];

				aux = p[i];
				p[i] = p[i + 1];
				p[i + 1] = aux;
				check = 1;
			}
		}
	}while(check == 1);
}

vector <pair<int, int> > heapify(int x[], int p[], Graph* G){
	int i;
	sort_it(x, p, G->N);
	vector <pair<int, int> > H;
	for(i = 0; i < G->N; i++){
		H.push_back(make_pair(x[i], i));
	}
	return H;
}

void prim(Graph* G, int root){
	// std::sort(G->edges.begin(), G->edges.end(), cmp);
	int i;
	vector<pair<int, int> > mt;
	list<int>::iterator it;
	mt.clear();
	int distances[MAX_N], p[MAX_N];
	for(i = 0; i < G->N; i++){
		distances[i] = INF;
		p[i] = -1;
	}
	distances[root] = 0;

	// vector< pair <int, pair<int, int> > >dstheap;
	// map < std::pair<int, int>, int >::iterator itm;
	// for(itm = G->edges.begin(); itm != G->edges.end(); itm++){
	// 	dstheap.push_back(make_pair((*itm).second, (*itm).first));
	// 	// cout << ((*itm).first).first << " " << (*itm).second << endl;
	// }

	// make_heap(dstheap.begin(), dstheap.end());
	// vector< pair <int, pair<int, int> > >::iterator h_it;
	// for(h_it = dstheap.begin(); h_it != dstheap.end(); h_it++){
	// 	cout << (*h_it).first << " ";
	// }
	// cout << endl;

	vector <pair<int, int> > H;
	H = heapify(distances, p, G);
	vector <pair<int, int> >::iterator it2;
	for(it2 = H.begin(); it2 != H.end(); it2++)
		cout << (*it2).first << " " << (*it2).second << endl;
	cout << endl;
	// make_heap(H.begin(), H.end());
	reverse(H.begin(), H.end());
	for(it2 = H.begin(); it2 != H.end(); it2++)
		cout << (*it2).first << " " << (*it2).second << endl;
	cout << endl;
	int u;
	// H.pop_back();
	while(!H.empty()){
		u = H.back().second;
		H.pop_back();
		mt.push_back(make_pair(u, p[u]));
		for(it = G->adjacency_list[u].begin(); it != G->adjacency_list[u].end(); it++){
			if(G->edges[make_pair(u, *it)] < distances[*it]){
				distances[*it] = G->edges[make_pair(u, *it)];
				p[*it] = u;
				// H.clear();
				H = heapify(distances, p, G);
				// reverse(H.begin(), H.end());
			}
		}
	}
	for(it2 = mt.begin(); it2 != mt.end(); it2++){
		cout << (*it2).first << " " << (*it2).second << endl;
	}
	cout << endl;
	// make_heap(dstheap.begin(), dstheap.end(), std::greater<int>{});
	// sort_heap(dstheap.begin(), dstheap.end());
	// vector<int>::iterator itv;
	// for(itv = dstheap.begin(); itv != dstheap.end(); itv++){
	// 	cout << *itv << " ";
	// }
	// cout << endl;
	// int u;
	// // cout << dstheap.back() << endl;
	// while(!dstheap.empty()){
	// 	u = dstheap.back();
	// 	cout << u << " " << endl;
	// 	// pop_heap(dstheap.begin(), dstheap.end());
	// 	dstheap.pop_back();
	// 	printf("?%i ?%i\n", u, p[u]);
	// 	// mt.push_back(make_pair(u, p[u]));
	// // 	for(it = G->adjacency_list[u].begin(); it != G->adjacency_list[u].end(); it++){
	// // 		if(G->edges[(u, *it)] < d[*it]){
	// // 			d[*it] = G->edges[(u, *it)][v];
	// // 			p[*it] = u;
	// // 		}
	// // 	}
	// }
}

// std::vector<int> prim(Graph* G, int origin){
// 	int i;
// 	auxG = G;
// 	// std::priority_queue<int, std::vector<int>, std::greater<int> > Q;
// 	std::priority_queue<int, std::vector<int>, Compare> Q;
// 	// std::queue<int> Q;
// 	G->reset_colours();
// 	G->reset_parents();
// 	std::list<int>::iterator it;

// 	G->nodes[origin].dist = 0;
// 	G->nodes[origin].colour = 'b';

// 	for(i = 0; i < G->N; i++){
// 		for(it = G->adjacency_list[origin].begin(); it != G->adjacency_list[origin].end(); it++){
// 			if(*it == i){
// 				G->nodes[i].dist = G->costs[origin][i];
// 				G->nodes[i].p = origin;
// 				Q.push(i);
// 			}
// 		}
// 	}

// 	printf("\nTesting queue: \n\n");
// 	std::priority_queue<int, std::vector<int>, Compare> auxQ = Q;
// 	while(!auxQ.empty()){
// 		printf("%i %i ", auxQ.top(), G->nodes[auxQ.top()].dist);
// 		auxQ.pop();
// 	}

// 	printf("\n");
// 	while(!Q.empty()){
// 		int node = Q.top();
// 		// int node = Q.front();
// 		Q.pop();
// 		G->nodes[node].colour = 'b';
// 		for(it = G->adjacency_list[node].begin(); it != G->adjacency_list[node].end(); it++){
// 			if(G->nodes[*it].colour == 'w' && G->nodes[*it].dist > G->nodes[node].dist + G->costs[node][*it]){
// 				G->nodes[*it].dist = G->nodes[node].dist + G->costs[node][*it];
// 				G->nodes[*it].p = node;
// 				Q.push(*it);
// 			}
// 			// printf("%i ? %i\n", G->nodes[*it].dist, G->costs[node][*it]);
// 		}
// 	}
// 	std::vector<int> path;
// 	path.push_back(destination);
// 	int trace = G->nodes[destination].p;
// 	while(trace != -1){
// 		path.push_back(trace);
// 		trace = G->nodes[trace].p;
// 	}
// 	return path;
// }

int main(){
	Graph G;
	G.storage_type = LIST;
	cout << "Prim algorithm" << endl;
	read_input("test.in", &G);
	// read_input("test.in", &G);
	G.display_structure();
	prim(&G, 0);
	return 0;
}