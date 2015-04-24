#include <queue>
#include <queue>
#include <cstdio>
#include <iostream>
#include <vector>
#include <fstream>
#include <ctime>
#include <cstdlib>
#include <time.h>
#include <math.h>
#define MAX_SIZE 250
using namespace std;
long n, k;
// vector<long> v;
long v[50000];
vector<long> s;

int comp(const void* a, const void* b){
	return (*(long*)a - *(long*)b);
}

void getInput(const char* inputFile){
	ifstream inp(inputFile);
	inp >> n >> k;
	printf("N: %li K: %li\n", n, k);
	// v.reserve(n);
	// v.resize(n,0);
	// s.resize(n * 2, 0);
	int i;
	for(i = 0; i < n; i++){
		inp >> v[i]; 
		printf("%li ", v[i]);
	}
	printf("\n");
	inp.close();
}
void generate(long size, long mat[MAX_SIZE][MAX_SIZE]){
	long i, j;
	srand(time(NULL));
	for(i = 0; i < size; i++){
		v[i] = rand() % 20;
		// printf("%li ", v[i]);
	}printf("\n");
	qsort(v, size, sizeof(long), comp);
	int check[MAX_SIZE] = {0};
	for(i = 0; i < size; i++){
		for(j = 0; j < size; j++){
			mat[i][j] = v[i] + v[j];
			// printf("%li[%li]\t", mat[i][j], i +j);
			printf("%li[%li %li]\t", mat[i][j], i, j);
			check[i+j]++;
			// printf("%li\t", mat[i][j]);
		}printf("\n");
	}
	printf("Elem\tSum\n");
	int sum = 0;
	i = 0;
	while(check[i] != 0){
		// printf("%i\t%li\t>%i\n", check[i], i, sum);
		sum += check[i];
		i++;
	}
}
/* receives two indices (coordinates for a pair sum) and finds how many pair 
	sums are smaller */
long getSmaller(long x, long y){
	long group = x + y;
	if(group == 0) return 0;
	if(group == 1) return 1;
	// long equal = 0;
	--group;
	/* compute how many pair sums are in the groups smaller than the group 
		before the current one */
	long smaller = group * (group + 1) / 2;
	/* In the group before the current one there could be a few smaller 
		elements, as well as in the next group */
	long p1, p2;
	/* setting two pointers to iterate through the vector, the sum of the two 
		pointer must not be greater than group */
	int i;
	for(i = 0; i < 4; i++){ 
	/* I check both current group and the group before it */
		if(group >= n / 2) break;
		if(group == 1){
			smaller++;
			break;
		}
		if(group >= n) {printf("?"); p1 = group - n;}
		else{printf("!"); p1 = group;}
		p2 = 0;
		printf("p1: %li p2: %li group: %li, %li \n", p1, p2, group, n);
		while(p2 <= p1){
			if(v[p1] + v[p2] < v[x] + v[y]){
				if(p1 != p2)smaller += 2;
				else smaller++;
			}
			p2++; p1--;
		}
		group++;
	}

	printf("Smaller: %li\n\n", smaller);
	return smaller;
}
void test1(){
	long i;
	long size = 8;
	long mat[MAX_SIZE][MAX_SIZE];
	n = size;
	generate(size, mat);
	printf("Check for: (%i %i)\n", 4, 7);
	getSmaller(4,7);
	int countTest;
	for(i = 0 ; i < size; i++){
		for(int j = 0; j < size; j++){
			if(v[4] + v[7] > mat[i][j])countTest++;
		}
	}
	printf("Smallest test: %i\n", countTest);
}

void preformanceTest(){
	long long i;
	k = 250000000;
	n = 50000;
	for(i = 0; i < log(k); i++){
		for(long j = 0; j < 8 * n; j++){

		}
	}
}

// void queueSolver(){
// 	priority_queue <pair<long,long> > pq;
// 	pair<long,long> maxPair(0,0);
// 	pq.push(maxPair);
// 	long a = 0, b = n;

// }

long getPos(long val){
	long p1 = 0, p2 = n - 1;
	long counter = 0;
	while(p1 <= p2){
		// counter++;
		if(v[p1] + v[p2] == val){
			printf("Pointers: (%li %li -> %li %li)", p1, p2, p1 + p2, p2 - p1);
			return counter;
		}
		if(v[p1] + v[p2] < val){
			// printf("%li ", v[p1] + v[p2]);
			counter = counter + p2 - p1;
			p1++;
		}
		else{
			// printf("%li ", v[p1] + v[p2]);
			// counter = counter + p2 - p1;
			p2--;
		}
	} printf("\n");
	return counter;
}

void displaySums(){
	int i, j;
	long aux[200], counter = 0;
	for(i = 0; i < n; i++) printf("%li ", v[i]);
	printf("\n");
	for(i = 0; i < n; i++){
		for(j = 0; j < n; j++){
			aux[counter] = v[i] + v[j];
			counter++;
		}
	}
	qsort(aux, counter, sizeof(long), comp);
	// for(i = 0; i < counter; i++){
	// 	printf("%li [%li]\t", aux[i], getPos(aux[i]));
	// } printf("\n");
	for(i = 0; i < counter; i++){
		printf(" [%li] -> [%i] val: %li\n", getPos(aux[i]), i, aux[i]);
	} printf("\n");

}

void solver(){
	int limit = log2(2 * v[n - 1]);
	long val = 2 * v[0], newVal;
	while(limit >= 0){
		newVal = val + (1 << limit);
		if(getPos(val) == k){
			printf("Value found: %li", val);
			break;
		}
		if(getPos(val) < k && newVal < 2 * v[n - 1]){
			val = newVal;
		}
		limit--;
	}
	printf("%li\n", val);
}

int main(){
	clock_t begin = clock();

	getInput("teste_patrat/patrat1.in");
	qsort(v, n, sizeof(long), comp);
	
	displaySums();
	// solver();


	clock_t end = clock();
	double elapsed_secs = double(end - begin) / CLOCKS_PER_SEC;
	printf("\nTime: %f\n", elapsed_secs);
	return 0;
}