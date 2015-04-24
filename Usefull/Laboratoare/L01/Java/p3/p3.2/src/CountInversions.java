/**
 * Proiectarea Algoritmilor, 2013
 * Lab 1: Divide et Impera
 * Task 3.2: Se da un sir S de lungime n. Sa se detemine cate inversiuni sunt in sirul dat.
 * 
 * @author 	Mihai Bivol, Alex Olteanu
 * @email	alexandru.olteanu@cs.pub.ro
 */

import java.io.File;
import java.util.Scanner;
import java.util.Vector;

public class CountInversions {

	public static final int NO_TESTS = 2;
	public int[][] v = new int[NO_TESTS][];			// arrays in with input data

	public static void main(String[] args) {
		CountInversions ci = new CountInversions();
		ci.readData("date.in");
		ci.test();
	}
	
	/** 
	 * for each of the tests  
	 * count the inversions
	 */
	private void test () {
		/* for each of the tests */
		for (int test=0; test<NO_TESTS; test++) {
			int inversions = countInversions(v[test]);
			System.out.print("In {");
			for (int e: v[test]) System.out.print(e+" ");
			System.out.println("} sunt "+ inversions +" inversiuni.");
		}
	}

	
	int countInversions(int[] vec) {
		//TODO Intoarceti numarul de inversiuni din vectorul v
	   
		int res = 0;
		
		return res;
		
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
