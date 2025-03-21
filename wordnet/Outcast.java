import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Outcast detection.
 * 
 * @author Yaohui Wu
 */
public final class Outcast {
    private final WordNet wordNet;

    /**
     * Constructor takes a WordNet object.
     */
    public Outcast(WordNet wordnet) {
        wordNet = wordnet;
    }

    /**
     * Given an array of WordNet nouns, returns an outcast.
     */
    public String outcast(String[] nouns) {
        String outcast = null;
        int max = Integer.MIN_VALUE;
        int len = nouns.length;
        /*
         * Compute the sum of the distances between each noun and every other
         * one.
         */
        for (int i = 0; i < len; i++) {
            String nounX = nouns[i];
            int dist = 0;
            for (int j = 0; j < len; j++) {
                if (i != j) {
                    String nounY = nouns[j];
                    dist += wordNet.distance(nounX, nounY);
                }
            }
            if (dist > max) {
                max = dist;
                outcast = nounX;
            }
        }
        return outcast;
    }

    /**
     * Test client.
     */
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int i = 2; i < args.length; i++) {
            In in = new In(args[i]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[i] + ": " + outcast.outcast(nouns));
        }
    }
}
