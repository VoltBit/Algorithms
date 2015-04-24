package lab2;

/**
 * Proiectarea Algoritmilor, 2013
 * Lab 2: Greedy si Programare Dinamica
 * Task 3.2: Dandu-se doua siruri S1 si S2, gasiti cel mai lung subsir comun al lor.
 *
 * @author 	Sorina Sandu, Mihai Bivol, Alex Olteanu
 * @email	alexandru.olteanu@cs.pub.ro
 */

import java.io.File;
import java.util.Scanner;

public class LongestPalindromeSubstring {

	public static final int NO_TESTS = 3;
	public int[][] v = new int[NO_TESTS][];			

	public static void run() {
		LongestPalindromeSubstring lps = new LongestPalindromeSubstring(); 
		lps.readData("date.in");
		lps.test();
	}

	/**
	 * for each of the tests
	 * find the longest common substring
	 */
	private void test () {
		/* for each of the tests */
		for (int test=0; test<NO_TESTS; test++) {
			int[] rez = lps(v[test]);
			System.out.print("In vectorul ");
			printv(v[test]);
			System.out.print(" cea mai mare subsecventa palindrom este ");
			printv(rez);
			System.out.println();
		}
	}

	/**
	 * functie ce calculeaza si returneaza cea mai lunga subsecventa palindrom din v
	 */
	private int[] lps(int[] v) {
		int n = v.length;					// lungimea vectorului de intrare
		boolean[][] L = new boolean[n][n];	// L[i][j]=1 cand de la i la j avem palindrom
		int max_len=1;						// lungimea celei mai mari subsecvente palindrom
		int max_poz=0;						// pozitia la care incepe subsecventa palindrom
		int[] sol;							// subsecventa propriu-zisa

		/* (1) folosim Programare Dinamica pentru a completa L 
		 * in acelasi timp actualizam max_len si max_poz
		 */
		
		/*TODO (1.1) calculam L pentru siruri de lungime 1 */
		
		
		/*TODO (1.2) calculam L pentru siruri de lungime 2 */
		
		
		/*TODO (1.3) calculam L pentru siruri de lungime cel putin 3
		 * hint: vom folosi o formula recursiva ce foloseste elemente calculate anterior */
		
		
		sol = new int[max_len];
		/*TODO (2) reconstruim subsecventa in sol */
		
		
		return sol;
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

				/* read the array in which to look for data */
				int n = scanner.nextInt();			// array length
				v[i] = new int[n];
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
	
	/** 
	 * Functie ce afiseaza pe ecran un vector
	 */
	private void printv(int[] v) {
		System.out.print("{ ");
		for (int elem:v){
			System.out.print(elem+" ");
		}
		System.out.print("}");
	}

}