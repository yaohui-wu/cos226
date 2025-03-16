import java.util.HashSet;
import java.util.Set;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public final class BoggleSolver {
    private static final int[] dirs = {-1, 0, 1};
    private final Node root;

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
        Set<String> words = new HashSet<>();
        int rows = board.rows();
        int cols = board.cols();
        boolean[][] visited = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                dfs(board, visited, i, j, root, words);
            }
        }
        return words;
    }

    private void dfs(BoggleBoard board, boolean[][] visited, int i, int j, Node node, Set<String> words) {
        if (node == null) {
            return;
        }
        if (node.isTerminal) {
            words.add(node.value);
            return;
        }
        if (isValid(board, i, j)) {
            visited[i][j] = true;
            for (int x : dirs) {
                int row = i + x;
                for (int y : dirs) {
                    int col = j + y;
                    if (isValid(board, row, col) && !visited[row][col]) {
                        dfs(board, visited, row, col, node, words);
                    }
                }
            }
        }
        visited[i][j] = false;
    }

    private boolean isValid(BoggleBoard board, int i, int j) {
        int rows = board.rows();
        int cols = board.cols();
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
