#include <iostream>
#include <math.h>
#include <cstdio>
using namespace std;
long x[11] = {0, 1, 12, 16, 17, 19, 21, 30, 31, 49};
int size = 10;
void binarySearch(long key){
	long inf = 0, sup = size, mid;
	while(inf <= sup){
		mid = (sup - inf) / 2 + inf;
		printf("%li [inf: %li, sup: %li]\n", mid, inf, sup);
		if(x[mid] == key){
			printf("Found on position %li\n", mid);
			break;
		}
		if(x[mid] < key) inf = mid + 1;
		else sup = mid - 1; 
	}
	if(inf > sup) printf("Not found\n");
}

void oneSideBinarySearch(long key){
	long start = 0, end = 10;
	long limit = log2(end);
	long pos = start, newPos;
	while(limit >= 0){
		if(x[pos] == key){
			printf("Found on position %li\n", pos);
			break;
		}
		newPos = pos + (1 << limit);
		if(newPos < end && x[newPos] <= key)pos = newPos;
		limit--;
	}
	if(limit < 0)printf("Not found\n");
}

// merg pe toate numerele de la s(0,0) la s(n-1,n-1)

void findTerms(){
	long i = 0, j = n - 1;
	while(i >= 0 && j < n){
		
	}
}

int main(){
	printf("Binary search: \n");
	// binarySearch(0);
	oneSideBinarySearch(17);
	return 0;
}