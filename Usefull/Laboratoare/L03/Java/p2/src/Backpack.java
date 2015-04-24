/**
 * Proiectarea Algoritmilor, 2013
 * Lab 3: Greedy si Programare Dinamica
 * Task 2: Problema rucsacului, varianta continua si discreta
 *
 * @author 	Sorina Sandu, Mihai Bivol, Alex Olteanu
 * @email	alexandru.olteanu@cs.pub.ro
 */

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

public class Backpack {

	public static final int NO_TESTS = 2;
	public static final int UNDEF = -1;
	public int[] cap = new int[NO_TESTS];				// capacitatea
	public Obiect[][] obiecte = new Obiect[NO_TESTS][];	// lista de obiecte

	public static void main(String[] args) {
		Backpack b = new Backpack();
		b.readData("date.in");
		b.test();
	}

	/**
	 * for each of the tests
	 * find the longest common substring
	 */
	private void test () {
		/* for each of the tests */
		for (int test=0; test<NO_TESTS; test++) {

			System.out.print("Capacitate "+cap[test]+" si obiectele ");
			printv(obiecte[test]);
			System.out.println();

			Obiect[] solutie = chooseObjectsGreedy(cap[test], obiecte[test]);
			System.out.print("Folosind Greedy, obtinem selectia ");
			printv(solutie);
			printValue(solutie);
			System.out.println();

			solutie = chooseObjectsPD(cap[test], obiecte[test]);
			System.out.print("Folosind PD, obtinem selectia ");
			printv(solutie);
			printValue(solutie);
			System.out.println();
			System.out.println();
		}
	}

	/**
	 * functie ce alege obiecte din vectorul obiecte pentru capacitatea cap folosind Greedy
	 */
	private Obiect[] chooseObjectsGreedy(int cap, Obiect[] obiecte) {

		Vector<Obiect> solutie = new Vector<Obiect>();

		Arrays.sort(obiecte);

		//printv(obiecte);
		//System.out.println();

		int capacitate = cap;
		
		for (Obiect ob : obiecte) {
			
			if (capacitate == 0) break;
			
			if (ob.weight <= capacitate) {
				
				solutie.add(ob);
				capacitate -= ob.weight;
				
			} else {
				
				double valoare = capacitate * ob.value / ob.weight;
				Obiect obiect_segmentat = new Obiect(capacitate, valoare);
				solutie.add(obiect_segmentat);
				capacitate = 0;
				
			}
			
		}
		
		return solutie.toArray(new Obiect[solutie.size()]);

	}

	/**
	 * functie ce alege obiecte din vectorul obiecte pentru capacitatea cap folosind PD
	 */
	private Obiect[] chooseObjectsPD(int cap, Obiect[] obiecte) {

		Vector<Integer> solutieIndex = new Vector<Integer>();	  // initial vom pune indecsii solutiei aici
		Obiect[] solutie;										  // iar in acest vector vom pune setul de obiecte
		double[][] valMax = new double[obiecte.length + 1][cap + 1]; // valMax[i][j] = valoarea maxima pentru
												// obiecte de la 0 la i, folosite pentru capacitatea maxima j

		// TODO Initializam prima coloana cu 0
		
		for (int i = 0; i < obiecte.length + 1; i++)
			valMax[i][0] = 0;
			

		// TODO Calculam pentru fiecare încarcare mai mica sau egala cu capacitatea
		// cea mai mare valoare pe care o putem obtine. Pentru aceasta,
		// avem nevoie de valorile pentru greutatile mai mici.
		//
		// Pentru a folosi fiecare obiect o singura data, consideram treptat toate
		// solutiile ce tin cont de primele {1, 2, ..., n} obiecte.
		//
		
		double maxim;
		
		for (int i = 1; i < obiecte.length + 1; i++) {
			
			for (int j = 0; j < cap + 1; j++) {
				
				maxim = valMax[i - 1][j];
				
				if (j - obiecte[i - 1].weight >= 0)
					if (maxim < valMax[i - 1][j - obiecte[i - 1].weight] + obiecte[i - 1].value)
						maxim = valMax[i - 1][j - obiecte[i - 1].weight] + obiecte[i - 1].value;
				
				valMax[i][j] = maxim;
				
			}
		}
		
		for (int i = 0; i < obiecte.length + 1; i++) {
			for (int j = 0; j < cap + 1; j++)
				System.out.print(valMax[i][j] + "\t");
			System.out.println();
		}
		
		int result = UNDEF;
		// TODO Cautam pe ultima linie a matricei valoarea maxima si salvam
		// indicele acesteia in variabila result.
		
		result = cap;
		
		// TODO Dupa ce am aflat pe ce pozitie se afla valoarea maxima care poate
		// fi atinsa, plecam de la pozitia maximului pentru a reconstitui solutia.

		for (int i = obiecte.length; i > 0; i--) {
			
			if (valMax[i][result] != valMax[i - 1][result]) {
				
				solutieIndex.add(i - 1);
				result -= obiecte[i - 1].weight;
				
			}
			
		}

		solutie = new Obiect[solutieIndex.size()];
		int i=0;
		for (Integer idx: solutieIndex) {
			solutie[i++] = obiecte[idx];
		}

		return solutie;

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
				cap[i] = scanner.nextInt();			// capacitate
				int n = scanner.nextInt();			// numar de obiecte
				obiecte[i] = new Obiect[n];
				for (int j=0;j<n;j++) {
					obiecte[i][j] = new Obiect(scanner.nextInt(), scanner.nextDouble());
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
	private void printv(Object[] v) {
		System.out.print("{ ");
		for (Object elem:v){
			System.out.print(elem+" ");
		}
		System.out.print("}");
	}
	private void printValue(Obiect[] os) {
		double totalValue = 0;
		for (Obiect o:os){
			totalValue += o.value;
		}
		System.out.print(" valoare totala = "+totalValue);
	}

	private class Obiect implements Comparable<Obiect>{
		int weight;
		double value;

		public Obiect(int weight, double value) {
			super();
			this.weight = weight;
			this.value = value;
		}

		@Override
		public String toString() {
			return "[w=" + weight + ", v=" + value + "] ";
		}

		public int compareTo(Obiect o) {
			// TODO comparati doua obiecte pentru Greedy (dorim sa sortam descrescator)
			return (int)(o.value - this.value);
		}
	}
}
