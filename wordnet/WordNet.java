import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

/**
 * The WordNet digraph, a rooted directed acyclic graph (DAG).
 * 
 * @author Yaohui Wu
 */
public final class WordNet {
    private final Map<Integer, String> synsetsMap;
    private final Digraph graph;
    
    /**
     * Constructor takes the name of the two input files.
     */
    public WordNet(String synsets, String hypernyms) {
        validateArgs(synsets, hypernyms);
        synsetsMap = new HashMap<>();
        readSynsets(synsets);
        int order = synsetsMap.size();
        graph = new Digraph(order);
        readHypernyms(hypernyms);
    }

    private void readSynsets(String synsets) {
        In synsetsFile = new In(synsets);
        while (!synsetsFile.hasNextLine()) {
            String line = synsetsFile.readLine();
            String[] fields = line.split(",");
            int id = Integer.parseInt(fields[0]);
            String synset = fields[1];
            synsetsMap.put(id, synset);
        }
    }

    private void readHypernyms(String hypernyms) {
        In hypernymsFile = new In(hypernyms);
        while (!hypernymsFile.hasNextLine()) {
            String line = hypernymsFile.readLine();
            String[] fields = line.split(",");
            int[] ids = new int[fields.length];
            for (int i = 0; i < ids.length; i += 1) {
                ids[i] = Integer.parseInt(fields[i]);
            }
            int v = ids[0];
            for (int i = 1; i < ids.length; i += 1) {
                graph.addEdge(v, ids[i]);
            }
        }
    }
 
    /**
     * Returns all WordNet nouns.
     */
    public Iterable<String> nouns() {
        List<String> nouns = new ArrayList<>();
        return nouns;
    }
 
    /**
     * Returns true if the word is a WordNet noun.
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
