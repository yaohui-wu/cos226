import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver {
    /**
     * Initializes the data structure using the given array of strings as the
     * dictionary. (Assume each word in the dictionary contains only the
     * uppercase letters A through Z.)
     */
    public BoggleSolver(String[] dictionary) {}

    /**
     * Returns the set of all valid words in the given Boggle board, as an
     * Iterable.
     */
    public Iterable<String> getAllValidWords(BoggleBoard board) {}

    /**
     * Returns the score of the given word if it is in the dictionary, zero
     * otherwise. (Assume the word contains only the uppercase letters A
     * through Z.)
     */
    public int scoreOf(String word) {}

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
