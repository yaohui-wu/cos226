import java.util.HashSet;
import java.util.Set;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public final class BoggleSolver {
    private final Trie dictionary;

    /**
     * Initializes the data structure using the given array of strings as the
     * dictionary. (Assume each word in the dictionary contains only the
     * uppercase letters A through Z.)
     */
    public BoggleSolver(String[] dictionary) {
        this.dictionary = new Trie();
        for (String word : dictionary) {
            this.dictionary.insert(word, word);
        }
    }

    /**
     * Returns the set of all valid words in the given Boggle board, as an
     * Iterable.
     */
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        Set<String> words = new HashSet<>();
        int rows = board.rows();
        int cols = board.cols();
        boolean[][] visited = new boolean[rows][cols];
        return words;
    }

    private dfs(BoggleBoard board) {}

    private boolean isValidIndex(int row, int col, int rows, int cols) {
        return row > 0 && row < rows && col > 0 && col < cols;
    }

    /**
     * Returns the score of the given word if it is in the dictionary, zero
     * otherwise. (Assume the word contains only the uppercase letters A
     * through Z.)
     */
    public int scoreOf(String word) {
        if (!dictionary.contains(word)) {
            return 0;
        }
        int length = word.length();
        if (length == 3 || length == 4) {
            return 1;
        } else if (length == 5) {
            return 2;
        } else if (length == 6) {
            return 3;
        } else if (length == 7) {
            return 5;
        } else if (length >= 8) {
            return 11;
        }
        return 0;
    }

    /**
     * Test client takes the filename of a dictionary and the filename of a
     * Boggle board as command-line arguments and prints out all valid words
     * for the given board using the given dictionary.
     */
    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}
