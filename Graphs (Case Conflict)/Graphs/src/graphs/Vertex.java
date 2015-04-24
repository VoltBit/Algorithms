package graphs;

public class Vertex<T> {
    public static enum colour{
        WHITE, GREY, BLACK;
    }
    public colour c;
    public Integer time;
    public Integer id;
    public Vertex pred;
    
    public Vertex(Integer identifier, Integer distance, Vertex p){
        if(identifier == null){
            System.err.println("Wrong vertex input");
            return;
        } else {
            this.id = identifier;
        }
        this.time = distance;
        this.c = Vertex.colour.WHITE;
        this.pred = p;
    }
    
    public void setColour(Vertex.colour col){
        this.c = col;
    }
    
    @Override
    public String toString(){
        StringBuilder output = new StringBuilder();
        output.append("(");
        output.append(this.id.toString());
//        output.append(" ");
//        output.append(this.d.toString());
        output.append(" ");
        if(this.c == Vertex.colour.WHITE) output.append(" W");
        else if(this.c == Vertex.colour.GREY) output.append(" G");
        else if(this.c == Vertex.colour.BLACK) output.append(" B");
        output.append(")");
        return output.toString();
    }
}
