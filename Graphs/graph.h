#include <cstdlib>
#include <iostream>
#include <fstream>
#include <list>
#include <utility>

using namespace std;
#ifndef GRAPH_H
#define GRAPH_H

enum colour{
    WHITE, GREY, BLACK
};

typedef struct Vertex {
    list<Vertex> adj;
    int ID;
    colour col;
}Vertex;

class Graph
{
public:
    Graph();
    ~Graph();
};

#endif // GRAPH_H
