import java.util.ArrayDeque;
import java.util.Deque;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;

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
        MinPQ<Node> pq = new MinPQ<>();
        MinPQ<Node> twinPQ = new MinPQ<>();
        Node init = new Node(initial, null);
        Node initTwin = new Node(initial.twin(), null);
        pq.insert(init);
        twinPQ.insert(initTwin);
        MinPQ<Node> solutionPQ = pq;
        Node curr = solutionPQ.delMin();
        while (!curr.board.isGoal()) {
            for (Board neighbor : curr.board.neighbors()) {
                if (curr.prev == null || !neighbor.equals(curr.prev.board)) {
                    Node newNode = new Node(neighbor, curr);
                    solutionPQ.insert(newNode);
                }
            }
            if (solutionPQ == pq) {
                solutionPQ = twinPQ;
            } else {
                solutionPQ = pq;
            }
            curr = solutionPQ.delMin();
        }
        solvable = (solutionPQ == pq);
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
        Deque<Board> steps = new ArrayDeque<>();
        Node curr = solution;
        while (curr != null) {
            steps.push(curr.board);
            curr = curr.prev;
        }
        return steps;
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

        public Node(Board gameBoard, Node prevNode) {
            board = gameBoard;
            if (prevNode == null) {
                moves = 0;
            } else {
                moves = prevNode.moves + 1;
            }
            prev = prevNode;
            priority = moves + board.manhattan();
        }

        @Override
        public int compareTo(Node other) {
            int cmp = Integer.compare(priority, other.priority);
            if (cmp == 0) {
                int man = board.manhattan();
                int otherMan = other.board.manhattan();
                cmp = Integer.compare(man, otherMan);
            }
            return cmp;
        }
    }
}
