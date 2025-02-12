import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private int numTrials; // Number of trials.
    private double[] thresholds; // Percolation thresholds.

    /**
     * Performs trials on an n-by-n grid.
     */
    public PercolationStats(int n, int trials) {
        validate(n, trials);
        numTrials = trials;
        thresholds = new double[numTrials];
        for (int i = 0; i < trials; i += 1) {
            double threshold = experiment(n);
            thresholds[i] = threshold;
        }
    }

    private void validate(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            String error = "Invalid grid size " + n + " or number of trials "
                + trials;
            throw new IllegalArgumentException(error);
        }
    }

    /**
     * Performs a single trial on an n-by-n grid.
     */
    private double experiment(int n) {
        Percolation percolation = new Percolation(n);
        // Open sites until percolation.
        while (!percolation.percolates()) {
            // Randomly open a site.
            int row = StdRandom.uniformInt(1, n + 1);
            int col = StdRandom.uniformInt(1, n + 1);
            percolation.open(row, col);
        }
        // Calculate percolation threshold.
        int open = percolation.numberOfOpenSites();
        double threshold = (double) open / (n * n);
        return threshold;
    }

    /**
     * Returns the sample mean of percolation thresholds.
     */
    public double mean() {
        return StdStats.mean(thresholds);
    }

    /**
     * Returns the sample standard deviation of percolation thresholds.
     */
    public double stddev() {
        if (numTrials == 1) {
            return Double.NaN;
        }
        return StdStats.stddev(thresholds);
    }

    /**
     * Returns the low endpoint of 95% confidence interval.
     */
    public double confidenceLo() {
        return mean() - confidence();
    }

    /**
     * Returns the high endpoint of 95% confidence interval.
     */
    public double confidenceHi() {
        return mean() + confidence();
    }

    private double confidence() {
        double confidence = CONFIDENCE_95 * stddev() / Math.sqrt(numTrials);
        return confidence;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = [" + stats.confidenceLo()
            + ", " + stats.confidenceHi() + "]");
    }
}
