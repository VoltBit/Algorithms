
#include <fstream>
#include <iostream>
#include <vector>
#include "vector_io.h"


std::vector<int> longest_palindorme_substring(std::vector<int>& v)
{
    // lungimea vectorului de intrare
    int n = v.size();

    // L[i][j]=1 cand de la i la j avem palindrom
    std::vector<std::vector<int> > L(n + 1, std::vector<int>(n + 1, 0));

    // lungimea celei mai mari subsecvente palindrom
    int max_len=1;

    // pozitia la care incepe subsecventa palindrom
    int max_poz=0;

    /* (1) folosim Programare Dinamica pentru a completa L
     * in acelasi timp actualizam max_len si max_poz
     */

     /*TODO (1.1) calculam L pentru siruri de lungime 1 */
    

    /*TODO (1.2) calculam L pentru siruri de lungime 2 */
    

    /*TODO (1.3) calculam L pentru siruri de lungime cel putin 3
     * hint: vom folosi o formula recursiva ce foloseste elemente calculate anterior */
    

    std::vector<int> sol(max_len);                        // subsecventa propriu-zisa
    /*TODO (2) reconstruim subsecventa in sol */
    

    return sol;
}

int main(void)
{
    std::ifstream f("date.in");
    std::vector<int> v;

    f >> v;
    std::cout << longest_palindorme_substring(v) << std::endl;

    v.clear();

    f >> v;
    std::cout << longest_palindorme_substring(v) << std::endl;

    v.clear();

    f >> v;
    std::cout << longest_palindorme_substring(v) << std::endl;

    return 0;
}
