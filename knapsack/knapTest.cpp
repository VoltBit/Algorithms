#include <vector>
#include <cstdio>
#include <algorithm>
#include <list>
#define MAX_W1 501
#define MAX_W2 501
using namespace std;
int getMax(int a, int b, int c) {
    int max = a>b?a:b;
    return c>max?c:max;
}

int knapsack[MAX_W1][MAX_W2] = {0};

void knapsackSolver(){
        int n, s1, s2, gain1, gain2, weight; // items, sack1, sack2, gain, cost
    freopen("teste_excursie/excursie5.in", "r", stdin);
    scanf("%d %d %d", &n, &s1, &s2);
    // filing knapsack
    int aux, i,j;
    for (i = 0; i < n; i++) {
        scanf("%d %d %d", &weight, &gain1, &gain2);
    // need to fill up all the table cannot stop if one sack is full because item might fit in other
        for (int w1 = s1; w1 >= 0; w1--) {
            for (int w2 = s2; w2 >= 0; w2--) {
                if (w1 >= weight && w2 >= weight){
                    knapsack[w1][w2] = getMax(knapsack[w1][w2], knapsack[w1 - weight][w2] + gain1, knapsack[w1][w2 - weight] + gain2);
                }
                else if (w1 >= weight){
                    knapsack[w1][w2] = max(knapsack[w1][w2], knapsack[w1 - weight][w2] + gain1);
                }
                else if (w2 >= weight){
                    knapsack[w1][w2] = max(knapsack[w1][w2], knapsack[w1][w2 - weight] + gain2);
                }
            }
        }
    }
    printf("\n\n");
        for(aux = 1; aux <= s1; aux++){
            for(j = 1; j <= s2; j++){
                printf("%i ", knapsack[aux][j]);
            } printf("\n");
        }
}

void test1(){
   vector< list<int> > vect(100, list<int>());

    list<int>::iterator itr;
    for( int i = 0; i < 10; i++)
        vect[0].push_front(i);
    for( int i = 0; i < 10; i += 2)
        vect[1].push_front(i);
    
    printf("Lista1: ");
    itr = vect[0].begin();
    for(int i = 0; i < vect[0].size(); i++, itr++){
        printf("%i ", *itr);
    } printf("\n\n");

    printf("Lista2: ");
    itr = vect[1].begin();
    for(int i = 0; i < vect[1].size(); i++, itr++){
        printf("%i ", *itr);
    } printf("\n\n");

    vect[0] = vect[1];
    vect[1].push_front(111);
    printf("Lista1: ");
    itr = vect[0].begin();
    for(int i = 0; i < vect[0].size(); i++, itr++){
        printf("%i ", *itr);
    } printf("\n\n");

    printf("Lista2: ");
    itr = vect[1].begin();
    for(int i = 0; i < vect[1].size(); i++, itr++){
        printf("%i ", *itr);
    } printf("\n\n");
   
}

struct index{
    int bag;
    int item;
};

void test2(){

    vector<vector<list<index> > >trace(100, vector<list<index> >(100, list<index>()));
    index t;
    t.bag = 10; 
    t.item = 10;
    printf("___%i___%i___\n", t.bag, t.item);
    trace[0][0].push_front(t);
    list<index>::iterator itr;
    itr = trace[0][0].begin();
    printf("LEEEL %i %i\n", (*itr).bag, (*itr).item);
    for(int i = 0; i < 10; i++){
        index aux; aux.bag = i * 10; aux.item = i * 23;
        trace[0][0].push_front(aux);
    }
    itr = trace[0][0].begin();
    for(int i = 0; i < trace[0][0].size(); i++, itr++){
        printf("\n%i %i\t", (*itr).bag, (*itr).item);
    }
}

int main() {
    // int trace[151][151][151];
    // No need to search for max value it always be Knapsack[s1][s2]
    // printf("%d\n", knapsack[s1][s2]);
    knapsackSolver();
    // test2();
 
    return 0;
}