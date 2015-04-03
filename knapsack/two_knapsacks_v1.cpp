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
	inp >> objCount;
	inp >> maxWeight1;
	inp >> maxWeight2;
	int i;
	for(i = 0; i < objCount; i++){
		inp >> weight[i];
		inp >> value1[i];
		inp >> value2[i];
	}
	inp.close();
}

void checkInput(){
	int i;
	cout << "Check input \n\n";
	cout << "Size: " << objCount << " " << maxWeight1 << " " << maxWeight2 << endl;
	for(i = 0 ; i < objCount; i++){
		cout << weight[i] << " " << value1[i] << " " << value2[i] << "\n";
	}
}

void solveKnapsaks(){

}

int main(){
	getInput("excursie1.in");
	checkInput();
	return 0;
}