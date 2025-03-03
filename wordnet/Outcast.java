import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

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
        String outcast = nouns[0];
        int max = Integer.MIN_VALUE;
        int len = nouns.length;
        for (int i = 0; i < len; i += 1) {
            String nounX = nouns[i];
            int dist = 0;
            for (int j = 0; j < len; j += 1) {
                String nounY = nouns[j];
                if (i != j) {
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
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
