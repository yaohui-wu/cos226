import java.util.HashSet;
import java.util.Set;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Represents a Boggle solver that finds all valid words in a given Boggle
 * board, using a given dictionary.
 * 
 * @author Yaohui Wu
 */
public final class BoggleSolver {
    private final Node root; // Root of trie.

    private int rows;
    private int cols;
    private char[][] letters;
    private boolean[][] visited;


    /**
     * Initializes the data structure using the given array of strings as the
     * dictionary. (Assume each word in the dictionary contains only the
     * uppercase letters A through Z.)
     */
    public BoggleSolver(String[] dictionary) {
        root = new Node();
        rows = 0;
        cols = 0;
        letters = null;
        visited = null;
        for (String word : dictionary) {
            insert(word, word);
        }
    }

    private void insert(String key, String value) {
        insert(root, key, value);
    }

    private void insert(Node node, String key, String value) {
        int length = key.length();
        for (int i = 0; i < length; i++) {
            int index = key.charAt(i) - 'A';
            if (node.children[index] == null) {
                node.children[index] = new Node();
            }
            node = node.children[index];
        }
        node.isTerminal = true;
        node.value = value;
    }

    private boolean contains(String value) {
        return contains(root, value);
    }

    private boolean contains(Node node, String key) {
        int length = key.length();
        for (int i = 0; i < length; i++) {
            int index = key.charAt(i) - 'A';
            if (node.children[index] == null) {
                return false;
            }
            node = node.children[index];
        }
        return node.isTerminal;
    }

    /**
     * Returns the set of all valid words in the given Boggle board, as an
     * Iterable.
     */
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        Set<String> words = new HashSet<>();
        rows = board.rows();
        cols = board.cols();
        letters = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                letters[i][j] = board.getLetter(i, j);
            }
        }
        visited = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                dfs(i, j, root, words);
            }
        }
        return words;
    }

    private void dfs(
        int i,
        int j,
        Node node,
        Set<String> words
    ) {
        char c = letters[i][j];
        int index = c - 'A';
        Node next = node.children[index];
        // Not a prefix of any word in the dictionary.
        if (next == null) {
            return;
        }
        // Qu is treated as a single letter.
        if (c == 'Q') {
            index = 'U' - 'A';
            next = next.children[index];
            if (next == null) {
                return;
            }
        }
        // Found a valid word.
        if (next.isTerminal) {
            String word = next.value;
            if (word.length() >= 3) {
                words.add(word);
            }
        }
        visited[i][j] = true;
        for (int x = -1; x <= 1; x++) {
            int row = i + x;
            if (isValid(row, rows)) {
                for (int y = -1; y <= 1; y++) {
                    int col = j + y;
                    if (isValid(col, cols) && !visited[row][col]) {
                        dfs(row, col, next, words);
                    }
                }
            }
        }
        // Backtracking optimization.
        visited[i][j] = false;
    }

    private boolean isValid(int index, int length) {
        return index >= 0 && index < length;
    }

    /**
     * Returns the score of the given word if it is in the dictionary, zero
     * otherwise. (Assume the word contains only the uppercase letters A
     * through Z.)
     */
    public int scoreOf(String word) {
        int length = word.length();
        if (length < 3) {
            return 0;
        }
        if (!contains(word)) {
            return 0;
        }
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

    private final class Node {
        private static final int ALPHABET_SIZE = 26;
    
        private final Node[] children;
        
        private boolean isTerminal;
        private String value;
        
        public Node() {
            children = new Node[ALPHABET_SIZE];
            isTerminal = false;
            value = null;
        }
    }
}
