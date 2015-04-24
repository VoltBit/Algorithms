/**
 * Proiectarea Algoritmilor, 2013
 * Lab 1: Divide et Impera
 * Task 3.1: Statistici de ordine: al k-lea element (+ quick sort) 
 * 
 * @author 	Mihai Bivol, Alex Olteanu
 * @email	alexandru.olteanu@cs.pub.ro
 */

import java.io.File;
import java.util.Scanner;

public class OrderStats {

	public static final int NO_TESTS = 2;
	public int[][] v = new int[NO_TESTS][];			// arrays in with input data

	public static void main(String[] args) {
		OrderStats os = new OrderStats();
		os.readData("date.in");
		os.test();
	}
	
	/** 
	 * for each of the tests  
	 * compute order statistics and sort
	 */
	private void test () {
		/* for each of the tests */
		for (int test=0; test<NO_TESTS; test++) {
			/* aflam al k-lea element, pentru toti k */
			for (int k=0; k<v[test].length; k++) {
				int kth = kthMin(v[test], 0, v[test].length-1,k);
				System.out.print("In {");
				for (int e: v[test]) System.out.print(e+" ");
				System.out.println("} al "+(k+1)+"-lea element ca ordine este "+ kth +".");
			}
			/* sortam vectorul folosind qsort definit mai jos */
			qsort(v[test], 0, v[test].length-1);
			System.out.print("iar vectorul sortat este {");
			for (int e: v[test]) System.out.print(e+" ");
			System.out.println("}");
		}
	}
	
	// http://stackoverflow.com/a/12308930/1481283
	
	private static void swap(int[] v, int i, int j) {
		
		int temp = v[i];
		v[i] = v[j];
		v[j] = temp;
		
	}
	
	int partition(int v[], int lower, int upper) {
		
		int pivot = v[lower];
		int i = lower;
		
		for (int j = lower + 1; j <= upper; j++) {
			
			int current = v[j];
			if (current < pivot) {
				
				swap(v, i + 1, j);
				i++;
				
			}
		}
		
		swap(v, lower, i);
		return i;
		
	}
	
	// http://www.geeksforgeeks.org/k-largestor-smallest-elements-in-an-array/#comment-3984
	
	int kthMin(int[] v, int lower, int upper, int k) {
		
	    /* TODO Completati codul pentru a afla al k-lea minim din vectorul v
	     * trebuie sa adaugati si o functie de partitionare (ca la quick sort)
	     */
	    
	    int index = partition(v, lower, upper);

	    if (k == index) return v[index];
	    else if (k < index) return kthMin(v, lower, index - 1, k);
	    else return kthMin(v, index + 1, upper, k); // if (k > index)
		
	}
	
	void qsort(int[] v, int lower, int upper) {
		
	    /*TODO Completati codul pentru a realiza quicksort
	     * folositi aceeasi functie de partitionare scrisa pentru kthMin
	     */
		
		if (upper - lower <= 1) return;
	    
		int index = partition(v, lower, upper);
		qsort(v, lower, index);
		qsort(v, index + 1, upper);
		
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
	
}
