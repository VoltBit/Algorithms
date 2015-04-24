import java.util.ArrayList;


/**
 * Proiectarea Algoritmilor, 2013
 * Lab 4: Backtracking si optimizari
 * Task 1: Queen Positioning Problem - Simple Backtracking
 *
 * @author 	Cristian Condurache
 * @email	cristian.condurache@cti.pub.ro
 */

public class QueenPositioning {

	public static int bktCounter = 0;
	public static int solutionCounter = 0;

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
	 * Intoarce true daca regina adaugata la pozitia (row, column)
	 * nu se ataca cu celelalte regine pozitionate pe coloanele [0, column - 1]
	 */
	public static boolean nonAttacking(int[] queens, int row, int column) {
		
		// TODO 1: Verifica daca reginele din queens pozitionate anterior
		// se ataca cu regina pozitionata la row si column
		
		//System.out.println(queens.length);
		
		//if (row == column) return false;
		
		for (int i = 0; i < column; i++)
			if (areQueensAttacking(queens[i], i, row, column)) {
				return false;
			}
		
		return true;
	}

	/**
	 * Implementarea backtracking-ului simplu
	 * in array-ul queens, la pozitia queens[i] se afla randul ales pentru
	 * regina aflata pe coloana i
	 */
	public static void doBKT(int[] queens, int current, int n) {

		bktCounter++; // incrementam numarul total de intrari in recursivitate

		// TODO 2: Implementarea algoritmului de backtracking simplu
		// TODO 3: Afisarea tuturor solutiilor gasite
		// TODO 4: Incrementarea variabilei solutionCounter pentru fiecare solutie
		
		if (current == n) {
			solutionCounter++;
			/*
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) 
					if (queens[j] == i) System.out.print("R ");
					else System.out.print("O ");
				System.out.println();
			}
			
			System.out.println();
			System.out.println("-------------------------");
			System.out.println();
			*/
			return;
		}
		
		/*
		 * 	If the remainder from dividing N by 6 is not 2 or 3 then the list is simply all even numbers 
		 * 		followed by all odd numbers <= N
			Otherwise, write separate lists of even and odd numbers (i.e. 2,4,6,8 - 1,3,5,7)
			If the remainder is 2, swap 1 and 3 in odd list and move 5 to the end (i.e. 3,1,7,5)
			If the remainder is 3, move 2 to the end of even list and 1,3 to the end of odd list (i.e. 4,6,8,2 - 5,7,9,1,3)
			Append odd list to the even list and place queens in the rows given by these numbers, 
				from left to right (i.e. a2, b4, c6, d8, e3, f1, g7, h5)
		 * 
		 */
		
		ArrayList<Integer> impare = new ArrayList<Integer>();
		ArrayList<Integer> pare = new ArrayList<Integer>();
		
		for (int i = 1; i <= n; i++)
			if (i % 2 == 0) pare.add(i);
			else impare.add(i);
		
		if (n % 6 == 2) {
			
			impare.set(0, 3);
			impare.set(1, 1);
			
		} else if (n % 6 == 3) {
			
			pare.remove(0);
			pare.add(2);
			
			impare.remove(0);
			impare.remove(1);
			impare.add(1);
			impare.add(3);
			
		}
		
		impare.addAll(pare);

		//System.out.println(impare);
		
		for (int i = 1; i <= n; i++) { // parcurg liniile
			
			if 	(nonAttacking(queens, impare.get(i - 1), current)) { // current = coloana
				queens[current] = impare.get(i - 1);
				doBKT(queens, current + 1, n);
			}
			
		}
	}

	public static void main(String[] args) {
		int maxDim = 10;

		for(int i = 8; i < maxDim; i++) {
			bktCounter = 0;
			solutionCounter = 0;

			doBKT(new int[i], 0, i);

			System.out.println("Numar regine : " + i);
			System.out.println("Numar de intrari in recursivitare :" + bktCounter);
			System.out.println("Numar de solutii gasite: " + solutionCounter);
			System.out.println();
		}


	}
}
