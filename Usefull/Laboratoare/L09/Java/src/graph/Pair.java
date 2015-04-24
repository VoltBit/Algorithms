/**
 * Proiectarea Algoritmilor, 2013
 * Lab 9: Arbori minimi de acoperire
 *
 * @author 	Alexandru Tudorica
 * @email	tudalex@gmail.com
 */
package graph;

/*
 * Generic Pair class, scraped from stackoverflow
 */

public class Pair<A, B> implements Comparable< Pair<A,B>> {
	private A first;
	private B second;

	public Pair(A first, B second)
	{
		this.first = first;
		this.second = second;
	}

	public int hashCode()
	{
		int hashFirst = first != null ? first.hashCode() : 0;
		int hashSecond = second != null ? second.hashCode() : 0;

		return (hashFirst + hashSecond) * hashSecond + hashFirst;
	}

	public boolean equals(Object other)
	{
		if (other instanceof Pair) {

			@SuppressWarnings("unchecked")
			Pair<A,B> otherPair = (Pair<A,B>) other;

			return
				((  this.first == otherPair.first ||
				  ( this.first != null && otherPair.first != null &&
				    this.first.equals(otherPair.first))) &&
				  ( this.second == otherPair.second ||
				  ( this.second != null && otherPair.second != null &&
				    this.second.equals(otherPair.second))) );
		}

		return false;
	}
	public void sort() {
		Comparable<A> x= (Comparable<A>) first;
		if (x.compareTo((A)second) > 0) {
			first =(A) second;
			second = (B) x;
		}
	}

	public String toString()
	{
		return "(" + first + ", " + second + ")";
	}

	public A getFirst()
	{
		return first;
	}

	public void setFirst(A first)
	{
		this.first = first;
	}

	public B getSecond()
	{
		return second;
	}

	public void setSecond(B second)
	{
		this.second = second;
	}
	
	@SuppressWarnings("unchecked")
	public int compareTo(Pair<A,B> o){
		Comparable<A> x = (Comparable<A>)o.getFirst();
		Comparable<B> y = (Comparable<B>)o.getSecond();
		if (x.compareTo(first) == 0)
			return -y.compareTo(second);
		return -x.compareTo(first);
	}

}
