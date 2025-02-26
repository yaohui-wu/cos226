public class WordNet {
    /**
     * Constructor takes the name of the two input files.
     */
    public WordNet(String synsets, String hypernyms) {}
 
    /**
     * Returns all WordNet nouns.
     */
    public Iterable<String> nouns() {}
 
    /**
     * Returns true if the word is a WordNet noun.
     */
    public boolean isNoun(String word) {}
 
    /**
     * Distance between nounA and nounB (defined below).
     */
    public int distance(String nounA, String nounB) {}
 
    /**
     * A synset (second field of synsets.txt) that is the common ancestor of
     * nounA and nounB.
     */
    public String sap(String nounA, String nounB) {}
 
    /**
     * Do unit testing of this class.
     */
    public static void main(String[] args) {}
}
