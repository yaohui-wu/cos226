import edu.princeton.cs.algs4.StdOut;

/**
 * An immutable data type that models an n-by-n board with sliding tiles.
 * 
 * @author Yaohui Wu
 */
public class Board {
    private int size; // Board size.
    private int[] board;

    /**
     * Creates a board from an n-by-n array of tiles, where
     * tiles[row][col] = tile at (row, col).
     */
    public Board(int[][] tiles) {
        size = tiles.length;
        board = new int[size * size];
    }

    private int xyTo1D(int x, int y) {
        return x * size + y;
    }
    
    /**
     * Returns the string representation of this board.
     */
    public String toString() {
        String s = size + "\n";
        for (int row = 0; row < size; row += 1) {
            for (int col = 0; col < size; col += 1) {
                int index = xyTo1D(row, col);
                s += " " + board[index];
            }
            s += "\n";
        }
        return s;
    }

    /**
     * Returns the board dimension n.
     */
    public int dimension() {
        return size;
    }

    /**
     * Returns the number of tiles out of place.
     */
    public int hamming() {}

    /**
     * Returns the sum of Manhattan distances between tiles and goal.
     */
    public int manhattan() {}

    /**
     * Checks if this board is the goal board.
     */
    public boolean isGoal() {}

    /**
     * Checks if this board is equal to another board.
     */
    public boolean equals(Object y) {}

    /**
     * Returns the all neighboring boards.
     */
    public Iterable<Board> neighbors() {}

    /**
     * Returns a board that is obtained by exchanging any pair of tiles.
     */
    public Board twin() {}

    // Unit tests.
    public static void main(String[] args) {}
}
