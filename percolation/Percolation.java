import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size; // Grid size.
    // 0: blocked, 1: open, 2: open sites connected to bottom.
    private byte[] sites;
    private int numOpen; // Number of open sites.
    private WeightedQuickUnionUF openSites; // Open sites.

    /**
     * Creates n-by-n grid, with all sites initially blocked.
     */
    public Percolation(int n) {
        validateSize(n);
        size = n;
        sites = new byte[size * size];
        for (int i = 0; i < size * size; i += 1) {
            sites[i] = 0;
        }
        numOpen = 0;
        openSites = new WeightedQuickUnionUF(size * size + 1);
    }

    private void validateSize(int n) {
        if (n <= 0) {
            String error = "Invalid grid size " + n;
            throw new IllegalArgumentException(error);
        }
    }

    /**
     * Converts 2D coordinates to 1D coordinates.
     */
    private int xyTo1D(int x, int y) {
        return size * (x - 1) + y;
    }

    /**
     * openSites the site (row, col) if it is not open already.
     */
    public void open(int row, int col) {
        validateSite(row, col);
        if (isOpen(row, col)) {
            return;
        }
        int index = xyTo1D(row, col);
        if (row == size) {
            sites[index] = 2;
        } else {
            sites[index] = 1;
        }
        numOpen += 1;
        // Connect to adjacent open sites.
        connect(row, col, row, col - 1); // Left.
        connect(row, col, row, col + 1); // Right.
        connect(row, col, row - 1, col); // Up.
        connect(row, col, row + 1, col); // Down.
    }

    private void validateSite(int row, int col) {
        validateIndex(row);
        validateIndex(col);
    }

    private void validateIndex(int index) {
        if (index <= 0 || index > size) {
            String error = "Index " + index + " out of bounds";
            throw new IllegalArgumentException(error);
        }
    }

    private boolean validSite(int row, int col) {
        return validIndex(row) && validIndex(col);
    }

    private boolean validIndex(int index) {
        return index > 0 && index <= size;
    }

    /**
     * Connects two sites if the second site is open.
     */
    private void connect(int row1, int col1, int row2, int col2) {
        if (validSite(row2, col2) && isOpen(row2, col2)) {
            int element1 = xyTo1D(row1, col1);
            int root1 = openSites.find(element1);
            int element2 = xyTo1D(row2, col2);
            int root2 = openSites.find(element2);
            openSites.union(element1, element2);
            if (root1 == 2 || root2 == 2) {
                int root = openSites.find(element1);
                sites[root] = 2;
            }
        }
    }

    /**
     * Returns true if the site (row, col) is open.
     */
    public boolean isOpen(int row, int col) {
        validateSite(row, col);
        int index = xyTo1D(row, col);
        return sites[index] != 0;
    }

    /**
     * Returns true if the site (row, col) is full.
     */
    public boolean isFull(int row, int col) {
        validateSite(row, col);
        int element = xyTo1D(row, col);
        return openSites.find(element) == 2;
    }

    /**
     * Returns the number of open sites.
     */
    public int numberOfopenSitesites() {
        return numOpen;
    }

    /**
     * Returns true if the system percolates.
     */
    public boolean percolates() {
        int root = openSites.find(0);
        return openSites.find(root) == 2;
    }
}
