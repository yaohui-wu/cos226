import java.util.Arrays;

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
        for (int row = 0; row < size; row += 1) {
            for (int col = 0; col < size; col += 1) {
                int index = xyToIndex(row, col);
                board[index] = tiles[row][col];
            }
        }
    }

    private int xyToIndex(int x, int y) {
        return x * size + y;
    }

    private int indexToX(int index) {
        return index / size;
    }

    private int indexToY(int index) {
        return index % size;
    }
    
    /**
     * Returns the string representation of this board.
     */
    public String toString() {
        String s = size + "\n";
        for (int row = 0; row < size; row += 1) {
            for (int col = 0; col < size; col += 1) {
                int index = xyToIndex(row, col);
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
    public int hamming() {
        /*
         * The Hamming distance between a board and the goal board is the
         * number of tiles in the wrong position.
         */
        int hamming = 0;
        for (int i = 0; i < board.length; i += 1) {
            int tile = board[i];
            if (tile != 0 && tile != i + 1) {
                hamming += 1;
            }
        }
        return hamming;
    }

    /**
     * Returns the sum of Manhattan distances between tiles and goal.
     */
    public int manhattan() {
        /*
         * The Manhattan distance between a board and the goal board is the
         * sum of the Manhattan distances (sum of the vertical and horizontal
         * distance) from the tiles to their goal positions.
         */
        int manhattan = 0;
        for (int i = 0; i < board.length; i += 1) {
            int tile = board[i];
            if (tile != 0) {
                int x1 = indexToX(i);
                int y1 = indexToY(i);
                int x2 = indexToX(tile);
                int y2 = indexToY(tile);
                manhattan += calcManhattan(x1, y1, x2, y2);
            }
        }
        return manhattan;
    }

    /**
     * Returns the Manhattan distance between two points.
     */
    private int calcManhattan(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    /**
     * Checks if this board is the goal board.
     */
    public boolean isGoal() {
        for (int i = 0; i < board.length - 1; i += 1) {
            int tile = board[i];
            if (tile != i + 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if this board is equal to another board.
     */
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null || getClass() != y.getClass()) {
            return false;
        }
        Board other = (Board) y;
        return Arrays.equals(board, other.board);
    }

    /**
     * Returns all neighboring boards.
     */
    public Iterable<Board> neighbors() {}

    /**
     * Returns a board that is obtained by exchanging any pair of tiles.
     */
    public Board twin() {
        Board twin = copy();
        int length = twin.board.length;
        for (int i = 0; i < length; i += 1) {
            int tile = twin.board[i];
            if (tile != 0) {
                int j = i + 1;
                int k = i - 1;
                int index = -1;
                if (j < length && twin.board[j] != 0) {
                    index = j;
                } else if (k > 0 && twin.board[k] != 0) {
                    index = k;
                }
                if (index != -1) {
                    int temp = twin.board[index];
                    twin.board[index] = tile;
                    twin.board[i] = temp;
                    return twin;
                }
            }
        }
        return null;
    }

    private Board copy() {
        Board copy = new Board(new int[size][size]);
        System.arraycopy(board, 0, copy.board, 0, board.length);
        return copy;
    }

    // Unit tests.
    public static void main(String[] args) {}
}
