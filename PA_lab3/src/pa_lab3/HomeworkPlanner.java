/**
 * Proiectarea Algoritmilor, 2014
 * Lab 3: Greedy si Programare Dinamica
 * Task 1: Planificarea temelor
 *
 * @author 	Sorina Sandu, Mihai Bivol, Alex Olteanu
 * @email	alexandru.olteanu@cs.pub.ro
 */
package pa_lab3;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.TreeMap;

public class HomeworkPlanner {

	public static final int NO_TESTS = 3;
	public int[] lastDay = new int[NO_TESTS];					// ultima zi in care se pot rezolva teme
	public Homework[][] homework = new Homework[NO_TESTS][];	// lista de teme

	public static void run() {
		HomeworkPlanner hp = new HomeworkPlanner();
		hp.readData("date.in");
		hp.test();
	}

	/**
	 * for each of the tests
	 * find the longest common substring
	 */
	private void test () {
		/* for each of the tests */
		for (int test=0; test<NO_TESTS; test++) {

			System.out.print("Pentru temele ");
			printv(homework[test]);
			System.out.println();

			Homework[] solutie = chooseHomework(homework[test], lastDay[test]);
			int i=0;
			for (Homework h:solutie) {
				i++;
				System.out.println("Ziua "+i+": "+h);
			}
		}
	}

        
	/**
	 * Intoarce planificarea temelor pentru un set de task-uri
	 * @homework: vector cu task-urile ce trebiue rezolvate
	 * @lastDay: ultima zi in care pot fi rezolvate teme
	 */
	Homework[] chooseHomework(Homework[] homework, int lastDay)
	{
		//adaugati in planning pe indicele i, tema care se va rezolva
		//in ziua i. Daca nu se va rezolva nicio tema, lasati null
		Homework[] planning = new Homework[lastDay+1];
		Homework[] homeworkCopy = new Homework[homework.length];
		int hi = 0;
		for (Homework h: homework)
			homeworkCopy[hi++] = new Homework(h.deadline, h.points);

		//TODO porniti planificarea de la ultima zi si adaugati tema care
		//maximizeaza numarul total de puncte obtinute.
                /* 
                   Sortez vectorul de teme descrescator dupa deadline. Apoi parcurg zilele de la ultima zi
                   din semestru pana la prima si petru fiecare zi aleg tema cu cel mai mare punctaj. Pentru a
                   determina tema cu cel mai mare punctaj parcurg vectorul sortat pana cand gasesc o tema
                   cu deadline mai mic decat ziua in care ma aflu si retin maximul.
                */
                Arrays.sort(homeworkCopy);
                System.out.println("Test: " + Arrays.toString(homeworkCopy));
                int sum = 0, best, lastDl = homeworkCopy[0].deadline;
                int i, pos = 0;
                Homework aux = null;
                for(i = lastDay; i >= 0; i--){
                    int max = 0;
                    for(int j = 0; j < homeworkCopy.length; j++){
                        if(homeworkCopy[j] != null){
                            if(homeworkCopy[j].deadline < i) break;
                            if(max < homeworkCopy[j].points){
                                max = homeworkCopy[j].points;
                                aux = homeworkCopy[j];
                                pos = j;
                            }
                        }
                    }
                    if(max != 0){
                        planning[i] = aux;
                        planning[pos] = null;
                    }
                }
		return planning;
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
				lastDay[i] = scanner.nextInt();		// ultima zi in care se pot rezolva teme
				int n = scanner.nextInt();			// numar de teme
				homework[i] = new Homework[n];
				for (int j=0;j<n;j++) {
					homework[i][j] = new Homework(scanner.nextInt(), scanner.nextInt());
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
			if (elem==null) System.out.println("null");
			else System.out.print(elem+" ");
		}
		System.out.print("}");
	}

	private class Homework implements Comparable<Homework> {
		int deadline;
		int points;

		public Homework(int deadline, int points) {
			super();
			this.deadline = deadline;
			this.points = points;
		}

		@Override
		public String toString() {
			return "(d:" + deadline + ", p:" + points + ") ";
		}

        @Override
        public int compareTo(Homework h) {
            return h.deadline - this.deadline;
        }

	}
}
