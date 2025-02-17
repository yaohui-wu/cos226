/**
 * An immutable data type that implements A* search to solve n-by-n slider puzzles.
 * 
 * @author Yaohui Wu
 */
public class Solver {

    /**
     * Finds a solution to the initial board using the A* algorithm.
     */
    public Solver(Board initial) {}

    /**
     * Checks if the initial board is solvable.
     */
    public boolean isSolvable() {}

    /**
     * Returns the minimum number of moves to solve the initial board, returns
     * -1 if unsolvable.
     */
    public int moves() {}

    // sequence of boards in a shortest solution; null if unsolvable
    /**
     * Returns the sequence of boards in a shortest solution, returns null if
     * unsolvable.
     */
    public Iterable<Board> solution() {}

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
