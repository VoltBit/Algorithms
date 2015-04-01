#include <stdlib.h>
#include <stdio.h>
size_t max(size_t a, size_t b){
	return (a > b) ? a : b;
}

void readData(int* value, int* objectCount, int* weight, int* maxWeight){
	int i;
	FILE* fileStream = fopen("data.in", "r");
	fscanf(fileStream, "%i", objectCount);
	fscanf(fileStream, "%i", maxWeight);
	// printf("%i %i\n", *objectCount, *maxWeight);
	for(i = 1; i <= *objectCount; i++){
		fscanf(fileStream, "%i", &weight[i]);
		fscanf(fileStream, "%i", &value[i]);
		// printf("%i %i\n", value[i], weight[i]);
	}
	fclose(fileStream);
}

void display(int dp[100][100], int weight[100], int n, int m){
	int i,j;
	printf("\t");
	for(i = 1; i <= m; i++) printf("%i ", i);
		printf("\n");
	for(i = 0; i <= n; i++){
		printf("%i\t",i);
		for(j = 1; j <= m; j++){
			printf("%i ",dp[i][j]);
		}
		printf("\n");
	}
	for(i = 1; i <= n; i++){
		printf("%i ", weight[i]);
	}
	printf("\n");
}

int knapsack(){
	int value[100], weight[100], dp[100][100];
	int objectCount, maxWeight;
	int i,j;
	readData(value, &objectCount, weight, &maxWeight);
	
	for(i = 0; i <= maxWeight; i++){
		dp[0][i] = 0;
	}
	for(i = 1; i <= objectCount; ++i){
		for(j = 1; j <= maxWeight; ++j){
			if(j >= weight[i]){
				dp[i][j] = (int) max(dp[i - 1][j], dp[i - 1][j - weight[i]] + value[i]);
			}
			else
				dp[i][j] = dp[i - 1][j];
		}
	}
	// display(dp, weight, objectCount, maxWeight);
	return dp[objectCount][maxWeight];
}

int main(){
	printf("Answer: %i\n", knapsack());
	return 0;
}