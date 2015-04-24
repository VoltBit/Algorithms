#include <iostream>
// using namespace std;

int n, sol[100];

bool isSolution(int k){
	return k == n + 1;
}

void print(){
	int i;
	for(i = 1; i <= n; i++)
		std::cout << sol[i] << " ";
	std::cout << "\n";
}

void initialize(int k){
	sol[k] = 0;
}

bool hasSuccessor(int k){
	return sol[k] < n;
}

void assignSuccessor(int k){
	sol[k]++;
}

bool isValid(int k){
	int i;
	for(i = 1; i < k; i++){
		if(sol[i] == sol[k]) return false;
	}
	return true;
}

void backtrack(int k){
	if(isSolution(k)) print();
	else{
		initialize(k);
		while(hasSuccessor(k)){
			assignSuccessor(k);
			if(isValid(k))backtrack(k+1);
		}
	}
}

int main(){
	std::cout << "Hellow World! Here are some combinations: \n";
	for(n = 1; n < 6; n++){
		std::cout << "n = " << n << "\n";
		backtrack(1);
	}
	return 0;
}