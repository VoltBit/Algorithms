#include <fstream>
#include <iostream>
#include <vector>
#include "vector_io.h"

#define NO_TESTS 2

std::vector<int> plan_route(int m, const std::vector<int>& distances)
{

    // TODO
    // Calculati numarul minim de opriri la benzinarii si returnati
    // o lista cu indicii benzinariilor la care se vor face opririle
    std::vector<int> result;

    
    return result;
}

int main(void)
{
    std::ifstream f("date.in");
    
    int m;
    std::vector<int> distances;
    std::vector<int> stops;
    
    for (unsigned int test = 0; test < NO_TESTS; test++) {
        f >> m >> distances;
        stops = plan_route(m, distances);
        std::cout << "Pentru ruta {";
        std::cout << distances;
        std::cout << "}, opririle planificate sunt {";
        std::cout << stops;
        std::cout << "} si numarul minim de opriri este ";
        std::cout << stops.size() << "\n";
        distances.clear();
    }

    return 0;
}
