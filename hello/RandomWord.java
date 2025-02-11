import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    /**
     * Reads a sequence of words from standard input and prints one of those
     * words uniformly at random.
     */
    public static void main(String[] args) {
        /*
         * Knuth's method: when reading the ith word, select it with
         * probability 1/i to be the champion, replacing the previous champion.
         */
        String champion = "";
        int i = 1;
        while (!StdIn.isEmpty()) {
            if (StdRandom.bernoulli(1 / i)) {
                champion = StdIn.readString();
            }
            i += 1;
        }
        // After reading all of the words, print the surviving champion.
        StdOut.println(champion);
    }
}
