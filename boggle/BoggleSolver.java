import java.util.HashSet;
import java.util.Set;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public final class BoggleSolver {
    private final Node root;
    private Set<String> words;

    /**
     * Initializes the data structure using the given array of strings as the
     * dictionary. (Assume each word in the dictionary contains only the
     * uppercase letters A through Z.)
     */
    public BoggleSolver(String[] dictionary) {
        root = new Node();
        for (String word : dictionary) {
            insert(word, word);
        }
        words = null;
    }

    public void insert(String key, String value) {
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

    public boolean contains(String value) {
        return search(value) != null;
    }

    public String search(String key) {
        return search(root, key);
    }

    private String search(Node node, String key) {
        int length = key.length();
        for (int i = 0; i < length; i++) {
            int index = key.charAt(i) - 'A';
            if (node.children[index] == null) {
                return null;
            }
            node = node.children[index];
        }
        return node.value;
    }

    /**
     * Returns the set of all valid words in the given Boggle board, as an
     * Iterable.
     */
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        words = new HashSet<>();
        int rows = board.rows();
        int cols = board.cols();
        char[][] letters = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                letters[i][j] = board.getLetter(i, j);
            }
        }
        boolean[][] visited = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                dfs(letters, visited, i, j, root);
            }
        }
        return words;
    }

    private void dfs(char[][] letters, boolean[][] visited, int i, int j, Node node) {
        if (!isValid(letters, i, j) || visited[i][j] || node == null) {
            return;
        }
        if (node.isTerminal) {
            String word = node.value;
            if (word.length() >= 3) {
                words.add(word);
            }
        }
        visited[i][j] = true;
        char c = letters[i][j];
        int index = c - 'A';
        Node next = node.children[index];
        for (int x = -1; x < 2; x++) {
            int row = i + x;
            for (int y = -1; y < 2; y++) {
                int col = j + y;
                dfs(letters, visited, row, col, next);
            }
        }
        visited[i][j] = false;
    }

    private boolean isValid(char[][] letters, int i, int j) {
        int rows = letters.length;
        int cols = letters[0].length;
        return i >= 0 && i < rows && j >= 0 && j < cols;
    }

    /**
     * Returns the score of the given word if it is in the dictionary, zero
     * otherwise. (Assume the word contains only the uppercase letters A
     * through Z.)
     */
    public int scoreOf(String word) {
        if (!contains(word)) {
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

    private static final class Node {
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
