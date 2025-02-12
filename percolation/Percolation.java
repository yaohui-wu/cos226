import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size;
    private boolean[][] sites;
    private int open;
    private WeightedQuickUnionUF connections;
    private int top;
    private int bottom;

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
        open = 0;
        connections = new WeightedQuickUnionUF(size * size + 2);
        top = 0;
        bottom = size * size + 1;
    }

    private void validateSize(int n) {
        if (n <= 0) {
            String error = "Invalid grid size " + n;
            throw new IllegalArgumentException(error);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateSite(row, col);
        if (isOpen(row, col)) {
            return;
        }
        sites[row - 1][col - 1] = true;
        open += 1;
        connect(row, col, row, col - 1); // Left
        connect(row, col, row, col + 1); // Right
        connect(row, col, row - 1, col); // Up
        connect(row, col, row + 1, col); // Down
        int element = xyTo1D(row, col);
        if (row == 1) {
            connections.union(element, top);
        } else if (row == size) {
            connections.union(element, bottom);
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

    private boolean validSite(int row, int col) {
        return validIndex(row) && validIndex(col);
    }

    private boolean validIndex(int index) {
        return index > 0 && index <= size;
    }

    private void connect(int row1, int col1, int row2, int col2) {
        if (validSite(row2, col2) && isOpen(row2, col2)) {
            int element1 = xyTo1D(row1, col1);
            int element2 = xyTo1D(row2, col2);
            connections.union(element1, element2);
        }
    }

    private boolean connected(int p, int q) {
        return connections.find(p) == connections.find(q);
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
        return isOpen(row, col) && connected(element, top);
    }

    // returns the number of open sites
    public int numberOfopen() {
        return open;
    }

    // does the system percolate?
    public boolean percolates() {
        return connected(top, bottom);
    }

    // test client (optional)
    public static void main(String[] args) {}
}
