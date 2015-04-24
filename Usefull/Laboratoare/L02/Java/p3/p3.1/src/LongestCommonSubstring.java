/**
 * Proiectarea Algoritmilor, 2013
 * Lab 2: Greedy si Programare Dinamica
 * Task 3.1: Dandu-se doua siruri S1 si S2, gasiti cel mai lung subsir comun al lor.
 *
 * @author 	Sorina Sandu, Mihai Bivol, Alex Olteanu
 * @email	alexandru.olteanu@cs.pub.ro
 */

import java.io.File;
import java.util.Scanner;

public class LongestCommonSubstring {

	public static final int NO_TESTS = 2;
	public int[][] s1 = new int[NO_TESTS][];			// arrays for s1
	public int[][] s2 = new int[NO_TESTS][];			// arrays for s2

	public static void main(String[] args) {
		LongestCommonSubstring lcs = new LongestCommonSubstring();
		lcs.readData("date.in");
		lcs.test();
	}

	/**
	 * for each of the tests
	 * find the longest common substring
	 */
	private void test () {
		/* for each of the tests */
		for (int test=0; test<NO_TESTS; test++) {
			int[] rez = lcs(s1[test], s2[test]);
			System.out.print("Cel mai lung subsir comun intre ");
			printv(s1[test]);
			System.out.print(" si ");
			printv(s2[test]);
			System.out.print(" este ");
			printv(rez);
			System.out.println();
		}
	}

	/**
	 * functie ce calculeaza si returneaza cel mai lung subsir comun al sirurilor s1 si s2
	 */
	private int[] lcs(int[] s1, int[] s2) {

		int[][] L = new int[s1.length][s2.length];	// stocheaza lungimea solutiei partiale
		int[] rez;									// subsirul propriu-zis
		
		/* bordam marginea de sus si marginea din stanga a lui L
		 * adica vom calcula lungimea maxima pentru prima litera din s1 si toate subsirurile lui s2,
		 * respectiv prima litera din s2 si toate subsirurile lui s1,
		 * pentru a scrie o formula de recurenta mai usoara
		 */
		for (int is1=0; is1<s1.length; is1++) {
			if (s1[is1] == s2[0]) L[is1][0]=1; 
			// else it's by default 0
		}
		for (int is2=0; is2<s2.length; is2++) {
			if (s2[is2] == s1[0]) L[0][is2]=1; 
			// else it's by default 0
		}
		
		/*TODO (1) calculam lungimea pentru toate celelalte elemente din L */
		
		int maxim = 0;
		int maxim_maxim = 0;
		
		for (int i = 1; i < s1.length; i++) {
			for (int j = 1; j < s2.length; j++) {
			
				if (s1[i] == s2[j]) {
					
					maxim = 0;
					for (int i1 = 0; i1 < i; i1++)
						for (int j1 = 0; j1 < j; j1++)
							if (L[i1][j1] > maxim) maxim = L[i1][j1];
					
					L[i][j] = maxim + 1;
					
					if (L[i][j] > maxim_maxim) maxim_maxim = L[i][j];
					
				}

			}
			
		}
		/*
		for (int i = 0; i < s1.length; i++) {
			for (int j = 0; j < s2.length; j++)
				System.out.print(L[i][j] + " ");
			System.out.println();
		}
		*/
		rez = new int[maxim_maxim];
		
		//System.out.println(maxim_maxim);
		
		int i = s1.length - 1;
		int j = s2.length - 1;
		int ceva = j;
		
		while (i >= 0) {
			
			j = ceva;
			
			while (j >= 0) {
				
				if (L[i][j] == maxim_maxim) {
					
					rez[maxim_maxim - 1] = s1[i];
					maxim_maxim--;
					ceva = j - 1;
					break;
					
				}
				
				j--;
				
			}
			
			i--;
			
			if (maxim_maxim == 0) break;
			
		}
		
		return rez;
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
				s1[i] = new int[n];
				for (int j=0;j<n;j++) {
					s1[i][j] = scanner.nextInt();
				}

				/* read the array in which to look for data */
				n = scanner.nextInt();				// array length
				s2[i] = new int[n];
				for (int j=0;j<n;j++) {
					s2[i][j] = scanner.nextInt();
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
