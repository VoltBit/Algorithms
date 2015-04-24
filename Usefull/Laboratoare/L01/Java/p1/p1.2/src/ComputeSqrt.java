/**
 * Proiectarea Algoritmilor, 2013
 * Lab 1: Divide et Impera
 * Task 1.2: Se da un numar natural n. Scrieti un algoritm de complexitate O(log n) 
 * care sa calculeze sqrt(n)
 * 
 * @author 	Mihai Bivol, Alex Olteanu
 * @email	alexandru.olteanu@cs.pub.ro
 */

public class ComputeSqrt {
	
	public static void main(String[] args) {
		
		/*TODO Calculati radicalul pentru trei valori alese de voi,
		 * folosind functia sqrt definita mai jos. Cel putin o valoare 
		 * trebuie sa fie subunitara. Precizia va fi de 0.001
		 * 
		 * Decideti care va fi valoarea upper folosita. 
		 * Hint: ce se intampla cand x<1?
		 */

	    System.out.println("sqrt(4, 1, 3, 0.001) = " + sqrt(4, 1, 3, 0.001));
	    System.out.println("sqrt(3, 1, 2, 0.001) = " + sqrt(3, 1, 2, 0.001));
	    System.out.println("sqrt(2, 1, 2, 0.001) = " + sqrt(2, 1, 2, 0.001));
	    System.out.println("sqrt(0.25, 0, 1, 0.001) = " + sqrt(0.25, 0, 1, 0.001));

	    
	}

	/**
	 * Function that says if two values are equal within precision
	 */
	private static boolean equal(double x, double y, double precision)
	{
	    return Math.abs(x - y) < precision;
	}

	/**
	 * Iterative function to compute sqrt
	 */
	private static double sqrt(double x, double lower, double upper, double precision)
	{
	    // TODO Cautati intre lower si upper o valoare care ridicata
	    // la patrat sa dea x.
	    // La calcularea pozitiei de mijloc folositi
	    //       double m = lower + (upper - lower) / 2;
	    // pentru a evita overflow la adunarea pe double
	    // Folositi functia equal pentru a verifica cu aproximare egalitatea
	    
		if (lower > upper) return -1;
		
		double m = lower + (upper - lower) / 2;
		
		if (equal(m * m, x, precision)) return m;
		else if (m * m > x) return sqrt(x, lower, m, precision);
		else return sqrt(x, m + precision, upper, precision); // if (m * m < x)
		
		//return -2;
		
	}
}
