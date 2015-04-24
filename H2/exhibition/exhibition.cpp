#include "graph.h"
#include <fstream>
#include <algorithm>
#include <iterator>
#include <vector>
Graph G(LIST);
long counter;
int min_degree = -INF;
int min_degree_node = -1;
std::list<int> odds;

std::vector< std::list<int> >copy_list;
/* Creates a copy of the initial nondirected configuration. */
void adjacency_copy(){
	int i;
	for(i = 0; i < G.N; i++){
		std::list<int> l;
		copy_list.push_back(G.adjacency_list[i]);
	}
}

void read_input(const char* file_name){
	std::ifstream file_stream(file_name);
	file_stream >> G.N;
	int i, aux1, aux2;
	for(i = 0; i < G.N; i++) {
		Vertex new_node;
		new_node.p = -1;
		new_node.colour = 'w';
		new_node.content = i;
		new_node.dist = INF;
		G.nodes.push_back(new_node);
		std::list<int> l;
		G.adjacency_list.push_back(l);
		G.degrees[i] = 0;
	}
	counter = 0;
	while(file_stream >> aux1 >> aux2){
		G.adjacency_list[aux1].push_back(aux2);
		G.adjacency_list[aux2].push_back(aux1);
		G.degrees[aux1]++;
		G.degrees[aux2]++;
		counter = counter + 1;
	}
	for(i = 0 ; i < G.N; i++){
		if(min_degree < G.degrees[i]){
			min_degree = G.degrees[i];
			min_degree_node = i;
		}
	}
	file_stream.close();
}

void write_output(bool check){
	int i;
	std::list<int>::iterator it;
	std::ofstream file_stream("expozitie.out");
	if(!check) file_stream << "Imposibil";
	else {
		for(i = 0; i < G.N; i++){
			if(!G.adjacency_list[i].empty()){
				for(it = G.adjacency_list[i].begin(); it != G.adjacency_list[i].end(); it++)
					file_stream << i << " " << *it << std::endl;
			}
		}
	}
}
/* Counts the nodes that have an odd degree and creats a lists of those nodes. */
int get_odds(){
	int i, count_odds = 0;
	for(i = 0; i < G.N; i++){
		if(G.adjacency_list[i].size() % 2){
			count_odds++;
			odds.push_back(i);
		}
	}
	return count_odds;
}
/* Counts the total edges in the graph - the edges can be either directed or nondirected. */
int get_total_edges(){
	int count_edges = 0, i;
	for(i = 0; i < G.N; i++)
		count_edges += G.adjacency_list[i].size();
	return count_edges;
}
/* Checks is an edge is directed or not. */
bool is_nondirected(int node1, int node2){
	std::list<int>::iterator it1, it2;
	it1 = find(G.adjacency_list[node1].begin(), G.adjacency_list[node1].end(), node2);
	it2 = find(G.adjacency_list[node2].begin(), G.adjacency_list[node2].end(), node1);
	return (it1 != G.adjacency_list[node1].end() && it2 != G.adjacency_list[node2].end());
}

/* Converts the non-directed edges to directed edges for a given node. */
void direct_edges(int node){
	std::list<int>::iterator it, del;
	/* If degree is odd, all edges become external. */
	if(G.degrees[node] % 2 == 0 && G.degrees[node] != 0){
		for(it = G.adjacency_list[node].begin(); it != G.adjacency_list[node].end(); it++){
			if(G.degrees[*it] > 0 && is_nondirected(node, *it)){
				G.degrees[*it]--;
				G.degrees[node]--;
				G.adjacency_list[*it].remove(node);
			}
		}
	}
	else {
		for(it = G.adjacency_list[node].begin(); it != G.adjacency_list[node].end(); it++){
			if(G.degrees[*it] == 1){
				if(G.degrees[*it] > 0 && is_nondirected(node, *it)) {
					G.degrees[*it]--;
					G.degrees[node]--;
					G.adjacency_list[*it].remove(node);
				}
			}
		}
		if(G.degrees[node] != 0 && G.degrees[node] % 2 == 0){
			for(it = G.adjacency_list[node].begin(); it != G.adjacency_list[node].end(); it++){
				if(G.degrees[*it] > 0 && is_nondirected(node, *it)){
					G.degrees[*it]--;
					G.degrees[node]--;
					G.adjacency_list[*it].remove(node);
				}
			}
		} else if( G.degrees[node] > 1){
			for(it = G.adjacency_list[node].begin(); it != G.adjacency_list[node].end(); it++){
				if(G.degrees[*it] > 0 && is_nondirected(node, *it)){
					G.degrees[*it]--;
					G.degrees[node]--;
					G.adjacency_list[*it].remove(node);
				}
				if(G.degrees[node] == 1) break;
			}
		}
	}
}

std::list<int> path;
/* Tracks back the path found by the search and creates a list with the nodes that are 
	part of the path. */
void get_path(int node){
	while(G.nodes[node].p != -1){
		path.push_back(node);
		node = G.nodes[node].p;
	}
	G.reset_parents();
}
/* Changes the orientation of every edge from a given path. The path used is declared above as "path". */
void reverse_path(){
	std::list<int>::iterator it, it2, check;
	it2 = path.begin();
	++it2;
	for(it = path.begin(); it2 != path.end(); it++, it2++){
		check = find(G.adjacency_list[*it].begin(), G.adjacency_list[*it].end(), *it2);
		if(check != G.adjacency_list[*it].end()){
			G.adjacency_list[*it].remove(*it2);
			G.adjacency_list[*it2].push_back(*it);
		}
		else {
			check = find(G.adjacency_list[*it2].begin(), G.adjacency_list[*it2].end(), *it);
			if(check == G.adjacency_list[*it2].end());
			else {
				G.adjacency_list[*it2].remove(*it);
				G.adjacency_list[*it].push_back(*it2);
			}
		}
	}
	path.clear();
}
/* BFS search to determine the path between two nodes. */
void find_path(int origin){
	G.reset_colours();
	G.reset_parents();
	std::queue<int> Q;
	std::list<int>::iterator it, del;
	Q.push(origin);
	path.push_back(origin);
	G.nodes[origin].colour = 'g';
	while(!Q.empty()){
		int top = Q.front();
		for(it = copy_list[top].begin(); it != copy_list[top].end(); it++) {
			if(G.nodes[*it].colour == 'w') {
				Q.push(*it);
				G.nodes[*it].colour = 'g';
				G.nodes[*it].p = top;
				del = std::find(odds.begin(), odds.end(), *it);
				if(del != odds.end()){
					get_path(*it);
					reverse_path();
					odds.erase(del);
					return;
				}
			}
		}
		G.nodes[top].colour = 'b';
		Q.pop();
	}
}
/* Turns every odd degree node into an even degree node. */
void solve_odds(){
	std::list<int>::iterator it;
	while(!odds.empty()){
		it = odds.begin();
		find_path(*it);
		odds.erase(it);
	}
}
/* Main search performed on the entire graph during which edges are directed. */
void bfs(int origin){
	G.reset_colours();
	std::queue<int> Q;
	std::list<int>::iterator it;
	Q.push(origin);
	G.nodes[origin].colour = 'g';
	while(!Q.empty()){
		int top = Q.front();
		for(it = G.adjacency_list[top].begin(); it != G.adjacency_list[top].end(); it++) {
			if(G.nodes[*it].colour == 'w') {
				Q.push(*it);
				G.nodes[*it].colour = 'g';
			}
		}
		direct_edges(top);
		G.nodes[top].colour = 'b';
		Q.pop();
	}
}

int main(){
	G.storage_type = LIST;
	counter = 0;
	read_input("Input/in1.in");
	// read_input("../expozitie.in");
	if(get_total_edges() % 2 != 0) write_output(false);
	adjacency_copy();
		bfs(min_degree_node);
		get_odds();
		solve_odds();
		if(get_odds() != 0){
			write_output(false);
		} else write_output(true);
	return 0;
}