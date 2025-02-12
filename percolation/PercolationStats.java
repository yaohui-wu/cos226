import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

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

    // sample mean of percolation threshold
    public double mean() {}

    // sample standard deviation of percolation threshold
    public double stddev() {}

    // low endpoint of 95% confidence interval
    public double confidenceLo() {}

    // high endpoint of 95% confidence interval
    public double confidenceHi() {}

    // test client (see below)
    public static void main(String[] args) {}

    private void validate(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
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
}
