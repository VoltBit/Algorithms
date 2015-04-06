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

struct index{
    int bag;
    int item;
};

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

void solveKnapsack(int nr){
	int matSolv[151 * 2] = {0};
	int i, j, maxWeight, value;
	size_t k;
	vector<list<int> > trace(151 * 2, list<int>());
	if(nr == 1)
		maxWeight = maxWeight1;
		else maxWeight = maxWeight2;
	for(i = 1; i <= objCount; i++){
		for(j = maxWeight; j >= weight[i]; j--){
			if(nr == 1) value = value1[i];
			else value = value2[i];
			matSolv[j] = max(matSolv[j], matSolv[j - weight[i]] + value);
			if(matSolv[j] == matSolv[j - weight[i]] + value){
				trace[j] = trace[j - weight[i]];
				trace[j].push_front(i);
			}
		}
		for(j = 1; j <= maxWeight; j++){
			printf("%i ", matSolv[j]);
		} printf("\n");
		for(j = 1; j <= maxWeight; j++){
			printf("valoare: [%i] lista: ", matSolv[j]);
			list<int>::iterator itr;
			printf("{ ");
			for(k = 1, itr = trace[j].begin(); k <= trace[j].size(); k++, itr++){
				if(nr == 1)
					printf("%i ", value1[*itr]);
				else printf("%i ", value2[*itr]);
			}printf("}\n");
		} printf("\n");
	}
	 printf("\nResult: %i\n", matSolv[maxWeight]);
}

void solveTwoKnapsacks(){
	// vector<vector<vector<int> > > trace(maxWeight1, vector<vector<int> >(maxWeight2, vector<int>(objCount)));
	// vector<vector<list<int> > > trace(maxWeight1, vector<vector<int> >(maxWeight2, list<int>(objCount)));
	// vector<vector<list<int> > >trace(maxWeight1, vector<list<int> >(maxWeight2, list<int>()));
	// vector<vector<vector<int> > > trace(maxWeight1 + 1, vector<vector<int> >(maxWeight2 + 1, vector<int>(objCount,0)));
	vector<vector<list<index> > > trace(maxWeight1 + 1, vector<list<index> >(maxWeight2 + 1, list<index>()));

	int i,j,k,aux1,aux2;
	long maxProd = 1;
	for(k = 1; k <= objCount; k++){
		for(i = maxWeight1; i >= 0; i--){
			for(j = maxWeight2; j >= 0; j--){
				if(j >= weight[k] && i >= weight[k]){
					aux1 = dp[i - weight[k]][j] + value1[k];
					aux2 = dp[i][j - weight[k]] + value2[k];
					dp[i][j] = getMax(dp[i][j], aux1, aux2);
					if(dp[i][j] == aux1){
						index var; 
						var.bag = 1;
						var.item = k;
						trace[i][j] = trace[i - weight[k]][j];
						trace[i][j].push_front(var);
					} else if(dp[i][j] == aux2){
							index var;
							var.bag = 2;
							var.item = k;
							trace[i][j] = trace[i][j - weight[k]];
							trace[i][j].push_front(var);
						}
				}
				else if(j >= weight[k]){
						aux2 = dp[i][j - weight[k]] + value2[k];
						dp[i][j] = max(dp[i][j], aux2);
						if(dp[i][j] == aux2){
							index var;
							var.bag = 2;
							var.item = k;
							trace[i][j] = trace[i][j - weight[k]];
							trace[i][j].push_front(var);
						}
					}
					else if(i >= weight[k]){
							aux1 = dp[i - weight[k]][j] + value1[k];
							dp[i][j] = max(dp[i][j], aux1);
							index var;
							var.bag = 1;
							var.item = k;
							trace[i][j] = trace[i - weight[k]][j];
							trace[i][j].push_front(var);
						}
			}
		}
	}
	
	// printf("\nSum1: %i\nSum2: %i\n", sum1, sum2);
	for(i = 0; i <= maxWeight1; i++){
		for(j = 0; j <= maxWeight2; j++){
			// printf("value: %i list: { ", dp[i][j]);
			list<index>::iterator itr = trace[i][j].begin();
			int sum1 =0, sum2 = 0;
			for(size_t r = 0; r < trace[i][j].size(); r++, itr++){
				if((*itr).bag == 1){
					sum1 += value1[(*itr).item];
					// printf("[%i,%i] ", (*itr).bag, value1[(*itr).item]);
				}
				else{
					sum2 += value2[(*itr).item];
					// printf("[%i,%i] ", (*itr).bag, value2[(*itr).item]);
				}
			}
			if(sum1 * sum2 > maxProd) maxProd = sum1 * sum2;
			// printf("}\t%i * %i = %i\n", sum1, sum2, sum1 * sum2);
		}
		// printf("\n");
	}
	printf("Best: %i\n", dp[maxWeight1][maxWeight2]);
	printf("Best product: %li\n", maxProd);
	// printf("[%i] * [%i] = %i\n\n", trace[maxWeight1][maxWeight2][0], trace[maxWeight1][maxWeight2][1],
	// 	trace[maxWeight1][maxWeight2][0] * trace[maxWeight1][maxWeight2][1]);
	// for(i = 1 ; i <= maxWeight1; i++){
	// 	for(j = 1 ; j <= maxWeight2; j++){
	// 		printf("[%i %i] ", trace[i][j][0], trace[i][j][1]);
	// 	} printf("\n");
	// }printf("\n");printf("\n");
}

void checkInput(){
	int i;
	cout << "Check input \n\n";
	cout << "Size: " << objCount << " " << maxWeight1 << " " << maxWeight2 << endl;
	for(i = 1 ; i <= objCount; i++){
		cout << weight[i] << " " << value1[i] << " " << value2[i] << "\n";
	}
}

int main(){
	getInput("teste_excursie/excursie7.in");
	// checkInput();
	// solveKnapsack(1);
	// solveKnapsack(2);
	solveTwoKnapsacks();
	// displayState();
	return 0;
}