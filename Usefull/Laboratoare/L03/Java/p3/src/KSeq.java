/**
 * Proiectarea Algoritmilor, 2013
 * Lab 3: Greedy si Programare Dinamica
 * Task 3: Alegerea a K subsecvente pentru suma maxima
 *
 * @author 	Sorina Sandu, Mihai Bivol, Alex Olteanu
 * @email	alexandru.olteanu@cs.pub.ro
 */

import java.io.File;
import java.util.Scanner;

public class KSeq {

	public static final int NO_TESTS = 3;
	public int[] k = new int[NO_TESTS];		// numarul de subsecvente
	public int[][] l = new int[NO_TESTS][];	// lista de numere

	public static void main(String[] args) {
		KSeq ks = new KSeq();
		ks.readData("date.in");
		ks.test();
	}

	/**
	 * for each of the tests
	 * find the longest common substring
	 */
	private void test () {
		/* for each of the tests */
		for (int test=0; test<NO_TESTS; test++) {

			int solutie = chooseKSeq(l[test], k[test]);

			System.out.print("Pentru lista ");
			printv(l[test]);
			System.out.println();
			System.out.println("cu "+k[test]+" subsecvente se poate obtine suma "+solutie);

		}
	}

	/**	Intoarce suma maxima ce se poate obtine folosind k secvente disjuncte din l
	 * e.g. l = 1 2 3 -1 2 -3 4
	 * k = 3
	 * Suma maxima este 12, folosind secventele (1 2 3) (2) si (4)
	 * k = 2
	 * Suma maxima este 11, folosind secventele (1 2 3 -1 2) si (4)
	 *
	 * presupunem ca vectorul are un 0 inserat la inceput, indicii vor incepe de la 1.
  	 */
	private int chooseKSeq(int[] l, int k)
	{
		int[][] maxSum = new int [k + 1][l.length + 1];	// maxSum[i][j] = suma maxima ce se poate obtine
													// din i secvente, folosind primele j elemente din v.
		int[] partialSums = new int[l.length];		// partialSums[i] = suma elementelor de la 0 la i inclusiv

		/*TODO 1) Calculati sumele partiale din lista l in partialSums */
		
		partialSums[0] = l[0];
		for (int i = 1; i < l.length; i++)
			partialSums[i] = partialSums[i - 1] + l[i];
		

		/*TODO 2) Adaugati in best suma maxima ce se poate obtine atunci cand nu
		 * putem folosi nicio secventa (completati maxSum[0][i])
		 */
		
		for (int i = 0; i < l.length + 1; i++)
			maxSum[0][i] = 0;

		/*TODO 3) Adaugati in maxSum suma maxima ce se poate obtine nefolosind niciun
		/* element din vector si k subsecvente
		 */
		
		for (int i = 0; i < k + 1; i++)
			maxSum[i][0] = 0;

		/*TODO 4) Completati intreaga matrice maxSum
		 * Cand calculez best[seq][i] am urmatoarele cazuri:
		 * 1) i nu face parte din secventa cu numarul seq.
		 * 2) i face parte din secventa cu numarul seq:
		 *	   -verific de unde poate poate incepe o secventa care se termina la i.
		 *	   -adaug suma secventei la cea mai buna suma pe care o pot obtine
		 *		   cu seq-1 secvente si nu se suprapune cu secventa mea.
		 */
		
		int maxim;
		
		for (int i = 1; i < k + 1; i++)
			for (int j = 1; j < l.length + 1; j++) {
				
				maxim = partialSums[l.length - 1]; // ultimul element
				
				for (int x = 1; x < i + 1; x++) // ma uita pe coloana precedenta la primii i termeni
					if (maxim < maxSum[x - 1][j - 1] + partialSums[i] - partialSums[x - 1])
						maxim = maxSum[x - 1][j - 1] + partialSums[i] - partialSums[x - 1];
				
				if (maxim > maxSum[i - 1][j]) maxSum[i][j] = maxim;
				else maxSum[i][j] =  maxSum[i - 1][j];
				
			}
		
		System.out.println("---------------");
		System.out.println("Sume partiale: ");
		for (int i = 0; i < partialSums.length; i++)
			System.out.print(partialSums[i] + " ");
		System.out.println();
		
		System.out.println("Matricea: ");
		for (int i = 0; i < k + 1; i++) {
			for (int j = 0; j < l.length + 1; j++)
				System.out.print(maxSum[i][j] + " ");
			System.out.println();
		}

		return maxSum[k][l.length - 1];
	}

	/**
	 * Function to read all the tests
	 * @param filename
	 */
	private void readData ( String filename ) {
		Scanner scanner = null;

		/* you should use try-catch for proper error handling! */
		try {

			scanner = new Scanner(new File(filename));

			for (int i=0;i<NO_TESTS;i++){

				/* read the array in which to look for data */
				k[i] = scanner.nextInt();		// numarul de subsecvente
				int n = scanner.nextInt();		// dimensiunea listei de numere
				l[i] = new int[n];
				for (int j=0;j<n;j++) {
					l[i][j] = scanner.nextInt();
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
