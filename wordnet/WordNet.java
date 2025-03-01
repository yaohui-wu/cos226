import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.Digraph;

/**
 * The WordNet digraph, a rooted directed acyclic graph (DAG).
 * 
 * @author Yaohui Wu
 */
public final class WordNet {
    private Digraph wordNet;
    /**
     * Constructor takes the name of the two input files.
     */
    public WordNet(String synsets, String hypernyms) {
        validateArgs(synsets, hypernyms);
    }
 
    /**
     * Returns all WordNet nouns.
     */
    public Iterable<String> nouns() {
        List<String> nouns = new ArrayList<>();
        return nouns;
    }
 
    /**
     * Returns true if the WORD is a WordNet noun.
     */
    public boolean isNoun(String word) {
        validateArgs(word);
    }
 
    /**
     * Distance between nounA and nounB.
     */
    public int distance(String nounA, String nounB) {
        validateArgs(nounA, nounA);
        validateNouns(nounA, nounB);
    }
 
    /**
     * A synset (second field of synsets.txt) that is the common ancestor of
     * nounA and nounB.
     */
    public String sap(String nounA, String nounB) {
        validateArgs(nounA, nounB);
        validateNouns(nounA, nounB);
    }
 
    /**
     * Do unit testing of this class.
     */
    public static void main(String[] args) {}

    private static void validateArgs(Object... args) {
        for (Object arg : args) {
            if (arg == null) {
                String error = "Argument cannot be null";
                throw new IllegalArgumentException(error);
            }
        }
    }

    private void validateNouns(String... nouns) {
        for (String noun : nouns) {
            if (!isNoun(noun)) {
                String error  = "Noun is not a WordNet noun";
                throw new IllegalArgumentException(error);
            }
        }
    }
}
