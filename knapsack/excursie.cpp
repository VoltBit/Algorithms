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

/* Return the maximum value between three numbers. */
int getMax(int a, int b, int c){
	return max(a, max(b, c));
}

/* Read data from file. */
void getInput(const char* inputFile){
	ifstream inp(inputFile);
	inp >> objCount >> maxWeight1 >> maxWeight2;
	int i;
	for(i = 1; i <= objCount; i++)
		inp >> weight[i] >> value1[i] >> value2[i];
	inp.close();
}

void writeOutput(const char* outputFile, long answer){
	ofstream out(outputFile);
	out << answer;
	out.close();
}

/* Solving the knapsack problem for two knapsacks. The function uses a two 
	dimensional array of lists to store the maximum possible value for a
	weight of i in first knapsack and a weight of j in the second knapsack.
	Return value is the highest product found.*/
long solveTwoKnapsacks(){
	vector<vector<list<index> > > trace(maxWeight1 + 1, 
										vector<list<index> >(maxWeight2 + 1, 
																list<index>()));
	int i,j,k,aux1,aux2;
	long maxProd = 1;
	/* Start by using the dynamic programming approach for the knapsack problem.
	*/
	for(k = 1; k <= objCount; k++){
		for(i = maxWeight1; i >= 0; i--){
			for(j = maxWeight2; j >= 0; j--){
				/*Check if the bags are fully loaded. */
				if(j >= weight[k] && i >= weight[k]){
					aux1 = dp[i - weight[k]][j] + value1[k];
					aux2 = dp[i][j - weight[k]] + value2[k];

					if(aux1 == aux2 && dp[i][j] < aux1){
					/*  Load balance, if an object has the same value for both
						knapsacks place it in the one less loaded. */
						if(trace[i - weight[k]][j].size() > 
							trace[i][j - weight[k]].size()) 
							dp[i][j] = aux1;
						else dp[i][j] = aux2;
					} 
					/*Or just Choose  best bag */
					else dp[i][j] = getMax(dp[i][j], aux1, aux2);
					/*  Check in what bag was chosen and fill the data in the 
						data structure. */
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
				else if(j >= weight[k]){ /* Only second bag has room left. */
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
					else if(i >= weight[k]){ /* Only first bag has room left. */
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
	/*  Finding best product by iterating through the results matrix, accessing
		each list, computing the sums for each bag and keeping the maximum 
		product. */
	for(i = 0; i <= maxWeight1; i++){
		for(j = 0; j <= maxWeight2; j++){
			list<index>::iterator itr = trace[i][j].begin();
			int sum1 =0, sum2 = 0;
			for(size_t r = 0; r < trace[i][j].size(); r++, itr++){
				if((*itr).bag == 1){
					sum1 += value1[(*itr).item];
				}
				else{
					sum2 += value2[(*itr).item];
				}
			}
			if(sum1 * sum2 > maxProd) maxProd = sum1 * sum2;
		}
	}
	return maxProd;
}

int main(int argc, char *argv[]){
	// clock_t begin = clock();
	getInput("excursie.in");
	writeOutput("excursie.out", solveTwoKnapsacks());
	// clock_t end = clock();
	// double elapsed_secs = double(end - begin) / CLOCKS_PER_SEC;
	// printf("\nTime: %f\n", elapsed_secs);
	return 0;
}