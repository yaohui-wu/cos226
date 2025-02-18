import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * An immutable data type that implements A* search to solve n-by-n slider puzzles.
 * 
 * @author Yaohui Wu
 */
public final class Solver {

    /**
     * Finds a solution to the initial board using the A* algorithm.
     */
    public Solver(Board initial) {
        validateArg(initial);
    }

    private void validateArg(Board initial) {
        if (initial == null) {
            String error = "The initial board cannot be null";
            throw new IllegalArgumentException(error);
        }
    }

    /**
     * Checks if the initial board is solvable.
     */
    public boolean isSolvable() {}

    /**
     * Returns the minimum number of moves to solve the initial board, returns
     * -1 if unsolvable.
     */
    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
    }

    /**
     * Returns the sequence of boards in a shortest solution, returns null if
     * unsolvable.
     */
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
    }

    /**
     * Test client.
     */
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
    
        // solve the puzzle
        Solver solver = new Solver(initial);
    
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
