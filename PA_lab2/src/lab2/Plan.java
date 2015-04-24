package lab2;

/**
 * Proiectarea Algoritmilor, 2014
 * Lab 2: Greedy & PD
 * Task 1: Se dorește parcurgerea unui drum între doua localități A și B. 
 * Cunoscând distanța către toate benzinăriile de pe ruta A→B față de orașul A 
 * și știind că având rezervorul plin se pot parcurge maxim m kilometri, să 
 * se facă o planificare astfel încat numărul de opriri la benzinării să fie 
 * minim. De asemenea, se garantează faptul că distanța dintre oricare doua 
 * benzinării consecutive este mai mică de m kilometri. 
 * 
 * Codul citeste un set de teste din fisierul de date, fiecare test constand din:
 * - numarul maxim de kilometri ce pot fi parcursi cu rezervorul plin m
 * - distanta catre toate benzinariile de pe ruta A->B
 * 
 * @author 	Vlad Bogolin
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Plan {

	public static final int NO_TESTS = 2;
	public int[][] distances = new int[NO_TESTS][];
	public int[] m = new int[NO_TESTS];

	public static void run() {
		Plan o = new Plan();
		o.readData("date1.in");
		o.test();
	}

	/**
	 * for each of the tests get the minimum number of stops
	 */
	private void test() {
		/* for each of the tests */
		for (int test = 0; test < NO_TESTS; test++) {
			ArrayList<Integer> stops = planRoute(m[test], distances[test]);
			System.out.print("Pentru ruta {");
			for (int e : distances[test])
				System.out.print(e + " ");
			System.out.print("}, opririle planificate sunt {");
			if (stops != null)
				for (int e : stops)
					System.out.print(e + " ");
			System.out.print("} si numarul minim de opriri este ");
			if (stops != null)
				System.out.println(stops.size());
			else
				System.out.println(0);
		}
	}

	private ArrayList<Integer> planRoute(int m, int[] distances) {
		// TODO
		// Calculati numarul minim de opriri la benzinarii si returnati
		// o lista cu indicii benzinariilor la care se vor face opririle
		ArrayList<Integer> result = new ArrayList<Integer>();
                int i = 1, aux = m;
                while(i < distances.length){
                    while(distances[i] - aux < 0){
                        i++;
                        if(i == distances.length) break;
                    }
                    result.add(distances[i - 1]);
                    aux = distances[i - 1] + m;
                    i++;
                }
		return result;
	}

	/**
	 * Function to read all the tests as pairs of arrays
	 * 
	 * @param filename
	 */
	private void readData(String filename) {
		Scanner scanner = null;

		/* you should use try-catch for proper error handling! */
		try {

			scanner = new Scanner(new File(filename));

			for (int i = 0; i < NO_TESTS; i++) {
				/* read the maximum number of km */
				m[i] = scanner.nextInt();
				/* read the distances array */
				int n = scanner.nextInt(); // array length
				distances[i] = new int[n];
				for (int j = 0; j < n; j++) {
					distances[i][j] = scanner.nextInt();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (scanner != null)
					scanner.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
