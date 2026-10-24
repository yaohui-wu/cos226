import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Takes an integer K, reads a sequence of N strings, and prints exactly K of
 * them, uniformly at random. Assume that 0 <= K <= N and note that N is not
 * given.
 * 
 * @author Yaohui Wu
 */
public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        // Use one RandomizedQueue object of maximum size at most K.
        RandomizedQueue<String> permutation = new RandomizedQueue<>();
        // Enqueue the first K strings.
        for (int i = 0; i < k; i++) {
            String item = StdIn.readString();
            permutation.enqueue(item);
        }
        int m = k;
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            m += 1;
            /*
             * Keep the new string with probability K / M where M is the
             * number of strings read so far.
             */
            int j = StdRandom.uniformInt(m);
            if (j < k) {
                /*
                 * Dequeue an old string in the RandomizedQueue uniformly at
                 * random with probality 1 / K.
                 */
                permutation.dequeue();
                // Enqueue the new string.
                permutation.enqueue(item);
            }
            /*
             * At any time, the probability of enqueuing a new string in the
             * RandomizedQueue after reading the first K strings is
             * (K / M) * (1 / K) = 1 / M, which follows a uniform distribution.
             */
        }
        for (String item : permutation) {
            StdOut.println(item);
        }
    }
 }
 