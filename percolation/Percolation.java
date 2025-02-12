import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size;
    private boolean[][] sites;
    private int openSites;
    private WeightedQuickUnionUF connections;

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
        openSites = 0;
        connections = new WeightedQuickUnionUF(size * size + 2);
        for (int i = 1; i <= size; i += 1) {
            connections.union(0, i);
            connections.union(size * size + 1, size * size + 1 - i);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateSite(row, col);
        sites[row - 1][col - 1] = true;
        openSites += 1;
        int element = xyTo1D(row, col);
        if (isOpen(row, col - 1)) {
            connections.union(element, xyTo1D(row, col - 1));
        }
        if (isOpen(row, col + 1)) {
            connections.union(element, xyTo1D(row, col + 1));
        }
        if (isOpen(row - 1, col)) {
            connections.union(element, xyTo1D(row - 1, col));
        }
        if (isOpen(row + 1, col)) {
            connections.union(element, xyTo1D(row + 1, col));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateSite(row, col);
        return sites[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validateSite(row, col);
        int element = xyTo1D(row, col);
        return connections.find(0) == connections.find(element);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return connections.find(0) == connections.find(size * size + 1);
    }

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

    private int xyTo1D(int x, int y) {
        return size * (x - 1) + y;
    }
}
