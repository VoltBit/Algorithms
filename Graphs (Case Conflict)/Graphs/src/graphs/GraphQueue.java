package graphs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

public class GraphQueue implements Queue{

    ArrayList<Vertex> queue;
    public GraphQueue(){
        this.queue = new ArrayList<Vertex>();
    }
    @Override
    public boolean add(Object e) {
        return queue.add((Vertex)e);
    }

    @Override
    public boolean offer(Object e) {
        return queue.add((Vertex)e);
    }

    @Override
    public Object remove() {
        Vertex aux = queue.get(0);
        queue.remove(0);
        return aux;
    }

    @Override
    public Vertex poll() {
        Vertex aux = queue.get(0);
        queue.remove(0);
        return aux;
    }

    @Override
    public Object element() {
        Vertex aux = queue.get(0);
        return aux;
    }

    @Override
    public Object peek() {
        if(this.queue.isEmpty()) return null;
        Vertex aux = queue.get(0);
        return aux;
    }

    @Override
    public int size() {
        return this.queue.size();
    }

    @Override
    public boolean isEmpty() {
        return this.queue.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.queue.contains(o);
    }

    @Override
    public Iterator iterator() {
        return this.queue.iterator();
    }

    @Override
    public Vertex[] toArray() {
        return (Vertex[])this.queue.toArray();
    }

    @Override
    public Object[] toArray(Object[] a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean remove(Object o) {
        return this.queue.remove(o);
    }

    @Override
    public boolean containsAll(Collection c) {
        return this.queue.containsAll(c);
    }

    @Override
    public boolean addAll(Collection c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeAll(Collection c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean retainAll(Collection c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        this.queue.clear();
    }

    @Override
    public String toString(){
        StringBuilder output = new StringBuilder();
        Iterator<Vertex> itr = this.iterator();
        while(itr.hasNext())
            output.append(itr.next().toString());
        return output.toString();
    }
}
