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
        openSites += 1;
        connectTop(row, col);
        connectBottom(row, col);
        connectLeft(row, col);
        connectRight(row, col);
        connectUp(row, col);
        connectDown(row, col);
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

    private void connect(int p, int q) {
        connections.union(p, q);
    }

    private void connectTop(int row, int col) {
        if (row == 1) {
            int element = xyTo1D(row, col);
            connect(0, element);
        }
    }
    private void connectBottom(int row, int col) {
        if (row == size) {
            int element = xyTo1D(row, col);
            connect(size * size + 1, element);
        }
    }

    private boolean validSite(int row, int col) {
        return validIndex(row) && validIndex(col);
    }

    private boolean validIndex(int index) {
        return index > 0 && index <= size;
    }

    private void connectLeft(int row, int col) {
        if (validSite(row, col - 1) && isOpen(row, col - 1)) {
            int element = xyTo1D(row, col);
            int left = xyTo1D(row, col - 1);
            connect(element, left);
        }
    }

    private void connectRight(int row, int col) {
        if (validSite(row, col + 1) && isOpen(row, col + 1)) {
            int element = xyTo1D(row, col);
            int right = xyTo1D(row, col + 1);
            connect(element, right);
        }
    }

    private void connectUp(int row, int col) {
        if (validSite(row - 1, col) && isOpen(row - 1, col)) {
            int element = xyTo1D(row, col);
            int up = xyTo1D(row - 1, col);
            connect(element, up);
        }
    }

    private void connectDown(int row, int col) {
        if (validSite(row + 1, col) && isOpen(row + 1, col)) {
            int element = xyTo1D(row, col);
            int down = xyTo1D(row + 1, col);
            connect(element, down);
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
        return isOpen(row, col) && connected(0, element);
    }

    private boolean connected(int p, int q) {
        return connections.find(p) == connections.find(q);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return connected(0, size * size + 1);
    }

    // test client (optional)
    public static void main(String[] args) {}
}
