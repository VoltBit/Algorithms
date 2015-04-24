/**
 * Proiectarea Algoritmilor, 2013
 * Lab 4: Backtracking si optimizari
 * Task 2: Queen Positioning Problem - Backtracking + AC3
 *
 * @author 	Cristian Condurache
 * @email	cristian.condurache@cti.pub.ro
 */
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class AC3 {

	private static class Arc {

		public Queen q1;
		public Queen q2;

		public Arc(Queen q1, Queen q2) {
			this.q1 = q1;
			this.q2 = q2;
		}
	}

	public static int bktCounter = 0;
	public static int solutionCounter = 0;

	/**
	 * Metoda care intoarce o copie deep (un ArrayList nou ce contine copii ale obiectelor)
	 * a ArrayList-ului trimis ca parametru
	 */
	public static ArrayList<Queen> makeListCopy(ArrayList<Queen> queens) {

		ArrayList<Queen> result = new ArrayList<Queen>(queens.size());
		for(Queen q : queens)
			result.add( (Queen)q.clone() );
		return result;
	}

	/**
	 * Metoda care intoarce true daca doua regine se ataca
	 */
	public static boolean areQueensAttacking(int row1, int column1, int row2, int column2) {
		// daca reginele sunt pe acelasi rand sau coloana
		boolean sameRow = (row1 == row2);
		boolean sameCol = (column1 == column2);
		// daca reginele sunt pe aceeasi diagonala paralela cu
		boolean sameFstDiagonal = (column2 - column1 == row2 - row1); // prima diagonala
		boolean sameSndDiagonal = (row1 + column1 == row2 + column2); // a doua diagonala

		return sameRow || sameCol || sameFstDiagonal || sameSndDiagonal;
	}

	/**
	 * Implementarea functiei de Verifica(Xk, Xm)
	 * - actualizeaza domeniul lui q1 si pastreaza valorile care au un corespondent in q2 care sa
	 * satisfaca restrictiile
	 * - intoarce true daca domeniul variabilei q1 a suferit modificari
	 */
	public static boolean check(Queen q1, Queen q2) {
		boolean delete = false;
		ArrayList<Integer> newDomain = new ArrayList<Integer>(); // vom crea un nou domeniu cu variabilele ramase

		// Pentru fiecare valoare din domeniul variabilei q1
		// verifica daca exista o valoare in domeniul lui q2 astfel incat
		// cele doua regine sa nu se atace reciproc
	
		boolean se_ataca = false;
		for (int i = 0; i < q1.domain.size(); i++) {
			
			se_ataca = true;
			
			for (int j = 0; j < q2.domain.size(); j++) 
				
				if (!areQueensAttacking(q1.domain.get(i), q1.column, q2.domain.get(j), q2.column)) {
					se_ataca = false;
					break;
				}
			
			if (!se_ataca) {
				newDomain.add(q1.domain.get(i));
			} else {
				//System.out.println("delete true");
				delete = true;
			}
			
		}
		
		
		// Daca pentru o valoare din domeniul variabilei q1
		// nu s-a gasit nicio valoare in domeniul lui q2 astfel incat
		// cele doua regine sa nu se atace => marcam delete = true

		// Hint: folositi functia areQueensAttacking pentru a verifica daca se ataca
		
		q1.domain = newDomain; // actualizam domeniul lui q1
		return delete; // intoarcem true daca s-au sters valori
	}

	/**
	 * Metoda care aplica agloritmul AC3 pe variabilele primite in lista queens
	 */
	public static void doAC3(ArrayList<Queen> queens) {
		
		Queue<Arc> q = new LinkedList<Arc>();
		// Initializare coada cu multimea arcelor
		/*
		for (int i = 0; i < queens.size() - 1; i++)
			for (int j = i + 1; j < queens.size(); j++)
				//q.add(new Arc(queens.get(i).clone(), queens.get(j)));
				q.add(new Arc((Queen) queens.get(i).clone(), (Queen) queens.get(j).clone()));
			
		*/
		
		for (int i = 0; i < queens.size(); i++)
			for (int j = 0; j < queens.size(); j++) {
				if (i != j)
					q.add(new Arc(queens.get(i), queens.get(j)));
			}
		
		// Cat timp mai exista arce de verificat in coada,
		// extrage primul arc si verifica domeniile folosind functia check de mai sus
		
		Arc a;
		
		while (!q.isEmpty()) {
			
			//System.out.println("intra ac3");
			
			a = q.remove();
			if (check(a.q1, a.q2))
				for (int i = 0; i < queens.size(); i++)
					if ((a.q1.column != queens.get(i).column) && (a.q2.column != queens.get(i).column))
						q.add(new Arc(queens.get(i), a.q1));
			
		}
		
		//System.out.println("iese ac3");
		
	}

	public static void doBKT(ArrayList<Queen> queens, int current, int n) {

		bktCounter ++; // incrementam numarul total de intrari in recursivitate
		
		// Aplicam algoritmul AC3 pe variabilele din lista queens
			
		if (current == n) {
			
			solutionCounter++;
			return;
			
		} 
		
		doAC3(queens);
		
		Queen currentQueen = queens.get(current); // variabila curenta pe domeniul careia aplicam bkt

		for (int i = 0 ; i < currentQueen.domain.size() ; i++) {

			//System.out.println("intra aici");
			
			ArrayList<Queen> queens_copy = makeListCopy(queens);
			queens_copy.get(current).setValue(currentQueen.domain.get(i));
			doBKT(queens_copy, current + 1, n);

		}

		
		// Aplicam backtracking pe valorile ramase in domeniul variabilei curente.
		// Atentie! va trebui sa folosim o copie a listei queens la intrarea in recursivitate.
		// Daca folosim direct functia setValue pe lista originala queens, la intoarcerea din
		// recursivitate vom pierde celelalte valori din domeniul variabilei pe care iteram.
		// Hint: folositi functia makeListCopy

	}

	public static void main(String[] args) {
		int maxDim = 10;

		for(int i = 8; i < maxDim; i++) {
			bktCounter = 0;
			solutionCounter = 0;

			ArrayList<Queen> queens = new ArrayList<Queen>();
			for(int j =0; j < i; j++)
				queens.add(new Queen(j, i));

			doBKT(queens, 0, i);

			System.out.println("Numar regine : " + i);
			System.out.println("Numar de intrari in recursivitare :" + bktCounter);
			System.out.println("Numar de solutii gasite: " + solutionCounter);
			System.out.println();
		}
	}
}
