import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int size; // Grid size.
    private boolean[][] sites; // True: open, false: blocked.
    private int open; // Number of open sites.
    private WeightedQuickUnionUF opens; // Open sites.
    private int top; // Virtual top site.
    private int bottom; // Virtual bottom site.

    /**
     * Creates n-by-n grid, with all sites initially blocked.
     */
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
        // Virtual top and bottom sites to check percolation.
        opens = new WeightedQuickUnionUF(size * size + 2);
        top = 0;
        bottom = size * size + 1;
        // Connect top row to virtual top site.
        for (int col = 1; col <= size; col += 1) {
            int element = xyTo1D(1, col);
            opens.union(element, top);
        }
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
        sites[row - 1][col - 1] = true;
        open += 1;
        int element = xyTo1D(row, col);
        // Connect to virtual bottom site.
        if (row == size) {
            opens.union(element, bottom);
        }
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
            int element2 = xyTo1D(row2, col2);
            opens.union(element1, element2);
        }
    }

    /**
     * Returns true if the site (row, col) is open.
     */
    public boolean isOpen(int row, int col) {
        validateSite(row, col);
        return sites[row - 1][col - 1];
    }

    /**
     * Returns true if the site (row, col) is full.
     */
    public boolean isFull(int row, int col) {
        validateSite(row, col);
        int element = xyTo1D(row, col);
        return isOpen(row, col);
    }

    /**
     * Returns the number of open sites.
     */
    public int numberOfOpenSites() {
        return open;
    }

    /**
     * Returns true if the system percolates.
     */
    public boolean percolates() {
        return opens.find(top) == opens.find(bottom);
    }
}
