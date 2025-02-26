/**
 * The WordNet digraph, a rooted directed acyclic graph (DAG).
 */
public final class WordNet {
    /**
     * Constructor takes the name of the two input files.
     */
    public WordNet(String synsets, String hypernyms) {
        validateArg(synsets);
        validateArg(hypernyms);
    }
 
    /**
     * Returns all WordNet nouns.
     */
    public Iterable<String> nouns() {}
 
    /**
     * Returns true if the WORD is a WordNet noun.
     */
    public boolean isNoun(String word) {
        validateArg(word);
    }
 
    /**
     * Distance between nounA and nounB.
     */
    public int distance(String nounA, String nounB) {
        validateArg(nounA);
        validateArg(nounB);
        validateNoun(nounA);
        validateNoun(nounB);
    }
 
    /**
     * A synset (second field of synsets.txt) that is the common ancestor of
     * nounA and nounB.
     */
    public String sap(String nounA, String nounB) {
        validateArg(nounA);
        validateArg(nounB);
        validateNoun(nounA);
        validateNoun(nounB);
    }
 
    /**
     * Do unit testing of this class.
     */
    public static void main(String[] args) {}

    private static void validateArg(Object arg) {
        if (arg == null) {
            String error = "Argument cannot be null";
            throw new IllegalArgumentException(error);
        }
    }

    private void validateNoun(String noun) {
        if (!isNoun(noun)) {
            String error  = "Noun is not a WordNet noun";
            throw new IllegalArgumentException(error);
        }
    }
}
