import java.util.ArrayDeque;
import java.util.Deque;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

/**
 * An immutable data type that applys the A* search algorithm to solve n-by-n
 * slider puzzles.
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
        MinPQ<Node> pq = new MinPQ<>();
        MinPQ<Node> twinPQ = new MinPQ<>();
        // The root of the game tree is the initial search node.
        Node init = new Node(initial, null);
        Node initTwin = new Node(initial.twin(), null);
        pq.insert(init);
        twinPQ.insert(initTwin);
        MinPQ<Node> solutionPQ = pq;
        Node curr = solutionPQ.delMin();
        // Run the A* algorithm on the board and its twin in lockstep.
        while (!curr.board.isGoal()) {
            // Insert onto the priority queue all valid neighboring search nodes.
            for (Board neighbor : curr.board.neighbors()) {
                /*
                 * Enqueue a neighbor if its board is not the same as the
                 * board of the previous search node in the game tree.
                 */
                Node prev = curr.prev;
                if (prev == null || !neighbor.equals(prev.board)) {
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
        // Exactly one of the two is solvable.
        solvable = (solutionPQ == pq);
        if (solvable) {
            // Cache the solution.
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
        // Stack to store the sequence of boards from initial to goal.
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

    /**
     * A search node of the game tree.
     */
    private static final class Node implements Comparable<Node> {
        private final Board board;
        // Number of moves made to reach the board.
        private final int moves;
        // Previous search node.
        private final Node prev;
        private final int manhattan;
        private final int priority;

        public Node(Board gameBoard, Node prevNode) {
            board = gameBoard;
            if (prevNode == null) {
                moves = 0;
            } else {
                moves = prevNode.moves + 1;
            }
            prev = prevNode;
            // Cache the Manhattan distance of the board.
            manhattan = board.manhattan();
            // Manhattan priority function.
            priority = moves + manhattan;
        }

        @Override
        public int compareTo(Node other) {
            int cmp = Integer.compare(priority, other.priority);
            if (cmp == 0) {
                // Break ties using the Manhattan distance.
                cmp = Integer.compare(manhattan, other.manhattan);
            }
            return cmp;
        }
    }
}
