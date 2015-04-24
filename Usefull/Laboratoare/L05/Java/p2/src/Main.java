import java.util.Random;
import java.util.Scanner;

public class Main {

	private static final int INF = 99999;
	private static final int MAXDEPTH = 7;

	static int negaMax(Nim n, int depth) {
		
		int score, max = -INF;
		
		if (n.IsOver()) return -1;
		if (depth == 0) return 0;
		
		for (int i = 3; i < n.groups.length; i++) {
			
			if (n.groups[i] > 0)
			for (int j = 1; j <= i / 2; j++) {
				
				n.Split(i, j);
				
				score = -negaMax(n, depth - 1);
				
				if (score > max) max = score;
				
				n.Merge(j, i - j);
				
			}
			
		}
		
		return max;
		
	}
	
	static int alphaBeta(Nim n, int alpha, int beta) {
		
		int score;
		
		if (n.IsOver()) return -1;
		
		for (int i = 3; i < n.groups.length; i++) {
			
			if (n.groups[i] > 0)
			for (int j = 1; j <= i / 2; j++) {
				
				n.Split(i, j);
				
				score = -alphaBeta(n, -beta, -alpha);
				
				if (score >= beta) return beta;
				
				if (score > alpha) alpha = score;
				
				n.Merge(j, i - j);
				
			}
			
		}
		
		return alpha;
		
	}

	static Pair PlayMove(Nim n) {
		Pair move = new Pair(-1, -1);

		// TODO calculeaza cea mai buna mutare din pozitia data de tabla b
		// Mutarea este data de perechea (rand, coloana) unde va fi plasat X sau
		// O
		// Hint: Implementati o functie (minimax / negamax) ce intoarce o mutare
		// prin parametru

		int score, max = -INF;
		
		for (int i = 3; i < n.groups.length; i++) {
			
			if (n.groups[i] > 0)
			for (int j = 1; j <= i / 2; j++) {
				
				n.Split(i, j);
				
				score = -negaMax(n, MAXDEPTH);
				//score = -alphaBeta(n, -INF, INF);
				
				if (score > max) {
					max = score;
					move = new Pair(i, j);
				}
				
				n.Merge(j, i - j);
				
			}
			
		}

		// Intoarce noua mutare
		return move;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		System.out.println("Initial heap size:");
		Nim n = new Nim(in.nextInt());

		Random r = new Random(System.currentTimeMillis());
		int start = Math.abs(r.nextInt()) % 2;
		int init = start;
		// Daca start este 1, incepe calculatorul, altfel jucatorul
		PLAYER perspective = PLAYER.PLAYER1;
		// Player1 mereu va avea prima mutare
		System.out.println("Initial piles:\n");
		System.out.println(n + "\n");
		while (!n.IsOver()) {
			Pair current_move;

			if (start == 1) {
				System.out.println("Computer is thinking...");
				Nim newNim = new Nim(n);
				current_move = PlayMove(newNim);
				System.out.println("Computer chose move: ("
						+ current_move.first + " " + current_move.second
						+ ")\n");
			} else {
				int initial, left_side;
				System.out.println("It's your turn, enter move:\n");
				System.out.println("Size of pile to split:");
				initial = in.nextInt();
				System.out.println("Size of left split:");
				left_side = in.nextInt();
				current_move = new Pair(initial, left_side);
			}

			if (n.Split(current_move.first, current_move.second) != 0) {
				System.out.println("Invalid move, exiting\n");
				return;
			}

			start = 1 - start;
			perspective = (perspective == PLAYER.PLAYER1) ? PLAYER.PLAYER2
					: PLAYER.PLAYER1;
			System.out.println("Current piles are:\n" + n + "\n");
		}

		System.out.println("Game is finished\n");
		if (perspective == PLAYER.PLAYER1) {
			if (init == 0) {
				System.out.println("Computer won\n");
				return;
			}
			System.out.println("Player won\n");
			return;
		}
		if (init == 0) {
			System.out.println("Player won\n");
			return;
		}
		System.out.println("Computer won\n");
		return;
	}
}
