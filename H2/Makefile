build-p1: colours.cpp graph.cpp graph.h
	g++ -Wall -O3 -o col colours.cpp graph.cpp

build-p2: exhibition.cpp graph2.cpp graph2.h
	g++ -Wall -O3 -o exh exhibition.cpp graph2.cpp

run-p1: build-p1
	./col

run-p2: build-p2
	./exh

run: build
	./exq

clean:
	rm -f exh
	rm -f col
