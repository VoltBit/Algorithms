class Vertex{
	private:
		Vertex *next;
		int id;
	public:
		Vertex ();
		Vertex (int x);
		Vertex (int x, Vertex* next);
		Vertex* next();
		int get_id();
		void set_next(Vertex* n);
		void set_id(int i);
};

Vertex::Vertex(){
	
}