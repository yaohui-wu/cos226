import java.util.ArrayDeque;
import java.util.Deque;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

/**
 * An immutable data type that implements A* search to solve n-by-n slider puzzles.
 * 
 * @author Yaohui Wu
 */
public final class Solver {
    private final boolean solvable;
    private final Node solution;
    private final int moves;

    /**
     * Finds a solution to the initial board using the A* algorithm.
     */
    public Solver(Board initial) {
        validateArg(initial);
        // A* algorithm.
        MinPQ<Node> priorityQueue = new MinPQ<>();
        Node init = new Node(initial, 0, null, false);
        Node initTwin = new Node(initial.twin(), 0, null, true);
        priorityQueue.insert(init);
        priorityQueue.insert(initTwin);
        Node curr = priorityQueue.delMin();
        while (!curr.board.isGoal()) {
            for (Board neighbor : curr.board.neighbors()) {
                if (curr.prev == null || !neighbor.equals(curr.prev.board)) {
                    Node newNode
                        = new Node(neighbor, curr.moves + 1, curr, curr.twin);
                    priorityQueue.insert(newNode);
                }
            }
            curr = priorityQueue.delMin();
        }
        solvable = !curr.twin;
        if (solvable) {
            solution = curr;
            moves = solution.moves;
        } else {
            solution = null;
            moves = -1;
        }
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
    public boolean isSolvable() {
        return solvable;
    }

    /**
     * Returns the minimum number of moves to solve the initial board, returns
     * -1 if unsolvable.
     */
    public int moves() {
        return moves;
    }

    /**
     * Returns the sequence of boards in a shortest solution, returns null if
     * unsolvable.
     */
    public Iterable<Board> solution() {
        if (!solvable) {
            return null;
        }
        Deque<Board> stack = new ArrayDeque<>();
        Node curr = solution;
        while (curr != null) {
            stack.push(curr.board);
            curr = curr.prev;
        }
        return stack;
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

    private static final class Node implements Comparable<Node> {
        private final Board board;
        private final int moves;
        private final Node prev;
        private final int priority;
        private final boolean twin;

        public Node(
            Board gameBoard,
            int numMoves,
            Node prevNode,
            boolean isTwin) {
            board = gameBoard;
            moves = numMoves;
            prev = prevNode;
            priority = moves + board.manhattan();
            twin = isTwin;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(priority, other.priority);
        }
    }
}
