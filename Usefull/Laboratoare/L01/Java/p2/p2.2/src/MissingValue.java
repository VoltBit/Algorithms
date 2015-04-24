import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

import sun.security.util.Length;

/**
 * Proiectarea Algoritmilor, 2013
 * Lab 1: Divide et Impera
 * Task 2.2: Se da o structura de date S, ce contine n-1 dintre cele n numere 
 * care au valori intre 0 si n-1
 * 
 * @author 	Mihai Bivol, Alex Olteanu
 * @email	alexandru.olteanu@cs.pub.ro
 */

public class MissingValue {

	public static final int NO_TESTS = 2;
	public static final int BIT_LENGTH = 16;
	long v[][] = new long[NO_TESTS][];
	
	public static void main(String[] args) {
		MissingValue mv = new MissingValue();
		mv.readData("date.in");
		mv.run();
	}
	
	private void run() {
		/* for each of the tests */
		for (int test=0; test<NO_TESTS; test++) {
			long missingValue = findMissing(v[test]);
			System.out.print("The missing value in {");
			for (long e: v[test]) System.out.print(e+" ");
			System.out.println("} is " + missingValue);
		}
	}


	private int get_middle(int n) {
		return (int) (Math.pow(2, n) / 2);
	}
	
	private long findMissing(long[] v) {
		//Vector<Long> setWithBit0;
		//Vector<Long> setWithBit1;

		long res = 0;
		//short current_bit = BIT_LENGTH - 1;
		short current_bit = (short) (Integer.toBinaryString(v.length).length() - 1);
		
	    /* TODO Gasiti valoarea lipsa din v.
	     * 
	     * v contine n-1 elemente distincte dintre cele n elemente de la 0 la n-1
	     * e.g. v = {0 1 3}, |v| = 3, v contine elemente in intervalul [0. 3], lipseste 2
	     *
	     * Hints!
	     * a) Presupunem ca bitii unui numar sunt numerotati de la 0 la n.
	     *    n = 3, x = 8
	     *        3 2 1 0
	     *    x = 1 0 0 0
	     *    
	     * b) In setul 0 1 2 3 5 6 7 trebuie cautat un numar de la 0 la 7. Daca sunt 4 numere 
	     * care au bitul 2 egal cu 0, putem spune cu certitudine ca avem toate numerele de la 0 la 3.
	     * De ce? Ce valoare are bitul 2 in numarul lipsa din set? Ce se intapla cand vom numara 
	     * cate numere au bitul 1 egal cu 0 in 5 6 7?
	     */
		
		int lower = 0;
		int upper = v.length - 1;
		Arrays.sort(v);
		
		do {
			
			int m = get_middle(current_bit + 1);
			
			if (getBit(v, lower + m - 1, current_bit) == 0) {
				
				lower = lower + m;
				res += m;
				
			} else {
				
				upper = m - 1;
				
			}
			
			/*
			System.out.println("current_bit: " + current_bit);
			System.out.println("m: " + m);
			System.out.println("bit: " + getBit(v, m - 1, current_bit));
			System.out.println("upper: " + upper);
			System.out.println("lower: " + lower);
			System.out.println();
			*/
			
			current_bit -= 1;
			
		} while (current_bit > -1);
		
	    return res;
		
	}
	
	/**
	 * Function that returns the bit on bit_index position inside the
	 * number on vector_index position in the v array
	 */
	public long getBit(long[] v, int vector_index, short bit_index)
	{
	    //return v[vector_index] & (1 << bit_index);
		return (v[vector_index] & ( 1 << bit_index )) >> bit_index;
	}
	
	/**
	 * Function to read all the tests as pairs of arrays
	 * @param filename
	 */
	private void readData ( String filename ) {
		Scanner scanner = null;
		
		/* you should use try-catch for proper error handling! */
		try {
			
			scanner = new Scanner(new File(filename));
			
			for (int i=0;i<NO_TESTS;i++){

				/* read the array of numbers */
				int n = scanner.nextInt();			// array length
				v[i] = new long[n];
				for (int j=0;j<n;j++) {
					v[i][j] = scanner.nextInt();
				}

			}
			
		} catch (Exception e) {		
			e.printStackTrace();
		} finally { 					
			/* trebuie sa inchidem buffered reader-ul */
			try {
				if (scanner != null) scanner.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
