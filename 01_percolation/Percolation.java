import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * A percolation system.
 * 
 * @author Yaohui Wu
 */
public class Percolation {
    private int size; // Grid size.
    // 0: blocked, 1: open, 2: open sites connected to bottom.
    private byte[] sites;
    private int numOpenSites; // Number of open sites.
    private WeightedQuickUnionUF openSites; // Open sites.

    /**
     * Creates an n-by-n grid, with all sites initially blocked.
     */
    public Percolation(int n) {
        validateSize(n);
        size = n;
        sites = new byte[size * size + 1];
        sites[0] = 1; // Virtual top site.
        for (int i = 1; i < size * size + 1; i += 1) {
            sites[i] = 0;
        }
        numOpenSites = 0;
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
     * Opens the site (row, col) if it is not open already.
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
        numOpenSites += 1;
        // Connect to virtual top site.
        if (row == 1) {
            connect(0, index);
        }
        // Connect to adjacent open sites.
        connectSites(row, col, row, col - 1); // Left.
        connectSites(row, col, row, col + 1); // Right.
        connectSites(row, col, row - 1, col); // Up.
        connectSites(row, col, row + 1, col); // Down.
    }

    private void validateSite(int row, int col) {
        validateIndex(row);
        validateIndex(col);
    }

    private void validateIndex(int index) {
        if (!validIndex(index)) {
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
    private void connectSites(int row1, int col1, int row2, int col2) {
        if (validSite(row2, col2) && isOpen(row2, col2)) {
            int index1 = xyTo1D(row1, col1);
            int index2 = xyTo1D(row2, col2);
            connect(index1, index2);
        }
    }

    private void connect(int index1, int index2) {
        int root1 = openSites.find(index1);
        int root2 = openSites.find(index2);
        openSites.union(index1, index2);
        /*
         * If either site is connected to the bottom before the connection,
         * then both sites are connected to the bottom after the connection.
         */
        if (sites[root1] == 2 || sites[root2] == 2) {
            int root = openSites.find(index1);
            sites[root] = 2;
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
        return openSites.find(element) == openSites.find(0);
    }

    /**
     * Returns the number of open sites.
     */
    public int numberOfOpenSites() {
        return numOpenSites;
    }

    /**
     * Returns true if the system percolates, that is, there is a full site in
     * the bottom row.
     */
    public boolean percolates() {
        /*
         * The system percolates if the root of the set that the virtual top
         * site is in is connected to the bottom.
         */
        int root = openSites.find(0);
        return sites[root] == 2;
    }
}
