import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final int INF = 999999;
    private static final int MAXDEPTH = 20;
    
    static int negaMax(Board b, PLAYER play_as) {
    	
    	int score, max = -INF;
    	
    	PLAYER other_player;
    	
    	if (play_as == PLAYER.PLAY_AS_ZERO)
    		other_player = PLAYER.PLAY_AS_X;
    	else
    		other_player = PLAYER.PLAY_AS_ZERO;
    	
    	if (b.GetWinner() != WINNER.PLAYER_NONE) { // daca avem un castigator
    		if (b.GetWinner() == WINNER.PLAYER_X)
    			if (play_as == PLAYER.PLAY_AS_X) return 1;
    			else return -1;
    		else if (b.GetWinner() == WINNER.PLAYER_ZERO)
    			if (play_as == PLAYER.PLAY_AS_ZERO) return 1;
    			else return -1;
    		else return 0;
    	}
    	
    	for (Pair p : b.GetEmptySquares()) {
    		
    		if (play_as == PLAYER.PLAY_AS_X) b.MarkX(p.first, p.second);
    		else b.MarkZero(p.first, p.second);
    		
    		score = -negaMax(b, other_player);
    		if (score > max) max = score;
    		
    		b.ClearSquare(p.first, p.second);
    		
    	}
    	
    	return max;
    	
    }
    
    static int alphaBeta(Board b, PLAYER play_as, int alpha, int beta) {
    	
    	int score;
    	
    	PLAYER other_player;
    	
    	if (play_as == PLAYER.PLAY_AS_ZERO)
    		other_player = PLAYER.PLAY_AS_X;
    	else
    		other_player = PLAYER.PLAY_AS_ZERO;
    	
    	if (b.GetWinner() != WINNER.PLAYER_NONE) { // daca avem un castigator
    		
    		if (b.GetWinner() == WINNER.PLAYER_X)
    			if (play_as == PLAYER.PLAY_AS_X) return 1;
    			else return -1;
    		else if (b.GetWinner() == WINNER.PLAYER_ZERO)
    			if (play_as == PLAYER.PLAY_AS_ZERO) return 1;
    			else return -1;
    		else return 0;
    		
    	}
    	
    	for (Pair p : b.GetEmptySquares()) {
    		
    		if (play_as == PLAYER.PLAY_AS_X) b.MarkX(p.first, p.second);
    		else b.MarkZero(p.first, p.second);
    		
    		score = -alphaBeta(b, other_player, -beta, -alpha);
    		
    		if (score >= beta) {
    			b.ClearSquare(p.first, p.second);
    			return beta;
    		}
    		
    		if (score > alpha) alpha = score;
    		
    		b.ClearSquare(p.first, p.second);
    		
    	}
    	
    	return alpha;
    	
    }
    
    // static function
    static Pair PlayMove(Board b, PLAYER play_as) {
        Pair move = new Pair(-1, -1);

        // TODO calculeaza cea mai buna mutare din pozitia data de tabla b
        // Mutarea este data de perechea (rand, coloana) unde va fi plasat X sau
        // O
        // Hint: Implementati o functie (minimax / negamax) ce intoarce o mutare
        // prin parametru

    	int score, max = -INF;
    	
    	PLAYER other_player;
    	
    	if (play_as == PLAYER.PLAY_AS_ZERO)
    		other_player = PLAYER.PLAY_AS_X;
    	else
    		other_player = PLAYER.PLAY_AS_ZERO;
        
    	for (Pair p : b.GetEmptySquares()) {
    		
    		if (play_as == PLAYER.PLAY_AS_X) b.MarkX(p.first, p.second);
    		else b.MarkZero(p.first, p.second);
    		
    		//score = -negaMax(b, other_player);
    		score = -alphaBeta(b, other_player, -INF, INF);
    		if (score >= max) {
    			max = score;
    			move = p;
    		}
    		
    		b.ClearSquare(p.first, p.second);
    		
    	}

        // Intoarce noua mutare
        return move;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Board b = new Board();
        Scanner in = new Scanner(System.in);

        Random r = new Random(System.currentTimeMillis());
        int start = Math.abs(r.nextInt()) % 2;
        int init = start;
        // Daca start este 1, incepe calculatorul, altfel jucatorul
        PLAYER perspective = PLAYER.PLAY_AS_X;
        // X mereu va avea prima mutare


        while (b.GetWinner() == WINNER.PLAYER_NONE) {
            Pair current_move;
            if (start == 1) {
                System.out.println("Computer is thinking...");
                Board newBoard = new Board(b);
                current_move = PlayMove(newBoard, perspective);
                System.out.println("Computer chose move: (" + current_move.first +" " + current_move.second + ")");
            } else {
                int row, col;
                System.out.println("It's your turn, enter move:");
                do {
                    System.out.println("Row:");
                    row = in.nextInt();
                } while (row < 0 || row > 2);
                do {
                    System.out.println("Column:");
                    col = in.nextInt();
                } while (col < 0 || col > 2);
                current_move = new Pair(row, col);
            }
            if (b.GetMark(current_move.first, current_move.second) != MARK.NONE) {
                System.out.println("Invalid move, exiting");
                return;
            }
            if (perspective == PLAYER.PLAY_AS_X) {
                b.MarkX(current_move.first, current_move.second);
            } else {
                b.MarkZero(current_move.first, current_move.second);
            }
            start = 1 - start;
            perspective = (perspective == PLAYER.PLAY_AS_X) ? PLAYER.PLAY_AS_ZERO : PLAYER.PLAY_AS_X;
            System.out.println("Current board is:\n" + b);
        }

        System.out.println("Game is finished");
        if (b.GetWinner() == WINNER.PLAYER_X) {
            if (init == 1) {
                System.out.println("Computer won");
                return;
            }
            System.out.println("Player won, you should at most draw, keep working on the AI");
            return;
        }
        if (b.GetWinner() == WINNER.PLAYER_ZERO) {
            if (init == 1) {
                System.out.println("Player won, you should at most draw, keep working on the AI");
                return;
            }
            System.out.println("Computer won");
            return;
        }
        System.out.println("Draw, Well Done!");
        return;
    }
}
