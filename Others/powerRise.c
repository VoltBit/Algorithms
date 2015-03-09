#include <stdio.h>
#include <stdlib.h>

int rise(int x, int power){
	if(power == 0) return 1;
	if(power % 2 == 1)
		return x * rise(x * x, (power - 1) / 2);
	else
		return rise(x * x, power / 2);
	return 1;
}

int main(){
	int a,b;
    freopen("lgput.in","r",stdin);
    freopen("lgput.out","w",stdout);
    scanf("%d %d", &a, &b);
    
	// printf("a: ");
	// scanf("%i",&a);
	// printf("b: ");
	// scanf("%i",&b);
	
	// printf("\na ^ b = %i\n\n", rise(a,b));
	printf("%d\n", rise(a,b));
	return 0;
}