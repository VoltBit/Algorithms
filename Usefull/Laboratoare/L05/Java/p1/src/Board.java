import java.util.ArrayList;

class Board {

	MARK board[][];

	public String toString() {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < 7; i++) {
			b.append("-");
		}
		b.append("\n");
		for (int i = 0; i < 3; i++) {
			b.append("|");
			for (int j = 0; j < 3; j++) {
				switch (board[i][j]) {
				case X:
					b.append("X");
					break;
				case ZERO:
					b.append("O");
					break;
				case NONE:
					b.append(" ");
					break;
				}
				b.append("|");
			}
			b.append("\n");
			for (int j = 0; j < 7; j++) {
				b.append("-");
			}
			b.append("\n");
		}

		return b.toString();

	}

	// Initializare BOARD gol
	public Board() {
		board = new MARK[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = MARK.NONE;
			}
		}
	}

	// Copiere Board
	public Board(Board b) {
		board = new MARK[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = b.board[i][j];
			}
		}
	}

	// Marcam la randul row si coloana col cu X
	public void MarkX(int row, int col) {
		if ((row < 0 || row > 2) || (col < 0 || col > 2)) {
			System.err.println("Invalid MarkX" + row + " " + col);
			System.exit(1);
		}
		board[row][col] = MARK.X;
	}

	// Marcam la randul row si coloana col cu Zero
	public void MarkZero(int row, int col) {
		if ((row < 0 || row > 2) || (col < 0 || col > 2)) {
			System.err.println("Invalid MarkZero" + row + " " + col);
			System.exit(1);
		}
		board[row][col] = MARK.ZERO;
	}

	// Intoarce marcajul de la randul row si coloana col
	public MARK GetMark(int row, int col) {
		if ((row < 0 || row > 2) || (col < 0 || col > 2)) {
			System.err.println("Invalid GetMark" + row + " " + col);
			System.exit(1);
		}
		return board[row][col];
	}

	// Intoarce un vector de coordonate cu casute ce au marcajul NONE
	public ArrayList<Pair> GetEmptySquares() {
		ArrayList<Pair> result = new ArrayList<Pair>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == MARK.NONE) {
					result.add(new Pair(i, j));
				}
			}
		}
		return result;
	}

	// Seteaza tot BOARD-ul la NONE
	public void ClearBoard() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[i][j] = MARK.NONE;
			}
		}
	}

	// Seteaza o casuta la NONE
	public void ClearSquare(int row, int col) {
		if ((row < 0 || row > 2) || (col < 0 || col > 2)) {
			System.err.println("Invalid GetMark" + row + " " + col);
			System.exit(1);
		}
		board[row][col] = MARK.NONE;
	}

	// Evalueaza BOARD-ul si intoarce castigatorul
	// Vezi mai sus semnificatia fiecarei valori
	public WINNER GetWinner() {
		int found = 0;
		int empty_squares = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == MARK.NONE) {
					empty_squares++;
				}
			}
		}
		for (int i = 0; i < 3; i++) {
			if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
				if (board[i][0] == MARK.X) {
					found |= 1;
				}
				if (board[i][0] == MARK.ZERO) {
					found |= 2;
				}
			}
		}
		for (int i = 0; i < 3; i++) {
			if (board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
				if (board[0][i] == MARK.X) {
					found |= 1;
				}
				if (board[0][i] == MARK.ZERO) {
					found |= 2;
				}
			}
		}
		if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
			if (board[0][0] == MARK.X) {
				found |= 1;
			}
			if (board[0][0] == MARK.ZERO) {
				found |= 2;
			}
		}
		if (board[2][0] == board[1][1] && board[1][1] == board[0][2]) {
			if (board[1][1] == MARK.X) {
				found |= 1;
			}
			if (board[1][1] == MARK.ZERO) {
				found |= 2;
			}
		}

		if (found == 0 && empty_squares > 0) {
			return WINNER.PLAYER_NONE;
		}
		if (found == 1) {
			return WINNER.PLAYER_X;
		}
		if (found == 2) {
			return WINNER.PLAYER_ZERO;
		}
		if (found == 3) {
			return WINNER.INVALID;
		}
		return WINNER.DRAW;
	}
};
