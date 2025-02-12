import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size;
    private boolean[][] sites;
    private WeightedQuickUnionUF unionFind;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        validateSize(n);
        size = n;
        sites = new boolean[size][size];
        for (int row = 0; row < size; row += 1) {
            for (int col = 0; col < size; col += 1) {
                sites[row][col] = false;
            }
        }
        unionFind = new WeightedQuickUnionUF(size * size);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateSite(row, col);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateSite(row, col);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateSite(row, col);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {}

    // does the system percolate?
    public boolean percolates() {}

    // test client (optional)
    public static void main(String[] args) {}

    private void validateSize(int n) {
        if (n <= 0) {
            String error = "Invalid grid size " + n;
            throw new IllegalArgumentException(error);
        }
    }

    private void validateSite(int row, int col) {
        validateIndex(row);
        validateIndex(col);
    }

    private void validateIndex(int index) {
        if (index <= 0 || index > size) {
            String error = "Index " + index + " out of bounds";
            throw new IndexOutOfBoundsException(error);
        }
    }

    private int xyTo1D(int x, int y) {}
}
