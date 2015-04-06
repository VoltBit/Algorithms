#include <vector>
#include <list>
#include <iostream>
#include <cstdio>
#include <fstream>
#include <algorithm>
using namespace std;

int weight[51], value1[51], value2[51];
int dp[151][151];
int objCount, maxWeight1, maxWeight2;

int getMax(int a, int b, int c){
	return max(a, max(b, c));
}

void getInput(const char* inputFile){
	ifstream inp(inputFile);
	inp >> objCount >> maxWeight1 >> maxWeight2;
	int i;
	for(i = 1; i <= objCount; i++)
		inp >> weight[i] >> value1[i] >> value2[i];
	inp.close();
}

void solvePartition(){
	
}

int main(){
	getInput("teste_excursie/excursie0.in");
	return 0;
}