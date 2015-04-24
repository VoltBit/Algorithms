public class Nim {

	// Vectorul trebuie interpretat in modul urmator:
	// groups[i] reprezinta numarul de gramezi cu i pietre
	// la initializare, groups[initial_heap_size] = 1
	int[] groups;

	public Nim(int initial_heap_size) {
		groups = new int[initial_heap_size + 1];
		for (int i = 0; i < initial_heap_size + 1; i++) {
			groups[i] = 0;
		}
		groups[initial_heap_size] = 1;
	}
	public Nim(Nim n) {
		groups = new int[n.groups.length];
		for (int i = 0; i < n.groups.length; i++) {
			groups[i] = n.groups[i];
		}
	}

	// Sparge gramada cu dimensiunea pile_size in doua gramezi
	// Prima gramada va avea dimensiunea left_size si cealalta pile_size -
	// left_size
	public int Split(int pile_size, int left_size) {
		// check for invalid splits
		if (pile_size >= groups.length || pile_size <= 2) {
			return -1;
		}
		// we can't split a pile unsigned into something bigger
		if (left_size >= pile_size) {
			return -1;
		}

		// there are no piles with that size
		if (groups[pile_size] == 0) {
			return -1;
		}

		groups[pile_size]--;
		groups[left_size]++;
		groups[pile_size - left_size]++;
		return 0;
	}

	// Uneste doua gramezi, prima de dimensiune left_pile si cealalta de
	// dimensiune
	// right_pile
	public int Merge(int left_pile, int right_pile) {
		if (groups[left_pile] == 0 || groups[right_pile] == 0) {
			return -1;
		}

		groups[left_pile]--;
		groups[right_pile]--;
		groups[left_pile + right_pile]++;
		return 0;
	}

	// Intoarce o copie a vectorului de gramezi.
	// Interpretarea sa este aceeasi ca si cea a vectorului groups
	public int[] GetPiles() {
		int[] copy = new int[groups.length];
		for (int i = 0; i < groups.length; i++) {
			copy[i] = groups[i];
		}
		return copy;
	}

	// Functia intoarce true daca nu mai pot avea loc spargeri
	public boolean IsOver() {
		for (int i = 3; i < groups.length; i++) {
			// if (groups[i] == 1) {
			if (groups[i] > 0) {
				return false;
			}
		}
		return true;
	}

	// Operator pentru afisarea gramezilor
	public String toString() {
		String result = "[";
		for (int i = groups.length - 1; i > 0; i--) {
			for (int j = 0; j < groups[i]; j++) {
				result += i + ", ";
			}
		}
		result += "]";
		return result;
	}
};