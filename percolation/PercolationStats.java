import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private double[] thresholds;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        validate(n, trials);
        thresholds = new double[trials];
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

    private double experiment(int n) {
        Percolation percolation = new Percolation(n);
        while (!percolation.percolates()) {
            int row = StdRandom.uniformInt(1, n + 1);
            int col = StdRandom.uniformInt(1, n + 1);
            percolation.open(row, col);
        }
        int open = percolation.numberOfOpenSites();
        double threshold = (double) open / (n * n);
        return threshold;
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - confidence();
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + confidence();
    }

    private double confidence() {
        double confidence = 1.96 * stddev() / Math.sqrt(thresholds.length);
        return confidence;
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = StdIn.readInt();
        int trials = StdIn.readInt();
        PercolationStats stats = new PercolationStats(n, trials);
        StdOut.println("mean = " + stats.mean());
        StdOut.println("stddev = " + stats.stddev());
        StdOut.println("95% confidence interval = [" + stats.confidenceLo()
            + ", " + stats.confidenceHi() + "]");
    }
}
