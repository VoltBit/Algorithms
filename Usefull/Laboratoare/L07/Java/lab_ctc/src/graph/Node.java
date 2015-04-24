/**
 * Proiectarea Algoritmilor, 2013
 * Lab 7: Aplicatii DFS
 * 
 * @author 	Radu Iacob
 * @email	radu.iacob23@gmail.com
 */

package graph;

import java.util.HashMap;

public class Node {

	String _name;
	int _id;
	
	/*
		Generic property field
	*/

	HashMap< Property, Integer > _prop;

	
	public Node( String name, int id )
	{
		_name = name;
		_id   = id;
		
		reset();
	}
	
	public Node( int id )
	{
		_name = "";
		_id = id;
		
		_prop = new HashMap< Property, Integer >();
		
		reset();
	}
	
	public boolean visited()
	{
		return !(level == UNSET);
	}
	
	public void reset()
	{
		level = lowlink = ctc_index = UNSET;
		in_stack = false;
		_prop.put( Property.CTC, UNSET);
	}
	
	public String get_name()
	{
		return _name;
	}
	
	public int  get_id()
	{
		return _id;
	}
	
	public int get_property( Property p )
	{
		return _prop.get(p);
	}
	
	public void set_property( Property p, int value )
	{
		_prop.put( p, value);
	}

	public String toString()
	{
		String res = "Node : ";
		
		if( _name.length() != 0 ) res += _name + " , ";
		res += (_id);
		
		return res;
	}
	
	/*
		DF traversal
	*/

	// timpul de initializare in parcurgerea DF
	public int level;		

	/*
		Componente tare conexe
	*/
	
	// cel mai mic level al unui nod accesibil din nodul curent 
	public int lowlink;

	// nodul se afla in stack
	public boolean in_stack;

	// optional - indexul componentei tare conexe
	public int ctc_index;
	
	// optional
	public int sum;
	
	public enum Property{
		CTC
	}

	public final static int UNSET = -1;
}
