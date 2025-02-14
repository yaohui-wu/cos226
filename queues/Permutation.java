import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Takes an integer K, reads a sequence of strings, and prints exactly K of
 * them, uniformly at random.
 * 
 * @author Yaohui Wu
 */
public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        // Use one RandomizedQueue object of maximum size at most K.
        RandomizedQueue<String> permutation = new RandomizedQueue<>();
        for (int i = 0; i < k; i++) {
            String item = StdIn.readString();
            permutation.enqueue(item);
        }
        int p = k + 1;
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            int j = StdRandom.uniform(p);
            if (j < k) {
                permutation.dequeue();
                permutation.enqueue(item);
            }
            p += 1;
        }
        for (String item : permutation) {
            StdOut.println(item);
        }
    }
 }
 