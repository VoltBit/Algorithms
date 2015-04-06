#include <iostream>
#include <cstdio>
#include <fstream>
#include <algorithm>
#define nmax 5006
using namespace std;
void solveKnapsackTwoDim(){
	int weight[nmax], value[nmax], dp[2][nmax * 2] = {0};
	int i, j, objCount, maxWeight;
	ifstream inp("rucsac.in");
	ofstream out("rucsac.out");
	inp >> objCount >> maxWeight;
	for(i = 1; i <= objCount; i++)
		inp >> weight[i] >> value[i];
	inp.close();
	int l = 0;
	for(i = 1; i <= objCount; i++){
		for(j = 1; j <= maxWeight; j++){
			if(j >= weight[i])
				dp[l][j] = max(dp[!l][j], dp[!l][j - weight[i]] + value[i]);
			else dp[l][j] = dp[!l][j];
		}
		l = !l;
	}
	out << max(dp[0][maxWeight], dp[1][maxWeight]);
	// cout << max(dp[0][maxWeight], dp[1][maxWeight]);
	out.close();
}

void solveKnapsackOneDim(){
	int weight, value, dp[nmax * 2] = {0};
	int i, j, objCount, maxWeight;
	ifstream inp("rucsac.in");
	ofstream out("rucsac.out");
	inp >> objCount >> maxWeight;
	for(i = 1; i <= objCount; i++){
		inp >> weight >> value;
		for(j = maxWeight; j >= weight; j--){
			dp[j] = max(dp[j], dp[j - weight] + value);
		}
		for(j = 1; j <= maxWeight; j++){
			printf("%i ", dp[j]);
		} printf("\n");
	}
	out << dp[maxWeight];
	// cout << dp[maxWeight] << endl;
	inp.close();
	out.close();
}

int main(){
	// solveKnapsackTwoDim();
	solveKnapsackOneDim();
	return 0;
}