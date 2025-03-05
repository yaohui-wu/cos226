import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * The WordNet digraph.
 * 
 * @author Yaohui Wu
 */
public final class WordNet {
    private final List<String> synsetsList;
    // Map from noun to list of synsets.
    private final Map<String, List<Integer>> synsetsMap;
    private final SAP sap; // Shortest ancestral path (SAP).

    /**
     * Constructor takes the name of the two input files.
     */
    public WordNet(String synsets, String hypernyms) {
        validateArgs(synsets, hypernyms);
        synsetsList = new ArrayList<>();
        synsetsMap = new HashMap<>();
        readSynsets(synsets);
        // WordNet is a rooted, directed acyclic graph (DAG).
        Digraph g = readHypernyms(hypernyms);
        sap = new SAP(g);
    }

    private void readSynsets(String synsets) {
        In synsetsFile = new In(synsets);
        while (synsetsFile.hasNextLine()) {
            String line = synsetsFile.readLine();
            String[] fields = line.split(",");
            int id = Integer.parseInt(fields[0]);
            String synset = fields[1];
            synsetsList.add(synset);
            String[] words = synset.split(" ");
            for (String word : words) {
                if (!synsetsMap.containsKey(word)) {
                    synsetsMap.put(word, new ArrayList<>());
                }
                synsetsMap.get(word).add(id);
            }
        }
        synsetsFile.close();
    }

    private Digraph readHypernyms(String hypernyms) {
        Digraph g = new Digraph(synsetsMap.size());
        In hypernymsFile = new In(hypernyms);
        while (hypernymsFile.hasNextLine()) {
            String line = hypernymsFile.readLine();
            String[] fields = line.split(",");
            int v = Integer.parseInt(fields[0]);
            for (int i = 1; i < fields.length; i++) {
                int w = Integer.parseInt(fields[i]);
                g.addEdge(v, w);
            }
        }
        hypernymsFile.close();
        validateDAG(g);
        return g;
    }
 
    /**
     * Returns all WordNet nouns.
     */
    public Iterable<String> nouns() {
        Set<String> nouns = new HashSet<>(synsetsMap.keySet());
        return nouns;
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
        validateArgs(nounA, nounB);
        validateNouns(nounA, nounB);
        List<Integer> v = synsetsMap.get(nounA);
        List<Integer> w = synsetsMap.get(nounB);
        return sap.length(v, w);
    }
 
    /**
     * A synset that is the common ancestor of nounA and nounB.
     */
    public String sap(String nounA, String nounB) {
        validateArgs(nounA, nounB);
        validateNouns(nounA, nounB);
        List<Integer> v = synsetsMap.get(nounA);
        List<Integer> w = synsetsMap.get(nounB);
        int ancestor = sap.ancestor(v, w);
        return synsetsList.get(ancestor);
    }
 
    /**
     * Do unit testing of this class.
     */
    public static void main(String[] args) {
        WordNet wordNet = new WordNet(args[0], args[1]);
        String nounA = args[2];
        String nounB = args[3];
        int distance = wordNet.distance(nounA, nounB);
        String sap = wordNet.sap(nounA, nounB);
        StdOut.println("distance = " + distance + ", sap = " + sap);
    }

    private void validateArgs(Object... args) {
        for (Object arg : args) {
            if (arg == null) {
                String error = "Argument cannot be null";
                throw new IllegalArgumentException(error);
            }
        }
    }

    private void validateDAG(Digraph g) {
        if (!hasOneRoot(g)) {
            String error = "Graph does not have only one root";
            throw new IllegalArgumentException(error);
        }
        if (hasCycle(g)) {
            String error = "Graph is not a directed acyclic graph";
            throw new IllegalArgumentException(error);
        }
    }

    private boolean hasOneRoot(Digraph g) {
        int count = 0;
        for (int v = 0; v < g.V(); v++) {
            if (isRoot(g, v)) {
                count += 1;
                if (count > 1) {
                    return false;
                }
            }
        }
        return count == 1;
    }

    private boolean isRoot(Digraph g, int v) {
        if (g.outdegree(v) == 0) {
            return g.indegree(v) > 0;
        }
        return false;
    }

    private boolean hasCycle(Digraph g) {
        DirectedCycle c = new DirectedCycle(g);
        return c.hasCycle();
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
