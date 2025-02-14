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
        RandomizedQueue<String> permutation = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            permutation.enqueue(item);
        }
        for (int i = 0; i < k; i++) {
            String randomItem = permutation.dequeue();
            StdOut.println(randomItem);
        }
    }
 }
 