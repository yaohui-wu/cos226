import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

import edu.princeton.cs.algs4.StdOut;

/**
 * An immutable data type that models an n-by-n board with sliding tiles
 * labeled 1 through n * n - 1, plus a blank square represented by 0.
 * 
 * @author Yaohui Wu
 */
public final class Board {
    private final int size; // Board size.
    // Use char to save memory space.
    private final char[] board; // Board tiles.

    /**
     * Creates a board from an n-by-n array of tiles, where
     * tiles[row][col] = tile at (row, col).
     */
    public Board(int[][] tiles) {
        size = tiles.length;
        board = new char[size * size];
        for (int row = 0; row < size; row += 1) {
            for (int col = 0; col < size; col += 1) {
                int index = xyToIndex(row, col);
                board[index] = intToChar(tiles[row][col]);
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

    private char intToChar(int num) {
        return (char) (num + '0');
    }

    private int charToInt(char c) {
        return c - '0';
    }
    
    /**
     * Returns the string representation of this board.
     */
    public String toString() {
        StringBuilder boardString = new StringBuilder();
        boardString.append(size + "\n");
        for (int row = 0; row < size; row += 1) {
            for (int col = 0; col < size; col += 1) {
                int index = xyToIndex(row, col);
                boardString.append(" " + charToInt(board[index]));
            }
            boardString.append("\n");
        }
        return boardString.toString();
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
            int tile = charToInt(board[i]);
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
         * sum of the Manhattan distances from the tiles to their goal
         * positions.
         */
        int manhattan = 0;
        for (int i = 0; i < board.length; i += 1) {
            int tile = charToInt(board[i]);
            if (tile != 0) {
                int x1 = indexToX(i);
                int y1 = indexToY(i);
                int x2 = indexToX(tile - 1);
                int y2 = indexToY(tile - 1);
                manhattan += calcManhattan(x1, y1, x2, y2);
            }
        }
        return manhattan;
    }

    /**
     * Returns the Manhattan distance (sum of the vertical and horizontal
     * distance) between two points.
     */
    private int calcManhattan(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    /**
     * Checks if this board is the goal board.
     */
    public boolean isGoal() {
        int last = board.length - 1;
        if (board[last] != '0') {
            return false;
        }
        for (int i = 0; i < last; i += 1) {
            int tile = charToInt(board[i]);
            if (tile != 0 && tile != i + 1) {
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
        if (size != other.size) {
            return false;
        }
        return Arrays.equals(board, other.board);
    }

    /**
     * Returns all neighboring boards.
     */
    public Iterable<Board> neighbors() {
        Queue<Board> neighbors = new ArrayDeque<>();
        final int MAX_NEIGHBORS = 4;
        int emptyIndex = findEmptyIndex();
        int emptyX = indexToX(emptyIndex);
        int emptyY = indexToY(emptyIndex);
        // Directions to move the empty tile: left, right, up, down.
        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        // Get a new board for each direction if possible.
        for (int i = 0; i < MAX_NEIGHBORS; i += 1) {
            int newX = emptyX + dx[i];
            int newY = emptyY + dy[i];
            if (validXY(newX, newY)) {
                // Swap the empty tile with the adjacent tile.
                char[] newBoard = board.clone();
                int newIndex = xyToIndex(newX, newY);
                newBoard[emptyIndex] = newBoard[newIndex];
                newBoard[newIndex] = '0';
                int[][] tiles = boardToTiles(newBoard);
                Board neighbor = new Board(tiles);
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }

    private int findEmptyIndex() {
        for (int i = 0; i < board.length; i += 1) {
            if (board[i] == '0') {
                return i;
            }
        }
        return -1;
    }

    private boolean validXY(int x, int y) {
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    private int[][] boardToTiles(char[] newBoard) {
        int[][] tiles = new int[size][size];
        int index = 0;
        for (int row = 0; row < size; row += 1) {
            for (int col = 0; col < size; col += 1) {
                int tile = charToInt(newBoard[index]);
                tiles[row][col] = tile;
                index += 1;
            }
        }
        return tiles;
    }

    /**
     * Returns a board that is obtained by exchanging any pair of tiles.
     */
    public Board twin() {
        char[] twinBoard = board.clone();
        int length = twinBoard.length;
        for (int i = 0; i < length; i += 1) {
            char tile = twinBoard[i];
            // Find the first two non-empty tiles to swap.
            if (tile != '0') {
                // Find the first adjacent valid tile to the right or left.
                int j = i + 1;
                int k = i - 1;
                int index = -1;
                if (j < length && twinBoard[j] != '0') {
                    index = j;
                } else if (k > 0 && twinBoard[k] != '0') {
                    index = k;
                }
                // Swap the tiles.
                if (index != -1) {
                    char temp = twinBoard[index];
                    twinBoard[index] = tile;
                    twinBoard[i] = temp;
                    int[][] tiles = boardToTiles(twinBoard);
                    Board twin = new Board(tiles);
                    return twin;
                }
            }
        }
        return null;
    }

    // Unit tests.
    public static void main(String[] args) {
        int[][] tiles = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8}
        };
        Board board = new Board(tiles);
        StdOut.print(board);
        StdOut.println("Dimension: " + board.dimension());
        StdOut.println("Hamming: " + board.hamming());
        StdOut.println("Manhattan: " + board.manhattan());
        StdOut.println("Goal: " + board.isGoal());
        StdOut.println("Equals: " + board.equals(new Board(tiles)));
        StdOut.println("Neighbors:");
        for (Board neighbor : board.neighbors()) {
            StdOut.print(neighbor);
        }
        StdOut.println("Twin: ");
        StdOut.print(board.twin());
    }
}
