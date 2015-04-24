build-p1: colours.cpp graph.cpp graph.h
	g++ -Wall -o colours colours.cpp graph.cpp

build-p2: exhibition.cpp graph.cpp graph.h
	g++ -Wall -O3 -o exhibition exhibition.cpp graph.cpp

run-p1: build-p1
	./colours

run-p2: build-p2
	./exhibition

run: build
	./exq

clean:
	rm -f exhibition
	rm -f colours
