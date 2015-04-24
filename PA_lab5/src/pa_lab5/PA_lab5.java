package pa_lab5;
import java.util.ArrayList;
import java.util.Scanner;
import static pa_lab5.Nim.Inf;

/**
 * O clasa cu 2 membri de orice tip
 * Echivalent cu std::pair din C++
 */
class Pair<F, S> {
    public F first;
    public S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }
}

/**
 * Reprezinta o mutare efectuata de un jucator
 */
class Move {
    public int amount, heap;

    public Move(int amount, int heap) {
        this.amount = amount; /* Cantitatea extrasa, 1, 2 sau 3 */
        this.heap = heap; /* Indicile multimii din care se face extragerea */
    }

    public Move() {
        this(0, -1);
    }
}

/**
 * Reprezinta starea jocului
 */
class Nim {
    public static int Inf = 123456789;

    public int heaps[];

    public Nim() {
        heaps = new int[3];
        heaps[0] = 3;
        heaps[1] = 4;
        heaps[2] = 5;
    }

    /**
     * Returneaza o lista cu mutarile posibile
     * care pot fi efectuate de player
     */
    public ArrayList<Move> get_moves(int player) {
        ArrayList<Move> ret = new ArrayList<Move>();
        for (int i = 0; i < 3; i++)
            for (int k = 1; k <= 3; k++)
                if (k <= heaps[i])
                    ret.add(new Move(k, i));
        return ret;
    }

    /**
     * Intoarce true daca jocul este intr-o stare finala
     */
    public boolean ended() {
        /**
         * TODO Determinati daca jocul s-a terminat
         */
        return heaps[0] + heaps[1] + heaps[2] == 1;
    }

    /**
     * Functia de evaluare a starii curente a jocului
     * Evaluarea se face din perspectiva jucatorului
     * aflat curent la mutare (player)
     */
    public int eval(int player) {
        
        /**
         * TODO Implementati o functie de evaluare
         * pentru starea curenta a jocului
         *
         * Aceasta trebuie sa intoarca:
         * Inf daca jocul este terminat in favoarea lui player
         * -Inf daca jocul este terminat in defavoarea lui player
         *
         * In celelalte cazuri ar trebui sa intoarca un scor cu atat
         * mai mare, cu cat player ar avea o sansa mai mare de castig
         */
//        int check = 0;
//        if((heaps[0] ^ aux) > heaps[0]) return -1;
//        if((heaps[1] ^ aux) > heaps[1]) return -1;
//        if((heaps[2] ^ aux) > heaps[2]) check -= 1;
//        return check;
        
        if(ended()) return -Inf;
        if(this.heaps[0] + this.heaps[1] + this.heaps[2] == 2) return Inf;
        int aux = this.heaps[0] ^ this.heaps[1] ^ this.heaps[2];

        int check = 0;
        //if(heaps[0] != 0 && heaps[1] != 0 && heaps[2] != 0){
            if((heaps[0] ^ aux) <= heaps[0]) check += 1;
            if((heaps[1] ^ aux) <= heaps[1]) check += 1;
            if((heaps[2] ^ aux) <= heaps[2]) check += 1;
        //}
        if(player == 1) return check;
        else return -check;
    }

    /**
     * Aplica o mutarea a jucatorului asupra starii curente
     * Returneaza false daca mutarea e invalida
     */
    public boolean apply_move(Move move) {
        /**
         * TODO Realizati efectuarea mutarii
         * (scadeti move.amount din multimea corespunzatoare
         */
        if(move.heap != -1 && move.heap < 3 && this.heaps[move.heap] > move.amount){
            this.heaps[move.heap] -= move.amount;
            return true;
        }
        return false;
    }

    /**
     * Returns true if player won
     */
    boolean winner(int player)
    {
        if (!ended())
            return false;
        int s = 0;
        for (int i = 0; i < 3; i++)
            s += heaps[i];
        return s == 0;
    }

    /**
     * Afiseaza starea jocului
     */
    public String toString() {
        String ret = "";
        for (int i = 0; i < 3; i++)
        {
            ret += i + ":";
            for (int j = 0; j < heaps[i]; j++)
                ret += " *";
            ret += "\n";
        }
        ret += "\n";

        return ret;
    }

    /**
     * Returneaza o copie a starii de joc
     */
    public Object clone() {
        Nim ret = new Nim();
        for (int i = 0; i < 3; i++)
            ret.heaps[i] = heaps[i];
        return ret;
    }
}

class PA_lab5 {

    /**
     * Implementarea algoritmului minimax (negamax)
     * Intoarce o pereche <x, y> unde x este cel mai bun scor
     * care poate fi obtinut de jucatorul aflat la mutare,
     * iar y este mutarea propriu-zisa
     */
    public static Pair<Integer, Move> minimax(Nim init, int player, int depth) {
        /**
         * TODO Implementati conditia de oprire
         */
        Pair pair = new Pair(init.eval(player), null);
        if(depth == 0 || init.ended()) return pair;
        ArrayList<Move> moves = init.get_moves(player);

        /**
         * TODO Determinati cel mai bun scor si cea mai buna mutare
         * folosind algoritmul minimax
         */
        int max = -Inf;
        int score;
        Pair<Integer, Move> aux2 = new Pair(null, null);
        Move auxMove = new Move();
        Nim copy;
        for(Move mov : moves){
            copy = (Nim)init.clone();
            if(!copy.apply_move(mov)) break;
            aux2 = minimax(init, -player, (depth - 1));
            if(-aux2.first > max){
                max = -aux2.first;
                auxMove = mov;
            }
        }
        
        return new Pair(max, auxMove);
//        return new Pair<Integer, Move>(-Nim.Inf, new Move());
    }

    /**
     * Implementarea de negamax cu alpha-beta pruning
     * Intoarce o pereche <x, y> unde x este cel mai bun scor
     * care poate fi obtinut de jucatorul aflat la mutare,
     * iar y este mutarea propriu-zisa
     */
    public static Pair<Integer, Move> minimax_abeta(Nim init, int player, int depth, int alfa, int beta) {
        /**
         * TODO Implementati conditia de oprire
         */

        ArrayList<Move> moves = init.get_moves(player);
        /**
         * TODO Determinati cel mai bun scor si cea mai buna mutare
         * folosind algoritmul minimax cu alfa-beta pruning
         */

        return new Pair<Integer, Move>(-Nim.Inf, new Move());
    }
    public static void test(){
        int a = 1, c = 7;
        System.out.println("Test: " + (3 ^ 4 ^ 5));
    }
    public static void main(String args[]) {
        Nim nim = new Nim();
        nim.heaps[0] = 5;
        nim.heaps[1] = 10;
        nim.heaps[2] = 20;
        System.out.print(nim);
        test();
        /* Choose here if you want COMP vs HUMAN or COMP vs COMP */
        boolean HUMAN_PLAYER = true;
        int player = 1;

        while (!nim.ended())
        {
            Pair<Integer, Move> p;
            if (player == 1)
            {
                p = minimax(nim, player, 6);
                //p = minimax_abeta(nim, player, 13, -Nim.Inf, Nim.Inf);

                System.out.println("Player " + player + " evaluates to " + p.first);
                nim.apply_move(p.second);
            }
            else
            {
                if (!HUMAN_PLAYER)
                {
                    p = minimax(nim, player, 6);
                    //p = minimax_abeta(nim, player, 13, -Inf, Inf);

                    System.out.println("Player " + player + " evaluates to " + p.first);
                    nim.apply_move(p.second);
                }
                else
                {
                    boolean valid = false;
                    while (!valid)
                    {
                        Scanner keyboard = new Scanner(System.in);
                        System.out.print("Insert amount [1, 2 or 3] and heap [0, 1 or 2]: ");
                        int am = keyboard.nextInt();
                        int h = keyboard.nextInt();

                        valid = nim.apply_move(new Move(am, h));
                    }
                }
            }

            System.out.print(nim);
            player *= -1;
        }

        int w = nim.heaps[0] + nim.heaps[1] + nim.heaps[2];
        if (w == 0)
            System.out.println("Player " + player + " WON!");
        else
            System.out.println("Player " + player + " LOST!");
    }
}
