package lab2;

/**
 * Proiectarea Algoritmilor, 2013
 * Lab 2: Greedy si Programare Dinamica
 * Task 2: Change giving problem
 *
 * @author 	Sorina Sandu, Mihai Bivol, Alex Olteanu
 * @email	alexandru.olteanu@cs.pub.ro
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

class GiveChange {

	public static final int NO_TESTS = 2;
	public int[][] B = new int[NO_TESTS][]; // lista de bancnote pentru fiecare test
	public int[][] x = new int[NO_TESTS][]; // lista de resturi pentru fiecare test

	public static void run() {
		GiveChange gc = new GiveChange();
		gc.readData("date2.in");
		gc.test();
	}

	/**
	 * for each of the tests
	 * find the longest common substring
	 */
	private void test () {
		/* for each of the tests */
		for (int test=0; test<NO_TESTS; test++) {
			System.out.print("Setul de bancnote este ");
			printv(B[test]);
			System.out.println();
			for (int rest: x[test]) {
				System.out.print("Folosind Greedy, "+rest+" se da ");
				printv(changeGreedy(rest, B[test]));
				System.out.println();
				System.out.print("Folosind PD, "+rest+" se da ");
				printv(changePD(rest, B[test]));
				System.out.println();
			}
			System.out.println();
		}
	}

	/**
	 * functie ce calculeaza setul de bancnote folosind Greedy
	 */
	private Integer[] changeGreedy(int x, int[] B) {
		ArrayList<Integer> rest = new ArrayList<Integer>();
		int i = 0;
		/*TODO: folositi Greedy pentru a determina forma restului
		 * presupunem ca B este ordonat descrescator */ 
                int aux = x;
                while(aux > 0){
                    if(B[i] <= aux){
                        if(aux - B[i] >= 0){
                            aux -= B[i];
                            rest.add(B[i]);
                        }else{
//                            if( i >= B.length - 1) { System.err.println("x1: " + aux); break;}
                            i++;
                        }
                    }else{
//                        if( i >= B.length - 1) { System.err.println("x2: " + aux); break;}
                        i++;
                    }
                }
		return rest.toArray(new Integer[rest.size()]);
	}

	/**
	 * functie ce calculeaza setul de bancnote folosind PD
	 */
	private Integer[] changePD(int x, int[] B) {
		ArrayList<Integer> rest = new ArrayList<Integer>();
		int[] C = new int[x+1];	// C[p] = numarul minim de bancnote pt restul p
		int[] S = new int[x+1]; // S[p] = indicele urmatoarei bancnote pt restul p
		
		/* folositi PD pentru a determina forma restului
		 * presupunem ca B este ordonat descrescator */ 

                if(x == 0){
                    rest.add(0);
                    return rest.toArray(new Integer[rest.size()]);
                }
                // C[i] = min(C[i-b] + 1)
		int B_cresc[] = new int[B.length];
                boolean check = false;
		/*TODO (a) ordonam B crescator in B_cresc */
                int i;
                for(i = B.length - 1; i >= 0; i--){
                    B_cresc[B.length - i - 1] = B[i];
                }
		
		/*TODO (b) completam C[p] = numarul minim de bancnote necesare pentru a da restul p
		 * si S[p] = indexul urmatoarei monede necesare pentru a da restul p
		 * pentru fiecare p intre 0 si x */
                int p, j;
                C[0] = 0;
                C[1] = 1;
                int min;
                for(p = 1; p <= x; p++){
                    min = 0;
                    for(j = 0; j < B.length; j++){
                        if(p >= B[j] && C[p - B[j]] < min){
                            min = C[p - B[j]];
                            S[p] = j;
                        }
                    }
                    C[p] = min;
                    
                }
                for(j = 0; j < x; j++){
                    
                    System.out.print(B[S[j]] + "->" + C[j]);
                }
                /*
                    
                    C[0] = 0
                    C[1] = 1
                    C[p] = 
                         0 0 0 0 0 0 0 0 0
                    c = [0 1 2 3 4 5 6 7 8]
                
                */
		/*TODO (c) reconstruim solutia pe baza lui C si a lui S */
		
		
		return rest.toArray(new Integer[rest.size()]);
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

				int n = scanner.nextInt();			// array length
				B[i] = new int[n];
				for (int j=0;j<n;j++) {
					B[i][j] = scanner.nextInt();
				}

				n = scanner.nextInt();			// array length
				x[i] = new int[n];
				for (int j=0;j<n;j++) {
					x[i][j] = scanner.nextInt();
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
	private void printv(Integer[] v) {
		System.out.print("{ ");
		for (Integer elem:v){
			System.out.print(elem+" ");
		}
		System.out.print("}");
	}

}