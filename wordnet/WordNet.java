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
    private final Map<String, List<Integer>> synsetsMap;
    private final SAP sap;

    /**
     * Constructor takes the name of the two input files.
     */
    public WordNet(String synsets, String hypernyms) {
        validateArgs(synsets, hypernyms);
        synsetsMap = new HashMap<>();
        readSynsets(synsets);
        Digraph graph = readHypernyms(hypernyms);
        sap = new SAP(graph);
    }

    private void readSynsets(String synsets) {
        In synsetsFile = new In(synsets);
        while (synsetsFile.hasNextLine()) {
            String line = synsetsFile.readLine();
            String[] fields = line.split(",");
            int id = Integer.parseInt(fields[0]);
            String synset = fields[1];
            String[] words = synset.split(" ");
            for (String word : words) {
                if (!synsetsMap.containsKey(word)) {
                    synsetsMap.put(word, new ArrayList<>());
                }
                synsetsMap.get(word).add(id);
            }
        }
    }

    private Digraph readHypernyms(String hypernyms) {
        Digraph graph = new Digraph(synsetsMap.size());
        In hypernymsFile = new In(hypernyms);
        while (hypernymsFile.hasNextLine()) {
            String line = hypernymsFile.readLine();
            String[] fields = line.split(",");
            int v = Integer.parseInt(fields[0]);
            for (int i = 1; i < fields.length; i += 1) {
                int w = Integer.parseInt(fields[i]);
                graph.addEdge(v, w);
            }
        }
        return graph;
    }
 
    /**
     * Returns all WordNet nouns.
     */
    public Iterable<String> nouns() {
        return synsetsMap.keySet();
    }
 
    /**
     * Returns true if the word is a WordNet noun.
     */
    public boolean isNoun(String word) {
        validateArgs(word);
        return synsetsMap.containsKey(word);
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
