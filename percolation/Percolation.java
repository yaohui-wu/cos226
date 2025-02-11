import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {}

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {}

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {}

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {}

    // returns the number of open sites
    public int numberOfOpenSites() {}

    // does the system percolate?
    public boolean percolates() {}

    // test client (optional)
    public static void main(String[] args) {}

    private boolean validIndex(int index) {
        if (index <= 0 || index > size) {
            String error = "Index " + index + " out of bounds";
            throw new IndexOutOfBoundsException(error);
        }
    }

    private int xyTo1D(int x, int y) {}
}
