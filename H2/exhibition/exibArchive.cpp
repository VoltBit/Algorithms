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

void adjacency_copy(){
	int i;
	for(i = 0; i < G.N; i++){
		std::list<int> l;
		copy_list.push_back(G.adjacency_list[i]);
		// copy_list[i] = G.adjacency_list[i];
	}
}

void read_input(const char* file_name){
	printf("Reading from: %s\n", file_name);
	std::ifstream file_stream(file_name);
	file_stream >> G.N;
	printf("%i\n", G.N);
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
		// std::cout << counter << " ";
		// printf("[%i %i]", aux1, aux2);
	}
	for(i = 0 ; i < G.N; i++){
		if(min_degree < G.degrees[i]){
			min_degree = G.degrees[i];
			min_degree_node = i;
		}
	}
	// for(i = 0; i < G.N; i++) printf("%i ", G.degrees[i]);
	// printf("\nBest degree node: %i %i\n", min_degree_node, min_degree);
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

int get_total_edges(){
	int count_edges = 0, i;
	for(i = 0; i < G.N; i++)
		count_edges += G.adjacency_list[i].size();
	return count_edges;
}

bool is_nondirected(int node1, int node2){
	std::list<int>::iterator it1, it2;
	it1 = find(G.adjacency_list[node1].begin(), G.adjacency_list[node1].end(), node2);
	it2 = find(G.adjacency_list[node2].begin(), G.adjacency_list[node2].end(), node1);
	return (it1 != G.adjacency_list[node1].end() && it2 != G.adjacency_list[node2].end());
}

/* Converts the non-directed edges to directed edges for a given node. */
void direct_edges(int node){
	std::list<int>::iterator it, del;
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

// void greedy_direct(){
// 	int i;
// 	for(i = 0; i < G.N; i++){

// 	}
// }

void broad_explore(int node){
	G.nodes[node].colour = 'g';
	std::list<int>::iterator it;
	std::list<int> tmp;
	for(it = G.adjacency_list[node].begin(); it != G.adjacency_list[node].end(); it++){
		if(G.nodes[*it].colour == 'w'){
			counter += G.degrees[*it];
			tmp.push_back(*it);
			G.nodes[*it].colour = 'g';
		}
	}
	if(!tmp.empty())
		for(it = tmp.begin(); it != tmp.end(); it++){
			broad_explore(*it);
			direct_edges(*it);
		}
	direct_edges(node);
	G.nodes[node].colour = 'b';
}

std::list<int> path;

void get_path(int node){
	// printf("\ngetting path: \n");
	while(G.nodes[node].p != -1){
		// printf("%i %i %i", node, G.nodes[node].p, G.nodes[G.nodes[node].p].p);
		path.push_back(node);
		node = G.nodes[node].p;
	}
	// path.push_back(node);
	G.reset_parents();
	// printf("\nfinished getting path \n");
}

void reverse_path(){
	// printf("\nPath: %i\n", path.size());
	std::list<int>::iterator it, it2, check;
	// for(it = path.begin(); it != path.end(); it++){
	// 	printf("%i ", *it);
	// } printf("\n");
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
			if(check == G.adjacency_list[*it2].end());// printf("\t\tProblema: %i %i\n", *it, *it2);
			else {
				// printf("Din %i scot %i si pun %i in %i", *it2, *it, *it2, *it);
				G.adjacency_list[*it2].remove(*it);
				G.adjacency_list[*it].push_back(*it2);
			}
		}
		// printf(" %i ", *it);
		// if(path.size() == 2){it++; it2++;}
		//it++; it2++;
	}
	printf("\n");
	path.clear();
}

void explore(int node, int* dst){
	std::list<int>::iterator it, del;
	G.nodes[node].colour = 'g';
	// printf("%i ", node);
	for(it = G.adjacency_list[node].begin(); it != G.adjacency_list[node].end(); it++){
		if(G.nodes[*it].colour == 'w'){
			path.push_back(*it);
			G.nodes[*it].p = node;
			del = std::find(odds.begin(), odds.end(), *it);
			if(del != odds.end()){
				reverse_path();
				odds.erase(del);
				*dst = *del;
				// printf("[found: %i] ", *it);
				break;
			}
			explore(*it, dst);
		}
	}
	// printf("%i ", node);
	G.nodes[node].colour = 'b';
}

bool save_dfs(){
	int i;
	for(i = 0; i < G.N; i++)
		if(G.nodes[i].colour == 'w'){
			counter = G.degrees[i];
			G.nodes[i].dist = 0;
			// explore(i);
			// broad_explore(i);
			// printf("%li %li\n", counter, counter / 2);
			if(counter / 2 % 2 == 1){
				// printf("Imposibil 2! \n");
				return false;
			}
		}
	return true;
}

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
					// printf("[found: %i] ", *it);
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

void solve_odds(){
	std::list<int>::iterator it;
	while(!odds.empty()){
		it = odds.begin();
		find_path(*it);
		odds.erase(it);
	}
}
void dfs(){
	int dst, i;
	G.reset_colours();
	std::list<int>::iterator it, it2 = odds.begin();
	while(!odds.empty())
		dst = -1;
		for(i = 0; i < G.N; i++){
			dst = -1;
			if(G.nodes[i].colour == 'w'){
				explore(i, &dst);
			}
		}
}

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
	// clock_t begin = clock();
	// double elapsed_secs;
	G.storage_type = LIST;
	counter = 0;
	read_input("Input/in1.in");
	// read_input("expozitie.in");
	if(get_total_edges() % 2 != 0) write_output(false);
	adjacency_copy();
	// G.display_structure();
	// printf("Total edges: %li\n", counter);
	// if(counter % 2 != 0) printf("\nImposibil 1!\n");
	// else{
		// counter = 0;
		// if(dfs()) printf("OK\n");
		bfs(min_degree_node);
		get_odds();
		// printf("\nWrong ones: %i\n", get_odds());
		// printf("Test: %lu\n", odds.size());
		std::list<int>::iterator it;
		for(it = odds.begin(); it != odds.end(); it++){
			printf("%i ", *it);
		}printf("\n");
		// G.display_structure();
		solve_odds();
		if(get_odds() != 0){
			write_output(false);
		} else write_output(true);
		// printf("Test: %lu\n", odds.size());
		// find_path(*odds.begin());
	// G.display_structure();
	// printf("\nTotal edges: %i || Initial total edges: %li\n", get_total_edges(), counter);
	// printf("\nWrong ones: %i\n", get_odds());
	// clock_t end = clock();
	// elapsed_secs = double(end - begin) / CLOCKS_PER_SEC;
	// printf("\nTime: %f\n", elapsed_secs);
	return 0;
}