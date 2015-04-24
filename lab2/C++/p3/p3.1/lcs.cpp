#include <fstream>
#include <iostream>
#include <vector>
#include "vector_io.h"

#define MAX_N 1001

std::vector<int> longest_common_subset(std::vector<int>& v1, std::vector<int>&v2)
{
    std::vector<std::vector<int> > lsc(v1.size() + 1,
                                       std::vector<int>(v2.size() + 1, 0));
    std::vector<int> sol;

    // TODO
    // Gasiti cel mai lung subsir comun pentru vectorii v1 si v2.
    //
    // ATENTIE: vectorii au -1 pe poziția 0, valoare ce va fi ignorată. S-a
    // ales această variantă pentru a fi mai usor de implementat recurenta și
    // a putea în același timp folosi vector_io.
    //
    // Practic, indexarea este [1, ..,  v.size() - 1]

    

    return sol;
}

void test(std::vector<int>& v1, std::vector<int>& v2)
{
    // ATENTIE: vectorii au -1 pe poziția 0, valoare ce va fi ignorată în
    // rezolvare, dar care va apărea la citire și afisare.

    std::cout << "Pentru vectorii:" << std::endl;
    std::cout << v1 << std::endl << v2 << std::endl;
    std::cout << "Rezultatul este:" << std::endl;
    std::cout << longest_common_subset(v1, v2) << std::endl << std::endl;
}

int main(void)
{
    std::ifstream f("date.in");
    std::vector<int> v1, v2;

    f >> v1 >> v2;
    test(v1, v2);

    v1.clear(); v2.clear();

    f >> v1 >> v2;
    test(v1, v2);

    return 0;
}
