/**
 * An immutable data type that models an n-by-n board with sliding tiles.
 * 
 * @author Yaohui Wu
 */
public class Board {
    private int size; // Board size.
    private int[] board;

    /**
     * Create a board from an n-by-n array of tiles, where tiles[row][col]
     * = tile at (row, col).
     */
    public Board(int[][] tiles) {
        size = tiles.length;
        board = new int[size * size];
    }
                                           
    /**
     * String representation of this board.
     */
    public String toString() {}

    /**
     * Board dimension n.
     */
    public int dimension() {}

    /**
     * Number of tiles out of place.
     */
    public int hamming() {}

    /**
     * Sum of Manhattan distances between tiles and goal.
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
     * All neighboring boards.
     */
    public Iterable<Board> neighbors() {}

    /**
     * A board that is obtained by exchanging any pair of tiles.
     */
    public Board twin() {}

    // unit testing (not graded)
    public static void main(String[] args) {}
}
